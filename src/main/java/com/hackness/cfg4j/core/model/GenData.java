package com.hackness.cfg4j.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Hack
 * Date: 23-Apr-20 07:16
 */
@Data
@AllArgsConstructor
public class GenData<E> {
    private E element;
    private String comment;
}
