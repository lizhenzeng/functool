package com.tiger.functool.util;

import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static boolean isNotBlank(String str){

        if(str!=null && str.trim().length()>0 && !str.equalsIgnoreCase("null")){
            return true;
        }
        return false;
    }
    public static boolean isNumeric(String str){
            for (int i = str.length();--i>=0;){
                    if (!Character.isDigit(str.charAt(i))){
                            return false;
                        }
                }
            return true;}

    public static boolean isMobile(String str) {
        boolean b = false;
        if(isNotBlank(str)){
            Pattern p = null;
            Matcher m = null;
            String s2="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";// 验证手机号
            if(isNotBlank(str)){
                p = Pattern.compile(s2);
                m = p.matcher(str);
                b = m.matches();
            }
        }
        return b;
    }

    public static boolean isDid(String str) {
        boolean b = false;
        if(isNotBlank(str)){
            String s2="^[8][\\d]{7}$";// 验证手机号
            if(isNotBlank(str)){
                Pattern p = Pattern.compile(s2);
                Matcher m = p.matcher(str);
                b = m.matches();
            }
        }
        return b;
    }

    public static List<String> getPermssionStr(String permssion){
        String s2 ="[A-Z]{2}[0-9]+";
        Pattern p = Pattern.compile(s2);
        Matcher m = p.matcher(permssion);
        List<String> res = new ArrayList<>();
        while(m.find()){
            res.add(m.group());
        }
        return res;
    }


    public static void strIfIntPutOrThrowException(Object target,String fieldName,String value,String errorMsg){
        try {
            if(ValidationUtils.isNotBlank(value)){
                Integer res = Integer.valueOf(value);
                Field field = target.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target,res);
            }else{
            }
        }catch (Exception e){
        }
    }

    public static Integer convertInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }
        return null;
    }


}