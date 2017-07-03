package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.AwardPassengerDao;
import com.szyciov.carservice.util.MessageConnection;
import com.szyciov.entity.Retcode;
import com.szyciov.message.UserMessage;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2017/6/21.
 */
@Service("AwardPassengerService")
public class AwardPassengerService {

    private AwardPassengerDao awardPassengerDao;

    @Resource(name="AwardPassengerDao")
    public void setAwardPassengerDao(AwardPassengerDao awardPassengerDao) {
        this.awardPassengerDao = awardPassengerDao;
    }

    /**
     * 转换对象为double
     * @param value
     * @return
     */
    private double parseDoulbe(Object value){
        if(value==null){
            return 0;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    /**
     * 返回积分给用户
     * @param params
     * @return
     */
    @Transactional
    public Map<String,Object> awardPoint(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        //"0"机构用户,"1"个人用户
        String usertype = (String) params.get("usertype");
        String userphone = (String) params.get("userphone");
        String passengerphone = (String) params.get("passengerphone");
        double money = parseDoulbe(params.get("money"));
        if(StringUtils.isBlank(usertype)||StringUtils.isBlank(userphone)||StringUtils.isBlank(userphone)||money<=0){
            res.put("status",Retcode.FAILED.code);
            res.put("message","参数不全");
            return res;
        }
        Map<String,Object> awardparams = new HashMap<String,Object>();
        //确保积分用户存在和钱包
        Map<String,Object> ensureparams = new HashMap<String,Object>();
        double awardpoint = 0;
        double awardpercent = 0;
        Map<String,Object> awardpercentparams = new HashMap<String,Object>();
        if("0".equals(usertype)){
            //机构用户
            //返回的金额百分比小数
            awardpercent = 0.20;
            awardpercentparams.put("type","机构用户订单支付返还比例");
            awardparams.put("phone",passengerphone);

            ensureparams.put("phone",passengerphone);
            ensureparams.put("isorg",true);
//            ensureExist(ensureparams);
        }else{
            //个人用户
            //返回的金额百分比小数
            awardpercent = 0.34;
            awardpercentparams.put("type","个人用户订单支付返还比例");
            awardparams.put("phone",userphone);

            //确保积分用户存在和钱包
            ensureparams.put("phone",passengerphone);
//            ensureExist(ensureparams);

            Map<String,Object> userphoneparams = new HashMap<String,Object>();
            userphoneparams.put("phone",userphone);
            ensureExist(userphoneparams);
        }
        Map<String,Object> onoff = awardPassengerDao.getAwardOnOff();
        if(onoff!=null&&"1".equals(onoff.get("value"))){
            //开关开着
            Map<String,Object> awardinfo = awardPassengerDao.getAwardPercent(awardpercentparams);
            if(awardinfo!=null){
                awardpercent = parseDoulbe(awardinfo.get("value"));
            }
            awardpoint = Math.round(awardpercent*money);
            Map<String,Object> awardcurrenttotal = awardPassengerDao.getAwardCurrentTotal();
            Map<String,Object> awardtotal = awardPassengerDao.getAwardTotal();
            if(awardpoint>0&&awardtotal!=null&&awardcurrenttotal!=null){
                double currentaward = parseDoulbe(awardcurrenttotal.get("value"));
                double totalaward = parseDoulbe(awardtotal.get("value"));
                if((currentaward+awardpoint)<=totalaward){
                    //没有达到上限才返积分
                    awardparams.put("awardpoint",awardpoint);
                    //发现并创建乘车人
                    ensureparams.put("awardpoint",awardpoint);
                    ensureExist(ensureparams);
                    Map<String,Object> peuserinfo = awardPassengerDao.getPeUserInfo((String) awardparams.get("phone"));
                    if(peuserinfo!=null){
                        awardparams.put("userid",peuserinfo.get("id"));
                        awardPassengerDao.addAwardPoint(awardparams);
                        //添加明细
                        Map<String,Object> expensesparams = new HashMap<String,Object>();
                        expensesparams.put("id",GUIDGenerator.newGUID());
                        expensesparams.put("userid",peuserinfo.get("id"));
                        expensesparams.put("expensetype","3");
                        expensesparams.put("awardpoint",awardpoint);
                        expensesparams.put("remark","返回积分");
                        expensesparams.put("tradetype","4");
                        expensesparams.put("detailtype","0");
                        awardPassengerDao.addExpenses4User(expensesparams);
                        //更新现在的积分金额
                        Map<String,Object> updatecurrentaward = new HashMap<String,Object>();
                        updatecurrentaward.put("awardpoint",awardpoint);
                        awardPassengerDao.updateCurrentAward(updatecurrentaward);
                    }
                }else{
                    //达到积分上限
                    awardPassengerDao.stopAward();
                }
            }
        }
        return res;
    }

    /**
     * 确保用户和钱包存在
     * @param ensureparams
     */
    @Transactional
    private void ensureExist(Map<String, Object> ensureparams) {
        String phone = (String) ensureparams.get("phone");
        if(StringUtils.isBlank(phone)){
            return ;
        }
        Map<String,Object> peaccountparams = new HashMap<String,Object>();

        //判断创建用户
        Map<String,Object> peuserinfo = awardPassengerDao.getPeUserInfo(phone);
        if(peuserinfo==null){
            //创建
            Map<String,Object> addperuserparams = new HashMap<String,Object>();
            String id = GUIDGenerator.newGUID();
            addperuserparams.put("id", id);
            addperuserparams.put("account", phone);
            String srcpwd = phone.substring(5);
            addperuserparams.put("pwd", PasswordEncoder.encode(srcpwd));
            awardPassengerDao.addPeUser(addperuserparams);
            boolean isorg = parseBoolean(ensureparams.get("isorg"));
            //发送短信
//            String content = "欢迎乘坐“如约的士”专车服务，现已为您开通如约账号："+phone+"，密码："+srcpwd+"，诚邀下载app登录乘车。";
            String content = null;
            if(isorg){
                double awardpoint = parseDoulbe(ensureparams.get("awardpoint"));
                content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.carservice.service.AwardPassengerService.ensureExistOrg",awardpoint,phone,srcpwd);
            }else{
                content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.carservice.service.AwardPassengerService.ensureExist", phone,srcpwd);
            }
            List<String> phones = new ArrayList<String>();
            phones.add(phone);
            UserMessage message = new UserMessage(phones, content, UserMessage.ADDUSER);
            MessageConnection.add(message);
            peaccountparams.put("userid",id);
        }else{
            peaccountparams.put("userid",peuserinfo.get("id"));
        }
        Map<String,Object> peuseraccountinfo = awardPassengerDao.getPeUserAccount(peaccountparams);
        if(peuseraccountinfo==null){
            peaccountparams.put("id",GUIDGenerator.newGUID());
            //创建钱包
            awardPassengerDao.createPeUserAccount(peaccountparams);
        }
    }

    private boolean parseBoolean(Object value){
        if(value==null){
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(value));
    }
}
