package com.demo.community.sercive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.community.entity.User;
import com.demo.community.mapper.*;
import com.demo.community.sercive.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author foofoo3
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikeStarMapper likeStarMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public int insertUser(String name,int number, String password) {
        log.info("注册name:{}", name);
        log.info("注册number:{}", number);
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("number", number);
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("name", name);
//        查询账号,用户名是否重复
        if (userMapper.selectOne(wrapper1)!=null){
            return 0;
        }else if (userMapper.selectOne(wrapper2)!=null){
            return 2;
        }else if (password.length() < 6){
            return 3;
        }else {
            String defaultDescription = "这个人很懒，什么都没留下";
            String defaultFace = "/images/dface.jpg";
//            密码 MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            User user = new User();
            user.setName(name);
            user.setNumber(number);
            user.setPassword(md5Password);
            user.setDescription(defaultDescription);
            user.setFace(defaultFace);
            int result = userMapper.insert(user);
            if (result == 1) {
                return 1;
            }
        }
        return 1;
    }

//    登录核对
    @Override
    public int loginUser(int number, String password){

        log.info("正在登录number:{}", number);

        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        User user = userMapper.selectOne(wrapper);
        log.info("正在登录user:{}", user);

        if (user==null) {
            return 0;
        }else if(!user.getPassword().equals(md5Password)){
            return 2;
        }else if (user.getPassword().equals(md5Password)){
            return 1;
        }
        return 1;
    }

//    账号查询用户
    @Override
    public User getUserByNumber(int number){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        return userMapper.selectOne(wrapper);
    }

//    uid查找用户
    @Override
    public User getUserByUid(int uid){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return userMapper.selectOne(wrapper);
    }

//    名字匹配用户名
    @Override
    public List<User> getUsersByName(String name){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() == 0){
            return new ArrayList<>();
        }else {
            return users;
        }
    }

    @Override
    public int updateUser(User user, String name, String description, String oldPassword, String newPassword1, String newPassword2, HttpServletResponse response) {

        String oldMd5Password = DigestUtils.md5DigestAsHex(oldPassword.getBytes(StandardCharsets.UTF_8));
        String newMd5Password = DigestUtils.md5DigestAsHex(newPassword2.getBytes(StandardCharsets.UTF_8));

        if (oldPassword  == ""){
            oldPassword = null;
        }else if (!Objects.equals(oldMd5Password, user.getPassword())){
            try {
                response.sendRedirect("resultRegister?=4");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }else if (newPassword1  == "" && newPassword2 == ""){
            try {
                response.sendRedirect("resultRegister?=5");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        if (newPassword1  == ""){
            newPassword1  = null;
        }else if (newPassword1.length() < 6){
            try {
                response.sendRedirect("resultRegister?=5");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }else if (oldPassword == null || !oldMd5Password.equals(user.getPassword())){
            try {
                response.sendRedirect("resultRegister?=4");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }else if (!newPassword1.equals(newPassword2)){
            try {
                response.sendRedirect("resultRegister?=6");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }else if (newPassword1.equals(oldPassword)){
            try {
                response.sendRedirect("resultRegister?=8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        if (newPassword2  == ""){
            newPassword2  = null;
        }else if (!newPassword2.equals(newPassword1)){
            try {
                response.sendRedirect("resultRegister?=6");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }else {
            user.setPassword(newMd5Password);
        }

        if (name == ""){
            user.setName(user.getName());
        }else {
            user.setName(name);
        }

        if (description  == ""){
            user.setDescription(user.getDescription());
        }else {
            user.setDescription(description);
        }

        return userMapper.updateById(user);
    }

    @Override
    public int updateUserFace(int uid,String filePath) {
        User user = userMapper.selectById(uid);
        user.setFace(filePath);
       return userMapper.updateById(user);
    }

    @Override
    public List<User> getUserByType() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("type", 0);
        return userMapper.selectList(wrapper);
    }

    @Override
    public int banUser(int uid) {
        User user = userMapper.selectById(uid);
        user.setType(0);
        return userMapper.updateById(user);
    }

    @Override
    public int unbanUser(int uid) {
        User user = userMapper.selectById(uid);
        user.setType(1);
        return userMapper.updateById(user);
    }

    @Override
    public int cancellationUser(int uid) {
        Map<String,Object> questionMap = new HashMap<>();
        questionMap.put("creator",uid);
        questionMapper.deleteByMap(questionMap);

        Map<String,Object> commentMap = new HashMap<>();
        commentMap.put("commentator",uid);
        commentMapper.deleteByMap(commentMap);

        Map<String,Object> likeStarMap = new HashMap<>();
        likeStarMap.put("uid",uid);
        likeStarMapper.deleteByMap(likeStarMap);

        Map<String,Object> notificationMap = new HashMap<>();
        notificationMap.put("notifier",uid);
        notificationMapper.deleteByMap(notificationMap);

        Map<String,Object> userMap = new HashMap<>();
        userMap.put("uid",uid);
        return userMapper.deleteByMap(userMap);
    }

}
