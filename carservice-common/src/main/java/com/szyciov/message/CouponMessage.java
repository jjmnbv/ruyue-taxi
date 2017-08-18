package com.szyciov.message;

import java.util.List;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.dto.coupon.CouponInfoDTO;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;

/**
 * 抵用券类消息
 * @author admin
 *
 */
public class CouponMessage extends BaseMessage {


	private String messagetype;

	private CouponInfoDTO couponInfoDTO;

	private String msg;

	private List<String> phoneList;

	/**
	 * 此构造函数只用于接受注解用
	 */
	public CouponMessage() {

	}

	/**
	 * 不需要额外参数的消息构造函数
	 * @param messagetype
	 */
	public CouponMessage(CouponInfoDTO couponInfoDTO, String messagetype,List<String> phoneList){
		this.couponInfoDTO = couponInfoDTO;
		this.messagetype = messagetype;
		this.phoneList = phoneList;

	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	@Override
	public void send() {
		this.sendCouponMessage();
	}
	
	/**
	 * 乘客端派单失败的提示消息
	 */
	private void sendCouponMessage() {
		PushPayload pushload4android = PushObjFactory.createCouponObj4Android(phoneList,couponInfoDTO);
		PushPayload pushload4ios = PushObjFactory.createCouponObj4IOS(phoneList, couponInfoDTO);
		AppMessageUtil.send(pushload4ios, pushload4android, AppMessageUtil.APPTYPE_PASSENGER);
	}

	public CouponInfoDTO getCouponInfoDTO() {
		return couponInfoDTO;
	}

	public void setCouponInfoDTO(CouponInfoDTO couponInfoDTO) {
		this.couponInfoDTO = couponInfoDTO;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<String> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<String> phoneList) {
		this.phoneList = phoneList;
	}
}
