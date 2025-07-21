package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.impl.ForLoopNode;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.FunctionCallExecNode;
import com.zsb.blueprint.backend.core.runtime.params.LiteralValueSource;
import com.zsb.blueprint.backend.core.runtime.params.NodeOutputSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.defaults.functions.SysLib_Integer;

import java.lang.reflect.Method;

public class BlueprintTest2 {
    public static void main(String[] args) throws Exception {
        ExecutionContext ctx = new ExecutionContext();

        ForLoopNode forLoopNode1 = new ForLoopNode("forLoopNode1", "forLoopNode1");
        forLoopNode1.setRange(new LiteralValueSource<>(9), new LiteralValueSource<>(14));
        forLoopNode1.setStepExec("printString1");
        forLoopNode1.setCompletedExec("printString2");
        ctx.addExecNode(forLoopNode1);

        Method printMethod = SysLib_Integer.class.getMethod("Print", ParamWrapper.class);
        FunctionCallExecNode printString1 = new FunctionCallExecNode("printString1", "printString1", printMethod);
        // 从父级ForLoopNode获取Index
        printString1.setParamInput("Str", new NodeOutputSource<>("forLoopNode1", "Index"));
        printString1.setNextExec("forLoopNode1");
        ctx.addExecNode(printString1);

        FunctionCallExecNode printString2 = new FunctionCallExecNode("printString2", "printString2", printMethod);
        printString2.setParamInput("Str", new LiteralValueSource<>("结束了！！！"));
        printString2.setNextExec(null);
        ctx.addExecNode(printString2);


        ctx.run("forLoopNode1");
    }
}
