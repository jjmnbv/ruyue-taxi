package com.szyciov.carservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.GiveIntegrationDao;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.util.SMMessageUtil;

@Service("giveIntegrationService")
public class GiveIntegrationService {
	private GiveIntegrationDao giveIntegrationDao;

	@Resource(name="giveIntegrationDao")
	public void setGiveIntegrationDao(GiveIntegrationDao giveIntegrationDao) {
		this.giveIntegrationDao = giveIntegrationDao;
	}
	public PubDictionary giveIntegration(){
		/*
		 * 查询字典表里面的字段，来判断活动是否终止，当次字段非空且value为0时表示活动已经终止
		 * text 0没有发过短信,1已经发过短信了
		 */
		PubDictionary pubDictionary = giveIntegrationDao.findKeyValue();
		if(pubDictionary != null){
			if(pubDictionary.getValue().equals("0") && pubDictionary.getText().equals("0")){
				//给所有用户发送短信
				//获取所有用户，并发送短信
				List<String> listUsers= giveIntegrationDao.findAllUsers();
				String messageContent = "您好！如约百万优惠即将派完，赶快邀请伙伴们参与赢取最后福利吧";
				//测试短信发送
				/*List<String> listUsers = new ArrayList<String>();
				listUsers.add("15717134245");
				listUsers.add("18317152443");
				listUsers.add("15717131425");*/
				// 发送短信
				if (listUsers != null && listUsers.size() != 0) {
					SMMessageUtil.send(listUsers, messageContent);
				}
				//发送完短信之后把text状态改为1,表示已经发送过短信了
				giveIntegrationDao.updateKeyText();
			}
		}
		return null;
	}
}
