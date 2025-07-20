package com.zsb.blueprint.backend.core.runtime;

import com.zsb.blueprint.backend.core.runtime.nodes.impl.BranchNode;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.BreakNode;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.FunctionCallNode;
import com.zsb.blueprint.backend.core.runtime.nodes.impl.WhileNode;
import com.zsb.blueprint.backend.core.runtime.params.LiteralValueSource;
import com.zsb.blueprint.backend.core.runtime.params.NodeOutputSource;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.defaults.functions.SysLib_Integer;
import com.zsb.blueprint.backend.defaults.functions.SysLib_Test;

import java.lang.reflect.Method;

public class BlueprintTest {
    public static void main(String[] args) throws Exception {
        ExecutionContext ctx = new ExecutionContext();

        // 1. While 节点
        WhileNode whileNode = new WhileNode("While1");
        whileNode.setCondition(new LiteralValueSource<>(true));  // 永远为真，直到Break
        whileNode.setBodyExec("Random1");
        whileNode.setExitExec("Func1");
        ctx.addNode(whileNode);

        // 2. RandomIntegerInRange 节点
        Method randomMethod = SysLib_Integer.class.getDeclaredMethod("RandomInRange",
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallNode randomNode = new FunctionCallNode("Random1", randomMethod);
        randomNode.addInput("Min", new LiteralValueSource<>(0));
        randomNode.addInput("Max", new LiteralValueSource<>(10));
        ParamWrapper<Integer> randomOut = new ParamWrapper<>();
        randomNode.addOutput("Ret", randomOut);
        randomNode.setNextExec("GreaterThan1");
        ctx.addNode(randomNode);

        // 3. GreaterThan 节点
        Method gtMethod = SysLib_Integer.class.getDeclaredMethod("GreaterThan",
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallNode gtNode = new FunctionCallNode("GreaterThan1", gtMethod);
        gtNode.addInput("A", new NodeOutputSource<>(randomNode, "Ret"));
        gtNode.addInput("B", new LiteralValueSource<>(7));
        ParamWrapper<Boolean> gtOut = new ParamWrapper<>();
        gtNode.addOutput("Ret", gtOut);
        gtNode.setNextExec("Branch1");
        ctx.addNode(gtNode);

        // 4. Branch 节点
        BranchNode branch = new BranchNode("Branch1");
        branch.setCondition(new NodeOutputSource<>(gtNode, "Ret"));
        branch.setTrueExec("Break1");
        branch.setFalseExec("While1");  // 回到While头
        ctx.addNode(branch);

        // 5. Break 节点
        BreakNode breakNode = new BreakNode("Break1", whileNode);
        breakNode.setNextExec("Func1");
        ctx.addNode(breakNode);

        // 6. TestAddAndMultiple 函数调用
        Method funcMethod = SysLib_Test.class.getDeclaredMethod("TestAddAndMultiple",
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class,
                ParamWrapper.class, ParamWrapper.class, ParamWrapper.class);
        FunctionCallNode funcNode = new FunctionCallNode("Func1", funcMethod);
        funcNode.addInput("Num1", new NodeOutputSource<>(randomNode, "Ret"));
        funcNode.addInput("Num2", new LiteralValueSource<>(3));
        ParamWrapper<Integer> addOut = new ParamWrapper<>();
        ParamWrapper<Long> subOut = new ParamWrapper<>();
        ParamWrapper<Float> multiOut = new ParamWrapper<>();
        ParamWrapper<Double> divOut = new ParamWrapper<>();
        funcNode.addOutput("Add", addOut);
        funcNode.addOutput("Sub", subOut);
        funcNode.addOutput("Multi", multiOut);
        funcNode.addOutput("Div", divOut);
        funcNode.setNextExec(null); // 结束
        ctx.addNode(funcNode);

        // 执行
        ctx.run("While1");

        System.out.println("最终结果：Add=" + addOut.value + ", Sub=" + subOut.value
                + ", Multi=" + multiOut.value + ", Div=" + divOut.value);
    }
}
