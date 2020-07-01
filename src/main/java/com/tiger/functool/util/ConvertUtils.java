package com.tiger.functool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tiger.functool.util.annotation.ColumName;
import com.tiger.functool.util.annotation.Recursion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/6/13.
 */
public class ConvertUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);
    private static Map<String, AnnotationFacorty> factorys = new HashMap<>();

    static {
        factorys.put(ColumName.class.getName(), new ColumNameFactory());
        factorys.put(Recursion.class.getName(), new RecursionFactory());
    }


    public static String convertInputStreamIntoString(InputStream in){
        return new BufferedReader(new InputStreamReader(in))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
    }

    public static InputStream convertStringInputStream(String str){
        return  new ByteArrayInputStream(str.getBytes());
    }

    public static Boolean isArray(Object object){
        if(object instanceof List){
            return true;
        }
        return false;
    }





    public static void changeValueByFieldName(String name,Object target,ChangeValueStrategy cvs){
        if(!isArray(target)){
            changeValue(target,name,cvs);
        }else{
            List  objects  = (List) target;
            objects.stream().forEach(val->changeValue(val,name,cvs));
        }
    }


    public static void changeValue(Object target,String name,ChangeValueStrategy cvs){
        Object value = getValueByField(name,target);
        setValueByField(name,target,cvs.changeValueStrategy(value));
    }



    public static <T> T getValueByField(String name,Object target){
        if(ValidationUtils.isNotBlank(name) && target!=null){


            try {
                Field nameField = target.getClass().getDeclaredField(name);
                if(nameField!=null){
                    nameField.setAccessible(true);
                    return (T) nameField.get(target);
                }
            } catch (NoSuchFieldException e) {
//            e.printStackTrace();
            } catch (IllegalAccessException e) {
//            e.printStackTrace();
            }
        }

        return null;
    }
    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    public static void setValueByField(String name,Object target,Object value){
        if(ValidationUtils.isNotBlank(name) && target!=null&& value!=null)
            try {
                Field nameField = target.getClass().getDeclaredField(toLowerCaseFirstOne(name));
                if(nameField!=null){
                    nameField.setAccessible(true);
                    nameField.set(target,value);
                }
            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
    }


    public static <T> List<T> recurListToListT(Object jsonObject, Class<T> clzz, boolean isCollection) {
        List<T> t = new ArrayList<>();
        T temp = null;
        try {
            if (isCollection) {
                t = recurListToListT((List) jsonObject, clzz);
            } else {
                temp = recurMapToT((Map) jsonObject, clzz);
            }
            t.add(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> recurListToListT(List objects, Class<T> clzz) throws IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        T t = clzz.newInstance();
        if (objects != null) {
            for (Object o : objects) {
                if (t instanceof Map) {
                    if (o instanceof Map) {
                        list.add((T) o);
                    }
                } else {
                    t = recurMapToT((Map) o, clzz);
                }
                list.add(t);
            }
        }

        return list;
    }

    public static <T, V> T recurVtoT(V v, Class<T> clzz) {
        String jsonStr = JSONObject.toJSONString(v);
        Map tempMap = JSONObject.parseObject(jsonStr, Map.class);
        try {
            return recurMapToT(tempMap, clzz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static <T> T recurMapToT(Map object, Class<T> clzz) throws IllegalAccessException, InstantiationException {
        Field[] fields = clzz.getDeclaredFields();
        T target = (T) clzz.newInstance();
        for (Field f : fields) {
            Annotation[] an = f.getDeclaredAnnotations();
            for (Annotation a : an) {
                AnnotationFacorty annotationFacorty = factorys.get(a.annotationType().getName());
                if (annotationFacorty != null) {
                    annotationFacorty.setField(f, target, object);
                }
            }
            if (an.length == 0) {
                AnnotationFacorty annotationFacorty = new NoAnnotationFactory();
                annotationFacorty.setField(f, target, object);
            }
        }
        return target;
    }


    /**
     * @Description: 将List<Map> 转换为程序中映射List<T>
     * @Param: list<Map> 要转化的数据源 t要映射的实体类的类
     * @return: list<T> 实体类
     * @Author: 李振增
     * @Date: 2019/1/31
     */

    public static <T> List<T> convertListtoListT(List<Map> list, Class<T> t) {
        List<T> convertResult = new ArrayList<>();
        if (list != null) {
            for (Map m : list) {
                convertResult.add(convertMaptoT(m, t));
            }
        } else {
            try {
                convertResult.add(t.newInstance());
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        return convertResult;
    }

    /**
     * @Description: 将List<T> 转换为程序中映射List<T>
     * @Param: list<T> 要转化的数据源 t要映射的实体类的类
     * @return: list<T> 实体类
     * @Author: lzy
     * @Date: 2019/8/24
     */
    public static <T> List<T> convertListToListT(List<?> list, Class<T> t) {
        List<T> convertResult = new ArrayList<>();
        if (list != null) {
            try {
                String oldString = JSONArray.toJSONString(list);
                convertResult = JSONArray.parseArray(oldString, t);
            } catch (Exception e) {

            }
        }
        return convertResult;
    }

    public static Object getUnitValue(String unit, Object o) {
        Object temp = o;
        if (unit != null && !unit.equalsIgnoreCase("")) {
            temp = new String(o + unit);
        }
        return temp;
    }

    public static Object getFormateValue(String formate, Object o) {
        Object temp = o;
        if (formate != null && !formate.equalsIgnoreCase("")) {
            if ("timestamp".equalsIgnoreCase(formate)) {
                temp = DateUtil.date2TimeStamp((String) o);
            }
            if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(formate)) {
                temp = DateUtil.date2TimeStamp((String) o);
            }
        }

        return temp;
    }

    public static Object getScalaValue(String scala, Object o) {
        Object temp = null;
        if (scala != null && !scala.equalsIgnoreCase("")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < Integer.valueOf(scala); i++) {
                if (i == 0) {
                    sb.append("0");
                } else if (i == 1) {
                    sb.append(".0");
                } else {
                    sb.append("0");
                }
            }
            try {
                DecimalFormat df = new DecimalFormat(sb.toString());
                temp = df.format(new Double(o.toString()));
            } catch (Exception e) {
                temp = o;
            }
            if (temp == null) {
                temp = o;
            }
        } else {
            temp = o;
        }
        return temp;
    }

    /**
     * @Description: 将Map转换为对应的实体类
     * @Param: Map 输入的map t要映射实体类的类
     * @return: 返回一个新的t实体类
     * @Author: 李振增
     * @Date: 2019/1/31
     */

    public static <T> T convertMaptoT(Map source, Class<T> t) {
        if (source == null) {
            try {
                return t.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Set<String> keys = source.keySet();
        Field[] fields = t.getDeclaredFields();
        T var = null;
        try {
            var = t.newInstance();
            for (String key : keys) {
                if (source.get(key) != null && ValidationUtils.isNotBlank(String.valueOf(source.get(key)))) {
                    for (Field f : fields) {
                        try {
                            if (f.getAnnotation(ColumName.class) != null) {
                                ColumName an = f.getAnnotation(ColumName.class);


                                if (an.value().equals(key)) {
                                    try {

                                        Object o = source.get(key);
                                        o = getScalaValue(an.scala(), o);
                                        o = getFormateValue(an.formate(), o);
                                        o = getUnitValue(an.unit(), o);
                                        f.setAccessible(true);
                                        f.set(var, convert(f.getType(),o,an));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            if (f.getName().equals(key)) {
                                try {
                                    Object o = source.get(key);
                                    f.setAccessible(true);
                                    f.set(var, convert(f.getType(),o,null));
                                } catch (Exception e) {
                                   e.printStackTrace();
                                }
                                break;
                            }

                        } catch (Exception e) {
//                            logger.error(String.format("convertMaptoT is error source[key] is %s:error %s", source.get(key), e.toString()));
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
//            logger.error(String.format("convertMaptoT is error source is %s:error %s", source, e.toString()));
        }
        return var;
    }

    public static <T> void init(Object source) {
        Field[] targets = source.getClass().getDeclaredFields();
        for (Field var2 : targets) {
            Unit unit = var2.getAnnotation(Unit.class);
            if (unit != null && unit.unit() != null && !unit.unit().equalsIgnoreCase("")) {
                try {
                    var2.setAccessible(true);
                    String o = (String) var2.get(source);
                    if (o != null && !o.equalsIgnoreCase("")) {
                        String value = (String) var2.get(source);
                        value = String.format(unit.unit(), value);
                        var2.set(source, value);
                    }

                } catch (Exception e) {
                    //                            e.printStackTrace();
                }
            }
        }
    }


    /**
     * @Description: 将source值写入target中 可以通过@Source建立映射
     * @Param: source->target  isOverWrite 是否对已有的值进行覆盖
     * @return: 返回target
     * @Author: 李振增
     * @Date: 2019/1/31
     */
    public static <T> List<T> convert(List sources, Class<T> t) {
        List<T> result = new ArrayList<>();
        for (Object o : sources) {
            try {
                result.add(convert(o, t.newInstance(), t, true));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static <T> T convert(Object source, Object target, boolean isOverwrite) {
       return convert( source,  target, null, isOverwrite);
    }



    /**
     * 映射source对象到target对象
     * 可根据@ColumName注解改变需要映射的source对象字段名
     * @param source
     * @param target
     * @param t
     * @param isOverwrite 是否覆盖原有值
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Object target, Class<T> t, boolean isOverwrite) {
        Field[] targets = target.getClass().getDeclaredFields();
        for (Field targetField : targets) {
            ColumName columNameAn = targetField.getAnnotation(ColumName.class);
            List<String> sourceFieldNames = new ArrayList<>();
            sourceFieldNames.add(targetField.getName());
            String sourceFieldName;
            if (columNameAn != null) {
                Class[] forbidClazz = columNameAn.forbidClass();
                Class sourceClass = source.getClass();
                boolean isForbid = false;
                for (Class clazz : forbidClazz) {
                    if (sourceClass == clazz) {
                        isForbid = true;
                    }
                }
                if (isForbid) {//禁止则跳过
                    continue;
                }
                sourceFieldName = columNameAn.value();
                if(ValidationUtils.isNotBlank(sourceFieldName)){
                    if(sourceFieldName.contains(Constant.COMMA)){
                        sourceFieldNames.addAll(Arrays.asList(sourceFieldName.split(Constant.COMMA)));
                    } else if (sourceFieldName.equals(Constant.MIDLINE)) {
                        sourceFieldNames.clear();
                    } else{
                        sourceFieldNames.add(sourceFieldName);
                    }
                }
                if (columNameAn.values()!=null && columNameAn.values().length>0){
                    sourceFieldNames.addAll(Arrays.asList(columNameAn.values()));
                }
            } else {
                sourceFieldName = targetField.getName();
                sourceFieldNames.add(sourceFieldName);
            }
            for(String var:sourceFieldNames){
                copyValueToAnother(source, target, targetField, var, isOverwrite);
            }

        }



        return (T) target;
    }

    private static void copyValueToAnother(Object source, Object target, Field targetField, String sourceFieldName, Boolean isOverwrite) {
        try {
            Field sourceField = source.getClass().getDeclaredField(sourceFieldName);
            if(sourceField!=null && targetField!=null){
                Type sourceType = sourceField.getGenericType();
                Type targetType= targetField.getGenericType();
                if (sourceType.getTypeName().contentEquals(targetType.getTypeName())) {
                    targetField.setAccessible(true);
                    Object targetObject = targetField.get(target);
                    sourceField.setAccessible(true);
                    Object sourceObject = sourceField.get(source);
                    if (isOverwrite && targetObject != null && sourceObject != null) {
                        targetField.set(target, sourceObject);
                    }

                    if((isOverwrite && targetObject != null && sourceObject instanceof String && ((String) sourceObject).equalsIgnoreCase(Constant.NULL_STR))){
                        targetField.set(target, null);
                    }
                    if (targetObject == null) {
                        targetField.set(target, sourceObject);
                    }
                } else {
                    copySource2Targert(sourceField, targetField, target, source, isOverwrite);
                }
            }

        } catch (Exception e) {
//            logger.error(String.format("copyValueToAnother is error, source is %s,target is %s,targetField is %s,fieldName is %s:error %s",source,target,targetField,null,e.toString()));
        }
    }

    private static void copySource2Targert(Field sourceField, Field targetField, Object target, Object source, boolean isOverwrite) throws IllegalAccessException {
        targetField.setAccessible(true);
        Object o = targetField.get(target);
        sourceField.setAccessible(true);
        Object o1 = sourceField.get(source);
        ColumName columName = sourceField.getAnnotation(ColumName.class);

        if (isOverwrite && o != null) {
            targetField.set(target, convert(targetField.getType(), o1,columName));
        }
        if (o == null && o1 != null) {
            targetField.set(target, convert(targetField.getType(), o1,columName));
        }

    }

    private static Object convert(Class clzz, Object o,ColumName cln) {
        if (clzz.isAssignableFrom(String.class)) {
            if(o instanceof String){
                if(ValidationUtils.isNotBlank(String.valueOf(o))){
                    return String.valueOf(o);
                }
            }
            if(o instanceof Date){
                SimpleDateFormat sdf = new SimpleDateFormat((cln!=null&&cln.formate()!=null)?cln.formate():"yyyy-MM-dd HH:mm:ss");
                TimeZone timeZone;
                if(cln!=null&&cln.timeZone()!=null){
                    timeZone = TimeZone.getTimeZone(cln.timeZone());
                }else{
                    timeZone = TimeZone.getTimeZone("GMT+8:00");
                }

                sdf.setTimeZone(timeZone);
                return sdf.format(o);
            }
            if(ValidationUtils.isNotBlank(String.valueOf(o)) && o instanceof Integer){
                return String.valueOf(o);
            }

        }
        if (clzz.isAssignableFrom(Integer.class)) {
            if(ValidationUtils.isNotBlank(String.valueOf(o))){
                return Integer.valueOf(String.valueOf(o));
            }
        }
        if (clzz.isAssignableFrom(Double.class)) {
            if(ValidationUtils.isNotBlank(String.valueOf(o))){
                return Double.valueOf(String.valueOf(o));
            }
        }
        if (clzz.isAssignableFrom(Float.class)) {
            if(ValidationUtils.isNotBlank(String.valueOf(o))){
                return Float.valueOf(String.valueOf(o));
            }
        }
        if (clzz.isAssignableFrom(Long.class)) {
            if(ValidationUtils.isNotBlank(String.valueOf(o))){
                return Long.valueOf(String.valueOf(o));
            }
        }

        if (clzz.isAssignableFrom(Date.class)) {
            if( o instanceof Date){
                return o;
            }
            if( o instanceof String){
                SimpleDateFormat formatter = new SimpleDateFormat((cln!=null&&cln.formate()!=null)?cln.formate():"yyyy-MM-dd HH:mm:ss");
                ParsePosition pos = new ParsePosition(0);
                return formatter.parse((String) o, pos);
            }
        }
        return null;
    }

    private static String convertDataFormateToFiledName(String name) {
        String[] names = name.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < names.length; i++) {
            if (i == 0) {
                sb.append(names[i]);
            } else {
                sb.append(names[i].substring(0, 1).toUpperCase() + names[i].substring(1));
            }
        }
        return sb.toString();
    }

    private static String getSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String getGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


    public static <T> List<Map> convertT2Map(List<T> ts) {
        List<Map> mapList = new ArrayList<>();
        for (T t : ts) {
            Map temp = convertT2Map(t);
            mapList.add(temp);
        }
        return mapList;
    }

    public static <T> Map convertT2Map(T t) {
        String jsonStr = JSON.toJSONString(t);
        return JSONObject.parseObject(jsonStr, Map.class);
    }
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
