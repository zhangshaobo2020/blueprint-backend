package com.zsb.blueprint.backend.core.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BlueprintInitializer implements ApplicationRunner {

    @Resource
    private BlueprintScanner blueprintScanner;

    @Override
    public void run(ApplicationArguments args) {
        // 解析默认的内置Java类型
        blueprintScanner.processDefaultType();
        // 扫描自定义的@BlueprintType
        blueprintScanner.processBlueprintType("com.zsb.blueprint.backend.defaults.types");
        // 扫描自定义的@BlueprintFunctionLibrary
        blueprintScanner.processBlueprintFunction("com.zsb.blueprint.backend.defaults.functions");
    }
}
