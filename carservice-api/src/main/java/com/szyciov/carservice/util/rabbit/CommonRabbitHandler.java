package com.szyciov.carservice.util.rabbit;

import com.alipay.util.Base64;
import com.rabbitmq.client.Channel;
import com.szyciov.carservice.dao.PubJpushlogDao;
import com.szyciov.entity.CommonRabbitData;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.enums.CommonRabbitEnum;
import com.szyciov.enums.PubJpushLogEnum;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.http.HttpMethod;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/6/7.
 */
public class CommonRabbitHandler implements ChannelAwareMessageListener {

    private static final Logger LOGGER = Logger.getLogger(CommonRabbitHandler.class);

    /**
     * ios司机AUTHORIZATION
     */
    private static final String AUTHORIZATION_DRIVER_IOS = "Basic " + Base64.encode((SystemConfig.getSystemProperty("appkey_driver_ios")
            + ":" + SystemConfig.getSystemProperty("mastersecret_driver_ios")).getBytes());

    /**
     * android司机AUTHORIZATION
     */
    private static final String AUTHORIZATION_DRIVER_ANDROID = "Basic " + Base64.encode((SystemConfig.getSystemProperty("appkey_driver_android")
            + ":" + SystemConfig.getSystemProperty("mastersecret_driver_android")).getBytes());

    /**
     * 推送送达状态码
     */
    private static final int JPUSH_CODE_OK = 0;

    private TemplateHelper templateHelper = new TemplateHelper();

    private PubJpushlogDao pubJpushlogDao;
    @Resource(name = "PubJpushlogDao")
    public void setPubJpushlogDao(PubJpushlogDao pubJpushlogDao) {
        this.pubJpushlogDao = pubJpushlogDao;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msg = null;
        try {
            //获取队列数据
            msg = new String(message.getBody(), "UTF-8");
            LOGGER.info("公共队列上传数据:" + msg);
            CommonRabbitData commonRabbitData = StringUtil.parseJSONToBean(msg, CommonRabbitData.class);
            if(CommonRabbitEnum.SEND_RULE_PUSH.code.equals(commonRabbitData.getType())) {
                sendruleMsg(commonRabbitData.getData());
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("公共获取队列数据出错", e);
            try {
                if(message.getMessageProperties().getRedelivered()) {
                    LOGGER.error("公共消息(" + msg + ")重复处理失败，拒绝再次接收");
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    LOGGER.error("公共消息(" + msg + ")处理失败，返回队列");
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            } catch (IOException e1) {

            }
        }
    }

    /**
     * 派单消息
     * @param jpushlog
     */
    private void sendruleMsg(Object obj) {
        PubJpushlog jpushlog = StringUtil.parseJSONToBean(JSONObject.fromObject(obj).toString(), PubJpushlog.class);
        Map<String, String> headerMap = new HashMap<String, String>();
        JSONObject result = null;

        List<String> registrationIds = new ArrayList<String>();
        registrationIds.add(jpushlog.getRegistrationid());

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("registration_ids", registrationIds);
        data.put("date", StringUtil.formatDate(jpushlog.getSendtime(), StringUtil.TIME_WITH_DAY));

        //ios消息
        headerMap.put("Authorization", AUTHORIZATION_DRIVER_IOS);
        data.put("msg_id", jpushlog.getIosmsgid());
        result = templateHelper.dealRequestWithFullUrlTokenHeader(SystemConfig.getSystemProperty("jpush_status_url"), HttpMethod.POST,
                null, data, headerMap, JSONObject.class);
        if(null != result && !result.isEmpty() && result.containsKey(jpushlog.getRegistrationid())) {
            Object retCode = result.getJSONObject(jpushlog.getRegistrationid()).get("status");
            if(retCode.equals(JPUSH_CODE_OK)) {
                updatePubJpushLog(jpushlog);
                return;
            }
        }

        //android消息
        headerMap.put("Authorization", AUTHORIZATION_DRIVER_ANDROID);
        data.put("msg_id", jpushlog.getAndroidmsgid());
        result = templateHelper.dealRequestWithFullUrlTokenHeader(SystemConfig.getSystemProperty("jpush_status_url"), HttpMethod.POST,
                null, data, headerMap, JSONObject.class);
        if(null != result && !result.isEmpty() && result.containsKey(jpushlog.getRegistrationid())) {
            Object retCode = result.getJSONObject(jpushlog.getRegistrationid()).get("status");
            if(retCode.equals(JPUSH_CODE_OK)) {
                updatePubJpushLog(jpushlog);
            }
        }
    }

    /**
     * 修改jpush数据
     * @param jpushlog
     */
    private void updatePubJpushLog(PubJpushlog object) {
        PubJpushlog jpushlog = new PubJpushlog();
        jpushlog.setId(object.getId());
        jpushlog.setPushstate(PubJpushLogEnum.SUCCESS_PUSHSTATE_PUBJPUSHLOG.icode);
        pubJpushlogDao.updatePubJpushlogById(jpushlog);
    }
}
