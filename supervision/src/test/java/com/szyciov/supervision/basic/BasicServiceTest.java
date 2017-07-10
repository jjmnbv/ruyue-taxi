package com.szyciov.supervision.basic;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.szyciov.supervision.Constants;
import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.basic.request.PlatformCompanyOperation;
import com.szyciov.supervision.basic.service.BasicService;
import com.szyciov.supervision.util.EntityInfoList;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * Created by admin on 2017/7/10.
 */
public class BasicServiceTest extends SupervisionApplicationTests
{

	private @Autowired BasicService basicService;

	@Test
	public void test() throws IOException
	{
		List<PlatformCompanyOperation> list = new ArrayList<PlatformCompanyOperation>();
		PlatformCompanyOperation info = new PlatformCompanyOperation("Test", "RY", 1, 1, 1, Long.valueOf(LocalDateTime.now().format(Constants.DATE_FORMAT)));
		list.add(info);
		EntityInfoList<PlatformCompanyOperation> infoList = new EntityInfoList<PlatformCompanyOperation>(list);
		String result = JSONUtil.toJackson(infoList);
		EntityInfoList<PlatformCompanyOperation> response = basicService.sendPlatformCompanyOperation(result);

		assertThat(response, notNullValue());
	}
}
