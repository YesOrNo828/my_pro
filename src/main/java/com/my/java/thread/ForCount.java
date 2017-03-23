package com.my.java.thread;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yexianxun on 2017/1/17.
 */
public class ForCount {

    public static void forCount() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
    }
}
