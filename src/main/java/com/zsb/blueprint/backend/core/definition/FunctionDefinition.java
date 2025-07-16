package com.zsb.blueprint.backend.core.definition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 函数定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDefinition {

    private String name;                          // 方法名，如 Test
    private String qualifiedName;                 // 路径 + 方法名（如 Math.Test）
    private String category;                      // 分类（如 Math|Test）
    private List<ParamDefinition> params;         // 所有输入输出参数
    private String description;                   // 可选提示或说明
    private String displayName;                   // 展示的名称
    private boolean executable;                   // 是否是可执行的控制流节点
    private boolean latent;                       // 是否是延迟执行控制流节点
}
