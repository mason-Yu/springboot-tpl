/**
 * 
 */
package com.github.reinert.jjschema.v1;

import java.lang.reflect.Type;
import java.util.Set;

import com.github.reinert.jjschema.ManagedReference;
import com.github.reinert.jjschema.SimpleTypeMappings;

/**
 * @author guqk
 *
 */
public class DefaultSchemaWrapperFactory {
    public static SchemaWrapper createWrapper(Type type, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        // If it is void then return null
        if (type == Void.class || type == void.class || type == null) {
            return new NullSchemaWrapper(type);
        }
        // If it is a simple type, then just put the type
        else if (SimpleTypeMappings.isSimpleType(type)) {
            return new DefaultBehaviorSimpleSchemaWrapper(type);
        }
        // If it is an Enum than process like enum
        else if (type instanceof Class && ((Class<?>) type).isEnum()) {
            return new EnumSchemaWrapper((Class<?>) type);
        }
        // If none of the above possibilities were true, then it is a custom object
        else {
            if (managedReferences != null)
                if (relativeId != null)
                    return new OrderByPropertiesCustomSchemaWrapper(type, managedReferences, relativeId, ignoreProperties);
                else
                    return new OrderByPropertiesCustomSchemaWrapper(type, managedReferences, ignoreProperties);
            else
                return new OrderByPropertiesCustomSchemaWrapper(type, ignoreProperties);
        }
    }
    
    
    public static SchemaWrapper createArrayWrapper(Class<?> type, Type propertyType, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        return new DefaultBehaviorArraySchemaWrapper(type, propertyType, managedReferences, relativeId, ignoreProperties);
    }
}
