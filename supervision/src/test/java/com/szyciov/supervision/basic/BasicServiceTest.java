package com.szyciov.supervision.basic;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervision.api.basic.CompanyOperateInfo;
import com.szyciov.supervision.basic.service.BasicService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.szyciov.supervision.SupervisionApplicationTests;

import com.szyciov.supervision.util.EntityInfoList;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * Created by admin on 2017/7/10.
 */
public class BasicServiceTest extends SupervisionApplicationTests
{

	private @Autowired
	BasicService basicService;

	@Test
	public void test() throws IOException
	{
		List<CompanyOperateInfo> list = new ArrayList<CompanyOperateInfo>();
		CompanyOperateInfo info = new CompanyOperateInfo(4,5);
		list.add(info);
		EntityInfoList<CompanyOperateInfo> infoList = new EntityInfoList<CompanyOperateInfo>(list);
		String result = JSONUtil.toJackson(infoList);
		EntityInfoList<CompanyOperateInfo> response = basicService.sendCompanyOperateInfo(result);
		System.out.println(response);

		assertThat(response, notNullValue());
	}
}
