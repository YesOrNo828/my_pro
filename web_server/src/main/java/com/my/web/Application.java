package com.my.web;

import com.my.web.service.MessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by yexianxun on 2017/2/20.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        MessagePrinter printer = applicationContext.getBean(MessagePrinter.class);
        printer.printMessage();*/
        SpringApplication.run(Application.class, args);
    }
}
