package com.my.annotation.inherited;

import java.lang.annotation.*;

/**
 * Created by yexianxun@corp.netease.com on 2019/5/29.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InheritedDemo {
}
