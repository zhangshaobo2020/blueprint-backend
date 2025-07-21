package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.impl.*;
import com.zsb.blueprint.backend.core.runtime.params.LiteralValueSource;
import com.zsb.blueprint.backend.core.runtime.params.NodeOutputSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.defaults.functions.SysLib_Integer;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BlueprintTest2 {
    public static void main(String[] args) throws Exception {
        ExecutionContext ctx = new ExecutionContext();

        ForLoopNode forLoopNode1 = new ForLoopNode("forLoopNode1", "forLoopNode1");
        forLoopNode1.setRange(new LiteralValueSource<>(0), new LiteralValueSource<>(10));
        forLoopNode1.setStepExec("printString1");
        forLoopNode1.setCompletedExec("printString2");
        ctx.addExecNode(forLoopNode1);

        Method printMethod = SysLib_Integer.class.getMethod("Print", ParamWrapper.class);
        FunctionCallExecNode printString1 = new FunctionCallExecNode("printString1", "printString1", printMethod);
        // 从父级ForLoopNode获取Index
        printString1.setParamInput("Str", new NodeOutputSource<>("forLoopNode1", ForLoopNode.INDEX));
        printString1.setNextExec("branchNode1");
        ctx.addExecNode(printString1);

        Method greaterThanMethod = SysLib_Integer.class.getMethod("GreaterThan", ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallPureNode greaterThan1 = new FunctionCallPureNode("greaterThan1", "greaterThan1", greaterThanMethod);
        greaterThan1.setParamInput("A", new NodeOutputSource<>("forLoopNode1", ForLoopNode.INDEX));
        greaterThan1.setParamInput("B", new LiteralValueSource<>(5));
        ParamWrapper<Boolean> greaterResult = new ParamWrapper<>();
        greaterThan1.setParamOutput("Ret", greaterResult);
        ctx.addPureNode(greaterThan1);

        BranchNode branchNode1 = new BranchNode("branchNode1", "branchNode1");
        branchNode1.setCondition(new NodeOutputSource<>("greaterThan1", "Ret"));
        branchNode1.setTrueExec("breakLoop1");
        branchNode1.setFalseExec("forLoopNode1");
        ctx.addExecNode(branchNode1);

        BreakExecNode breakLoop1 = new BreakExecNode("breakLoop1", "breakLoop1");
        breakLoop1.setLoopTarget("forLoopNode1");
        ctx.addExecNode(breakLoop1);

        FunctionCallExecNode printString2 = new FunctionCallExecNode("printString2", "printString2", printMethod);
        printString2.setParamInput("Str", new LiteralValueSource<>("结束了！！！"));
        printString2.setNextExec(null);
        ctx.addExecNode(printString2);

        ctx.run("forLoopNode1");
    }
}
