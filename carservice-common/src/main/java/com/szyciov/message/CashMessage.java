package com.szyciov.message;

import java.util.List;

import com.szyciov.util.PushObjFactory;
import org.apache.commons.lang.StringUtils;

import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.SMMessageUtil;

import cn.jpush.api.push.model.PushPayload;

public class CashMessage extends BaseMessage {

    //发送类型：司机/乘客
	private String pushtype;
    //发送电话
	private List<String> phones;
    //短信内容
	private String smscontent;
    //推送内容
    private String pushContent;
    //
    private List<String> tag_ands;
    //消息标识字段
    private String hinttype;
	public CashMessage(){
	}
	
	/**
	 * 设置推送的信息参数
	 * @param pushtype
	 * @param pushContent
	 * @param tag_ands
	 */
	public void setPushParam(String pushtype,String pushContent,
                             List<String> tag_ands,List<String> phones,String hinttype){
		this.pushtype = pushtype;
		this.pushContent = pushContent;
		this.tag_ands = tag_ands;
        this.phones = phones;
        this.hinttype = hinttype;
	}
	
	/**
	 * 设置发送短信的信息参数
	 * @param smscontent
	 */
	public void setSMSParam(String smscontent){
		this.smscontent = smscontent;
	}

	@Override
	public void send() {
		if(phones!=null&&phones.size()>0&&StringUtils.isNotBlank(smscontent)){
			SMMessageUtil.send(phones, smscontent);
		}
        this.sendPushContent();
	}


    /**
     * 发起接班推送给交班司机
     */
    private void sendPushContent(){
        //发送系统推送
        PushPayload push4android = PushObjFactory.creatHintObj4Android(pushContent,hinttype, phones, tag_ands);
        PushPayload push4ios = PushObjFactory.creatHintObj4IOS(pushContent,hinttype, phones, tag_ands);
        AppMessageUtil.send(push4ios,push4android,pushtype);
    }

	public String getPushtype() {
		return pushtype;
	}

	public void setPushtype(String pushtype) {
		this.pushtype = pushtype;
	}


	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String getSmscontent() {
		return smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent;
	}

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public List<String> getTag_ands() {
        return tag_ands;
    }

    public void setTag_ands(List<String> tag_ands) {
        this.tag_ands = tag_ands;
    }

    public String getHinttype() {
        return hinttype;
    }

    public void setHinttype(String hinttype) {
        this.hinttype = hinttype;
    }
}
