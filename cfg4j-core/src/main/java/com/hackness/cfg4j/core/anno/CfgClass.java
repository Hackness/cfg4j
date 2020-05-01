package com.hackness.cfg4j.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hack
 * Date: 25-Nov-19 11:08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface CfgClass {
    /**
     * @return - path of the config file that will be used by default in all @Cfg annotations in the class that has
     * no file specified
     */
    String value() default "";
}
