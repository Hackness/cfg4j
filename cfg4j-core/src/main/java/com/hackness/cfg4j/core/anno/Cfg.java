package com.hackness.cfg4j.core.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hack
 * Date: 11-Nov-19 00:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cfg {
    /**
     * @return - config description that will be placed above the config as a comment
     */
    String value() default "";

    /**
     * @return - if true the config will not be generated, but sill can be read
     */
    boolean optional() default false;

    /**
     * @return - the file where the field's value is stored. Will override the file given by the owning class
     */
    String file() default "";
}
