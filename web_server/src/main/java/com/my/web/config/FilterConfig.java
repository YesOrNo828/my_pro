package com.my.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yexianxun@corp.netease.com on 2019/7/19.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new OpenIdFilter());
        registration.addUrlPatterns("/otherApi/*");
        registration.setName("OpenIdFilter");
        registration.setOrder(1);
        return registration;
    }
}
