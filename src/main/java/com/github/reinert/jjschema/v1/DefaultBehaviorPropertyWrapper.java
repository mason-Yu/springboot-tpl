/**
 * 
 */
package com.github.reinert.jjschema.v1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

import com.github.reinert.jjschema.ManagedReference;

/**
 * @author guqk
 *
 */
public class DefaultBehaviorPropertyWrapper extends PropertyWrapper {
    public DefaultBehaviorPropertyWrapper(CustomSchemaWrapper ownerSchemaWrapper, Set<ManagedReference> managedReferences, Method method, Field field) {
        super(ownerSchemaWrapper, managedReferences, method, field);
    }

    @Override
    protected SchemaWrapper createWrapper(Set<ManagedReference> managedReferences, Type genericType, String relativeId1) {
        return DefaultSchemaWrapperFactory.createWrapper(genericType, managedReferences, relativeId1, shouldIgnoreProperties());
    }

    @Override
    protected SchemaWrapper createArrayWrapper(Set<ManagedReference> managedReferences, Type propertyType, Class<?> collectionType, String relativeId1) {
        return DefaultSchemaWrapperFactory.createArrayWrapper(collectionType, propertyType, managedReferences, relativeId1, shouldIgnoreProperties());
    }

}
