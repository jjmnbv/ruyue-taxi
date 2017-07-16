package com.szyciov.supervision.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.api.service.BaseService;

/**
 * Created by admin on 2017/7/10.
 */
@Component
//@RabbitListener(queues = "")
public class PlatformCompanyOperationReceiver
{

	private @Autowired
	BaseService basicService;

////	@RabbitHandler
//	public void consumer(String message) throws Exception
//	{
//		EntityInfoList<CompanyOperateInfo> response = basicService.sendCompanyOperateInfo(message);
//
//		if (!response.isAllSuccess())
//		{
//			// TODO 处理未成功上传的数据
//		}
//
//	}
}
