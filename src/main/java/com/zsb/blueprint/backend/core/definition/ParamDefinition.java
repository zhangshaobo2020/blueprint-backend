package com.zsb.blueprint.backend.core.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamDefinition {

    private String name;               // 形参名称（例如 Num1）
    private boolean input;             // true 表示输入，false 表示输出
    private TypeDefinition type;       // 参数类型结构
}
