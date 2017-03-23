package com.my.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by yexianxun on 2017/2/22.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class ApplicationWeb {

    public static void main(String[] args) {
//        SpringApplication.run(ApplicationWeb.class, args);

        SpringApplication springApplication = new SpringApplication();
        ApplicationContext applicationContext = springApplication.run(ApplicationWeb.class, args);
        MessagePrinter messagePrinter = (MessagePrinter) applicationContext.getBean("messagePrinter");
        messagePrinter.printMessage();


    }
}
