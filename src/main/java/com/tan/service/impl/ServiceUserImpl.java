package com.tan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tan.mapper.MapperUser;
import com.tan.pojo.Result;
import com.tan.pojo.User;
import com.tan.service.ServiceUser;
import com.tan.utils.JwtUtils;
import com.tan.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 上午10:58
 */
@Service
public class ServiceUserImpl extends ServiceImpl<MapperUser,User> implements ServiceUser {

    private static final Logger log = LoggerFactory.getLogger(ServiceUserImpl.class);
    @Autowired
    private MapperUser mapperUser;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {

        //1,查询该用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.mapperUser.selectOne(queryWrapper);
        //2,存在
        if(loginUser!=null){

            //判断密码是否正确
            //获取数据库中的md5密文
            String rawPassword = loginUser.getPassword();
            //获取登录输入的密码-->加密后进行对比
            String loginPassword = Md5Util.getMD5String(user.getPassword());

            log.info("rawPassword:{}",rawPassword);
            log.info("loginPassword:{}",loginPassword);

            if(!loginPassword.equals(rawPassword)){
                return Result.error("密码错误");
            }

            //存在生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            //不存密码
            String jwt = JwtUtils.generateJwt(claims);
            //  将用户信息存入redis
            loginUser.setPassword(null);//密码不存进去
            log.info("Ser_token+jwt:{}","token"+jwt);
            redisTemplate.opsForValue().set("token:"+jwt, JSONObject.toJSONString(loginUser));

            return Result.success(jwt);
        }
        return Result.error("该用户不存在,请先注册");
    }


    /**
     * 注册
     * @param
     * @param
     */
    @Override
    public void register(String username, String password,Integer roleId) {
        //对密码进行加密存储
        String md5String = Md5Util.getMD5String(password);
        log.info("md5String:" + md5String);
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5String);
        user.setRoleId(roleId);
        mapperUser.insert(user);
    }

    /**
     * 判断该用户是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean isExist(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = mapperUser.selectOne(queryWrapper);
        return user!=null?true:false;
    }


}
