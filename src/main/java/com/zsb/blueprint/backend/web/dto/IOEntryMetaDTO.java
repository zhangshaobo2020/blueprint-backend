package com.zsb.blueprint.backend.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class IOEntryMetaDTO implements Serializable {
    private String name;
    private boolean input;  // 有些 meta 中没有 input 字段
    private TypeDTO type;
}
