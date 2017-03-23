package com.my.java.thread;

/**
 * wait() notify() 两个方法都需在synchronized代码中。
 * 线程有以下四个状态：新建、就绪（start）、等待（未获得锁）、运行（获取锁）、死亡（释放锁&执行完毕）状态
 * sleep() 会使线程进入休眠状态，但不会释放同步锁，休眠时间可以指定，休眠完毕后等待cpu调度进入运行状态
 * Created by yexianxun on 2017/1/17.
 */
public class ThreadWait {

    static class ThreadA extends Thread {
        public ThreadA(String name) {
            super(name);
        }

        @Override
        public synchronized void run() {
            System.out.println(currentThread().getName() + " call notify()");
            notify();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadA threadA = new ThreadA("threadA");
        synchronized (threadA) {
            try {
                System.out.println(Thread.currentThread().getName() + " start threadA");
                threadA.start();
                System.out.println(Thread.currentThread().getName() + " call threadA wait()");
                threadA.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }

    }
}
