package com.my.java.thread;

/**
 * 1、通过继承Thread和实现接口Runnable进行对比同步代码块的区别。
 * 2、new Thread Lambda 表达式
 * Created by yexianxun on 2017/1/17.
 */
public class ThreadDemo1 extends Thread {

    @Override
    public synchronized void run() {
        ForCount.forCount();
    }

    public static void main(String[] args) {
//        Thread threadDemo1 = new ThreadDemo1();
//        Thread threadDemo12 = new ThreadDemo1();
//        threadDemo1.start();
//        threadDemo12.start();
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable, "t1");
        Thread thread2 = new Thread(runnable, "t2");
        thread1.start();
        thread2.start();
//        new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Thread.currentThread().getName() + " : " + i);
//            }
//        }, "t3").start();
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                ForCount.forCount();
            }
        }
    }

}
