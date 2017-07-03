package com.szyciov.message;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定解绑消息处理
 * Created by LC on 2017/3/13.
 */
public class DriverVehicleBindMessage extends BaseMessage
{


    private List<String> userids;

    private String content;

    private BindingStateEnum messagetype;
    
    private String visualtype;

    private String hinttype;

    private String operation;

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BindingStateEnum getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(BindingStateEnum messagetype) {
        this.messagetype = messagetype;
    }

	public String getVisualtype() {
		return visualtype;
	}

	public void setVisualtype(String visualtype) {
		this.visualtype = visualtype;
	}

    public String getHinttype() {
        return hinttype;
    }

    public void setHinttype(String hinttype) {
        this.hinttype = hinttype;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    //接受注入参数用
    public DriverVehicleBindMessage(){}

    public DriverVehicleBindMessage(String userid, String content,
                                    BindingStateEnum messagetype,
                                    String visualtype){
        this.userids= new ArrayList<String>();
        this.userids.add(userid);
        this.content = content;
        this.messagetype = messagetype;
        this.visualtype = visualtype;
    }

    //供解绑 处理交班时调用
    public DriverVehicleBindMessage(String userid, String content,
                                    String hinttype,
                                    String operation){
        this.userids= new ArrayList<String>();
        this.userids.add(userid);
        this.content = content;
        this.hinttype = hinttype;
        this.operation = operation;
    }



    @Override
    public void send() {
        //绑定成功推送
        if(BindingStateEnum.BINDING.equals(messagetype)){
            this.bindMessage();
        }else if(BindingStateEnum.UN_BINDING.equals(messagetype)){
            this.unBindMessage();
        }else if(hinttype.equals(PushObjFactory.HINT_SHIFT_DRIVER_UNBIND)){
            this.pendingMessage();
        }

    }
    /**
     * 解除绑定
     */
    private void unBindMessage(){
        PushPayload pushload4ios = PushObjFactory.createUnBindCarObj4IOS(userids,content,visualtype);
        PushPayload pushload4android = PushObjFactory.createUnBindCarObj4Android(userids,content,visualtype);
        AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
    }
    /**
     * 绑定成功
     */
    private void bindMessage(){
        PushPayload pushload4ios = PushObjFactory.createBindCarObj4IOS(userids,content);
        PushPayload pushload4android = PushObjFactory.createBindCarObj4Android(userids,content);
        AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
    }


    /**
     * 绑定成功
     */
    private void pendingMessage(){
        PushPayload pushload4ios = PushObjFactory.creatPending4Ios(content,hinttype,operation,userids);
        PushPayload pushload4android = PushObjFactory.creatPending4Android(content,hinttype,operation,userids);
        AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
    }

}
 