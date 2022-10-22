package com.demo.community.exception;
/**
 * @author foofoo3
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUNT(2001,"问题不存在，要不然换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"问题跑丢了，无法回复，请刷新重试"),
    NO_LOGIN(2003,"用户未登录，请登录后操作"),
    SYSTEM_ERROR(2004,"出错了，请重试"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在或错误"),
    COMMENT_NOT_FOUNT(2006,"你回复的评论不存在"),
    COMMENT_IS_EMPTY(2007,"回复不能为空"),
    READ_NOTIFICATION_FAIL(2008,"通知信息获取错误"),
    NOTIFICATION_NOT_FOUND(2009,"消息不翼而飞了，请刷新重试"),
    QUESTION_QUERY_NOT_FOUND(2010,"未搜索到问题，换个关键词试试？"),
    USER_NOT_FOUND(2011,"用户不存在，请重试");

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
