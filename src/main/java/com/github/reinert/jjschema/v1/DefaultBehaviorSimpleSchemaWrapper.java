/**
 * 
 */
package com.github.reinert.jjschema.v1;

import java.lang.reflect.Type;

import com.github.reinert.jjschema.DefaultJJSchemaUtil;

/**
 * @author guqk
 *
 */
public class DefaultBehaviorSimpleSchemaWrapper extends SimpleSchemaWrapper {
    public DefaultBehaviorSimpleSchemaWrapper(Type type) {
        super(type);
        DefaultJJSchemaUtil.setDefaultFormatByType(getType(), getNode());
    }
}
