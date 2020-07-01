package com.tiger.functool.util;

/**
 * Created by Administrator on 2019/4/28.
 */


public class Constant {
    public static final Long ZERO =Long.valueOf(0);
    public static final Long ONE = Long.valueOf(1);
    public static final String NULL="";
    public static final String NULL_STR="NULL";
    public static final String EMPTY ="";
    public static final String MIDLINE = "-";
    public static final String SPRING_PROFILE_ACTIVE_BETA = "beta";
    public static final String SPRING_PROFILE_ACTIVE_DEV = "dev";
    public static final String ERR_MSG_TOKEN_EXP = "token已过期";
    public static final String ERR_MSG_TOKEN_ERR = "token解析错误";
    public static final String ERR_MSG_TOKEN_EMPTY = "token内容为空";
    public static final String ERR_MSG_USERNAME_EMPTY = "用户名为空";
    public static final String ERR_MSG_LOGIN_OTHER_PLACE = "账号在别处登录";
    public static final String CREATE_METHOD = "CREATE_METHOD";
    public static final String UPDATE_METHOD = "UPDATE_METHOD";
    public static final String APPLICATION = "source-client";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String SUBSCRIBE = "subscribe";
    public static final Integer DEFAULT_BIND_PHONE_NO  = 5;



    /**====设备类型标识========**/
    public static final String PC_DEVICE = "PC";
    public static final String APP_DEVICE = "APP";
    public static final String WX_APPLETS = "wxApplets";
    /**====设备授权相关常量=====**/
    public static final int COMPANY_MAX_APP_COUNT = 10;
    public static final int COMPANY_MAX_PC_COUNT = 10;
    /**====设备授权唯一标识常量=====**/
    public static  final String DEVICE_TYPE = "deviceType";
    public static  final String USER_NAME = "username";
    public static  final String MAC = "mac";
    public static  final String APP_ID = "appId";
    public static  final String CPU = "cpu";
    public static  final String COMPANY_NO = "companyNo";
    public static  final String PLATFORM = "platform";
    /**====HTTP配置常量==========**/
    public static final String HTTP_CONTENT_TYPE = "Content-type";
    public static final String HTTP_HEADER_CHARSET = "text/html;charset=UTF-8";
    public static final String HTTP_CHARSET = "UTF-8";
    public static final String HTTP_AUTHORIZATION = "Authorization";
    public static final String HTTP_AUTH = "auth";
    public static final String HTTP_BEARER = "Bearer ";
    public static final String HTTP_STATUS_401 = "401";
    public static final String HTTP_RESPONSE_DATA = "data";
    public static final String HTT_OPEN_LOGIN = "/backend/login";


    /**====业务判断标识常量==========**/
    public static final String BIZ_DEVICETYPE = "deviceType";

    /**====业务模块功能常量==作为Redis key的标识========**/
    public static String keyLoginMoudle = "login";
    public static String getEmployeeIdUrl = "http://192.168.1.191:8183/backend/toc/createId/employee";
    /**====系统常量========**/
    public static final String SYS_SEPARATE_CHART = "_";
    public static final String SYS_CONTEXT_CHART  = "/backend";
    /**====SQL查询匹配符号========**/
    public static final String likeSymbol = "%";
    public static final String POINT= ".";
    public static final String COMMA= ",";
    public static final String REG_COMMA= "\\,";
    public static final String LINE_BREAK= "\n";
    public static final String SEPARATION_CHARACTER= "|";
    public static final String SEPARATION_CHARACTER_REGX= "\\|";
}
