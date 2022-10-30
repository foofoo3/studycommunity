package com.demo.community.sercive;

import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.Comment;
import com.demo.community.entity.Notification;
import com.demo.community.entity.User;
import com.demo.community.enums.NotificationStatusEnum;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.NotificationMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @author foofoo3
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(int uid, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        //        搜索总数
        int totalCount = notificationMapper.countByUid(uid);
        //        计算页码总大小
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        //        超出页数范围判断,防止越界
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset =size *(page - 1);

        if (totalCount != 0) {
            List<Notification> notifications = notificationMapper.listByUid(uid, offset, size);
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            if (notifications.size() == 0) {
                return paginationDTO;
            }

            for (Notification notification : notifications) {
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(notification, notificationDTO);
                notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
                notificationDTOList.add(notificationDTO);
            }

            paginationDTO.setData(notificationDTOList);

            return paginationDTO;

        }else {
            paginationDTO.setData(new ArrayList<>());

            return paginationDTO;
        }
    }

    //    创建通知方法
    public void createAdminNotify(int notifier, int outerId, int receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType) {
        Notification notification = new Notification();
        notification.setGmt_create(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(notifier);
        notification.setReceiver(receiver);
        notification.setNotifier_name(notifierName);
        notification.setOuter_title(outerTitle);
        notification.setStatus(NotificationStatusEnum.ADMIN_UNREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public Long unreadCount(int uid) {
        return notificationMapper.unreadCountByUid(uid);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getUid())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        if (notification.getStatus() == 2){
//            管理员消息
            notification.setStatus(NotificationStatusEnum.ADMIN_READ.getStatus());
            notificationMapper.updateStatus(notification);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return notificationDTO;

        }else {
//            状态更新为已读
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
            notificationMapper.updateStatus(notification);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return notificationDTO;
        }

    }

    public int deleteNotificationById(Integer id) {
        return notificationMapper.deleteById(id);
    }

    public PaginationDTO adminList(int adminId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        //        搜索总数
        int totalCount = notificationMapper.countByAdminId(adminId);
        //        计算页码总大小
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }
        //        超出页数范围判断,防止越界
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset =size *(page - 1);

        if (totalCount != 0) {
            List<Notification> notifications = notificationMapper.listByAdminId(adminId, offset, size);
            List<NotificationDTO> notificationDTOList = new ArrayList<>();
            if (notifications.size() == 0) {
                return paginationDTO;
            }

            for (Notification notification : notifications) {
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(notification, notificationDTO);
                notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
                notificationDTOList.add(notificationDTO);
            }

            paginationDTO.setData(notificationDTOList);

            return paginationDTO;

        }else {
            paginationDTO.setData(new ArrayList<>());

            return paginationDTO;
        }

    }
}
