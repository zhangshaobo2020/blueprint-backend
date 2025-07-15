package com.zsb.blueprint.backend.custom.types;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@BlueprintType
@Data
@NoArgsConstructor
public class School implements Serializable {
    private String name;
    private LocalDateTime createTime;
    private String address;
    private Integer year;
}
