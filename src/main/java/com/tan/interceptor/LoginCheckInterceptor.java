package com.tan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.tan.pojo.Result;
import com.tan.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by TanLiangJie
 * Time:2024/4/27 下午1:29
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        System.out.println("preHandle...");

        //获取请求的url--->这里用StringBuilder的getRequestURL
        String url = req.getRequestURL().toString();
        log.info("请求的url:{}",url);
        //判断url是否存在login,存在就放行
        if(url.contains("login")){
            log.info("登录操作,放行..");
            return true;
        }

        //不是登录,则获取token
        String jwt = req.getHeader("token");

        //判断token是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("当前未登录,jwt为空,请重新登录");
            Result notLogin = Result.error("NOT_LOGIN");
            resp.getWriter().write(JSONObject.toJSONString(notLogin));//响应给前端,前端通过这个跳转到登录页面
            return false;
        }

        //解析token
        try {
            JwtUtils.parseJWT(jwt);}
        catch (Exception e) {//解析失败
            e.printStackTrace();
            log.info("解析token失败");
            Result notLogin = Result.error("NOT_LOGIN");
            resp.getWriter().write(JSONObject.toJSONString(notLogin));//响应给前端,前端通过这个跳转到登录页面
            return false;
        }

        //放行
        log.info("令牌合法,放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
