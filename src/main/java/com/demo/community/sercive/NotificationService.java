package com.demo.community.sercive;

import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.User;
import com.demo.community.enums.NotificationTypeEnum;

/**
 * @Author: foofoo3
 */
public interface NotificationService {

    PaginationDTO adminList(int id, Integer page, Integer size);

    void createAdminNotify(int id, int parent_id, int commentator, String name, String content, NotificationTypeEnum deleteComment);

    NotificationDTO read(Long id, User user);

    int deleteNotificationById(Integer id);

    PaginationDTO list(int uid, Integer page, Integer size);
}
