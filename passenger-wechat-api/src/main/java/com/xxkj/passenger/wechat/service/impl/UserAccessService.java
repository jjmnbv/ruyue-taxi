package com.xxkj.passenger.wechat.service.impl;

import com.szyciov.entity.PubSmsToken;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.param.LoginParam;
import com.szyciov.passenger.param.RegisterParam;
import com.szyciov.util.*;
import com.xxkj.passenger.wechat.Const;
import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.mapper.UserAccessMapper;
import com.xxkj.passenger.wechat.service.IUserAccessService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author lenovo23
 * @Title:UserAccessService
 * @Package com.xxkj.passenger.wechat.service.impl
 * @Description
 * @date 2017/8/7
 * @Copyrigth 版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Service
public class UserAccessService implements IUserAccessService {
    private static final Logger logger = Logger.getLogger("UserAccessService");

    @Autowired
    UserAccessMapper userAccessDao;

    @Override
    public User getUserById(String id) {
        return userAccessDao.getUserById(id);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return userAccessDao.getUserByOpenId(openId);
    }

    @Override
    public Map<String, Object> registerAndLogin(String phoneNum, String smsCode, String openId) {
        // 校验验证码
        Map<String, Object> res = verifySMSCode(phoneNum, smsCode);
        if (!res.get("status").equals(Retcode.OK.code)){
            // 校验码错误
            return res;
        }

        // 用户是否存在，不存在则注册
        User hasregister = userAccessDao.getUser4Op(phoneNum);
        if (hasregister != null){
            // 存在则绑定openId
            userAccessDao.bindUserWithOpenId(hasregister);
        }else {
            //  不存在则注册用户
//            User registerUser = new User();
//            registerUser.setAccount(phoneNum);
//            registerUser.setWechatopenid(openId);

            RegisterParam registerParam = new RegisterParam();
            registerParam.setPhone(phoneNum);
            doRegister(registerParam, openId);
        }

        // 登陆
//        login(hasregister, "");
        return null;
    }

