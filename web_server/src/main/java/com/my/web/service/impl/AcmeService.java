package com.my.web.service.impl;

import com.my.web.entities.AcmeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yexianxun@corp.netease.com on 2019/5/31.
 */
@Service
public class AcmeService {
    private final AcmeProperties acmeProperties;

    @Autowired
    public AcmeService(AcmeProperties acmeProperties) {
        this.acmeProperties = acmeProperties;
    }

    public String getRemoteAddress() {
        return acmeProperties.getRemoteAddress().getHostAddress();
    }

}
