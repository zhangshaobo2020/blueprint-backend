package com.zsb.blueprint.backend.custom.types;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import lombok.Data;
import lombok.NoArgsConstructor;

@BlueprintType
@Data
@NoArgsConstructor
public class Lesson {
    private String name;
}
