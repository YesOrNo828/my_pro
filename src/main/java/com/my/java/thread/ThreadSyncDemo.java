package com.my.java.thread;

/**
 * synchronized修饰this 表示对该对象进行同步锁，修饰object 表示对该变量进行同步锁，对象不进行同步锁
 * static synchronized表示全局锁 无论多少实例访问都会共享该锁
 * Created by yexianxun on 2017/1/17.
 */
public class ThreadSyncDemo {

    static class Count {
        Object object = new Object();
        Object nextObject = new Object();

        public void count() {
            synchronized (object) {
                ForCount.forCount();
            }
        }

        public synchronized void unCount() {
            synchronized (nextObject) {
                ForCount.forCount();
            }
        }

        public static synchronized void syAllCount() {
            ForCount.forCount();
        }
    }

    public static void main(String[] args) {
        Count count = new Count();
        new Thread(() -> count.count(), "t1").start();
        new Thread(() -> count.unCount(), "t2").start();
        new Thread(() -> count.syAllCount(), "t3").start();
    }

}
