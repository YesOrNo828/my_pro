package com.my.flink.kerberos.config;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by yexianxun@corp.netease.com on 2021/6/7.
 */
public class ConfigGenerator {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigGenerator.class);
	private static final Object lock = new Object();

	public synchronized String saveConfInPath(Path confPath, String confName, byte[] confValues) throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		String confFile = String.format("%s/%s", confPath.toString(), confName);

		synchronized (lock) {
			if (!Paths.get(confFile).toFile().exists()) {
				LOG.info("{} do copy {}.", threadName, confFile);
				try (FileOutputStream fileOutputStream = new FileOutputStream(confFile)) {
					ByteStreams.copy(new ByteArrayInputStream(confValues), fileOutputStream);
					LOG.info("{} finish copy.", threadName);
				} catch (IOException e) {
					throw new UncheckedIOException("Fail to save conf files in work space", e);
				}
			} else {
				LOG.info("{} {} is exists.", threadName, confFile);
			}
		}


		return confFile;
	}
}
