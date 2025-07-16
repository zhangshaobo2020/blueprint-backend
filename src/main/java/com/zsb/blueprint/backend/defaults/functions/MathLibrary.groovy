package com.zsb.blueprint.backend.defaults.functions

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction
import com.zsb.blueprint.backend.core.annotations.BlueprintFunctionLibrary
import com.zsb.blueprint.backend.core.annotations.ParamInput
import com.zsb.blueprint.backend.core.annotations.ParamOutput
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper
import com.zsb.blueprint.backend.defaults.types.School

import java.time.LocalDateTime

@BlueprintFunctionLibrary
class MathLibrary {

//    @BlueprintFunction(displayName = "MathLibrary.TestAdd")
    static void TestAdd(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = Num1.value + Num2.value
    }

    @BlueprintFunction(
            executable = false,
            displayName = "测试多输出",
            description = "输出两个整数的四则运算结果"
    )
    static void TestAddAndMultiple(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Add") ParamWrapper<Integer> Add,
            @ParamOutput("Sub") ParamWrapper<Long> Sub,
            @ParamOutput("Multi") ParamWrapper<Float> Multi,
            @ParamOutput("Div") ParamWrapper<Double> Div
    ) {
        Add.value = Num1.value + Num2.value
        Sub.value = Num1.value - Num2.value
        Multi.value = Num1.value * Num2.value
        Div.value = Num1.value / Num2.value
    }

//    @BlueprintFunction(displayName = "MathLibrary.TestAddList")
    static void TestAddList(
            @ParamInput("NumsList") ParamWrapper<List<Integer>> NumsList,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = 0
        for (final Num in NumsList.value) {
            Sum.value += Num
        }
    }

//    @BlueprintFunction(displayName = "MathLibrary.TestReverseList")
    static void TestReverseList(
            @ParamInput("InputList") ParamWrapper<List<Integer>> InputList,
            @ParamOutput("OutputList") ParamWrapper<List<Integer>> OutputList
    ) {
        OutputList.value = new ArrayList<>(InputList.value)
        Collections.reverse(OutputList.value)
    }

    @BlueprintFunction()
    static void TestAddDays(
            @ParamInput("Now") ParamWrapper<LocalDateTime> Now,
            @ParamInput("Days") ParamWrapper<Integer> Days,
            @ParamOutput("After") ParamWrapper<LocalDateTime> After
    ) {
        After.value = Now.value.plusDays(Days.value)
    }

    @BlueprintFunction()
    static void TestComplexType(
            @ParamInput("Param1") ParamWrapper<Map<String, Integer>> Param1,
            @ParamInput("Param2") ParamWrapper<List<School>> Param2,
            @ParamOutput("Param3") ParamWrapper<Integer> Param3
    ) {
        println Param1
        println Param2
        println Param3
    }
}
