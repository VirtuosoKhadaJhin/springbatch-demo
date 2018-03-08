package com.nuanyou.merchant.batch.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by xiejing on 2016/9/10.
 */
public class JavaClassReader {
    public static void readClass(Class clazz, ClassVisitor visitor) throws IllegalAccessException {
        if(clazz.equals(Object.class)) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            visitor.visitField(field);
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                visitor.visitFieldAnnotation(field, annotation);
            }
        }
        readClass(clazz.getSuperclass(), visitor);
    }
}
