package com.my.web;

import com.my.web.service.MessageService;
import com.my.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yexianxun on 2017/2/20.
 */
@Component
public class MessagePrinter {
    final MessageService messageService;

    @Autowired
    public MessagePrinter(MessageService messageService) {
        this.messageService = messageService;
    }
    @Autowired
    private UserService userService;

    public void printMessage() {
        System.out.println(messageService.getMessage());
        userService.addUser();
    }
}
