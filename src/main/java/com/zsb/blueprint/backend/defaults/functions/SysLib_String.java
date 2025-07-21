package com.zsb.blueprint.backend.defaults.functions;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction;
import com.zsb.blueprint.backend.core.annotations.BlueprintFunctionLibrary;
import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@BlueprintFunctionLibrary
public class SysLib_String {

    @BlueprintFunction(displayName = "打印", executable = true)
    public static void Print(
            @ParamInput("Str") ParamWrapper<String> Str
    ) {
        System.out.println(Str.value);
    }

    @BlueprintFunction(displayName = "追加")
    public static void Append(
            @ParamInput("Str1") ParamWrapper<String> Str1,
            @ParamInput("Str2") ParamWrapper<String> Str2,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        Ret.value = Str1.value + Str2.value;
    }

    @BlueprintFunction(displayName = "长度")
    public static void Length(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = (Str.value != null) ? Str.value.length() : 0;
    }

    @BlueprintFunction(displayName = "子串")
    public static void Substring(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("BeginIndex") ParamWrapper<Integer> BeginIndex,
            @ParamInput("EndIndex") ParamWrapper<Integer> EndIndex,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        if (Str.value == null) {
            Ret.value = null;
        } else {
            int start = Math.max(0, BeginIndex.value);
            int end = (EndIndex.value != null && EndIndex.value <= Str.value.length())
                    ? EndIndex.value
                    : Str.value.length();
            Ret.value = Str.value.substring(start, end);
        }
    }

    @BlueprintFunction(displayName = "包含")
    public static void Contains(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("SubStr") ParamWrapper<String> SubStr,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (Str.value != null && SubStr.value != null) && Str.value.contains(SubStr.value);
    }

    @BlueprintFunction(displayName = "替换")
    public static void Replace(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("Target") ParamWrapper<String> Target,
            @ParamInput("Replacement") ParamWrapper<String> Replacement,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        Ret.value = (Str.value != null) ? Str.value.replace(Target.value, Replacement.value) : null;
    }

    @BlueprintFunction(displayName = "转大写")
    public static void ToUpper(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        Ret.value = (Str.value != null) ? Str.value.toUpperCase() : null;
    }

    @BlueprintFunction(displayName = "转小写")
    public static void ToLower(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        Ret.value = (Str.value != null) ? Str.value.toLowerCase() : null;
    }

    @BlueprintFunction(displayName = "去除空格")
    public static void Trim(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        Ret.value = (Str.value != null) ? Str.value.trim() : null;
    }

    @BlueprintFunction(displayName = "是否为空")
    public static void IsEmpty(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (Str.value == null || Str.value.isEmpty());
    }

    @BlueprintFunction(displayName = "是否以...开头")
    public static void StartsWith(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("Prefix") ParamWrapper<String> Prefix,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (Str.value != null && Prefix.value != null) && Str.value.startsWith(Prefix.value);
    }

    @BlueprintFunction(displayName = "是否以...结尾")
    public static void EndsWith(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("Suffix") ParamWrapper<String> Suffix,
            @ParamOutput("Ret") ParamWrapper<Boolean> Ret
    ) {
        Ret.value = (Str.value != null && Suffix.value != null) && Str.value.endsWith(Suffix.value);
    }

    @BlueprintFunction(displayName = "索引位置")
    public static void IndexOf(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("SubStr") ParamWrapper<String> SubStr,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = (Str.value != null && SubStr.value != null) ? Str.value.indexOf(SubStr.value) : -1;
    }

    @BlueprintFunction(displayName = "最后索引位置")
    public static void LastIndexOf(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("SubStr") ParamWrapper<String> SubStr,
            @ParamOutput("Ret") ParamWrapper<Integer> Ret
    ) {
        Ret.value = (Str.value != null && SubStr.value != null) ? Str.value.lastIndexOf(SubStr.value) : -1;
    }

    @BlueprintFunction(displayName = "分割")
    public static void Split(
            @ParamInput("Str") ParamWrapper<String> Str,
            @ParamInput("Delimiter") ParamWrapper<String> Delimiter,
            @ParamOutput("Ret") ParamWrapper<List<String>> Ret
    ) {
        if (Str.value != null && Delimiter.value != null) {
            Ret.value = new ArrayList<>(Arrays.asList(Str.value.split(Delimiter.value)));
        } else {
            Ret.value = new ArrayList<>();
        }
    }

    @BlueprintFunction(displayName = "连接")
    public static void Join(
            @ParamInput("Delimiter") ParamWrapper<String> Delimiter,
            @ParamInput("Parts") ParamWrapper<List<String>> Parts,
            @ParamOutput("Ret") ParamWrapper<String> Ret
    ) {
        if (Parts.value == null || Parts.value.isEmpty()) {
            Ret.value = "";
        } else {
            Ret.value = String.join(Delimiter.value != null ? Delimiter.value : "", Parts.value);
        }
    }
}
