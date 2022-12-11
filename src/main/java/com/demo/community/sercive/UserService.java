package com.demo.community.sercive;

import com.demo.community.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: foofoo3
 */
public interface UserService {

    List<User> getUserByType();

    int banUser(int uid);

    int unbanUser(int uid);

    int cancellationUser(int uid);

    int updateUserFace(int uid, String filePath);

    User getUserByUid(int uid);

    List<User> getUsersByName(String searchName);

    int loginUser(int number, String password);

    User getUserByNumber(int number);

    int insertUser(String name, int number, String password);

    int updateUser(User user, String name, String description, String oldPassword, String newPassword1, String newPassword2, HttpServletResponse response);
}
