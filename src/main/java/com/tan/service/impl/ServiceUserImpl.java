package com.tan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tan.mapper.MapperUser;
import com.tan.pojo.User;
import com.tan.service.ServiceUser;
import com.tan.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午10:58
 */
@Service
public class ServiceUserImpl implements ServiceUser {

    private static final Logger log = LoggerFactory.getLogger(ServiceUserImpl.class);
    @Autowired
    private MapperUser mapperUser;

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
       return mapperUser.select(user);
    }

    /**
     * 查询该用户
     * @param username
     * @return
     */
    @Override
    public User check(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return mapperUser.selectOne(queryWrapper);
    }

    /**
     * 注册
     * @param username
     * @param password
     */
    @Override
    public void register(String username, String password) {
        //对密码进行加密存储
        String md5String = Md5Util.getMD5String(password);
        log.info("md5String:" + md5String);
        mapperUser.insert(new User(null,username,md5String));
    }
}
