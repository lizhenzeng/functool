//package com.tiger.functool.util;
//
//import com.google.common.collect.Lists;
//import lombok.Data;
//
//import java.lang.reflect.*;
//import java.util.*;
//
//public class NewConvertUtils {
//
//    public void convert(Object source, Object target, Boolean isOverWrite, String[] mapping) {
//
//        Field[] targetFields = target.getClass().getDeclaredFields();
//
//
//    }
//
//    public void convert(List<Object> sources,Object target,Field targetField,Type targetType){
//
//    }
//
//    public void convert(Object source,Object target,Field targetField,Type targetRawType){
//        try {
//            Object value = ((Class)targetRawType).newInstance();
//            Field[] sourceFields = source.getClass().getDeclaredFields();
//
//            for(Field sourceField: sourceFields){
//                Field tf = value.getClass().getDeclaredField(sourceField.getName());
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void resolveFieldType(Field targetField,Field sourceField){
//        Type type = targetField.getGenericType();
//        Type rawType;
//        Type[] actualType;
//        if (type instanceof GenericArrayType) {
//            Type clzz = ((GenericArrayType) type).getGenericComponentType();
//            console(clzz);
//        }
//        // List<T>
//        if (type instanceof ParameterizedType) {
//            rawType = ((ParameterizedType) type).getRawType();
//            console(rawType);
//            Type type1 = ((ParameterizedType) type).getOwnerType();
//            console(type1);
//            actualType = ((ParameterizedType) type).getActualTypeArguments();
//            console(actualType);
//            if(rawType.equals(List.class) && actualType.length == 1){
//
//            }
//        }
//        if (type instanceof TypeVariable) {
//            Type[] types = ((TypeVariable) type).getBounds();
//            console(types);
//        }
//        if (type instanceof WildcardType) {
//            Type[] types = ((WildcardType) type).getUpperBounds();
//            console(types);
//        }
//    }
//    public static void main(String[] args) {
//        Demo dm = new Demo(1, "2", new Demo(2, "3"));
//        List<Demo> demos = new ArrayList();
//        demos.add(new Demo(2, "3"));
//        Demo dm2 = new Demo(1, "2", demos);
//        dm2.setDemoMaps("3", dm2);
//        Field[] fs = dm.getClass().getDeclaredFields();
//        for (Field f : fs) {
//             resolveFieldType(f);
////            System.out.println(f.getGenericType());
//        }
//    }
//
//    public static void console(Type... types) {
//        if (types != null && types.length > 0) {
//            Arrays.stream(types).forEach(val ->{
//                if (val != null) {
//                    System.out.println(String.format("%s:%s", val.getTypeName(), val.getClass()));
//                }
//            });
//
//        }
//    }
//
//    @Data
//    public static class Demo {
//        private String value;
//        private Demo demo;
//        private Integer id;
//        private List<Demo> demos = new ArrayList<>();
//        private Demo[] demoArrays = new Demo[1];
//        private List<Demo>[] demoListAndArrays = new List[2];
//        private Map<String, Demo> demoMap = new HashMap<>();
//
//        public Demo(Integer id, String value) {
//            this.id = id;
//            this.value = value;
//        }
//
//        public Demo(Integer id, String value, Demo demo) {
//            this.id = id;
//            this.value = value;
//            this.demo = demo;
//        }
//
//        public Demo(Integer id, String value, List<Demo> demos) {
//            this.id = id;
//            this.value = value;
//            this.demos = demos;
//        }
//
//        public void setDemoMaps(String key, Demo demo) {
//            demoMap.put(key, demo);
//        }
//
//    }
//}
