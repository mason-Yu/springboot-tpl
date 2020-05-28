/**
 * 
 */
package com.hanhan.store.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.mapper.JsonMapper;

import com.hanhan.store.model.dto.RequestContext;

/**
 * @author JerryXia
 *
 */
public class ServiceProxyUtil {
    private static final ConcurrentHashMap<String, String>   SERVICE_NAME_UPPER_DICT                   = new ConcurrentHashMap<String, String>();
    private static final ConcurrentHashMap<String, String>   SERVICE_NAME_LOWER_DICT                   = new ConcurrentHashMap<String, String>();
    private static final ConcurrentHashMap<String, Class<?>> SERVICE_NAME_CLASS_DICT                   = new ConcurrentHashMap<String, Class<?>>();
    private static final Class<?>                            NULL_RESULT_CLASS                         = NullResultClass.class;
    private static final ConcurrentHashMap<String, Method>   SERVICE_PROVIDER_METHOD_DICT              = new ConcurrentHashMap<String, Method>();
    private static final Method                              NULL_RESULT_METHOD                        = BeanUtils.findMethod(NULL_RESULT_CLASS, "nullResult");
    private static final ConcurrentHashMap<String, Class<?>> SERVICE_PROVIDER_METHOD_PARAMS_CLASS_DICT = new ConcurrentHashMap<String, Class<?>>();

    public static String upperCaseFirstName(String serviceName) {
        if (StringUtils.isEmpty(serviceName)) {
            return "";
        }
        String result = SERVICE_NAME_UPPER_DICT.get(serviceName);
        if (result == null) {
            char[] values = serviceName.toCharArray();
            char firstChar = values[0];
            values[0] = Character.toUpperCase(firstChar);
            result = new String(values);
            SERVICE_NAME_UPPER_DICT.put(serviceName, result);
        }
        return result;
    }

    public static String lowerCaseFirstName(String serviceName) {
        if (StringUtils.isEmpty(serviceName)) {
            return "";
        }
        String result = SERVICE_NAME_LOWER_DICT.get(serviceName);
        if (result == null) {
            char[] values = serviceName.toCharArray();
            char firstChar = values[0];
            values[0] = Character.toLowerCase(firstChar);
            result = new String(values);
            SERVICE_NAME_LOWER_DICT.put(serviceName, result);
        }
        return result;
    }

    public static Class<?> retriveClass(String className) {
        Class<?> instanceClass = SERVICE_NAME_CLASS_DICT.get(className);
        if (instanceClass == null) {
            try {
                instanceClass = ClassUtils.forName(className, null);
            } catch (ClassNotFoundException e) {

            } catch (LinkageError e) {

            } finally {
                if (instanceClass == null) {
                    instanceClass = NULL_RESULT_CLASS;
                }
                SERVICE_NAME_CLASS_DICT.put(className, instanceClass);
            }
        }
        if (NULL_RESULT_CLASS == instanceClass) {
            return null;
        } else {
            return instanceClass;
        }
    }

    public static Method retriveMethod(String sp, Class<?> clazz, String methodName, Class<?> paramType) {
        Method method = SERVICE_PROVIDER_METHOD_DICT.get(sp);
        if (method == null) {
            if (paramType == null) {
                method = BeanUtils.findMethod(clazz, methodName);
            } else {
                method = BeanUtils.findMethod(clazz, methodName, paramType);
            }
            if (method == null) {
                method = NULL_RESULT_METHOD;
            }
            SERVICE_PROVIDER_METHOD_DICT.put(sp, method);
        }
        if (NULL_RESULT_METHOD == method) {
            return null;
        } else {
            return method;
        }
    }

    public static Class<?> tryRetriveMethodParamClass(String inputClassName) {
        Class<?> instanceClass = SERVICE_PROVIDER_METHOD_PARAMS_CLASS_DICT.get(inputClassName);
        if (instanceClass == null) {
            try {
                instanceClass = ClassUtils.forName(inputClassName, null);
            } catch (ClassNotFoundException e) {

            } catch (LinkageError e) {

            } finally {
                if (instanceClass == null) {
                    instanceClass = NULL_RESULT_CLASS;
                }
                SERVICE_NAME_CLASS_DICT.put(inputClassName, instanceClass);
            }
        }
        if (NULL_RESULT_CLASS == instanceClass) {
            return null;
        } else {
            return instanceClass;
        }
    }

    public static RequestContext retriveRequestCtx(HttpServletRequest httpServletReqeust) {
        String ip4 = httpServletReqeust.getRemoteAddr();
        String method = httpServletReqeust.getMethod();
        String contentType = httpServletReqeust.getContentType();
        String userAgent = tryGetSingleHeaderValue(httpServletReqeust, HttpHeaders.USER_AGENT);
        String accept = tryGetSingleHeaderValue(httpServletReqeust, HttpHeaders.ACCEPT);
        String acceptLanguage = tryGetSingleHeaderValue(httpServletReqeust, HttpHeaders.ACCEPT_LANGUAGE);
        RequestContext requestCtx = new RequestContext(ip4, method, contentType, userAgent, accept, acceptLanguage);
        return requestCtx;
    }

    public static boolean isFormPost(HttpServletRequest httpServletReqeust) {
        String contentType = httpServletReqeust.getContentType();
        String method = httpServletReqeust.getMethod();
        return (contentType != null && contentType.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE) && HttpMethod.POST.matches(method));
    }

    public static boolean isJsonPost(HttpServletRequest httpServletReqeust) {
        String contentType = httpServletReqeust.getContentType();
        String method = httpServletReqeust.getMethod();
        return (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE) && HttpMethod.POST.matches(method));
    }

    public static Object convertInputStream(HttpServletRequest httpServletReqeust, Class<?> paramType) {
        // convert http request inputSteam
        Object serviceInputDto = null;
        try {
            serviceInputDto = JsonMapper.INSTANCE.getMapper().readValue(httpServletReqeust.getInputStream(), paramType);
        } catch (IOException e) {
            // log.warn("parse json string error:", e);
        }
        return serviceInputDto;
    }

    public static Object convertParameterMap(HttpServletRequest httpServletRequest, Class<?> paramType) {
        HashMap<String, String> source = new HashMap<String, String>();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        Iterator<Entry<String, String[]>> parameterEntryIterator = parameterMap.entrySet().iterator();
        while (parameterEntryIterator.hasNext()) {
            Entry<String, String[]> entry = parameterEntryIterator.next();
            source.put(entry.getKey(), entry.getValue() != null && entry.getValue().length > 0 ? entry.getValue()[0] : "");
        }
        Object serviceInputDto = BeanMapper.map(source, paramType);
        return serviceInputDto;
    }

    public static String tryGetSingleHeaderValue(HttpServletRequest httpServletReqeust, String name) {
        String headerValue = null;
        Enumeration<String> enumeration = httpServletReqeust.getHeaders(name);
        while (enumeration.hasMoreElements()) {
            headerValue = enumeration.nextElement();
            if (StringUtils.hasLength(headerValue)) {
                break;
            }
        }
        return headerValue;
    }

    private static class NullResultClass {
        @SuppressWarnings("unused")
        public void nullResult() {

        }
    }
}
