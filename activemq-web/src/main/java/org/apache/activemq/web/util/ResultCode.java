package org.apache.activemq.web.util;

/**
 * 系统状态码
 */
public class ResultCode {
    //默认 系统异常
    public static final int SYS_ERROR = 1000;
    //请求成功
    public static final int SUCCESSFUL = 0;
    public static final int FAILURE = -1;

    //账户异常相关
    public static final int ACCOUNT_ERROR = 2000;
    //登录异常
    public static final int ACCOUNT_ERROR_LOGIN = 2100;
    //密码错误
    public static final int ACCOUNT_ERROR_LOGIN_PASSWORD = 2101;
    //账户不存在
    public static final int ACCOUNT_ERROR_LOGIN_EMPTY_ACCOUNT = 2102;
    //输入错误
    public static final int ACCOUNT_ERROR_LOGIN_INPUT_ERROR = 2103;
    //未匹配到toekn
    public static final int ACCOUNT_ERROR_LOGIN_TOKEN_EMTPY = 2104;




}
