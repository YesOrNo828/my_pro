package com.my.esindex;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yexianxun on 2017/1/16.
 */
public class TestMonitor {
    private static final Logger logger = LoggerFactory.getLogger(TestMonitor.class);

    public static void main(String[] args) {
        logger.info("测试监控邮件");
        MonitorEcCount.MyCountTimeTask myCountTimeTask = new MonitorEcCount.MyCountTimeTask(StringUtils.join(args, ","));
        myCountTimeTask.run();
    }
}
