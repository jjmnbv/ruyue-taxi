package com.xxkj.passenger.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.xxkj.passenger.wechat,com.szyciov.passenger")
public class PassengerWechatApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassengerWechatApiApplication.class, args);
	}
}
