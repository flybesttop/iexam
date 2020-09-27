package com.sot.iexam.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ClassBuilder {
    public static Object setNullToDefault(Object o) throws IllegalAccessException {
        Class _objclass = o.getClass();
        Field[] filed = _objclass.getDeclaredFields();
        for (int i = 0; i < filed.length; i++) {
            Field f = filed[i];
            f.setAccessible(true);
            String type = f.getType().toString();
            if (type.endsWith("String") && f.get(o) == null) {
                f.set(o, "");
            } else if ((type.endsWith("int") || type.endsWith("Integer") || type.endsWith("double") && f.get(o) == null)) {
                f.set(o, -1);
            } else if (type.endsWith("long") || type.endsWith("Long") && f.get(o) == null) {
                f.set(o, -1L);
            } else if (type.endsWith("Date") && f.get(o) == null) {
                f.set(o, Date.valueOf("1970-01-01"));
            } else if (type.endsWith("Timestamp") && f.get(o) == null) {
                f.set(o, Timestamp.valueOf("1970-01-01 00:00:00"));
            } else if (type.endsWith("BigDecimal") && f.get(o) == null) {
                f.set(o, new BigDecimal(-1));
            }
        }
        return o;
    }

    public static Object Concat(Object o, Object o1) throws IllegalAccessException {
        Class _objclass = o.getClass();
        Field[] fields = _objclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            if (f.get(o) != null) {
                f.set(o1, f.get(o));
            }
        }
        return o1;
    }
}
