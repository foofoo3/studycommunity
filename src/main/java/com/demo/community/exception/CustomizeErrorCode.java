package com.demo.community.exception;
/**
 * @author foofoo3
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUNT(2001,"问题不存在或已被删除，要不然换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"问题跑丢了或已被删除，无法回复，请刷新重试"),
    NO_LOGIN(2003,"用户未登录，请登录后操作"),
    SYSTEM_ERROR(2004,"出错了，请重试"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在或已被删除"),
    COMMENT_NOT_FOUNT(2006,"你回复的评论不存在或已被删除"),
    COMMENT_IS_EMPTY(2007,"回复不能为空"),
    READ_NOTIFICATION_FAIL(2008,"通知信息获取错误"),
    NOTIFICATION_NOT_FOUND(2009,"消息不翼而飞了或已被删除，请刷新重试"),
    QUESTION_QUERY_NOT_FOUND(2010,"未搜索到问题或已被删除，换个关键词试试？"),
    USER_NOT_FOUND(2011,"用户不存在，请重试"),
    IMAGE_TYPE_NOT_FOUND(2012,"图片格式错误，请上传正确格式"),
    ADMIN_NOT_FOUND(2013,"管理员未登录,您没有权限打开此页面");

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
