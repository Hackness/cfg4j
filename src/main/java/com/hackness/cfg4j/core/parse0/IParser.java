package com.hackness.cfg4j.core.parse0;

import java.io.File;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:11
 */
public interface IParser<T> {
    void process(File file, Object owner);

    void parse(T root, Object owner);
}
