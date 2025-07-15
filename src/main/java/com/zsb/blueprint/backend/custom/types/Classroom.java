package com.zsb.blueprint.backend.custom.types;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@BlueprintType
@Data
@NoArgsConstructor
public class Classroom implements Serializable {
    private String name;
    private School school;
}
