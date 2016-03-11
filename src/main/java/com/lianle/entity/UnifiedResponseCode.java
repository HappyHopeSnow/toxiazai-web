package com.lianle.entity;

/**
 * Description: <br>
 *
 * @author <a href=mailto:lianle1@jd.com>连乐</a>
 * @date 2016/3/11 14:51
 */
public interface UnifiedResponseCode {

    /** 成功（RC表示ResponseCode，加这个前缀是以免跟ActionSupport定义的响应码冲突）*/
    public static final int RC_SUCC = 200;
    /** 失败（客户端原因造成）*/
    public static final int RC_FAIL = 400;
    /** 401 已经加精|参数不合法|表情或表情包不可用(不可用、已经下架等)*/
    public static final int RC_FAIL_401 = 401;
    /** 402 加精次数已经用完|等级太低|没有申请或邀请*/
    public static final int RC_FAIL_402 = 402;
    /** 450  失败（客户端原因造成）*/
    public static final int RC_NO_AUTHORITY = 450;
    /** 失败（服务端原因造成）*/
    public static final int RC_ERROR = 500;

}
