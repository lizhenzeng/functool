package com.tiger.functool.util;

import lombok.Data;

import java.io.Serializable;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class HttpResult implements Serializable {

    private String code;
    private String message;
    private Object data;

    public HttpResult(String code, Object data){
        this.code = code;
        this.data = data;
    }
    public HttpResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpResult fail(String message) {
        return new HttpResult(BusinessCodeEnum.CODE_500.getCode(), message);
    }

    public static HttpResult fail(String message, Object data) {
        return new HttpResult(BusinessCodeEnum.CODE_500.getCode(), message, data);
    }

    public static HttpResult success() {
        return new HttpResult(BusinessCodeEnum.CODE_200.getCode(), BusinessCodeEnum.CODE_200.getName());
    }

    public static HttpResult success(Object data) {
        return new HttpResult(BusinessCodeEnum.CODE_200.getCode(), BusinessCodeEnum.CODE_200.getName(), data);
    }
}