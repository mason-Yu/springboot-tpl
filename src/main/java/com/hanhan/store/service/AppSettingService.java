package com.hanhan.store.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.jerryxia.devutil.ObjectId;
import com.google.common.io.Files;
import com.vip.vjtools.vjkit.io.FilePathUtil;
import com.vip.vjtools.vjkit.io.FileUtil;

import com.hanhan.store.generated.Consts;
import com.hanhan.store.generated.RuntimeVars;
import com.hanhan.store.generated.mapper.AppSettingClassMapper;
import com.hanhan.store.generated.mapper.AppSettingMapper;
import com.hanhan.store.generated.util.GeneralUtil;
import com.hanhan.store.generated.util.ObjectValCheck;
import com.hanhan.store.model.po.AppSetting;
import com.hanhan.store.model.po.AppSettingClass;
import com.hanhan.store.model.po.AppSettingClassLoadType;
import com.hanhan.store.model.po.AppSettingExample;

/**
 * @author Administrator
 *
 */
@Service
public class AppSettingService {
    private static final Logger log                = LoggerFactory.getLogger(AppSettingService.class);
    private static final String DEFAULT_ENV        = "";
    private static final String DEFAULT_VALUE_TYPE = "string";

    @Autowired
    private AppSettingClassMapper appSettingClassMapper;
    @Autowired
    private AppSettingMapper      appSettingMapper;

    private String         extraClassPath;
    private URLClassLoader extraClassLoader;

    @PostConstruct
    public void init() throws IOException {
        if (log.isInfoEnabled()) {
            log.info("activeProfiles: {}, defaultProfiles:{}", RuntimeVars.env.getActiveProfiles(), RuntimeVars.env.getDefaultProfiles());
        }
        this.extraClassPath = FilePathUtil.concat(System.getProperty("user.dir"), "target", "classes");
        @SuppressWarnings("deprecation")
        URL[] urls = { new File(this.extraClassPath).toURL() };
        extraClassLoader = new URLClassLoader(urls);
    }

    @Cacheable(cacheNames = "appSettings")
    public String get(String key) {
        String activeProfile = RuntimeVars.env.getActiveProfiles()[0];

        AppSettingExample example = new AppSettingExample();
        example.createCriteria().andValidEqualTo(Boolean.TRUE).andKeyEqualTo(key);
        List<AppSetting> list = appSettingMapper.selectByExample(example);

        String defaultValue = null;
        String profileValue = null;
        for (AppSetting item : list) {
            if ("".equals(item.getEnv())) {
                defaultValue = item.getValue();
            }
            if (activeProfile.equals(item.getEnv())) {
                profileValue = item.getValue();
            }
        }
        return profileValue == null ? defaultValue : profileValue;
    }

    @CacheEvict(cacheNames = "appSettings", key = "#key")
    public void set(String key, String value) {
        String activeProfile = RuntimeVars.env.getActiveProfiles()[0];

        AppSettingExample example = new AppSettingExample();
        example.createCriteria().andValidEqualTo(Boolean.TRUE).andKeyEqualTo(key);
        List<AppSetting> list = appSettingMapper.selectByExample(example);

        AppSetting defaultItem = null;
        AppSetting profileItem = null;
        for (AppSetting item : list) {
            if (DEFAULT_ENV.equals(item.getEnv())) {
                defaultItem = item;
            }
            if (activeProfile.equals(item.getEnv())) {
                profileItem = item;
            }
        }
        if (profileItem == null) {
            if (defaultItem == null) {
                AppSetting record = new AppSetting();
                record.setId(ObjectId.get().toString());
                record.setEnv(DEFAULT_ENV);
                record.setKey(key);
                record.setValueType(DEFAULT_VALUE_TYPE);
                record.setValue(value);
                record.setMark("");
                record.setValid(Boolean.TRUE);
                int rowEffects = appSettingMapper.insertSelective(record);
                if (rowEffects < 1) {
                    log.warn("appSettingMapper.insertSelective fail, key:{}, value:{}", key, value);
                }
            } else {
                defaultItem.setValue(value);
                appSettingMapper.updateByPrimaryKeySelective(defaultItem);
            }
        } else {
            profileItem.setValue(value);
            appSettingMapper.updateByPrimaryKeySelective(profileItem);
        }
    }

