/**
 * 
 */
package com.github.reinert.jjschema.v1;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.ManagedReference;

/**
 * @author guqk
 *
 */
public class DefaultBehaviorArraySchemaWrapper extends ArraySchemaWrapper {
    private SchemaWrapper itemsSchemaWrapper;

    /**
     * @param type
     * @param propertyType
     * @param managedReferences
     * @param relativeId
     * @param ignoreProperties
     */
    public DefaultBehaviorArraySchemaWrapper(Class<?> type, Type propertyType, Set<ManagedReference> managedReferences, String relativeId, boolean ignoreProperties) {
        super(type, propertyType, managedReferences, relativeId, ignoreProperties);

        if (propertyType != null) {
            if (!Collection.class.isAssignableFrom(type))
                throw new RuntimeException("Cannot instantiate a SchemaWrapper of a non Collection class with a Parametrized Type.");
            if (managedReferences == null)
                this.itemsSchemaWrapper = DefaultSchemaWrapperFactory.createWrapper(propertyType, null, null, false);
            else
                this.itemsSchemaWrapper = DefaultSchemaWrapperFactory.createWrapper(propertyType, managedReferences, relativeId, ignoreProperties);
            setItems(this.itemsSchemaWrapper.asJson());
        } else {
            this.itemsSchemaWrapper = null;
        }
    }

    @Override
    public SchemaWrapper getItemsSchema() {
        return this.itemsSchemaWrapper;
    }

    @Override
    protected void setItems(JsonNode itemsNode) {
        if(itemsSchemaWrapper == null) {
            
        } else {
            getNode().set("items", itemsNode);
        }
        
    }

}
