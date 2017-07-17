/**
 * 
 */
package com.ry.taxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ry.taxi.config.MyBatisMapperScannerConfig;
import com.ry.taxi.config.WebMvcConfig;


/**
 * @Title:RyMeterServiceApplication.java
 * @Package com.ry.taxi
 * @Description
 * @author zhangdd
 * @date 2017年7月6日
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@RestController
@EnableWebMvc
@EnableAutoConfiguration
@EnableScheduling
@AutoConfigureAfter({MyBatisMapperScannerConfig.class})
@ComponentScan({"com.ry.taxi.*.*","com.ry.taxi.*"})
@MapperScan("com.ry.taxi.*.mapper")
@SpringBootApplication(scanBasePackages="com.ry.taxi")
public class RyMeterServiceApplication extends WebMvcConfig {
	
	public static void main(String[] args) {
		SpringApplication.run(RyMeterServiceApplication.class, args);
	}

}
