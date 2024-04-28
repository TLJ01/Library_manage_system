package com.tan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tan.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MapperUser extends BaseMapper<User> {
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Select("select * from user where username=#{username} and password=#{password}")
    User select(User user);
}
