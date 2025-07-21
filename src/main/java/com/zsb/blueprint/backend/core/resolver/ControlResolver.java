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
        // BeginPlay节点
        map.put("CONTROL.BeginPlay", definitionBeginPlay());
        // EndPlay节点
        map.put("CONTROL.EndPlay", definitionEndPlay());
        // IfElse节点
        map.put("CONTROL.IfElse", definitionIfElse());
        // While节点
        map.put("CONTROL.While", definitionWhile());
        // ForLoop节点
        map.put("CONTROL.ForLoop", definitionForLoop());
        // SwitchInteger节点
        map.put("CONTROL.SwitchInteger", definitionSwitchInteger());
        return map;
    }

    private static ControlDefinition definitionBeginPlay() {
        ControlDefinition def = new ControlDefinition();
        def.setName("BeginPlay");
        def.setDisplayName("开始运行");
        def.setQualifiedName("CONTROL.BeginPlay");
        def.setDescription("程序的起点");

        def.getExecPins().add(new ExecPin("Exec", false));
        return def;
    }

    private static ControlDefinition definitionEndPlay() {
        ControlDefinition def = new ControlDefinition();
        def.setName("EndPlay");
        def.setDisplayName("结束运行");
        def.setQualifiedName("CONTROL.EndPlay");
        def.setDescription("程序的终点");

        def.getExecPins().add(new ExecPin("Exec", true));
        return def;
    }

    private static ControlDefinition definitionIfElse() {
        ControlDefinition def = new ControlDefinition();
        def.setName("IfElse");
        def.setDisplayName("Branch分支");
        def.setQualifiedName("CONTROL.IfElse");
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
        def.setQualifiedName("CONTROL.While");
        def.setDescription("While(...){...}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("Break", true));
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
        def.setQualifiedName("CONTROL.ForLoop");
        def.setDescription("For(...,...,...){...}");

        def.getExecPins().add(new ExecPin("Exec", true));
        def.getExecPins().add(new ExecPin("Break", true));
        def.getExecPins().add(new ExecPin("Step", false));
        def.getExecPins().add(new ExecPin("Completed", false));

        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "From",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "To",
                                true,
                                BlueprintScanner.PRIMITIVE_TYPE_DEFINITION.get("java.lang.Integer")
                        )
                )
        );
        def.getParamPins().add(
                new ParamPin(
                        new ParamDefinition(
                                "Index",
                                false,
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
        def.setQualifiedName("CONTROL.SwitchInteger");
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
}
