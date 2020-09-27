package com.sot.iexam.util;

import org.thymeleaf.spring5.expression.Fields;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Kimbobo
 */
public class BeanUtils {

    public static <T> T map2Bean(Map<String, Object> map, Class<T> beanClass) throws Exception {
        T obj = beanClass.newInstance();
        if (map == null) {
            return obj;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (map.get(field.getName()) == null) {
                continue;
            }
            if (map.get(field.getName()) == "") {
                continue;
            }
            field.setAccessible(true);
            if (field.getType() == String.class) {
                field.set(obj, map.get(field.getName()));
            } else if (field.getType() == Integer.class) {
                field.set(obj, Integer.valueOf(map.get(field.getName()).toString()));
            }
        }
        return obj;
    }

    public static Integer mapString2Integer(Object o) {
        return Integer.valueOf(o.toString());
    }
}
    