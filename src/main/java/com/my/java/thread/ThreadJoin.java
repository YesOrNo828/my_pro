package com.my.java.thread;

/**
 * join() 会使当前线程等待，直到子线程执行完毕，再继续执行当前线程
 * Created by yexianxun on 2017/1/17.
 */
public class ThreadJoin {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " run");
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " run");
            try {
                System.out.println(Thread.currentThread().getName() + " sleep 5000ms");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        thread.start();
        System.out.println(Thread.currentThread().getName() + " continue");
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finish");

    }
}
