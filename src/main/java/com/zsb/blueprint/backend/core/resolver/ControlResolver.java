package com.zsb.blueprint.backend.core.resolver;

import com.zsb.blueprint.backend.core.config.BlueprintScanner;
import com.zsb.blueprint.backend.core.definition.ControlDefinition;
import com.zsb.blueprint.backend.core.definition.ParamDefinition;
import com.zsb.blueprint.backend.core.definition.pin.ExecPin;
import com.zsb.blueprint.backend.core.definition.pin.ParamPin;

import java.util.HashMap;
import java.util.Map;

public class ControlResolver {

    public static Map<String, ControlDefinition> processDefaultControl() {
        Map<String, ControlDefinition> map = new HashMap<>();
        // IfElse节点
        map.put("Control.IfElse", definitionIfElse());
        // While节点
        map.put("Control.While", definitionWhile());
        // ForLoop节点
        map.put("Control.ForLoop", definitionForLoop());
        // SwitchInteger节点
        map.put("Control.SwitchInteger", definitionSwitchInteger());
        // ExecuteFunction节点
        map.put("Control.ExecuteFunction", definitionExecuteFunction());
        return map;
    }

    private static ControlDefinition definitionIfElse() {
        ControlDefinition def = new ControlDefinition();
        def.setName("IfElse");
        def.setDisplayName("Branch分支");
        def.setQualifiedName("Control.IfElse");
        def.setDescription("If{...}Else{...}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("True", false));
        def.getExecPins().add(new ExecPin("False", false));

        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Cond",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        return def;
    }

    private static ControlDefinition definitionWhile() {
        ControlDefinition def = new ControlDefinition();
        def.setName("While");
        def.setDisplayName("While循环");
        def.setQualifiedName("Control.While");
        def.setDescription("While(...){...}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("LoopBody", false));
        def.getExecPins().add(new ExecPin("Completed", false));

        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Cond",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        return def;
    }

    private static ControlDefinition definitionForLoop() {
        ControlDefinition def = new ControlDefinition();
        def.setName("ForLoop");
        def.setDisplayName("For循环");
        def.setQualifiedName("Control.ForLoop");
        def.setDescription("For(...,...,...){...}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("Step", false));
        def.getExecPins().add(new ExecPin("Completed", false));

        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Cond",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Boolean")
                        )
                )
        );
        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Index",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        return def;
    }

    private static ControlDefinition definitionSwitchInteger() {
        ControlDefinition def = new ControlDefinition();
        def.setName("SwitchInteger");
        def.setDisplayName("SwitchInteger选择");
        def.setQualifiedName("Control.SwitchInteger");
        def.setDescription("SwitchInteger(...){{...};{...};}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("Completed", false));

        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Cond",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        return def;
    }

    private static ControlDefinition definitionExecuteFunction() {
        ControlDefinition def = new ControlDefinition();
        def.setName("ExecuteFunction");
        def.setDisplayName("执行函数");
        def.setQualifiedName("Control.ExecuteFunction");
        def.setDescription("函数执行");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("Exec", false));

        def.setExecutable(true);
        return def;
    }
}
