package com.zsb.blueprint.backend.tests

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction
import com.zsb.blueprint.backend.core.annotations.ParamInput
import com.zsb.blueprint.backend.core.annotations.ParamOutput
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper

class MathLibrary {

    @BlueprintFunction()
    static void TestAdd(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = Num1.value + Num2.value
    }

    @BlueprintFunction()
    static void TestAddAndMultiple(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum,
            @ParamOutput("Product") ParamWrapper<Integer> Product
    ) {
        Sum.value = Num1.value + Num2.value
        Product.value = Num1.value * Num2.value
    }

    @BlueprintFunction()
    static void TestAddList(
            @ParamInput("NumsList") ParamWrapper<List<Integer>> NumsList,
            @ParamOutput("Sum") ParamWrapper<Integer> Sum
    ) {
        Sum.value = 0
        for (final Num in NumsList.value) {
            Sum.value += Num
        }
    }

    @BlueprintFunction()
    static void TestReverseList(
            @ParamInput("InputList") ParamWrapper<List<Integer>> InputList,
            @ParamOutput("OutputList") ParamWrapper<List<Integer>> OutputList
    ) {
        OutputList.value = new ArrayList<>(InputList.value)
        Collections.reverse(OutputList.value)
    }
}
