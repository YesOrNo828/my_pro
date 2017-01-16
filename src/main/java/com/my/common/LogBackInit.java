package com.my.common;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * Linux 无法正确找到LogBack 配置文件 需要指定 tomcat 环境不需要指定
 */
public class LogBackInit {
    private static void init(InputStream is) {
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            configurator.doConfigure(is);
            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Linux 无法正确找到LogBack 配置文件 需要指定 tomcat 环境不需要指定
     *
     * @param filePath 相对classpath 的路径
     */
    public static void initLogBack(String filePath) {
        if ("/".equals(File.separator)) {
            try {
                InputStream is = LogBackInit.class.getClassLoader().getResourceAsStream(filePath);
                LogBackInit.init(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
