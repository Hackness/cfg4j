package com.hackness.cfg4j.core.parse;

import com.hackness.cfg4j.core.model.GenData;

import java.io.File;
import java.util.List;

/**
 * Created by Hack
 * Date: 25-Nov-19 12:11
 */
public interface IParser<T> {
    void process(File file);

    void parse(T root);

    void generate(File file, T root, List<GenData<T>> add);
}
