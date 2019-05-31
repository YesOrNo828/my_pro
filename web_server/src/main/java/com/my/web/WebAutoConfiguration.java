package com.my.web;

import com.my.web.entities.AcmeProperties;
import com.my.web.service.MessageService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yexianxun@corp.netease.com on 2019/5/30.
 */
@Configuration
@EnableConfigurationProperties(AcmeProperties.class)
public class WebAutoConfiguration {

    @Bean
    MessageService mockMessageService() {
        return () -> "hello world";
    }

//    @Bean
//    public ServerProperties serverProperties() {
//        return new ServerProperties();
//    }
}
