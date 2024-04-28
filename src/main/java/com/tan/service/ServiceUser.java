package com.tan.service;

import com.tan.pojo.User;

public interface ServiceUser {
    /**
     * 登录
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询该用户是否存在
     * @param username
     * @return
     */
    User check(String username);


    /**
     * 注册
     * @param username
     * @param password
     */
    void register(String username, String password);
}
