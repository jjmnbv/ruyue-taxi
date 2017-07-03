package com.szyciov.carservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szyciov.carservice.dao.PhoneAuthenticationDao;
import com.szyciov.dto.api.PhoneAuthenticationDto;
import com.szyciov.dto.api.XunChengApiResultDto;
import com.szyciov.entity.PubPhoneAuthentication;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.api.PhoneAuthenticationEnum;
import com.szyciov.enums.api.XunChengResultEnum;
import com.szyciov.param.PhoneAuthenticationParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.HttpRequest;
import com.szyciov.util.JedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 手机号相关认证
 * Created by LC on 2017/4/7.
 */
@Service
public class PhoneAuthenticationService {

    @Autowired
    private PhoneAuthenticationDao dao;

    //日志
    private static Logger logger = LoggerFactory.getLogger(PhoneAuthenticationService.class);

    /**
     * 手机号、身份证、姓名是否一致验证
     * @param param    验证参数
     */
    public boolean  realNameAuthentication(PhoneAuthenticationParam param){
        try {
            logger.debug("----------请求手机号实名制接口返回参数：param:{}-------------",JSONObject.toJSONString(param));
            //如果没有验证过，则请求接口验证
            if (!isAuthenticationed(param)) {
                if(this.isExistsSuccess(param)){
                    return true;
                }
                String url = "http://v.apistore.cn/api/v4/telecom/";
                StringBuffer paramStr = new StringBuffer();
                paramStr.append("key=34f7fe34ac94fdcf1a338e0b3fa764ed");
                paramStr.append("&realName=");
                paramStr.append(param.getRealName());
                paramStr.append("&cardNo=");
                paramStr.append(param.getCardNo());
                paramStr.append("&mobile=");
                paramStr.append(param.getMobile());

                //请求接口
                String result = HttpRequest.sendPost(url, paramStr.toString());

                logger.debug("----------请求手机号实名制接口返回结果：{}-------------",result);
                //如果返回值不为空
                if (StringUtils.isNotEmpty(result)) {

                    XunChengApiResultDto dto = JSON.parseObject(result,XunChengApiResultDto.class);
                    if (XunChengResultEnum.ERR_CODE_OK.code == dto.getError_code()) {
                        PhoneAuthenticationDto phoneDto = JSON.parseObject(dto.getResult(), PhoneAuthenticationDto.class);
                        if (PhoneAuthenticationEnum.IS_OK.code != phoneDto.getIsok()) {
                            //redis中设置缓存
                            setAuthenticationed(param);
                            setAuthenticationIpCount(param);
                            return false;
                        } else {
                            //保存验证通过记录
                            this.save(param);
                            return true;
                        }
                    } else {
                        //redis中设置缓存
                        setAuthenticationed(param);
                        setAuthenticationIpCount(param);
                        logger.error("请求手机号实名认证失败：{}", result);
                        return false;
                    }
                }
            }
        }catch (Exception e){
            logger.error("请求手机号实名制认证失败！",e);
        }
        return false;
    }


    /**
     * 保存验证成功记录
     * @param param    验证参数
     */
    private void save(PhoneAuthenticationParam param){
        PubPhoneAuthentication phoneAuthentication = new PubPhoneAuthentication();
        phoneAuthentication.setId(GUIDGenerator.newGUID());
        phoneAuthentication.setCardno(param.getCardNo());
        phoneAuthentication.setMobile(param.getMobile());
        phoneAuthentication.setRealname(param.getRealName());
        phoneAuthentication.setIpaddr(param.getIpAddr());
        phoneAuthentication.setCreatetime(new Date());
        this.dao.save(phoneAuthentication);
    }


    /**
     * 是否存在验证通过记录
     * @param param
     * @return
     */
    private boolean isExistsSuccess(PhoneAuthenticationParam param){
        //获取存储通过记录
        int count = this.dao.getAuthenticationCount(param);
        //如果记录数大于0
        if(count>0){
            return true;
        }
        return false;
    }

