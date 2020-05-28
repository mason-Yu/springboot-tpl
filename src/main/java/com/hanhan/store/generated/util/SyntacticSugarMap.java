/**
 * 
 */
package com.hanhan.store.generated.util;

import java.util.HashMap;

/**
 * @author JerryXia
 *
 */
public class SyntacticSugarMap {
    private HashMap<String, Object> map;

    private SyntacticSugarMap(int capacity) {
        if (this.map == null) {
            this.map = new HashMap<String, Object>(capacity);
        }
    }

    public SyntacticSugarMap put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public HashMap<String, Object> instance() {
        return this.map;
    }

    public static SyntacticSugarMap init() {
        return init(16);
    }

    public static SyntacticSugarMap init(int capacity) {
        SyntacticSugarMap o = new SyntacticSugarMap(capacity);
        return o;
    }
}
