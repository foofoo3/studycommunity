package com.demo.community.enums;
/**
 * @author foofoo3
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1),
    ADMIN_UNREAD(2),
    ADMIN_READ(3);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
