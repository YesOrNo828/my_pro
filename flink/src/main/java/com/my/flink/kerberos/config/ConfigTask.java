package com.my.flink.kerberos.config;

import com.sun.tools.javac.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * Created by yexianxun@corp.netease.com on 2021/6/7.
 */
public class ConfigTask {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigTask.class);
	private static String fileName = "config.txt";
	private static byte[] datas = null;
	private static final int LENGTH = 4446;

	private static Path confCachePath;

	public static void main(String[] args) throws IOException {
		String path = "/config/krb/" + fileName;
		URL in = ConfigTask.class.getResource(path);
		datas = readBytesFromFile(in.getPath());
		int length = datas.length;
		Assert.check(length == LENGTH);
		LOG.info("{}, length={}", in.getPath(), length);


		String workPath = Paths.get("").toAbsolutePath().toString();
		String confPath = String.format("%s/%s", workPath, "arctic_krb_conf");
		confCachePath = Paths.get(confPath);

		if (!confCachePath.toFile().exists()) {
			confCachePath.toFile().mkdirs();
		} else {
			for (File file : Objects.requireNonNull(confCachePath.toFile().listFiles())) {
				file.delete();
			}
		}

		int threads = 10;
		Task[] tasks = new Task[threads];
		for (int i = 0; i < threads; i++) {
			ConfigGenerator configGenerator = new ConfigGenerator();
			String threadName = "task-" + i;
			Task task = new Task(configGenerator, threadName);
			tasks[i] = task;
		}
		runAll(tasks);
	}

	private static void runAll(Task[] tasks) {
		Assert.checkNonNull(tasks);
		for (Task task : tasks) {
			task.start();
		}
	}

	static class Task extends Thread {
		ConfigGenerator configGenerator;
		String threadName;

		public Task(ConfigGenerator configGenerator, String threadName) {
			super(threadName);
			this.configGenerator = configGenerator;
			this.threadName = threadName;
		}

		@Override
		public void run() {
			String newFile = null;
			try {
				newFile = configGenerator.saveConfInPath(confCachePath, fileName, datas);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			byte[] ds = readBytesFromFile(newFile);
			int length = ds.length;
			LOG.info("{}, length={}", threadName, length);
		}
	}

	private static byte[] readBytesFromFile(String filePath) {
		try {
			return toByteArray(new FileInputStream(filePath));
		} catch (IOException e) {
			throw new UncheckedIOException("Read config failed:" + filePath, e);
		}
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		return count > 2147483647L ? -1 : (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output) throws IOException {
		return copyLarge(input, output, new byte[4096]);
	}

	public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
		long count = 0L;

		int n;
		for (; -1 != (n = input.read(buffer)); count += n) {
			output.write(buffer, 0, n);
		}

		return count;
	}
}
