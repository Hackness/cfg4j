package com.hackness.cfg4j.core;

import lombok.Getter;

import java.io.File;

/**
 * Created by Hack
 * Date: 10-Nov-19 20:40
 */
public class ConfigLoader {
    @Getter(lazy = true) private static final ConfigLoader instance = new ConfigLoader();

    public File getConfigFile(Class<?> clazz) {
        return null;
    }
}
