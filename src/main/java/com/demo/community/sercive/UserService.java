package com.demo.community.sercive;

import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    public int insertUser(String name,int number, String password) {
        log.info("name:{}", name);
        log.info("number:{}", number);
        log.info("password:{}", password);
//        查询账号,用户名是否重复
        if (userMapper.SelectByNumber(number)!=null){
            return 0;
        }else if (userMapper.SelectByName(name)!=null){
            return 2;
        }else if (password.length() < 6){
            return 3;
        }else {
            String defaultDescription = "这个人很懒，什么都没留下";
            String defaultFace = "https://tvax1.sinaimg.cn/thumbnail/007E7MVRly1h68twaikmyj30jt0juabj.jpg";
            int result = userMapper.InsertUser(name, number, password,defaultDescription,defaultFace);
            if (result == 1) {
                return 1;
            }
        }
        return 1;
    }

//    登录核对
    public int loginUser(int number, String password){
        log.info("number:{}", number);
        log.info("password:{}", password);

        User user = userMapper.SelectByNumber(number);
        log.info("user:{}", user);

        if (user==null) {
            return 0;
        }else if(!user.getPassword().equals(password)){
            return 2;
        }else if (user.getPassword().equals(password)){
            return 1;
        }
        return 1;
    }

//    账号查询用户
    public User getUserByNumber(int number){
        User currentUser = userMapper.SelectByNumber(number);
        return currentUser;
    }

//    uid查找用户
    public User getUserByUid(int uid){
        User currentUser = userMapper.SelectByUid(uid);
        return currentUser;
    }

    //    名字查询用户
    public User getUserByName(String name){
        User currentUser = userMapper.SelectByName(name);
        return currentUser;
    }

    public int updateUser(User user, String name, String description, String oldPassword, String newPassword1, String newPassword2, HttpServletResponse response) {

        if (oldPassword  == ""){
            oldPassword = null;
        }else if (!Objects.equals(oldPassword, user.getPassword())){
            try {
                response.sendRedirect("resultRegister?=4");
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
        }else if (oldPassword == null || !oldPassword.equals(user.getPassword())){
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
            user.setPassword(newPassword2);
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
        return userMapper.cancellationUser(uid);
    }
}
