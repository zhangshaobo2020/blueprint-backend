package com.zsb.blueprint.backend.core.config;

import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.core.resolver.TypeResolver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Slf4j
@Component
public class BlueprintTypeScanner {

    private final Map<String, TypeDefinition> typeMap = new HashMap<>();

    public void processPrimitive() {
        TypeResolver.PRIMITIVE_CLASSES.forEach(clazz -> {
            TypeDefinition def = TypeResolver.resolveType(clazz);
            typeMap.put(clazz.getName(), def);
        });
    }

    public void scanCustom(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(BlueprintType.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                TypeDefinition def = TypeResolver.resolveType(clazz);
                typeMap.put(clazz.getName(), def);
            } catch (Exception e) {
                log.error("解析类型失败{}", bd.getBeanClassName());
            }
        }
    }
}
