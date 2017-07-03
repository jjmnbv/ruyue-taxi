package com.szyciov.message;

import cn.jpush.api.push.model.PushPayload;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 交接班成功消息处理
 * Created by LC on 2017/3/13.
 */
public class ProcessedMessage extends BaseMessage
{


    private List<String> userids;

    private String content;

    private String messagetype;

    private String hinttype;


    //接受注入参数用
    public ProcessedMessage(){}

    public ProcessedMessage(String userid,String content,String messagetype,String hinttype){
        this.userids= new ArrayList<String>();
        this.userids.add(userid);
        this.content = content;
        this.messagetype = messagetype;
        this.hinttype = hinttype;
    }

    @Override
    public void send() {
        //车辆回收成功
        if(PeDrivershiftEnum.SHIFT_TYPE_RECYCLE.code.equals(messagetype)){
            SMMessageUtil.send(userids,content);
            this.recycleMessage();
        }else if(PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code.equals(messagetype)){
            //如果推送关键字为空，则只发短信
            if(this.hinttype==null){
                SMMessageUtil.send(userids,content);
            }else {
                this.processedMessage();
            }
        }

    }
    /**
     * 交班成功信息
     */
    private void processedMessage(){
        PushPayload pushload4ios = PushObjFactory.creatProcessed4Ios(content,hinttype,userids);
        PushPayload pushload4android = PushObjFactory.creatProcessed4Android(content,hinttype,userids);
        AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
    }
    /**
     * 车辆回收消息
     */
    private void recycleMessage(){
        PushPayload pushload4ios = PushObjFactory.creatProcessed4Ios(content,hinttype,userids);
        PushPayload pushload4android = PushObjFactory.creatProcessed4Android(content,hinttype,userids);
        AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
    }


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

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getHinttype() {
        return hinttype;
    }

    public void setHinttype(String hinttype) {
        this.hinttype = hinttype;
    }

}
 