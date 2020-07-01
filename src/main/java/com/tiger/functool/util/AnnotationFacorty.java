package com.tiger.functool.util;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/26.
 */
public interface AnnotationFacorty {
    void setField(Field field, Object target, Map map);
}
