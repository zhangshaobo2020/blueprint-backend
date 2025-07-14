package com.zsb.blueprint.backend.core.definition;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类型定义
 */
@Data
@NoArgsConstructor
public class TypeDefinition implements Serializable {

    private String name;                            // 简短类型名，例如 Integer、String、MyType
    private String qualifiedName;                   // 完整类名，例如 java.lang.Integer、java.util.List、com.xxx.MyType
    private boolean primitive;                      // 是否是原始/基础类型，例如 int, String, float
    private boolean list;                           // 是否是List泛型包装类型
    private boolean map;                            // 是否是Map泛型包装类型
    private List<TypeDefinition> generics;          // Map 或 List 的子类型
    private boolean blueprintType;                  // 是否是由 @BlueprintType 标记的自定义类型
    private Map<String, TypeDefinition> fields;     // 由 @BlueprintType 标记的类型下的字段
}
