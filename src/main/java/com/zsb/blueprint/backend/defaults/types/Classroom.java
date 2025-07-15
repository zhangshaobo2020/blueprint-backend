package com.zsb.blueprint.backend.defaults.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Classroom implements Serializable {
    private String name;
    private School school;
}
