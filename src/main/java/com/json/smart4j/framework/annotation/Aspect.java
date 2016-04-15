package com.json.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by wuhao on 16/4/12.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
