package com.demo.community.sercive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.community.dto.NotificationDTO;
import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.Notification;
import com.demo.community.entity.User;
import com.demo.community.enums.NotificationStatusEnum;
import com.demo.community.enums.NotificationTypeEnum;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.NotificationMapper;
import com.demo.community.sercive.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @author foofoo3
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public PaginationDTO list(int uid, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        //        搜索总数
        QueryWrapper<Notification> countWrapper = new QueryWrapper<>();
        countWrapper.eq("receiver",uid);
        int totalCount = Math.toIntExact(notificationMapper.selectCount(countWrapper));
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

//        Integer offset =size *(page - 1);

        if (totalCount != 0) {
            QueryWrapper<Notification> notificationQueryWrapper = new QueryWrapper<>();
            notificationQueryWrapper.eq("receiver",uid)
                    .orderByDesc("gmt_create");
            List<Notification> notifications = notificationMapper.selectPage(new Page<>(page,size),notificationQueryWrapper).getRecords();
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
    @Override
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
        QueryWrapper<Notification> notificationQueryWrapper = new QueryWrapper<>();
        notificationQueryWrapper.eq("receiver",uid)
                .and(i -> i.eq("status",0).or().eq("status",2));
        return notificationMapper.selectCount(notificationQueryWrapper);
    }

    @Override
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
            UpdateWrapper<Notification> notificationUpdateWrapper = new UpdateWrapper<>();
            notificationUpdateWrapper.set("status",NotificationStatusEnum.ADMIN_READ.getStatus());
            notificationMapper.update(notification,notificationUpdateWrapper);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return notificationDTO;

        }else {
//            状态更新为已读
            UpdateWrapper<Notification> notificationUpdateWrapper = new UpdateWrapper<>();
            notificationUpdateWrapper.set("status",NotificationStatusEnum.ADMIN_READ.getStatus());
            notificationMapper.update(notification,notificationUpdateWrapper);

            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            return notificationDTO;
        }

    }

    @Override
    public int deleteNotificationById(Integer id) {
        return notificationMapper.deleteById(id);
    }

    @Override
    public PaginationDTO adminList(int adminId, Integer page, Integer size) {

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        //        搜索总数
        QueryWrapper<Notification> notificationQueryWrapper = new QueryWrapper<>();
        notificationQueryWrapper.eq("notifier",adminId)
                .and(i -> i.eq("type",3).or().eq("type",4).or().eq("type",5));
        int totalCount = Math.toIntExact(notificationMapper.selectCount(notificationQueryWrapper));
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

//        Integer offset =size *(page - 1);

        if (totalCount != 0) {
            QueryWrapper<Notification> wrapper = new QueryWrapper<>();
            wrapper.eq("notifier",adminId)
                    .and(i -> i.eq("type",3).or().eq("type",4).or().eq("type",5))
                    .orderByDesc("gmtCreate");
            List<Notification> notifications = notificationMapper.selectPage(new Page<>(page,size),wrapper).getRecords();
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
