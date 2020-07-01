package com.tiger.functool.util;


import com.tiger.functool.util.annotation.Recursion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/26.
 */
public class RecursionFactory implements AnnotationFacorty {
    @Override
    public void setField(Field field, Object target, Map jsonObject) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation an : annotations) {
            if (an instanceof Recursion) {
                List o = (List) jsonObject.get(field.getName());
                List list = null;
                try {
                    if (((Recursion) an).value().isAssignableFrom(Map.class)) {
                        list = o;
                    } else {
                        list = ConvertUtils.recurListToListT(o, ((Recursion) an).value());
                    }
                    field.setAccessible(true);
                    field.set(target, list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
