package com.zsb.blueprint.backend.core.definition;

import com.zsb.blueprint.backend.core.definition.pin.DataPin;
import com.zsb.blueprint.backend.core.definition.pin.FlowPin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制流定义
 */
@Data
@NoArgsConstructor
public class ControlFlowDefinition {

    private String name;                                    // 方法名，如 If、While
    private String qualifiedName;                           // 路径 + 方法名（如 ControlFlow.If）
    private String category = "ControlFlow";                // 分类

    private List<FlowPin> inputs = new ArrayList<>();       // 控制流输入引脚（如 Entry）
    private List<FlowPin> outputs = new ArrayList<>();      // 控制流输出引脚（如 True/False）

    private List<DataPin> dataInputs = new ArrayList<>();   // 输入数据（可为空）
    private List<DataPin> dataOutputs = new ArrayList<>();  // 输出数据（可为空）
    private String description;                             // 可选提示或说明

    private boolean switchCase = false;                     // 对于Switch节点，动态输出节点

    private boolean executeFunction = false;                // 是否是执行函数执行节点(关联函数定义)
    private FunctionDefinition functionDefinition;          // 关联函数定义
}
