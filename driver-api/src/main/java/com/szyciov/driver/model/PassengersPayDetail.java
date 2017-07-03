package com.szyciov.driver.model;

import java.math.BigDecimal;

/**
 * Created by ZF on 2017/5/4.
 */
public class PassengersPayDetail {
    private String userId; // 乘客ID
    private double balance; // 乘客余额

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
