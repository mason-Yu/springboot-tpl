/**
 * 
 */
package com.hanhan.store.generated.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

/**
 * @author JerryXia
 *
 */
public class EmptyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return SimpleKey.EMPTY;
    }
}