    /**
     * 验证手机号是否实名认证
     * @param param     手机号
     */
    public boolean phoneAuthentication(PhoneAuthenticationParam param){
        try {
            logger.debug("----------请求手机号实名制接口返回参数：param:{}-------------",JSONObject.toJSONString(param));
            //如果没有验证过，则请求接口验证
            if (!isAuthenticationed(param)) {
                if(this.isExistsSuccess(param)){
                    return true;
                }
                String url = "http://v.apistore.cn/api/c119/";
                String paramUrl = "key=f0c10eefdaaf1aca127588acf9192655&mobile=" + param.getMobile();
                //请求接口
                String result = HttpRequest.sendGet(url, paramUrl);
                logger.debug("----------请求手机号是否实名接口返回结果：{}-------------",result);
                //如果返回值不为空
                if (StringUtils.isNotEmpty(result)) {
                    XunChengApiResultDto dto = JSON.parseObject(result,XunChengApiResultDto.class);
                    if (XunChengResultEnum.ERR_CODE_OK.code == dto.getError_code()) {
                        PhoneAuthenticationDto phoneDto = JSON.parseObject(dto.getResult(), PhoneAuthenticationDto.class);
                        if (PhoneAuthenticationEnum.IS_OK.code != phoneDto.getIsok()) {
                            //redis中设置缓存
                            setAuthenticationed(param);
                            setAuthenticationIpCount(param);
                            return false;
                        } else {
                            //保存验证通过记录
                            this.save(param);
                            return true;
                        }
                    } else {
                        //redis中设置缓存
                        setAuthenticationed(param);
                        setAuthenticationIpCount(param);
                        logger.error("请求手机是否实名认证失败：{}", result);
                        return false;
                    }
                }
            }
        }catch (Exception e){
            logger.error("请求手机是否实名制认证失败！",e);
        }
        return false;
    }

    /**
     * 判断是否验证过
     * @param param
     * @return
     */
    private boolean isAuthenticationed(PhoneAuthenticationParam param){

        //rediskey
        StringBuffer keyBuffer = new StringBuffer();
        keyBuffer.append(RedisKeyEnum.PHONE_REAL_NAME.code);
        keyBuffer.append(param.getMobile());
        keyBuffer.append("#");
        keyBuffer.append(param.getRealName());
        keyBuffer.append("#");
        keyBuffer.append(param.getCardNo());
        //获取redis值
        String value = JedisUtil.getString(keyBuffer.toString());
        //如果不存在
        if(StringUtils.isNotEmpty(value)){
            logger.warn("----------手机号已存在验证不通过记录：param:{}-------------",JSONObject.toJSONString(param));
            return true;
        }
        return false;
    }


    /**
     * 未验证通过，设置历史记录
     * @param param
     * @return
     */
    private void setAuthenticationed(PhoneAuthenticationParam param){
        logger.debug("----------手机号验证不通过记录：param:{}-------------", JSONObject.toJSONString(param));
        //rediskey
        StringBuffer keyBuffer = new StringBuffer();
        keyBuffer.append(RedisKeyEnum.PHONE_REAL_NAME.code);
        keyBuffer.append(param.getMobile());
        keyBuffer.append("#");
        keyBuffer.append(param.getRealName());
        keyBuffer.append("#");
        keyBuffer.append(param.getCardNo());

        //设置redis缓存，超时时间为1天
        JedisUtil.setString(keyBuffer.toString(),60*60*24,"1");

    }

    /**
     * 未验证通过，记录访问次数
     * @param param 当问参数
     * @return
     */
    private void setAuthenticationIpCount(PhoneAuthenticationParam param){
        logger.debug("----------手机号验证不通过记录：param:{}-------------", JSONObject.toJSONString(param));
        //rediskey
        StringBuffer keyBuffer = new StringBuffer();
        keyBuffer.append(RedisKeyEnum.PHONE_REAL_NAME_COUNT.code);
        keyBuffer.append("#");
        keyBuffer.append(param.getMobile());

        //设置redis缓存，超时时间为1天
        JedisUtil.eval(this.getScript(),1,keyBuffer.toString());


    }


    /**
     * 返回 lua脚本
     * @return
     */
    private String getScript()
    {
        return "local current\n" +
                "current = redis.call(\"get\",KEYS[1])\n" +
                "if not current then\n" +
                "    redis.call(\"setex\",KEYS[1],60*60*24,1)\n" +
                "else\n" +
                "    redis.call(\"setex\",KEYS[1],60*60*24,tonumber(current)+1)\n" +
                "end";
    }


}




