package com.berritus.mis.controller.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MisLogger {
    String value() default "";
}
