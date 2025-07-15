package com.zsb.blueprint.backend.tests

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction
import com.zsb.blueprint.backend.core.annotations.ParamInput
import com.zsb.blueprint.backend.core.annotations.ParamOutput
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper
import com.zsb.blueprint.backend.custom.types.Student

import java.time.LocalDateTime

class MathLibrary {

    @BlueprintFunction(displayName = "MathLibrary.TestAdd")
    static void TestAdd(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = Num1.value + Num2.value
    }

    @BlueprintFunction(displayName = "MathLibrary.TestAddAndMultiple")
    static void TestAddAndMultiple(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum,
            @ParamOutput("Product") ParamWrapper<Integer> Product
    ) {
        Sum.value = Num1.value + Num2.value
        Product.value = Num1.value * Num2.value
    }

    @BlueprintFunction(displayName = "MathLibrary.TestAddList")
    static void TestAddList(
            @ParamInput("NumsList") ParamWrapper<List<Integer>> NumsList,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = 0
        for (final Num in NumsList.value) {
            Sum.value += Num
        }
    }

    @BlueprintFunction(displayName = "MathLibrary.TestReverseList")
    static void TestReverseList(
            @ParamInput("InputList") ParamWrapper<List<Integer>> InputList,
            @ParamOutput("OutputList") ParamWrapper<List<Integer>> OutputList
    ) {
        OutputList.value = new ArrayList<>(InputList.value)
        Collections.reverse(OutputList.value)
    }

    @BlueprintFunction(displayName = "MathLibrary.TestAddDays")
    static void TestAddDays(
            @ParamInput("Now") ParamWrapper<LocalDateTime> Now,
            @ParamInput("Days") ParamWrapper<Integer> Days,
            @ParamOutput("After") ParamWrapper<LocalDateTime> After
    ) {
        After.value = Now.value.plusDays(Days.value)
    }

    @BlueprintFunction(displayName = "MathLibrary.TestComplexType")
    static void TestComplexType(
            @ParamInput("Param1") ParamWrapper<Map<String, Integer>> Param1,
            @ParamInput("Param2") ParamWrapper<List<Student>> Param2,
            @ParamOutput("Param3") ParamWrapper<Integer> Param3
    ) {
        println Param1
        println Param2
        println Param3
    }
}
