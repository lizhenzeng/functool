package com.tiger.functool.util;




import com.tiger.functool.util.annotation.ColumName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/26.
 */
public class ColumNameFactory implements AnnotationFacorty {
    @Override
    public void setField(Field field, Object target, Map jsonObject) {
        Annotation[] annotations = field.getAnnotations();
        for(Annotation an :annotations){
            if(an instanceof ColumName){
                String value = ((ColumName) an).value();
                if(value != null){
                    Object o = jsonObject.get(value);
                    o = ConvertUtils.getScalaValue(((ColumName) an).scala(),o);
                    o = ConvertUtils.getFormateValue(((ColumName) an).formate(),o);
                    o = ConvertUtils.getUnitValue(((ColumName) an).unit(),o);
                    field.setAccessible(true);
                    try {
                        if(o != null && !Constant.NULL.equals(o) && !Constant.EMPTY.equals(o.toString().trim())){
                            String temp = String.valueOf(o);
                            field.set(target,temp);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                String[] values = ((ColumName) an).values();
                if(values!=null){
                    for(String var:values){
                        Object o = jsonObject.get(var);
                        o = ConvertUtils.getScalaValue(((ColumName) an).scala(),o);
                        o = ConvertUtils.getFormateValue(((ColumName) an).formate(),o);
                        o = ConvertUtils.getUnitValue(((ColumName) an).unit(),o);
                        field.setAccessible(true);
                        try {
                            if(o != null && !Constant.NULL.equals(o) && !Constant.EMPTY.equals(o.toString().trim())){
                                String temp = String.valueOf(o);
                                field.set(target,temp);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

    }


}
