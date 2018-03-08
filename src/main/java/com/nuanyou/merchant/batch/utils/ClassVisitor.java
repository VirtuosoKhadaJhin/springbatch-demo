package com.nuanyou.merchant.batch.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by xiejing on 2016/9/10.
 */
public interface ClassVisitor {
    void visitField(Field field);

    void visitFieldAnnotation(Field field, Annotation annotation) throws IllegalAccessException;
}