    /**
     * 校验验证码
     * @param params
     * @return
     */
    public Map<String, Object> verifySMSCode(String phone, String verificationcode) {
        Map<String, Object> res = new HashMap<String,Object>();
        try{
            String smscodeerrortimesstr = SystemConfig.getSystemProperty("smscodeerrortimes");
            int smscodeerrortimes = parseInt(smscodeerrortimesstr)<=0?5:parseInt(smscodeerrortimesstr);
            String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
            //redis错误次数是5，就直接返回false
            if(parseDouble(errortimes)>=smscodeerrortimes){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "错误次数超限!");
                return res;
            }
        }catch (Exception e){
            logger.error("获取redis信息出错",e);
        }
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("phone", phone);
        param.put("usertype", Const.USERTOKENTYPE_PEUSER);
        param.put("smstype", Const.SMSTYPE_REGISTER);
        PubSmsToken smsobj = userAccessDao.getSMSInfo(param);
        if(smsobj==null){
            res.put("status", Retcode.SMSCODEINVALID.code);
            res.put("message", Retcode.SMSCODEINVALID.msg);
        }else{
            String smscode = smsobj.getSmscode();
            Date savetime = smsobj.getUpdatetime();
            Date temptime = new Date(savetime.getTime() + (long)Const.SMSCODEVALITIME * 60 * 1000);
            Date currentime = new Date();
            if(currentime.after(temptime)){
                res.put("status", Retcode.SMSCODEOUTTIME.code);
                res.put("message", Retcode.SMSCODEOUTTIME.msg);
            }else if(smscode==null||!smscode.equals(verificationcode)){
                res.put("status", Retcode.SMSCODEINVALID.code);
                res.put("message", Retcode.SMSCODEINVALID.msg);
                try{
                    String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
                    JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
                    if(parseDouble(errortimes)<=0){
                        String smscodeerrorouttimestr = SystemConfig.getSystemProperty("smscodeerrorouttime");
                        int smscodeerrorouttime = parseInt(smscodeerrorouttimestr)<=0?5:parseInt(smscodeerrorouttimestr);
                        JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone,smscodeerrorouttime*60);
                    }
                }catch (Exception e){
                    logger.error("获取redis出错");
                }
            }else{
                try{
                    JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
                    JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
                }catch (Exception e){
                    logger.error("清除redis出错",e);
                }
                res.put("status", Retcode.OK.code);
                res.put("message", Retcode.OK.msg);
            }
        }
        return res;
    }


    /**
     * 注册个人用户账户, 用户不存在，所以不需要判断是否存在用户
     * @param registerparam
     * @return
     */
    public Map<String, Object> doRegister(RegisterParam registerparam, String openId) {
        Map<String, Object> res = new HashMap<String,Object>();
        Map<String,Object> loginlog = new HashMap<String,Object>();
        loginlog.put("id", GUIDGenerator.newGUID());
//        loginlog.put("device", registerparam.getDevice());
//        loginlog.put("version", registerparam.getVersion());
//        loginlog.put("appversion", registerparam.getAppversion());
//        loginlog.put("phonebrand", registerparam.getPhonebrand());
//        loginlog.put("phonemodel", registerparam.getPhonemodel());
//        loginlog.put("browserversion", registerparam.getBrowserversion());
//        loginlog.put("browsertype", registerparam.getBrowsertype());

        User peuser = new User();
        String phone = registerparam.getPhone();

        peuser.setWechatopenid(openId);
        peuser.setAccount(phone);
        String userid = GUIDGenerator.newGUID();
        loginlog.put("userid", userid);
        peuser.setId(userid);
        peuser.setUserpassword(registerparam.getPassword());
        encodePwd(peuser);
        try{
            peuser.setNickname("");
            userAccessDao.registerPeUser(peuser);
            loginlog.put("loginstatus", Const.LOGINSTATUS_OK);
            res.put("status", Retcode.OK.code);
            res.put("message", Retcode.OK.msg);
//            addUserInfo(res,registerparam.getUuid(), peuser);

            addUserInfo(res,"", peuser);
        }catch(Exception e){
            loginlog.put("loginstatus", Const.LOGINSTATUS_ERROR);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
//        userAccessDao.addLog4Pe(loginlog);
        return res;
    }

    private void encodePwd(User peuser){
        if(peuser==null){
            return ;
        }
        String pwd = peuser.getUserpassword();
        if(pwd==null||"".equalsIgnoreCase(pwd)){
            return ;
        }
        peuser.setUserpassword(PasswordEncoder.encode(pwd));
    }

    @Override
    public Map<String, Object> login(User user, String loginType) throws NoSuchAlgorithmException {
        LoginParam loginparam = new LoginParam();
        loginparam.setVersion("4.0.0");
        loginparam.setAppversion("");
        loginparam.setDevice("");
        loginparam.setPhonebrand("");
        loginparam.setPhonemodel("");
        loginparam.setBrowsertype("weixin");
        loginparam.setBrowserversion("weixin");
        loginparam.setPhone(user.getAccount());
        loginparam.setUuid("");
        loginparam.setLogintype(loginType);


        Map<String, Object> res = new HashMap<String,Object>();
        //登录日志信息
        Map<String,Object> loginfo = getLogObj(loginparam);
        String account = loginparam.getPhone();
        String uuid = loginparam.getUuid();
        User peuser = userAccessDao.getUser4Op(account);
        if(peuser==null){
            loginfo.put("loginstatus", Const.LOGINSTATUS_ERROR);
            //添加日志
            userAccessDao.addLog4Pe(loginfo);
            //用户不存在
            res.put("status", Retcode.USERNOTEXIST.code);
            res.put("message", Retcode.USERNOTEXIST.msg);
            return res;
        }
        loginfo.put("userid", peuser.getId());
//        if(Const.CODELOGIN.equalsIgnoreCase(loginparam.getLogintype())){
//            //验证码登录
//
//        }else{
//            // 静默登陆
//            addUserInfo(res,uuid,peuser);
//        }

        // 静默登陆
        addUserInfo(res,uuid,peuser);
        //记录日志
        userAccessDao.addLog4Pe(loginfo);
        return res;
    }

    /**
     * 验证码使用完成后删除
     * @param phone
     * @param usertype
     * @param smstype
     */
    private void deleteSMSCode(String phone,String usertype,String smstype){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("phone", phone);
        params.put("usertype", usertype);
        params.put("smstype", smstype);
        userAccessDao.deleteSMSCode(params);
    }

    private int parseInt(Object value){
        if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
            return 0;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    /**
     * 转化obj成value，空值就为0
     * @param value
     * @return
     */
    private double parseDouble(Object value){
        if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
            return 0;
        }
        return Double.parseDouble(String.valueOf(value));
    }

    private void addUserInfo(Map<String, Object> res,String uuid, User peuser) throws NoSuchAlgorithmException {
        String usertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,peuser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
        Map<String,Object> pp = new HashMap<String,Object>();
        pp.put("userid", peuser.getId());
        pp.put("usertype", Const.USERTOKENTYPE_PEUSER);
        Map<String,Object> dbusertokeninfo = userAccessDao.getUserTokenByUserId(pp);
        Map<String,Object> tokeninfo = new HashMap<String,Object>();

        if(dbusertokeninfo==null){
            tokeninfo.put("id", GUIDGenerator.newGUID());
        }

        tokeninfo.put("usertoken", usertoken);
        tokeninfo.put("userid", peuser.getId());
        tokeninfo.put("uuid", uuid);
        tokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
        res.put("name", peuser.getNickname());
        userAccessDao.createOrUpdateUsertoken(tokeninfo);
        //同步token到机构用户的token信息
        try{
            Map<String,Object> pep = new HashMap<String,Object>();
            OrgUser orguser = userAccessDao.getUser4Org(peuser.getAccount());
            if(orguser!=null){
                pep.put("userid", orguser.getId());
                pep.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                Map<String,Object> dborgusertokeninfo = userAccessDao.getUserTokenByUserId(pep);
                Map<String,Object> orgtokeninfo = new HashMap<String,Object>();
                if(dborgusertokeninfo==null){
                    orgtokeninfo.put("id", GUIDGenerator.newGUID());
                }
                orgtokeninfo.put("usertoken", usertoken);
                orgtokeninfo.put("userid", orguser.getId());
                orgtokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                orgtokeninfo.put("uuid", uuid);
                res.put("name", orguser.getNickName());
                userAccessDao.createOrUpdateUsertoken(orgtokeninfo);
            }
        }catch (Exception e){}

        byte[] usertokencode4 = Base64.decodeBase64(usertoken);
        String decodetoken = new String(usertokencode4);
        String timetag = decodetoken.substring(32, 49);
        List<String> tags = new ArrayList<String>();
        tags.add(timetag);
//		tags.add("1");
        res.put("tags", tags);
        res.put("usertoken", usertoken);
        res.put("nickname", peuser.getNickname());
        res.put("telphone", peuser.getAccount());
        res.put("sex", peuser.getSex());
        String imgpath = peuser.getHeadportraitmin();
        if(StringUtils.isNotBlank(imgpath)){
            res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
        }
    }

    private Map<String,Object> getLogObj(LoginParam loginparam){
        Map<String,Object> loginlog = new HashMap<String,Object>();
        loginlog.put("id", GUIDGenerator.newGUID());
        loginlog.put("device", loginparam.getDevice());
        loginlog.put("version", loginparam.getVersion());
        loginlog.put("appversion", loginparam.getAppversion());
        loginlog.put("phonebrand", loginparam.getPhonebrand());
        loginlog.put("phonemodel", loginparam.getPhonemodel());
        loginlog.put("browserversion", loginparam.getBrowserversion());
        loginlog.put("browsertype", loginparam.getBrowsertype());
        return loginlog;
    }
}
