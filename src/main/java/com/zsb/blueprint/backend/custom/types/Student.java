package com.zsb.blueprint.backend.custom.types;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@BlueprintType
@Data
@NoArgsConstructor
public class Student implements Serializable {
    private String name;
    private Integer age;
    private Classroom classroom;

    private Map<Lesson, Float> grades;
}
