package com.zsb.blueprint.backend.defaults.functions;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction;
import com.zsb.blueprint.backend.core.annotations.BlueprintFunctionLibrary;
import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;

import java.util.Objects;
import java.util.Random;

@BlueprintFunctionLibrary
public class SysLib_Integer {

    @BlueprintFunction(displayName = "打印", executable = true)
    public static void Print(
            @ParamInput("Num") ParamWrapper<Integer> Num
    ) {
        System.out.println(Num.value);
    }

    @BlueprintFunction(displayName = "加法")
    public static void Addition(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Num1.value + Num2.value;
    }

    @BlueprintFunction(displayName = "减法")
    public static void Subtraction(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Sum") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Num1.value - Num2.value;
    }

    @BlueprintFunction(displayName = "乘法")
    public static void Multiplication(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Num1.value * Num2.value;
    }

    @BlueprintFunction(displayName = "除法")
    public static void Division(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        if (Num2.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        Ret.value = Num1.value / Num2.value;
    }

    @BlueprintFunction(displayName = "取余")
    public static void Modulo(
            @ParamInput("Num1") ParamWrapper<Integer> Num1,
            @ParamInput("Num2") ParamWrapper<Integer> Num2,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Num1.value % Num2.value;
    }

    @BlueprintFunction(displayName = "取反")
    public static void Negate(
            @ParamInput("Value") ParamWrapper<Integer> Value,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = -Value.value;
    }

    @BlueprintFunction(displayName = "绝对值")
    public static void Absolute(
            @ParamInput("Value") ParamWrapper<Integer> Value,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Math.abs(Value.value);
    }

    @BlueprintFunction(displayName = "最大值")
    public static void Max(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Math.max(A.value, B.value);
    }

    @BlueprintFunction(displayName = "最小值")
    public static void Min(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Math.min(A.value, B.value);
    }

    @BlueprintFunction(displayName = "截断")
    public static void Clamp(
            @ParamInput("Value") ParamWrapper<Integer> Value,
            @ParamInput("Min") ParamWrapper<Integer> Min,
            @ParamInput("Max") ParamWrapper<Integer> Max,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = Math.min(Math.max(Value.value, Min.value), Max.value);
    }

    @BlueprintFunction(displayName = "幂运算")
    public static void Power(
            @ParamInput("Base") ParamWrapper<Integer> Base,
            @ParamInput("Exp") ParamWrapper<Integer> Exp,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = (int) Math.pow(Base.value, Exp.value);
    }

    @BlueprintFunction(displayName = "随机整数")
    public static void RandomInRange(
            @ParamInput("Min") ParamWrapper<Integer> Min,
            @ParamInput("Max") ParamWrapper<Integer> Max,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        if (Min.value > Max.value) {
            throw new IllegalArgumentException("Min cannot be greater than Max");
        }
        Ret.value = new Random().nextInt((Max.value - Min.value) + 1) + Min.value;
    }

    @BlueprintFunction(displayName = "等于")
    public static void Equal(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (Objects.equals(A.value, B.value));
    }

    @BlueprintFunction(displayName = "不等于")
    public static void NotEqual(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (!Objects.equals(A.value, B.value));
    }

    @BlueprintFunction(displayName = "大于")
    public static void GreaterThan(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (A.value > B.value);
    }

    @BlueprintFunction(displayName = "大于等于")
    public static void GreaterEqualThan(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (A.value >= B.value);
    }

    @BlueprintFunction(displayName = "小于")
    public static void LessThan(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (A.value < B.value);
    }

    @BlueprintFunction(displayName = "小于等于")
    public static void LessEqualThan(
            @ParamInput("A") ParamWrapper<Integer> A,
            @ParamInput("B") ParamWrapper<Integer> B,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (A.value <= B.value);
    }
}
