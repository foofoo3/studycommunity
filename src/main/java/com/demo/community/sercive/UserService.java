package com.demo.community.sercive;

import com.demo.community.entity.User;
import com.demo.community.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author foofoo3
 */

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private LikeStarMapper likeStarMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    public int insertUser(String name,int number, String password) {
        log.info("注册name:{}", name);
        log.info("注册number:{}", number);
//        查询账号,用户名是否重复
        if (userMapper.SelectByNumber(number)!=null){
            return 0;
        }else if (userMapper.SelectByName(name)!=null){
            return 2;
        }else if (password.length() < 6){
            return 3;
        }else {
            String defaultDescription = "这个人很懒，什么都没留下";
            String defaultFace = "https://tva3.sinaimg.cn/thumbnail/007E7MVRly1h7ng9bwrwmj30io0iodgw.jpg";
//            密码 MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            int result = userMapper.InsertUser(name, number, md5Password,defaultDescription,defaultFace);
            if (result == 1) {
                return 1;
            }
        }
        return 1;
    }

//    登录核对
    public int loginUser(int number, String password){

        log.info("正在登录number:{}", number);

        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        User user = userMapper.SelectByNumber(number);
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
    public User getUserByNumber(int number){
        return userMapper.SelectByNumber(number);
    }

//    uid查找用户
    public User getUserByUid(int uid){
        return userMapper.SelectByUid(uid);
    }

//    名字匹配用户名
    public List<User> getUsersByName(String name){
        return userMapper.SelectByName(name);
    }

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

        int i = userMapper.updateUser(user);

        return i;
    }

    public int updateUserFace(int uid,String filePath) {
       return userMapper.updateUserFace(uid,filePath);
    }

    public List<User> getUserByType() {
        return userMapper.getUserByType();
    }

    public int banUser(int uid) {
        return userMapper.banUser(uid);
    }

    public int unbanUser(int uid) {
        return userMapper.unbanUser(uid);
    }

    public int cancellationUser(int uid) {
        questionMapper.deleteByCreator(uid);
        commentMapper.deleteCommentByCommentator(uid);
        likeStarMapper.deleteLikeOrStarByUid(uid);
        notificationMapper.deleteByNotifier(uid);

        return userMapper.cancellationUser(uid);
    }

}
