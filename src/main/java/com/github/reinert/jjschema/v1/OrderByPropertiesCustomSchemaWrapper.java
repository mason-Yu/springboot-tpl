/**
 * 
 */
package com.github.reinert.jjschema.v1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.ManagedReference;

/**
 * @author guqk
 *
 */
public class OrderByPropertiesCustomSchemaWrapper extends CustomSchemaWrapper {
    private final Set<ManagedReference> managedReferences;

    public OrderByPropertiesCustomSchemaWrapper(Type type, boolean ignoreProperties) {
        this(type, new HashSet<ManagedReference>(), null, ignoreProperties);
    }

    public OrderByPropertiesCustomSchemaWrapper(Type type, Set<ManagedReference> managedReferences, boolean ignoreProperties) {
        this(type, managedReferences, null, ignoreProperties);
    }

    public OrderByPropertiesCustomSchemaWrapper(Type type, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        super(type, managedReferences, relativeId, ignoreProperties);
        this.managedReferences = managedReferences;
    }

    @Override
    protected void processProperties() {
        HashMap<Method, Field> properties = findProperties();
        for (Entry<Method, Field> prop : properties.entrySet()) {
            PropertyWrapper propertyWrapper = new DefaultBehaviorPropertyWrapper(this, this.managedReferences, prop.getKey(), prop.getValue());
            if (!propertyWrapper.isEmptyWrapper())
                addProperty(propertyWrapper);
        }
    }
    @Override
    protected void processAttributes(ObjectNode node, Type type) {
        final Attributes attributes = getJavaType().getAnnotation(Attributes.class);
        if (attributes != null) {
            com.github.reinert.jjschema.DefaultJJSchemaUtil.processCommonAttributes(getType(), node, attributes);
            if (attributes.required()) {
                setRequired(true);
            }
            if (!attributes.additionalProperties()) {
                node.put("additionalProperties", false);
            }
        }
    }

    private HashMap<Method, Field> findProperties() {
        Field[] fields = new Field[0];
        Class<?> javaType = getJavaType();
        while(javaType.getSuperclass() != null) {
            fields = concatFieldArrays(fields, javaType.getDeclaredFields());
            javaType = javaType.getSuperclass();
        }

        Method[] methods = getJavaType().getMethods();


        LinkedHashMap<Method, Field> props = new LinkedHashMap<Method, Field>();
        for(Field field : fields) {
            Class<?> declaringClass = field.getDeclaringClass();
            if (declaringClass.equals(Object.class)
                    || Collection.class.isAssignableFrom(declaringClass)) {
                continue;
            }

            for (Method method : methods) {
                if (isGetter(method)) {
                    boolean hasMethod = false;
                    String name = getNameFromGetter(method);
                    if (field.getName().equalsIgnoreCase(name)) {
                        props.put(method, field);
                        hasMethod = true;
                        break;
                    }
                    if (!hasMethod) {
                        //props.put(method, null);
                    }
                }
            }
        }
        return props;
    }

    private Field[] concatFieldArrays(Field[] first, Field[] second) {
        Field[] result = new Field[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private boolean isGetter(final Method method) {
        return method.getName().startsWith("get") || method.getName().startsWith("is");
    }

    private String getNameFromGetter(final Method getter) {
        String[] getterPrefixes = {"get", "is"};
        String methodName = getter.getName();
        String fieldName = null;
        for (String prefix : getterPrefixes) {
            if (methodName.startsWith(prefix)) {
                fieldName = methodName.substring(prefix.length());
            }
        }

        if (fieldName == null || "".equals(fieldName)) {
            return null;
        }

        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        return fieldName;
    }
}
