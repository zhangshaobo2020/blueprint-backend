package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.impl.*;
import com.zsb.blueprint.backend.core.runtime.params.LiteralValueSource;
import com.zsb.blueprint.backend.core.runtime.params.NodeOutputSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.defaults.functions.SysLib_Integer;

import java.lang.reflect.Method;

public class BlueprintTest {
    public static void main(String[] args) throws Exception {
        ExecutionContext ctx = new ExecutionContext();

        // === 1. 创建 RandomIntegerInRange 节点 (PureNode) ===
        Method randomMethod = SysLib_Integer.class.getMethod("RandomInteger",
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallPureNode randomNode = new FunctionCallPureNode("RandomNode", "RandomInt", randomMethod);
        randomNode.setParamInput("Min", new LiteralValueSource<>(1));
        randomNode.setParamInput("Max", new LiteralValueSource<>(10));
        ParamWrapper<Integer> randomResult = new ParamWrapper<>();
        randomNode.setParamOutput("Ret", randomResult);
        ctx.addPureNode(randomNode);

        // === 2. 创建 GreaterThan 节点 (PureNode) [random > 2 ?] ===
        Method greaterMethod = SysLib_Integer.class.getMethod("GreaterThan",
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallPureNode greaterNode = new FunctionCallPureNode("GreaterNode", "GreaterThan", greaterMethod);
        greaterNode.setParamInput("A", new NodeOutputSource<>("RandomNode", "Ret"));
        greaterNode.setParamInput("B", new LiteralValueSource<>(2));
        ParamWrapper<Boolean> greaterResult = new ParamWrapper<>();
        greaterNode.setParamOutput("Ret", greaterResult);
        ctx.addPureNode(greaterNode);

        // === 3. ForLoopNode: i = 1..3 ===
        ForLoopNode forLoopNode = new ForLoopNode("ForLoopNode", "ForLoop");
        forLoopNode.setRange(new LiteralValueSource<>(1), new LiteralValueSource<>(3));
        ctx.addExecNode(forLoopNode);

        // === 4. SwitchIntegerNode: 根据 i 选择不同的分支 ===
        SwitchIntegerNode switchNode = new SwitchIntegerNode("SwitchNode", "SwitchInteger");
//        switchNode.setCondition(new NodeOutputSource<>("ForLoopNode", "Index")); // 暂不实现
        switchNode.setCondition(new LiteralValueSource<>(1));
        ctx.addExecNode(switchNode);

        // === 5. Print 分支 (FunctionCallExecNode) ===
        Method printMethod = SysLib_Integer.class.getMethod("Print", ParamWrapper.class);
        FunctionCallExecNode printCase1 = new FunctionCallExecNode("PrintCase1", "PrintInt", printMethod);
        printCase1.setParamInput("Num", new LiteralValueSource<>(111));
        ctx.addExecNode(printCase1);

        FunctionCallExecNode printCase2 = new FunctionCallExecNode("PrintCase2", "PrintInt", printMethod);
        printCase2.setParamInput("Num", new LiteralValueSource<>(222));
        ctx.addExecNode(printCase2);

        FunctionCallExecNode printCase3 = new FunctionCallExecNode("PrintCase3", "PrintInt", printMethod);
        printCase3.setParamInput("Num", new LiteralValueSource<>(333));
        ctx.addExecNode(printCase3);

        // === 6. 绑定 Switch 分支 ===
        switchNode.addCase(1, "PrintCase1");
        switchNode.addCase(2, "PrintCase2");
        switchNode.addCase(3, "PrintCase3");
        switchNode.setDefaultExec(null);

        // === 7. 设置 ForLoop 执行流 ===
        forLoopNode.setStepExec("SwitchNode");
        forLoopNode.setCompletedExec("WhileNode");

        // === 8. WhileNode (Random < 8 才继续) ===
        WhileNode whileNode = new WhileNode("WhileNode", "WhileRandom");
        whileNode.setCondition(new NodeOutputSource<>("GreaterNode", "Ret"));
        whileNode.setLoopBodyExec("ForLoopNode");
        whileNode.setCompletedExec(null); // 结束
        ctx.addExecNode(whileNode);

        // === 9. 起点：每次循环前生成新的随机数 ===
        FunctionCallExecNode randomExec = new FunctionCallExecNode("RandomExec", "GenerateRandom", randomMethod);
        randomExec.setParamInput("Min", new LiteralValueSource<>(1));
        randomExec.setParamInput("Max", new LiteralValueSource<>(10));
        randomExec.setParamOutput("Ret", randomResult);
        randomExec.setNextExec("WhileNode");
        ctx.addExecNode(randomExec);

        // === 10. 设置所有分支回流 ===
        printCase1.setNextExec("ForLoopNode");
        printCase2.setNextExec("ForLoopNode");
        printCase3.setNextExec("ForLoopNode");

        // === 11. 执行 ===
        ctx.run("RandomExec");
    }
}
