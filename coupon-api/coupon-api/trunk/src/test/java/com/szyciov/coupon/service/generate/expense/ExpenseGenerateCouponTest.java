package com.szyciov.coupon.service.generate.expense;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.szyciov.entity.coupon.PubCouponRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** 
* ExpenseGenerateCoupon Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 3, 2017</pre> 
* @version 1.0 
*/ 
public class ExpenseGenerateCouponTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: validRule(PubCouponRule rule, GenerateCouponParam param, PubCouponActivityDto activity)
    *
    */
    @Test
    public void testValidRule() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: validHaved(PubCouponActivityDto activity, String userId)
    *
    */
    @Test
    public void testValidHaved() throws Exception {
    //TODO: Test goes here...
    }


    /**
    *
    * Method: validOrderCount(PubCouponRule rule, OrderQueryParam param1, PubCouponActivityDto activity)
    *
    */
    @Test
    public void testValidOrderCount() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("validOrderCount", PubCouponRule.class, OrderQueryParam.class, PubCouponActivityDto.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getStartDt(PubCouponActivityDto activity, PubCouponRule rule)
    *
    */
    @Test
    public void testGetStartDt() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("getStartDt", PubCouponActivityDto.class, PubCouponRule.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: validOrderMoney(PubCouponRule rule, OrderQueryParam param1, PubCouponActivityDto activity)
    *
    */
    @Test
    public void testValidOrderMoneyForRuleParam1Activity() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("validOrderMoney", PubCouponRule.class, OrderQueryParam.class, PubCouponActivityDto.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: validOrderMoneySum(PubCouponRule rule, OrderQueryParam param1, PubCouponActivityDto activity)
    *
    */
    @Test
    public void testValidOrderMoneySum() throws Exception {
    //TODO: Test goes here...
    //
    //    try {
    //
    //        PubCouponRule rule = new PubCouponRule();
    //        rule
    //       Method method = ExpenseGenerateCoupon.class
    //           .getMethod("validOrderMoneySum", PubCouponRule.class,
    //               OrderQueryParam.class, PubCouponActivityDto.class);
    //       method.setAccessible(true);
    //       method.invoke( , rule);
    //    } catch(NoSuchMethodException e) {
    //
    //
    //    } catch(IllegalAccessException e) {
    //
    //
    //    } catch(InvocationTargetException e) {
    //
    //
    //    }

    }

    /**
    *
    * Method: savePouconOrderMoneyOnce(PubCouponRule rule, OrderQueryParam param1, PubCouponActivityDto activity)
    *
    */
    @Test
    public void testSavePouconOrderMoneyOnce() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("savePouconOrderMoneyOnce", PubCouponRule.class, OrderQueryParam.class, PubCouponActivityDto.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: validOrderMoney(PubCouponRule rule, Double money)
    *
    */
    @Test
    public void testValidOrderMoneyForRuleMoney() throws Exception {
    //TODO: Test goes here...
        try {
           PubCouponRule rule = new PubCouponRule();
           rule.setConsumemoneysingelfull(12d);
           Double money = 11d;
           ExpenseGenerateCoupon expenseGenerateCoupon = new ExpenseGenerateCoupon();
           Method method = ExpenseGenerateCoupon.class
                          .getDeclaredMethod("validOrderMoney", PubCouponRule.class, Double.class);
           method.setAccessible(true);
           method.invoke(expenseGenerateCoupon,rule,money);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
    *
    * Method: saveMoneyCoupon(PubCouponActivityDto activity, String userId)
    *
    */
    @Test
    public void testSaveMoneyCoupon() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("saveMoneyCoupon", PubCouponActivityDto.class, String.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getOrderTbName(Integer serviceType, Integer ruletarget)
    *
    */
    @Test
    public void testGetOrderTbName() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = ExpenseGenerateCoupon.getClass().getMethod("getOrderTbName", Integer.class, Integer.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
