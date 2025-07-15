package com.zsb.blueprint.backend.core.config;

import com.zsb.blueprint.backend.core.annotations.BlueprintFunctionLibrary;
import com.zsb.blueprint.backend.core.annotations.BlueprintType;
import com.zsb.blueprint.backend.core.definition.FunctionDefinition;
import com.zsb.blueprint.backend.core.definition.TypeDefinition;
import com.zsb.blueprint.backend.core.resolver.FunctionResolver;
import com.zsb.blueprint.backend.core.resolver.TypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BlueprintScanner {

    public static final Map<String, TypeDefinition> TYPE_DEFINITION = new HashMap<>();
    public static final Map<String, FunctionDefinition> FUNCTION_DEFINITION = new HashMap<>();

    public void processDefaultType() {
        TypeResolver.PRIMITIVE_CLASSES.forEach(clazz -> {
            TypeDefinition def = TypeResolver.resolveType(clazz);
            TYPE_DEFINITION.put(clazz.getName(), def);
        });
    }

    public void processBlueprintType(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(BlueprintType.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                TypeDefinition def = TypeResolver.resolveType(clazz);
                TYPE_DEFINITION.put(clazz.getName(), def);
            } catch (Exception e) {
                log.error("解析类型失败{}", bd.getBeanClassName());
            }
        }
    }

    public void processBlueprintFunction(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(BlueprintFunctionLibrary.class));

        List<FunctionDefinition> definitionList = new ArrayList<>();
        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                definitionList.addAll(FunctionResolver.resolveFromClass(clazz));
            } catch (Exception e) {
                log.error("解析函数失败{}", bd.getBeanClassName());
            }
        }
        definitionList.forEach(definition -> {
            FUNCTION_DEFINITION.put(definition.getQualifiedName(), definition);
        });
    }
}
