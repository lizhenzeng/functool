package com.tiger.functool.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum BusinessCodeEnum {

    CODE_200("200", "SUCESS"),
    CODE_404("404", "FAIL"),
    CODE_403("403", "PERMISSION DENIED"),
    CODE_500("500", "FAIL"),
    CODE_501("501", "用户信息错误"),
    CODE_502("502", "设备信息错误");

    private String code;
    private String name;

    private static Map<String, BusinessCodeEnum> map = new HashMap<>();

    static {
        Arrays.stream(BusinessCodeEnum.values()).forEach(status -> map.put(status.getCode(), status));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BusinessCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static BusinessCodeEnum getByCode(String code) {
        return map.get(code);
    }
}
