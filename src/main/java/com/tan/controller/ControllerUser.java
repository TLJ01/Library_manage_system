package com.tan.controller;

import com.tan.pojo.Result;
import com.tan.pojo.User;
import com.tan.service.ServiceUser;
import com.tan.utils.JwtUtils;
import com.tan.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午10:56
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(String username, String password) {
        User user = serviceUser.check(username);
        if(user != null) {
            return Result.error("用户名已存在");
        }
        serviceUser.register(username,password);
        return Result.success();
    }


    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){

        //1,查询该用户是否存在
        User user1 = serviceUser.check(user.getUsername());

        if(user1 == null) {
            //2,不存在返回错误信息
            return Result.error("用户名错误");
        }
        //3,存在,判断密码
        if(Md5Util.getMD5String(user.getPassword()).equals(user1.getPassword())) {
            HashMap<String , Object> claims = new HashMap<>();
            claims.put("id",user1.getId());
            claims.put("username", user1.getUsername());
            claims.put("password", user1.getPassword());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }

        return Result.error("密码错误");

    }

}
