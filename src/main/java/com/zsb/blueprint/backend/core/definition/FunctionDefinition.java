package com.zsb.blueprint.backend.core.definition;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 函数定义
 */
@Data
@NoArgsConstructor
public class FunctionDefinition {

    private String name;                          // 方法名，如 Test
    private String qualifiedName;                 // 路径 + 方法名（如 Math.Test）
    private String category;                      // 分类（如 Math|Test）
    private List<ParamDefinition> params;         // 所有输入输出参数
    private String description;                   // 可选提示或说明
}
