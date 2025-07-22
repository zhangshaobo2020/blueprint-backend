package com.zsb.blueprint.backend.core.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomPathConfig {

    @Value("${blueprint-backend.blueprint-type-path}")
    public String blueprintTypePath;
    @Value("${blueprint-backend.blueprint-function-path}")
    public String blueprintFunctionPath;
}
