package com.zsb.blueprint.backend.core.resolver;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class TypeResolver {

    private static final Set<Class<?>> PRIMITIVE_CLASSES;

    static {
        PRIMITIVE_CLASSES = new HashSet<>();
        PRIMITIVE_CLASSES.add(java.lang.String.class);
        PRIMITIVE_CLASSES.add(java.lang.Integer.class);
        PRIMITIVE_CLASSES.add(java.lang.Long.class);
        PRIMITIVE_CLASSES.add(java.lang.Double.class);
        PRIMITIVE_CLASSES.add(java.lang.Float.class);
        PRIMITIVE_CLASSES.add(java.lang.Boolean.class);
        PRIMITIVE_CLASSES.add(java.lang.Character.class);
        PRIMITIVE_CLASSES.add(java.lang.Short.class);
        PRIMITIVE_CLASSES.add(java.lang.Byte.class);
        PRIMITIVE_CLASSES.add(java.math.BigDecimal.class);
        PRIMITIVE_CLASSES.add(java.util.Date.class);
        PRIMITIVE_CLASSES.add(java.time.LocalDate.class);
        PRIMITIVE_CLASSES.add(java.time.LocalTime.class);
        PRIMITIVE_CLASSES.add(java.time.LocalDateTime.class);
    }

    public static TypeDefinition resolveType(Type genericType) {
        TypeDefinition def = new TypeDefinition();
        def.setGenerics(new ArrayList<>());
        def.setFields(new HashMap<>());

        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            Class<?> rawType = (Class<?>) pt.getRawType();
            def.setName(rawType.getSimpleName());
            def.setQualifiedName(rawType.getName());

            // 标记为 List 或 Map
            def.setList(List.class.isAssignableFrom(rawType));
            def.setMap(Map.class.isAssignableFrom(rawType));
            def.setPrimitive(false);

            // 解析泛型参数
            for (Type arg : pt.getActualTypeArguments()) {
                def.getGenerics().add(resolveType(arg));
            }

            // 自定义 Blueprint 类型字段（结构体）
            if (rawType.isAnnotationPresent(BlueprintType.class)) {
                def.setBlueprintType(true);
                def.setFields(resolveFields(rawType));
            }

        } else if (genericType instanceof Class<?>) {
            Class<?> clazz = (Class<?>) genericType;
            def.setName(clazz.getSimpleName());
            def.setQualifiedName(clazz.getName());

            def.setPrimitive(isPrimitive(clazz));
            def.setList(false);
            def.setMap(false);

            if (clazz.isAnnotationPresent(BlueprintType.class)) {
                def.setBlueprintType(true);
                def.setFields(resolveFields(clazz));
            }
        }

        return def;
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || PRIMITIVE_CLASSES.contains(clazz);
    }

    private static Map<String, TypeDefinition> resolveFields(Class<?> clazz) {
        Map<String, TypeDefinition> fields = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            fields.put(field.getName(), resolveType(field.getGenericType()));
        }
        return fields;
    }
}
