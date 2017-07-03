package com.szyciov.carservice.util.sendservice.sendrules.impl;

import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;

/**
 * 抽象出公用的方法和属性
 * 使用的时候在提出也可以
 * Created by zhu on 2017/6/19.
 */
public abstract class AbstractSendRule implements SendRuleHelper {

    public String pushlimit;

    /**
     * 提供公用的方法给公用方法用
     * @return
     */
    public String getPushlimit() {
        return pushlimit;
    }
}
