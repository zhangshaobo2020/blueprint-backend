package com.zsb.blueprint.backend.core.definition;

import com.zsb.blueprint.backend.core.definition.pin.ParamPin;
import com.zsb.blueprint.backend.core.definition.pin.ExecPin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制流定义
 */
@Data
@NoArgsConstructor
public class ControlDefinition {

    private String name;                                    // 方法名，如 If、While
    private String qualifiedName;                           // 路径 + 方法名（如 ControlFlow.If）
    private String category = "CONTROL";                    // 分类
    private List<ExecPin> execPins = new ArrayList<>();     // 控制流引脚（如 Entry/True/False）
    private List<ParamPin> paramPins = new ArrayList<>();   // 数据流引脚（如 Condition）
    private String description;                             // 可选提示或说明
    private String displayName;                             // 展示的名称
    private boolean executable = false;                     // 是否是执行函数执行节点(关联函数定义)
    private FunctionDefinition functionDefinition;          // 关联函数定义
}
