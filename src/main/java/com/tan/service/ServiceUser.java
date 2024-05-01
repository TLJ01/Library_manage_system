package com.tan.service;

import com.tan.pojo.Result;
import com.tan.pojo.User;

import java.util.Map;

public interface ServiceUser {
    /**
     * 登录
     *
     * @param user
     * @return
     */
    Result login(User user);


    /**
     * 注册
     * @param username
     * @param password
     */
    void register(String username, String password,Integer roleId);

    /**
     * 判断注册时用户名是否存在
     * @param username
     * @return
     */
    Boolean isExist(String username);
}
