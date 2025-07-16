package com.zsb.blueprint.backend.core.resolver;

import com.zsb.blueprint.backend.core.config.BlueprintScanner;
import com.zsb.blueprint.backend.core.definition.ControlFlowDefinition;
import com.zsb.blueprint.backend.core.definition.ParamDefinition;
import com.zsb.blueprint.backend.core.definition.pin.DataPin;
import com.zsb.blueprint.backend.core.definition.pin.FlowPin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ControlFlowResolver {

    public static final Map<String, ControlFlowDefinition> CONTROL_FLOW_DEFINITION = new HashMap<>();

    public void processDefaultControlFlow() {
        // IfElse节点
        CONTROL_FLOW_DEFINITION.put("ControlFlow.IfElse", definitionIfElse());
        // While节点
        CONTROL_FLOW_DEFINITION.put("ControlFlow.While", definitionWhile());
        // ForLoop节点
        CONTROL_FLOW_DEFINITION.put("ControlFlow.ForLoop", definitionForLoop());
        // SwitchInteger节点
        CONTROL_FLOW_DEFINITION.put("ControlFlow.SwitchInteger", definitionSwitchInteger());
        // ExecuteFunction节点
        CONTROL_FLOW_DEFINITION.put("ControlFlow.ExecuteFunction", definitionExecuteFunction());
    }

    private ControlFlowDefinition definitionIfElse() {
        ControlFlowDefinition def = new ControlFlowDefinition();
        def.setName("IfElse");
        def.setQualifiedName("ControlFlow.IfElse");
        def.setDescription("If...Else...条件控制");

        def.getInputs().add(new FlowPin("Exec"));
        def.getOutputs().add(new FlowPin("True"));
        def.getOutputs().add(new FlowPin("False"));

        def.getDataInputs().add(
                new DataPin(
                        new ParamDefinition(
                                "Condition",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        return def;
    }

    private ControlFlowDefinition definitionWhile() {
        ControlFlowDefinition def = new ControlFlowDefinition();
        def.setName("While");
        def.setQualifiedName("ControlFlow.While");
        def.setDescription("While条件控制");

        def.getInputs().add(new FlowPin("Exec"));
        def.getOutputs().add(new FlowPin("LoopBody"));
        def.getOutputs().add(new FlowPin("Completed"));

        def.getDataInputs().add(
                new DataPin(
                        new ParamDefinition(
                                "Condition",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        return def;
    }

    private ControlFlowDefinition definitionForLoop() {
        ControlFlowDefinition def = new ControlFlowDefinition();
        def.setName("ForLoop");
        def.setQualifiedName("ControlFlow.ForLoop");
        def.setDescription("For...Loop...条件控制");

        def.getInputs().add(new FlowPin("Exec"));
        def.getOutputs().add(new FlowPin("Step"));
        def.getOutputs().add(new FlowPin("Completed"));

        def.getDataInputs().add(
                new DataPin(
                        new ParamDefinition(
                                "Condition",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        def.getDataOutputs().add(
                new DataPin(
                        new ParamDefinition(
                                "Index",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        return def;
    }

    private ControlFlowDefinition definitionSwitchInteger() {
        ControlFlowDefinition def = new ControlFlowDefinition();
        def.setName("Switch");
        def.setQualifiedName("ControlFlow.Switch");
        def.setSwitchCase(true);
        def.setDescription("Switch...条件控制");

        def.getInputs().add(new FlowPin("Exec"));
        def.getOutputs().add(new FlowPin("Completed"));

        def.getDataInputs().add(
                new DataPin(
                        new ParamDefinition(
                                "Switch",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        return def;
    }

    private ControlFlowDefinition definitionExecuteFunction() {
        ControlFlowDefinition def = new ControlFlowDefinition();
        def.setName("ExecuteFunction");
        def.setQualifiedName("ControlFlow.ExecuteFunction");
        def.setExecuteFunction(true);
        def.setDescription("函数执行");

        def.getInputs().add(new FlowPin("Exec"));
        def.getOutputs().add(new FlowPin("Exec"));
        return def;
    }
}
