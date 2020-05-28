package com.hanhan.store.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanhan.store.generated.RuntimeVars;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.reinert.jjschema.v1.DefaultSchemaWrapperFactory;
import com.vip.vjtools.vjkit.io.FilePathUtil;
import com.vip.vjtools.vjkit.io.FileUtil;
import com.vip.vjtools.vjkit.text.Charsets;

import com.hanhan.store.generated.util.GeneralUtil;
import com.hanhan.store.generated.util.SyntacticSugarMap;
import com.hanhan.store.generated.web.BaseWebMvcController;
import com.hanhan.store.model.po.AppSetting;
import com.hanhan.store.model.po.AppSettingClass;
import com.hanhan.store.model.po.AppSettingClassLoadType;
import com.hanhan.store.model.vo.GeneralRes;
import com.hanhan.store.service.AppSettingService;
import com.hanhan.store.util.EnumsUtil;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(path = "${management.context-path}")
public class AppSettingController extends BaseWebMvcController {
    private String serverContextPath;

    @Autowired
    private AppSettingService appSettingService;
    
    @PostConstruct
    public void init() {
        this.serverContextPath = RuntimeVars.env.getProperty("server.context-path", "");
    }

    @GetMapping("/appsetting/info")
    public ModelAndView info(@RequestParam(name = "id", required = false) String id, HttpServletRequest httpServletRequest) {
        val mv = new ModelAndView("management/appsetting/info");
        val viewData = tkd(mv, "应用设置", "", "");
        // org.springframework.web.servlet.support.RequestContext
        // freemarker.ext.servlet.HttpRequestHashModel;
        TreeMap<String, ArrayList<AppSetting>> appSettings = appSettingService.queryAll();
        if (appSettings.size() == 0) {
            appSettingService.create("");
            appSettings = appSettingService.queryAll();
        }
        viewData.put("appSettings", appSettings);

        String requestURI = httpServletRequest.getRequestURI();
        AppSetting appSetting = appSettingService.query(id);
        if (appSetting == null) {
            String firstKey = appSettings.keySet().iterator().next();
            String jumpurl = requestURI + "?id=" + appSettings.get(firstKey).get(0).getId();
            String msg = "无效的参数，正在转到第一个";
            return getMvRedirect(jumpurl, msg);
        }
        AppSettingClass appSettingClass = null;
        String jsonSchema = null;
        if ("json".equalsIgnoreCase(appSetting.getValueType())) {
            appSettingClass = appSettingService.queryAppSettingClass(appSetting.getClassId());
            if (appSettingClass != null) {
                jsonSchema = entitySchema(appSettingClass);
            }
        }
        if (appSettingClass == null) {
            appSettingClass = new AppSettingClass();
            appSettingClass.setLoadType(AppSettingClassLoadType.LOCAL.getCode());
        }
        viewData.put("appSetting", appSetting);
        viewData.put("appSettingClass", appSettingClass);
        viewData.put("jsonSchema", jsonSchema);
        viewData.put("urlPrefix", StringUtils.stripEnd(requestURI, "/info"));

        return mv;
    }

    @PostMapping("/appsetting/baseinfo/create")
    @ResponseBody
    public GeneralRes baseInfoCreate() {
        GeneralRes generalRes = new GeneralRes();
        AppSetting appSetting = appSettingService.create("");
        generalRes.setData(SyntacticSugarMap.init().put("appSetting", appSetting).instance());
        return generalRes;
    }

