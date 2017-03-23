package com.my.java.juc;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by yexianxun on 2017/1/20.
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(10);
        System.out.println(atomicLong.compareAndSet(10, 11));
        System.out.println(atomicLong.compareAndSet(11, 12));
    }
}
