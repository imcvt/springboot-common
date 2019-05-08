package com.imc.annotation;

import java.lang.annotation.*;

/**
 * @author luoly
 * @date 2019/4/15 10:17
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PoParam {
    String table();
}
