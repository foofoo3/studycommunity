package com.demo.community.sercive;

import com.demo.community.entity.User;
import com.demo.community.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        log.info("currentUser:{}",currentUser);
        return currentUser;
    }

//    uid查找用户
    public User getUserByUid(int uid){
        User currentUser = userMapper.SelectByUid(uid);
        log.info("currentUser:{}",currentUser);
        return currentUser;
    }

    //    名字查询用户
    public User getUserByName(String name){
        User currentUser = userMapper.SelectByName(name);
        log.info("currentUser:{}",currentUser);
        return currentUser;
    }

}
