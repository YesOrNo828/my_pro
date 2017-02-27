package com.my.web.service.impl;

import com.my.web.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Created by yexianxun on 2017/2/21.
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public void addUser() {
        System.out.println("add a user");
    }
}