    @PostMapping("/appsetting/baseinfo/modify")
    @ResponseBody
    public GeneralRes baseInfoModify(String id, String env, String key, String valueType, String mark, String classId, Integer loadType, String className, String classFileName,
            String tempFilePath, String jsonSchema) throws IOException {
        GeneralRes generalRes = new GeneralRes();

        if (!"json".equals(valueType)) {
            boolean modifySuccess = appSettingService.modifyBaseInfo(id, env, key, valueType, mark, classId, true);
            if (modifySuccess) {

            } else {
                generalRes.failWithMsg("更新失败，请重试");
            }
        } else {
            AppSettingClassLoadType loadTypeEnum = (AppSettingClassLoadType) EnumsUtil.valueOf(AppSettingClassLoadType.class, loadType);
            switch (loadTypeEnum) {
            case LOCAL:
            case JSON_SCHEMA: {
                val appSettingClass = appSettingService.modifyAppSettingClass(classId, loadTypeEnum, className, null, classFileName, null, jsonSchema);
                if (appSettingClass == null) {
                    generalRes.failWithMsg("修改失败，请重试");
                    return generalRes;
                }
                classId = appSettingClass.getId();
            }
                break;
            case REMOTE: {
                AppSettingClass appSettingClass = null;
                boolean needUpdateAppSettingClass = false;
                if (StringUtils.isEmpty(classId)) {
                    needUpdateAppSettingClass = true;
                } else {
                    if (StringUtils.isEmpty(tempFilePath)) {
                        // 二次进入后，没有更改文件
                        appSettingClass = appSettingService.queryAppSettingClass(classId);
                        if (appSettingClass == null) {
                            generalRes.failWithMsg("无效的参数：classId");
                            return generalRes;
                        } else {
                            appSettingClass = appSettingService.modifyAppSettingClass(classId, loadTypeEnum, className, appSettingClass.getContentMd5(), classFileName, appSettingClass.getClassFileData(), jsonSchema);
                            if (appSettingClass == null) {
                                generalRes.failWithMsg("修改失败，请重试");
                                return generalRes;
                            }
                        }
                    } else {
                        // 二次进入后，更改了文件
                        needUpdateAppSettingClass = true;
                    }
                }
                if (needUpdateAppSettingClass) {
                    File tempFile = new File(tempFilePath);
                    byte[] classFileData = FileUtil.toByteArray(tempFile);
                    String contentMd5 = GeneralUtil.md5(tempFile);
                    appSettingClass = appSettingService.modifyAppSettingClass(classId, loadTypeEnum, className, contentMd5, classFileName, classFileData, null);
                    if (appSettingClass == null) {
                        generalRes.failWithMsg("修改失败，请重试");
                        return generalRes;
                    }
                    classId = appSettingClass.getId();
                }
            }
                break;
            }
            boolean modifySuccess = appSettingService.modifyBaseInfo(id, env, key, valueType, mark, classId, true);
            if (modifySuccess) {
                appSettingService.modifyValueInfo(id, key, "");
            } else {
                generalRes.failWithMsg("更新失败，请重试");
            }
        }
        return generalRes;
    }

    @PostMapping("/appsetting/classinfo/uploadfile")
    @ResponseBody
    public GeneralRes classInfoUploadFile(@RequestParam(name = "name") String name, @RequestParam(name = "uuid") String uuid, @RequestParam("file") MultipartFile file,
            HttpServletRequest httpServletRequest) throws IOException {
        GeneralRes generalRes = new GeneralRes();

        String chunkNameDir = FilePathUtil.concat(httpServletRequest.getServletContext().getRealPath("/"), uuid);
        FileUtil.makesureDirExists(chunkNameDir);
        String destFilePath = FilePathUtil.concat(chunkNameDir, name);
        File dest = new File(destFilePath);
        file.transferTo(dest);

        generalRes.setData(SyntacticSugarMap.init().put("name", name).put("filePath", destFilePath).instance());
        return generalRes;
    }

    @PostMapping("/appsetting/valueinfo/modify")
    @ResponseBody
    public GeneralRes valueInfoModify(@RequestBody AppSetting appSetting) {

        GeneralRes generalRes = new GeneralRes();
        String key = appSettingService.query(appSetting.getId()).getKey();
        boolean modifySuccess = appSettingService.modifyValueInfo(appSetting.getId(), key, appSetting.getValue());
        if (modifySuccess) {

        } else {
            generalRes.failWithMsg("更新失败，请重试");
        }
        return generalRes;
    }

    @GetMapping("/appsetting/schema")
    public void schema(String id, HttpServletResponse response) throws IOException {
        AppSettingClass appSettingClass = appSettingService.queryAppSettingClass(id);
        String jsonSchema = entitySchema(appSettingClass);
        response.setCharacterEncoding(Charsets.UTF_8_NAME);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().print(jsonSchema);
    }

    public String entitySchema(AppSettingClass appSettingClass) {
        AppSettingClassLoadType loadType = (AppSettingClassLoadType) EnumsUtil.valueOf(AppSettingClassLoadType.class, appSettingClass.getLoadType());
        Class<?> type = null;
        String jsonSchema = null;
        try {
            if (loadType != null) {
                switch (loadType) {
                case LOCAL: {
                    type = ClassUtils.forName(appSettingClass.getClassName(), null);
                    val schemaWrapper = DefaultSchemaWrapperFactory.createWrapper(type, null, null, false);
                    jsonSchema = schemaWrapper.putDollarSchema().asJson().toString();
                }
                    break;
                case REMOTE: {
                    type = appSettingService.loadRemoteClassFiles(appSettingClass);
                    val schemaWrapper = DefaultSchemaWrapperFactory.createWrapper(type, null, null, false);
                    jsonSchema = schemaWrapper.putDollarSchema().asJson().toString();
                }
                    break;
                case JSON_SCHEMA:
                    jsonSchema = appSettingClass.getJsonSchema();
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("class not found", e);
        } catch (LinkageError e) {
            log.error("link fail", e);
        } catch (IOException e) {
            log.error("io error", e);
        }
        return jsonSchema;
    }

    private void setPageGlobal(HashMap<String, Object> viewData) {
        viewData.put("page_global_serverContextPath", this.serverContextPath);
    }
}
