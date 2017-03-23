package com.my.java.thread;

/**
 * 线程优先级共10级从1-10，
 * 守护线程:在主线程运行结束离开jvm，守护线程也将终止运行
 * Created by yexianxun on 2017/1/18.
 */
public class ThreadDemo2 {

    static class MyThreadPriority extends Thread {
        public MyThreadPriority(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + " : [" + getPriority() + "] : " + i);
            }
        }
    }

    static class Demo {
        public static void main(String[] args) {
            MyThreadPriority myThreadPriority = new MyThreadPriority("t1");
            MyThreadPriority myThreadPriority1 = new MyThreadPriority("t2");
            myThreadPriority.setPriority(1);
            myThreadPriority1.setPriority(10);
            System.out.println(Thread.currentThread().getName() + " : [" + Thread.currentThread().getPriority() + "] : ");
            myThreadPriority.start();
            myThreadPriority1.start();
        }
    }

    static class Demo2 {
        public static void main(String[] args) {
            MyThread myThread = new MyThread("t1");
            MyThreadDaemon myThreadDaemon = new MyThreadDaemon("t2");
            myThreadDaemon.setDaemon(true);
            myThread.set_thread(myThreadDaemon);
            myThread.start();
            myThreadDaemon.start();
        }
    }


    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        private Thread _thread;

        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(3);
                    if (i == 3) {
//                        _thread.interrupt();// 测试守护线程是否可以被中断
                    }
                    System.out.println(this.getName() + "(isDaemon=" + this.isDaemon() + ")  " + _thread.getName() + ".isAlive=" + isAlive() + " " + i);
                }
            } catch (InterruptedException e) {
            }
        }

        public void set_thread(Thread _thread) {
            this._thread = _thread;
        }
    }

    static class MyThreadDaemon extends Thread {
        public MyThreadDaemon(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5000; i++) {
                    Thread.sleep(1);
                    System.out.println(getName() + "(isDaemon=" + isDaemon() + " priority=" + getPriority() + ")" + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
