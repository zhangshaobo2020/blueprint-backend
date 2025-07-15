package com.zsb.blueprint.backend.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BlueprintFunction {

    String displayName() default "默认名称";           // 蓝图编辑器上展示的名称

    String description() default "";                  // 蓝图编辑器上展示的详细描述

    String category() default "默认分类";              // 蓝图编辑器上展示的分类

    boolean executable() default true;                // 是否是executable节点

    boolean latent() default false;                   // 是否是latent节点
}
