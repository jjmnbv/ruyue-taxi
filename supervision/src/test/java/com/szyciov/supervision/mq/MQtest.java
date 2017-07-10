package com.szyciov.supervision.mq;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.szyciov.supervision.SupervisionApplicationTests;


public class MQtest extends SupervisionApplicationTests
{

	private @Autowired AmqpTemplate amqpTemplate;
	@Test
	public void test() throws InterruptedException{
		amqpTemplate.convertAndSend("test_queues", "2222222222222222222222222");
		TimeUnit.SECONDS.sleep(30);
	}
}














