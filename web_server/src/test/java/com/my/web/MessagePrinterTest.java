package com.my.web;

import com.my.web.entities.AcmeProperties;
import com.my.web.service.impl.AcmeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yexianxun@corp.netease.com on 2019/5/31.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessagePrinterTest {
    @Autowired
    private MessagePrinter messagePrinter;
    @Autowired
    private AcmeService acmeService;
    @Autowired
    private AcmeProperties acmeProperties;


    @Test
    public void printMessage() {
        messagePrinter.printMessage();
    }

    @Test
    public void printServerName() {
        System.out.println("------");
        System.out.println(acmeService.getRemoteAddress());
        System.out.println(acmeProperties.getRemoteAddress().getHostAddress());
    }
}
