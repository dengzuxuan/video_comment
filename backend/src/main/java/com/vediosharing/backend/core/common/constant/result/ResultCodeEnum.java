package com.vediosharing.backend.core.common.constant.result;

import lombok.Getter;

/**
 * @ClassName ResultCodeEnum
 * @Description 返回值枚举
 * @Author Colin
 * @Date 2023/10/26 0:54
 * @Version 1.0
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),

    USER_NOT_EXIST(2001,"用户不存在"),

    USER_PASSWORD_WRONG(2002,"用户密码错误"),
    USER_NAME_PARAM_WRONG(2003,"用户名格式有误"),
    USER_NAME_ALREADY_EXIST(2004,"用户已存在"),
    USER_NAME_NOT_EXIST(2005,"该用户不存在"),
    PASSWORD_NOT_EMPTY(2006,"密码不能为空"),
    PASSWORD_NOT_EQUAL(2007,"两次密码输入不等"),
    PASSWORD_PARAM_WRONG(2008,"密码格式有误"),
    SEXUAL_PARAM_WRONG(2009,"性别格式有误"),
    PHOTO_PARAM_WRONG(2010,"头像格式有误"),
    EMAIL_PARAM_WRONG(2011,"邮箱格式有误"),
    NICKNAME_PARAM_WRONG(2012,"昵称格式有误"),


    VIDEO_PARAMS_WRONG(3001,"视频上传格式有误"),
    PHOTO_PARAMS_WRONG(3002,"图片上传格式有误"),
    FRIEND_ADD_WRONG(4001,"你们已经是好友了") ;

    private final Integer code;

    private final String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
