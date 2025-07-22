package com.zsb.blueprint.backend.defaults.functions;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunction;
import com.zsb.blueprint.backend.core.annotations.BlueprintFunctionLibrary;
import com.zsb.blueprint.backend.core.annotations.ParamInput;
import com.zsb.blueprint.backend.core.annotations.ParamOutput;
import com.zsb.blueprint.backend.core.wrapper.ParamWrapper;
import com.zsb.blueprint.backend.defaults.types.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@BlueprintFunctionLibrary
public class MyLib_Student {

    @BlueprintFunction()
    public static void MakeStudent(
            @ParamInput("Name") ParamWrapper<String> Name,
            @ParamInput("Age") ParamWrapper<Integer> Age,
            @ParamInput("Sex") ParamWrapper<Boolean> Sex,
            @ParamOutput("Stu") ParamWrapper<Student> Stu
    ) {
        Student student = new Student();
        student.setName(Name.value);
        student.setAge(Age.value);
        student.setSex(Sex.value);
        Stu.value = student;
    }

    @BlueprintFunction(executable = true)
    public static void PrintStudent(
            @ParamInput("Stu") ParamWrapper<Student> Stu
    ) {
        System.out.println(Stu.value);
    }
}
