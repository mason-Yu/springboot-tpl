/**
 * 
 */
package com.hanhan.store.util;

import java.util.concurrent.ConcurrentHashMap;

import com.hanhan.store.model.IsEnum;
import com.hanhan.store.model.SsEnum;

public class EnumsUtil {
    @SuppressWarnings("rawtypes")
    private static ConcurrentHashMap<Class, IsEnum[]> isMap = new ConcurrentHashMap<Class, IsEnum[]>();

    @SuppressWarnings("rawtypes")
    private static ConcurrentHashMap<Class, SsEnum[]> ssMap = new ConcurrentHashMap<Class, SsEnum[]>();

    public static <T extends IsEnum> IsEnum valueOf(Class<T> clazz, Integer code) {
        // 得到values
        IsEnum[] enums = values(clazz);

        if (enums == null || enums.length == 0) {
            return null;
        }
        if(code != null) {
            for (IsEnum t : enums) {
                if (t.getCode() == code.intValue()) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T extends IsEnum> IsEnum[] values(Class<T> clazz) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        }
        // 得到values
        IsEnum[] isEnums = isMap.get(clazz);
        if (isEnums == null) {
            isEnums = clazz.getEnumConstants();
            isMap.put(clazz, isEnums);
        } else {
            // ts = (SsEnum[]) obj;
        }
        return isEnums;
    }
    
    /**
     * 获取枚举
     * 
     * @param clazz
     * @param code
     * @return
     */
    public static <T extends SsEnum> SsEnum valueOfIgnoreCase(Class<T> clazz, String code) {
        return valueOf(clazz, code, true);
    }

    /**
     * 获取枚举,区分大小写
     * 
     * @param clazz
     * @param code
     * @return
     */
    public static <T extends SsEnum> SsEnum valueOf(Class<T> clazz, String code) {
        return valueOf(clazz, code, false);
    }

    /**
     * 获取枚举,区分大小写
     * 
     * @param clazz
     * @param code
     * @param ignoreCase
     * @return
     */
    private static <T extends SsEnum> SsEnum valueOf(Class<T> clazz, String code, boolean ignoreCase) {
        // 得到values
        SsEnum[] enums = ssEnumValues(clazz);

        if (enums == null || enums.length == 0) {
            return null;
        }

        for (SsEnum t : enums) {
            if (ignoreCase && t.getCode().equalsIgnoreCase(code)) {
                return t;
            } else if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 获取枚举集合
     * 
     * @param clazz
     * @return
     */
    public static <T extends SsEnum> SsEnum[] ssEnumValues(Class<T> clazz) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        }
        // 得到values
        SsEnum[] ssEnums = ssMap.get(clazz);
        if (ssEnums == null) {
            ssEnums = clazz.getEnumConstants();
            ssMap.put(clazz, ssEnums);
        } else {
            // ts = (SsEnum[]) obj;
        }
        return ssEnums;
    }
}
