package com.my.java.thread;

/**
 * Created by yexianxun on 2017/1/18.
 */
public class ThreadInterrupt extends Thread {

    public ThreadInterrupt(String name) {
        super(name);
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                System.out.println(this.getName() + " : " + this.getState() + " : " + ++i);
                this.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupt threadInterrupt = new ThreadInterrupt("t1");
        System.out.println(threadInterrupt.getName() + " : " + threadInterrupt.getState() + " created");
        threadInterrupt.start();
        System.out.println(threadInterrupt.getName() + " : " + threadInterrupt.getState() + " started");
        Thread.sleep(300);
        System.out.println(threadInterrupt.getName() + " : " + threadInterrupt.isInterrupted() + " interrupted");
        threadInterrupt.interrupt();
//        Thread.sleep(300);
        System.out.println(threadInterrupt.getName() + " : " + threadInterrupt.isInterrupted() + " interrupted");
    }
}
