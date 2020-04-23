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

//TODO: implement or remove
public @interface CfgClass {
    /**
     * @return - description of the class, that will appear at the header of a config file
     */
    String value();

    /**
     * @return - path to the config file, extension included.
     */
    String filePath();
}
