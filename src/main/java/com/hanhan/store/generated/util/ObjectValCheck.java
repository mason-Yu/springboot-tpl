/**
 * 
 */
package com.hanhan.store.generated.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author guqk
 *
 */
public final class ObjectValCheck {

    public static boolean isTrue(Boolean obj) {
        return obj != null ? obj.booleanValue() : false;
    }
    
    public static boolean isFalse(Boolean obj) {
        return obj != null ? obj.booleanValue() == false : true;
    }


    public static boolean isZero(Integer obj) {
        return obj != null ? NumberUtils.INTEGER_ZERO.intValue() == obj.intValue() : false;
    }
    
    public static boolean isOne(Integer obj) {
        return obj != null ? NumberUtils.INTEGER_ONE.intValue() == obj.intValue() : false;
    }
}
