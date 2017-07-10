package com.szyciov.supervision.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.basic.request.PlatformCompanyOperation;
import com.szyciov.supervision.basic.service.BasicService;
import com.szyciov.supervision.util.EntityInfoList;

/**
 * Created by admin on 2017/7/10.
 */
@Component
@RabbitListener(queues = "test_queues")
public class PlatformCompanyOperationReceiver
{

	private @Autowired BasicService basicService;

	@RabbitHandler
	public void consumer(String message) throws Exception
	{
		EntityInfoList<PlatformCompanyOperation> response = basicService.sendPlatformCompanyOperation(message);

		if (!response.isAllSuccess())
		{
			// TODO 处理未成功上传的数据
		}

	}
}
