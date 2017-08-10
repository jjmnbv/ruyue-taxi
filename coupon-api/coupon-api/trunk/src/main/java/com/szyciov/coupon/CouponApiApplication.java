package com.szyciov.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot主方法
 * @author LC
 */
@SpringBootApplication
@MapperScan("com.szyciov.coupon.mapper")
@ComponentScan
public class CouponApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponApiApplication.class, args);
	}
}
