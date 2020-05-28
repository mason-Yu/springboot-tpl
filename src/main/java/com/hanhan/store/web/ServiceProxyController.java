package com.hanhan.store.web;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vip.vjtools.vjkit.base.ObjectUtil;

import com.hanhan.store.generated.Consts;
import com.hanhan.store.generated.RuntimeVars;
import com.hanhan.store.model.vo.GeneralRes;
import com.hanhan.store.util.ServiceProxyUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ServiceProxyController {
    private static final String PACKAGE_BASENAME                      = "com.hanhan.store";
    private static final String SERVICE_PACKAGE_NAME                  = PACKAGE_BASENAME + "." + "service";
    private static final String SERVICE_CLASS_SUFFIX                  = "Service";
    private static final String MODEL_PACKAGE_NAME                    = PACKAGE_BASENAME + "." + "model";
    private static final String MODEL_SERVICE_DTO_PACKAGE_NAME        = MODEL_PACKAGE_NAME + "." + "dto";
    private static final String MODEL_SERVICE_INPUT_DTO_CLASS_SUFFIX  = "Input";
    @SuppressWarnings("unused")
    private static final String MODEL_SERVICE_OUTPUT_DTO_CLASS_SUFFIX = "Output";

    @RequestMapping(value = "/apiproxy")
    public GeneralRes apiproxy(String sp, HttpServletRequest httpServletReqeust, HttpServletResponse httpServletResponse) {
        GeneralRes response = new GeneralRes();

        if (StringUtils.isEmpty(sp)) {
            response.failWithMsg("Invalid paramater: sp.");
            return response;
        }
        sp = StringUtils.trim(sp);
        List<String> serviceAndMethod = Consts.POINT_SPLITTER.splitToList(sp);
        if (serviceAndMethod.size() < 2) {
            response.failWithMsg(StringUtils.EMPTY);
            return response;
        }
        String serviceName = ServiceProxyUtil.lowerCaseFirstName(serviceAndMethod.get(0));
        String methodName = serviceAndMethod.get(1);
        String serviceBeanName = serviceName + SERVICE_CLASS_SUFFIX;
        String serviceClassName = SERVICE_PACKAGE_NAME + "." + serviceAndMethod.get(0) + SERVICE_CLASS_SUFFIX;
        String serviceInputClassName = MODEL_SERVICE_DTO_PACKAGE_NAME + "." + serviceName + "." + methodName + "." + ServiceProxyUtil.upperCaseFirstName(methodName)
                + MODEL_SERVICE_INPUT_DTO_CLASS_SUFFIX;
        if (log.isDebugEnabled()) {
            log.debug("sp:{}, service:{}, medthod:{}, serviceBean:{}, serviceClass:{}, serviceInputClass:{}", sp, serviceName, methodName, serviceBeanName, serviceClassName,
                    serviceInputClassName);
        }

        Class<?> instanceClass = ServiceProxyUtil.retriveClass(serviceClassName);
        if (instanceClass == null) {
            response.failWithMsg("There's no exists service.");
            return response;
        }
        Class<?> paramType = ServiceProxyUtil.tryRetriveMethodParamClass(serviceInputClassName);
        if (paramType != null) {
            if (log.isDebugEnabled()) {
                log.debug("find service method param {}", paramType.toString());
            }
        } else {
            log.info("has't find service method param class: {}", serviceInputClassName);
        }
        // every method name only one exists.
        Method instanceMethod = ServiceProxyUtil.retriveMethod(sp, instanceClass, methodName, paramType);
        if (instanceMethod == null) {
            response.failWithMsg("There's no exists service method.");
            return response;
        }

        try {
            Object instance = RuntimeVars.applicationContext.getBean(serviceBeanName, instanceClass);
            Object methodResult = null;
            if (paramType != null) {
                Object serviceInputDto = null;
                if (ServiceProxyUtil.isFormPost(httpServletReqeust)) {
                    serviceInputDto = ServiceProxyUtil.convertParameterMap(httpServletReqeust, paramType);
                } else if (ServiceProxyUtil.isJsonPost(httpServletReqeust)) {
                    serviceInputDto = ServiceProxyUtil.convertInputStream(httpServletReqeust, paramType);
                } else {
                    // ignore
                }
                methodResult = instanceMethod.invoke(instance, serviceInputDto);
            } else {
                methodResult = instanceMethod.invoke(instance);
            }
            // result handler
            if (!java.lang.Void.TYPE.equals(instanceMethod.getReturnType())) {
                if (log.isDebugEnabled()) {
                    log.debug("methodResult: {}", ObjectUtil.toPrettyString(methodResult));
                }
            }
            response.setData(methodResult);
        } catch (BeansException e) {
            log.error("getBean fail, beanName:{}", serviceBeanName, e);
            response.failWithMsg("There's no exists service bean finded.");
            return response;
        } catch (Exception e) {
            log.error("service method invoke error", e);
            response.failWithMsg(StringUtils.EMPTY);
            response.setException(e);
        }
        return response;
    }
}
