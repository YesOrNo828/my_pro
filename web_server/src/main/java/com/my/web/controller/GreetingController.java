package com.my.web.controller;

import com.my.web.MessagePrinter;
import com.my.web.service.MessageService;
import com.my.web.service.UserService;
import com.my.web.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yexianxun on 2017/2/22.
 */
@RestController
@RequestMapping(value = {"api", "otherApi"})
public class GreetingController {
    private static final AtomicInteger id = new AtomicInteger();

    @Autowired
    private UserService userService;
    @Autowired
    private MessagePrinter messagePrinter;
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = {"/greeting"})
    public Greeting hello(@RequestParam(name = "name", defaultValue = "world") String name) {
        userService.addUser();
        return new Greeting(id.incrementAndGet(), name);
    }

    @RequestMapping("/print")
    public String print() {
        return messagePrinter.getMessage();
    }

    @RequestMapping("/print1")
    public String print1() {
        return messageService.getMessage();
    }

    public static void main(String[] args) {
        final AtomicInteger id = new AtomicInteger();
        while (true) {
            Integer i = new Integer(id.incrementAndGet());
            System.out.println(i);
        }
    }
}
