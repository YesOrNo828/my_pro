package com.my.java;

import java.text.SimpleDateFormat;

/**
 * Created by yexianxun on 2017/1/4.
 */
public class Date {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println(sdf.format(new java.util.Date()));
    }
}
