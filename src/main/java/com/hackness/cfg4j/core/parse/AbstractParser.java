package com.hackness.cfg4j.core.parse;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:30
 */
@Slf4j
public abstract class AbstractParser<T> implements IParser<T> {
    @Getter private File parsingFile;

    @Override
    public void process(File file, Object owner) {
        try {
            if (file.isDirectory()) {
                Files.walk(file.toPath()).filter(path -> !Files.isDirectory(path)).forEach(path -> {
                    try {
                        parsingFile = path.toFile();
                        parse(build(parsingFile), owner);
                        logFileLoaded(parsingFile);
                    } catch (Exception e) {
                        logFileErr(parsingFile, e);
                    }
                });
            } else {
                parsingFile = file;
                parse(build(parsingFile), owner);
                logFileLoaded(parsingFile);
            }
        } catch (Exception e) {
            logFileErr(parsingFile, e);
        }

    }

    protected void logFileLoaded(File file) {
        log.info("Config file loaded: " + file);
    }

    protected void logFileErr(File file, Exception e) {
        log.warn("Failed to load config file: " + parsingFile, e);
    }

    protected abstract T build(File file) throws Exception;
}
