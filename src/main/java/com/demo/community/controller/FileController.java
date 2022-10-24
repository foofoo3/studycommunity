package com.demo.community.controller;

import com.demo.community.entity.User;
import com.demo.community.exception.CustomizeErrorCode;
import com.demo.community.exception.CustomizeException;
import com.demo.community.sercive.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author foofoo3
 */
@Controller
public class FileController {
    @Autowired
    private UserService userService;

//上传头像
    @PostMapping("/file/uploadFace")
    public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User user=(User) session.getAttribute("user");
        int begin = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
        int last = file.getOriginalFilename().length();
        //获得文件后缀名 type
        String type = file.getOriginalFilename().substring(begin, last);

//        判断格式
        if (".jpg".equals(type) || ".png".equals(type) || ".jpeg".equals(type) || ".doc".equals(type)){
            //        判断上传是第几次
            int frequency;
            if (Objects.equals(user.getFace(), "https://tvax1.sinaimg.cn/thumbnail/007E7MVRly1h68twaikmyj30jt0juabj.jpg") || user.getFace().length() > 40){
                frequency = 1;
            }else {
                String face = user.getFace();
                int point = face.indexOf(".");
                int e = face.indexOf("e",face.indexOf("e")+1);
                int oldFrequency = Integer.parseInt(face.substring(e + 1, point));
                frequency = oldFrequency + 1;
            }

//            路径写入数据库 文件写入磁盘
            String fileName=user.getUid()+"face"+frequency+type;
            String filePath = "/images/"+fileName;
            String filePathReal = "E:/test/studycommunity/src/main/resources/static/images/" +fileName;
            File file1 = new File(filePathReal);


            Thread back = new Thread(() -> {
//                上传时先跳转回首页
                try {
                    response.sendRedirect("/resultRegister?=10");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


            Thread update = new Thread(() -> {
//                等待一分钟 已经上传后刷新数据库与session
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                路径写入数据库
                int res = userService.updateUserFace(user.getUid(), filePath);
//                成功写入数据库后刷新session
                if (res !=0) {
                    session.removeAttribute("user");
                    User newUser = userService.getUserByUid(user.getUid());
                    session.setAttribute("user", newUser);
                }
            });

            try {
//                本地写入磁盘
                file.transferTo(file1);
//                开启线程back 跳转回首页
                back.start();
//                开启线程update 等待上传完成后刷新数据库session
                update.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new CustomizeException(CustomizeErrorCode.IMAGE_TYPE_NOT_FOUND);
        }

    }
}
