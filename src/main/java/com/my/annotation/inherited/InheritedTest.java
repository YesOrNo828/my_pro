package com.my.annotation.inherited;

/**
 * Created by yexianxun@corp.netease.com on 2019/5/29.
 */
public class InheritedTest {
    public static void main(String[] args) {
        InheritedDemo inheritedDemo = Child.class.getAnnotation(InheritedDemo.class);
        System.out.println("1=" + (inheritedDemo == null));
        System.out.println("2=" + Parent.class.isAnnotationPresent(InheritedDemo.class));
        System.out.println("3=" + Child.class.isAnnotationPresent(InheritedDemo.class));
    }
}
