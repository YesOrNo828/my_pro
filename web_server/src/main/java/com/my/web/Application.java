package com.my.web;

import com.my.web.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * Created by yexianxun on 2017/2/20.
 */
@Configuration
@ComponentScan
public class Application {

    @Bean
    MessageService mockMessageService() {
        return new MessageService() {
            @Override
            public String getMessage() {
                return "hello world";
            }
        };
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        MessagePrinter printer = applicationContext.getBean(MessagePrinter.class);
        printer.printMessage();
    }
}
