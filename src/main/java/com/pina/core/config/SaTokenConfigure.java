package com.pina.core.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.pina.core.enums.ReturnCode;
import com.pina.core.exception.BaseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 配置类
 */
@Configuration
public class SaTokenConfigure {

    /**
     * 注册 [Sa-Token全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()

                // 指定 拦截路由 与 放行路由
                .addInclude("/**").addExclude("/favicon.ico")    /* 排除掉 /favicon.ico */

                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    SaRouter.match("/**", "/user/session", StpUtil::checkLogin);
                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    throw new BaseException(ReturnCode.USER_ERROR_A0200);
                });
    }
}
