package com.szyciov.coupon.service.generate.expense;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/** 
* AutoExpenseGenerateCoupon Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 6, 2017</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AutoExpenseGenerateCouponTest {

    @Resource
    private AutoExpenseGenerateCoupon autoExpenseGenerateCoupon;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: autoGenerateCoupon() 
* 
*/ 
@Test
public void testAutoGenerateCoupon() throws Exception { 
//TODO: Test goes here...
    System.out.println("-------------");
    autoExpenseGenerateCoupon.autoGenerateCoupon();
} 

/** 
* 
* Method: generateCoupon(PubCouponRule rule, OrderQueryParam orderParam) 
* 
*/ 
@Test
public void testGenerateCoupon() throws Exception { 
//TODO: Test goes here... 
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
   Method method = AutoExpenseGenerateCoupon.getClass().getMethod("getOrderTbName", Integer.class, Integer.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
