package com.hackness.cfg4j.core.model.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.Map;

/**
 * Created by Hack
 * Date: 22-Apr-20 05:59
 */
@AllArgsConstructor
@Getter
public class FileCache<T> {
    private File file;
    private Map<String, T> elements;
    private T root;
}
