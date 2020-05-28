/**
 * 
 */
package com.hanhan.store.generated;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.cors.CorsConfiguration;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Splitter;
import com.vip.vjtools.vjkit.mapper.JsonMapper;

/**
 * @author JerryXia
 *
 */
public final class Consts {
    public static final Splitter VERTICAL_LINE_SPLITTER = Splitter.on('|');
    public static final Splitter UNDERLINE_SPLITTER     = Splitter.on('_');
    public static final Splitter SLASH_SPLITTER         = Splitter.on('/');
    public static final Splitter COMMA_SPLITTER         = Splitter.on(',');
    public static final Splitter POINT_SPLITTER         = Splitter.on('.');
    public static final Splitter QUES_SPLITTER          = Splitter.on("?");
    public static final Splitter AMPERSAND_SPLITTER     = Splitter.on("&");
    public static final Splitter EQUALS_SPLITTER        = Splitter.on("=");

    public static final JavaType ARRAYLIST_STRING_TYPE  = JsonMapper.INSTANCE.buildCollectionType(ArrayList.class, String.class);
    public static final JavaType ARRAYLIST_INTEGER_TYPE = JsonMapper.INSTANCE.buildCollectionType(ArrayList.class, Integer.class);

    public static final JavaType HASHMAP_STRING_STRING_TYPE        = JsonMapper.INSTANCE.buildMapType(HashMap.class, String.class, String.class);
    public static final JavaType HASHMAP_STRING_OBJECT_TYPE        = JsonMapper.INSTANCE.buildMapType(HashMap.class, String.class, Object.class);
    public static final JavaType HASHMAP_STRING_STRING_ARRAY_TYPE  = JsonMapper.INSTANCE.buildMapType(HashMap.class, String.class, String[].class);
    public static final JavaType HASHMAP_STRING_OBJECT_ARRAY_TYPE  = JsonMapper.INSTANCE.buildMapType(HashMap.class, String.class, Object[].class);
    public static final JavaType HASHMAP_INTEGER_STRING_TYPE       = JsonMapper.INSTANCE.buildMapType(HashMap.class, Integer.class, String.class);
    public static final JavaType HASHMAP_INTEGER_STRING_ARRAY_TYPE = JsonMapper.INSTANCE.buildMapType(HashMap.class, Integer.class, String[].class);

    // custom 
    public static final JavaType HASHMAP_STRING_CORSCONFIGURATION_TYPE = JsonMapper.INSTANCE.buildMapType(HashMap.class, String.class, CorsConfiguration.class);

}
