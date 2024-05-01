package com.tan.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by TanLiangJie
 * Time:2024/5/1 上午11:00
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //创建一个普通的拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加一个分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        ///返回
        return interceptor;
    }

}
