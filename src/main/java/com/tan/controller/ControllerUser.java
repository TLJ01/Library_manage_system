package com.tan.controller;

import com.tan.pojo.Result;
import com.tan.pojo.User;
import com.tan.service.ServiceUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午10:56
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    /*
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(String username, String password,Integer roleId) {

        //判断该用户名是否存在
        if(serviceUser.isExist(username)) {
            return Result.error("用户名已存在");
        }
        //用户名可用,继续注册
        serviceUser.register(username,password,roleId);
        return Result.success();
    }


    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result rs = serviceUser.login(user);
        //rs中可以是用户不存在,密码错误,或者jwt正确信息
        return rs;
    }


}