    @CacheEvict(cacheNames = "appSettings", key = "#key")
    public boolean modifyBaseInfo(String id, String env, String key, String valueType, String mark, String classId, boolean valid) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(key)) {
            return false;
        }
        AppSetting oldEntity = appSettingMapper.selectByPrimaryKey(id);
        if (oldEntity != null) {
            AppSetting record = new AppSetting();
            record.setId(id);
            record.setEnv(env);
            record.setKey(key);
            record.setValueType(valueType);
            record.setMark(mark);
            record.setClassId(classId);
            record.setValid(valid);
            int rowEffects = appSettingMapper.updateByPrimaryKeySelective(record);
            return rowEffects >= 1;
        } else {
            return false;
        }
    }

    @CacheEvict(cacheNames = "appSettings", key = "#key")
    public boolean modifyValueInfo(String id, String key, String value) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(key)) {
            return false;
        }
        AppSetting oldEntity = appSettingMapper.selectByPrimaryKey(id);
        if (oldEntity != null) {
            AppSetting record = new AppSetting();
            record.setId(id);
            record.setValue(value);
            int rowEffects = appSettingMapper.updateByPrimaryKeySelective(record);
            return rowEffects >= 1;
        } else {
            return false;
        }
    }

    public AppSetting create(String key) {
        AppSetting record = new AppSetting();
        record.setId(ObjectId.get().toString());
        record.setEnv(DEFAULT_ENV);
        record.setKey(key);
        record.setValueType(DEFAULT_VALUE_TYPE);
        record.setValue("");
        record.setMark("");
        record.setClassId("");
        record.setValid(Boolean.TRUE);
        int rowEffects = appSettingMapper.insertSelective(record);
        return rowEffects >= 1 ? record : null;
    }

    public TreeMap<String, ArrayList<AppSetting>> queryAll() {
        AppSettingExample example = new AppSettingExample();
        example.createCriteria().andValidEqualTo(Boolean.TRUE);
        List<AppSetting> list = appSettingMapper.selectByExample(example);
        return group(list);
    }

    public AppSetting query(String id) {
        AppSetting entity = appSettingMapper.selectByPrimaryKey(id);
        if (entity != null && ObjectValCheck.isTrue(entity.getValid())) {
            return entity;
        }
        return null;
    }

    private TreeMap<String, ArrayList<AppSetting>> group(List<AppSetting> list) {
        String activeProfile = RuntimeVars.env.getActiveProfiles()[0];
        TreeMap<String, ArrayList<AppSetting>> groupMap = new TreeMap<String, ArrayList<AppSetting>>();

        for (AppSetting item : list) {
            String key = item.getKey();
            if (!groupMap.containsKey(key)) {
                groupMap.put(key, new ArrayList<AppSetting>());
                if (StringUtils.isEmpty(item.getMark())) {
                    item.setMark(item.getKey());
                }
            }
            if (DEFAULT_ENV.equals(item.getEnv()) || activeProfile.equals(item.getEnv())) {
                groupMap.get(key).add(item);
            }
        }
        return groupMap;
    }

    public AppSettingClass queryAppSettingClass(String id) {
        return appSettingClassMapper.selectByPrimaryKey(id);
    }

    public AppSettingClass modifyAppSettingClass(String classId, AppSettingClassLoadType loadType, String className, String contentMd5,
            String classFileName, byte[] classFileData, String jsonSchema) {
        if(StringUtils.isEmpty(classId)) {
            AppSettingClass record = null;
            int rowEffects = 0;
            switch(loadType) {
            case LOCAL:
                record = new AppSettingClass();
                record.setId(ObjectId.get().toString());
                record.setLoadType(loadType.getCode());
                record.setClassName(className);
                record.setContentMd5("");
                record.setClassFileName("");
                record.setClassFileData(new byte[0]);
                record.setJsonSchema("");
                rowEffects = appSettingClassMapper.insertSelective(record);
                break;
            case REMOTE:
                record = new AppSettingClass();
                record.setId(ObjectId.get().toString());
                record.setLoadType(loadType.getCode());
                record.setClassName(className);
                record.setContentMd5(contentMd5);
                record.setClassFileName(classFileName);
                record.setClassFileData(classFileData);
                record.setJsonSchema("");
                rowEffects = appSettingClassMapper.insertSelective(record);
                break;
            case JSON_SCHEMA:
                record = new AppSettingClass();
                record.setId(ObjectId.get().toString());
                record.setLoadType(loadType.getCode());
                record.setClassName("");
                record.setContentMd5("");
                record.setClassFileName("");
                record.setClassFileData(new byte[0]);
                record.setJsonSchema(jsonSchema);
                rowEffects = appSettingClassMapper.insertSelective(record);
                break;
            default:
                break;
            }
            return rowEffects > 0 ? record : null;
        } else {
            AppSettingClass record = appSettingClassMapper.selectByPrimaryKey(classId);
            int rowEffects = 0;
            switch(loadType) {
            case LOCAL:
                record.setLoadType(loadType.getCode());
                record.setClassName(className);
                rowEffects = appSettingClassMapper.updateByPrimaryKeySelective(record);
                break;
            case REMOTE:
                record.setLoadType(loadType.getCode());
                record.setClassName(className);
                record.setContentMd5(contentMd5);
                record.setClassFileName(classFileName);
                record.setClassFileData(classFileData);
                rowEffects = appSettingClassMapper.updateByPrimaryKeySelective(record);
                break;
            case JSON_SCHEMA:
                record.setLoadType(loadType.getCode());
                record.setJsonSchema(jsonSchema);
                rowEffects = appSettingClassMapper.updateByPrimaryKeySelective(record);
                break;
            default:
                break;
            }
            return rowEffects > 0 ? record : null;
        }
    }

    public Class<?> loadRemoteClassFiles(AppSettingClass appSettingClass) throws ClassNotFoundException, IOException {
        List<String> segs = Consts.POINT_SPLITTER.splitToList(appSettingClass.getClassName());
        String[] paths = new String[segs.size()];
        segs.toArray(paths);
        paths[paths.length - 1] = appSettingClass.getClassFileName();

        String classFilePath = FilePathUtil.concat(this.extraClassPath, paths);
        File classFile = new File(classFilePath);

        boolean needDownLoad = false;
        if (FileUtil.isFileExists(classFile)) {
            String currentFileMd5 = GeneralUtil.md5(classFile);
            if (currentFileMd5.equalsIgnoreCase(appSettingClass.getContentMd5())) {
                // ignore
            } else {
                needDownLoad = true;
            }
        } else {
            FileUtil.makesureParentDirExists(classFile);
            FileUtil.touch(classFile);
            needDownLoad = true;
        }

        if (needDownLoad) {
            Files.write(appSettingClass.getClassFileData(), classFile);
        }

        Class<?> clazz = extraClassLoader.loadClass(appSettingClass.getClassName());
        return clazz;
    }
}
