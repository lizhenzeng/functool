package com.tiger.functool.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/26.
 */
public class NoAnnotationFactory implements AnnotationFacorty {
    @Override
    public void setField(Field field, Object target, Map jsonObject) {
        Object o = jsonObject.get(field.getName());
        try {
//            if (o instanceof Integer) {
//                o = String.valueOf(o);
//            }
            String type =  field.getGenericType().toString();
            if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                if(!(o instanceof String)){
                  o = String.valueOf(o);
                }

            }
            if (type.equals("class java.lang.Integer")) {
                if(!(o instanceof Integer) && o!=null){
                    o = Integer.valueOf(String.valueOf(o));
                }

            }
            if (type.equals("class java.lang.Boolean")) {
                if(!(o instanceof Boolean && o!=null)){
                    o = Boolean.valueOf(String.valueOf(o));
                }
            }
            if (type.equals("class java.util.Date")) {
                if(!(o instanceof Date && o!=null)){
                    if(o instanceof Long){
                        o = new Date(((Long) o).longValue());
                    }
                    if(o instanceof String){
                        o = new Date((Long.valueOf((String) o)).longValue());
                    }
                }

            }
            field.setAccessible(true);
            field.set(target, o);
        } catch (IllegalAccessException e) {
//                    e.printStackTrace();
        }
    }


}
