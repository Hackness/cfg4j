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
     * @return - config description that will be placed above as a comment
     */
    String value() default "";

    /**
     * @return - if true the config will not be generated, but sill can be read
     */
    //TODO: implement
    boolean optional() default false;

    /**
     * @return - the file where the field's value is stored. Will override the file given by the owning class
     */
    String file() default "";

    /**
     * @return - if the field should be loaded as a configurable owner. Means the field must contain a not null class
     * instance with configurable non static fields that will be processed.
     */
    boolean instanceLoad() default false;
}
