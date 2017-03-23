package com.my.java.thread;

/**
 * yield() 并不会释放锁
 * Created by yexianxun on 2017/1/17.
 */
public class ThreadYield {


    static class MyRunnableYield implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                    if (i % 4 == 0) {
                        Thread.yield();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        MyRunnableYield myRunnableYield = new MyRunnableYield();
        new Thread(myRunnableYield, "t1").start();
        new Thread(myRunnableYield, "t2").start();
    }

}
