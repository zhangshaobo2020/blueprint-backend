package com.zsb.blueprint.backend.core.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BlueprintInitializer implements ApplicationRunner {

    @Resource
    private BlueprintTypeScanner blueprintTypeScanner;

    @Override
    public void run(ApplicationArguments args) {
        // 解析默认的内置Java类型
        blueprintTypeScanner.processPrimitive();
        // 扫描自定义的@BlueprintType
        blueprintTypeScanner.scanCustom("com.zsb.blueprint.backend.custom.types");
    }
}
