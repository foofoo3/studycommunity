package com.demo.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUNT(2001,"问题不存在，要不然换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"问题跑丢了，无法回复，请刷新重试"),
    NO_LOGIN(2003,"用户未登录，请登录后操作");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
