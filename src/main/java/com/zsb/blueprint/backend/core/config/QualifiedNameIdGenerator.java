package com.zsb.blueprint.backend.core.config;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;

/**
 * 为了解决JSON循环依赖的问题而引入
 * 目前方案还不完善,因此暂时不使用
 * //@JsonIdentityInfo(
 * //        generator = QualifiedNameIdGenerator.class,
 * //        property = "@ref",
 * //        scope = TypeDefinition.class
 * //)
 */
@Deprecated
public class QualifiedNameIdGenerator extends ObjectIdGenerator<String> {
    @Override
    public Class<?> getScope() {
        return Object.class;
    }

    @Override
    public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
        return objectIdGenerator.getClass() == this.getClass();
    }

    @Override
    public ObjectIdGenerator<String> forScope(Class<?> aClass) {
        return this;
    }

    @Override
    public ObjectIdGenerator<String> newForSerialization(Object o) {
        return this;
    }

    @Override
    public IdKey key(Object o) {
        return new IdKey(this.getClass(), this.getScope(), o);
    }

    @Override
    public String generateId(Object o) {
        if (o instanceof TypeDefinition) {
            TypeDefinition typeDefinition = (TypeDefinition) o;
            if (typeDefinition.isBlueprintType()) {
                return typeDefinition.getQualifiedName();
            }
        }
        return null;
    }
}
