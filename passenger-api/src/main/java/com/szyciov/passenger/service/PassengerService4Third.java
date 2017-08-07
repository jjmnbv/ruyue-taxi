package com.szyciov.passenger.service;

import com.alipay.util.OrderInfoUtil;
import com.szyciov.driver.enums.AlarmProcessEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubMostaddress;
import com.szyciov.entity.PubMostcontact;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserType;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PhoneAuthenticationParam;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.dao.DictionaryDao;
import com.szyciov.passenger.dao.OpDao;
import com.szyciov.passenger.dao.OrderDao;
import com.szyciov.passenger.dao.OrgDao;
import com.szyciov.passenger.dao.PubDriverDao;
import com.szyciov.passenger.dao.PubMostaddressDao;
import com.szyciov.passenger.dao.PubMostcontactDao;
import com.szyciov.passenger.dao.UserDao;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.entity.VehicleModels;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.passenger.util.VelocityUtil;
import com.szyciov.util.*;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2017/5/26.
 */
@Service("PassengerService4Third")
public class PassengerService4Third {

    private static final Logger logger = Logger.getLogger(PassengerService4Third.class);

    private TemplateHelper4OrgApi orgapi = new TemplateHelper4OrgApi();

    private TemplateHelper4leaseApi leaseapi = new TemplateHelper4leaseApi();

    private TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();

    public final TemplateHelper templateHelper = new TemplateHelper();
    /**
     * 字典相关的dao
     */
    private DictionaryDao dicdao;

    /**
     * 用户相关的dao
     */
    private UserDao userdao;

    /**
     * 订单相关dao
     */
    private OrderDao orderdao;

    /**
     * 机构相关的dao
     */
    private OrgDao orgdao;

    /**
     * 运营相关的dao
     */
    private OpDao opdao;

    @Resource(name = "PubMostcontactDao")
    private PubMostcontactDao mostcontactDao;

    @Resource(name = "PubMostaddressDao")
    private PubMostaddressDao mostaddressDao;

    @Resource(name = "PubDriverDao")
    private PubDriverDao driverDao;

    @Resource(name = "DictionaryDao")
    public void setDictionaryDao(DictionaryDao dicdao) {
        this.dicdao = dicdao;
    }

    @Resource(name = "UserDao")
    public void setUserDao(UserDao userdao) {
        this.userdao = userdao;
    }

    @Resource(name = "OrderDao")
    public void setOrderDao(OrderDao orderdao) {
        this.orderdao = orderdao;
    }

    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgdao) {
        this.orgdao = orgdao;
    }

    @Resource(name = "OpDao")
    public void setOpDao(OpDao opdao) {
        this.opdao = opdao;
    }

    /**
     * 为返回结果加入统一的属性
     * @param res
     * @return
     */
    private void addPubInfos(Map<String,Object> res){
        if(res==null){
            return ;
        }
        res.put("servertime", (new Date()).getTime());
    }

    /**
     * 登录前判断
     * @param params
     * @return
     */
    public Map<String,Object> preLogin(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String phone = (String) params.get("phone");
            String version = (String) params.get("version");
            if(StringUtils.isBlank(phone)||StringUtils.isBlank(version)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                //ios说这个必须给
                res.put("info","");
                res.put("hasorgidentity",false);
                res.put("hasorguser",false);
                return res;
            }
            OrgUser orguser = userdao.getUser4Org(phone);
            if(orguser==null){
                //没有机构用户信息
                res.put("hasorguser",false);
                res.put("hasorgidentity",false);
                //处理个人用户的信息
                dillOpUser(res, phone);
            }else{
                res.put("hasorguser",true);
                if(orguser.getStatus()!=1){
                    //离职的
                    res.put("hasorgidentity",false);
                    //处理个人用户的信息
                    dillOpUser(res, phone);
                }else{
                    //在职有机构用户信息
                    res.put("hasorgidentity",true);
                    //机构用户肯定有登录密码
                    res.put("info","2");
                }
            }
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            //ios说这个必须给
            res.put("info","");
            res.put("hasorgidentity",false);
            res.put("hasorguser",false);
        }
        return res;
    }

    private void dillOpUser(Map<String, Object> res, String phone) {
        //获取用户对象
        PeUser userinfo = userdao.getUser4Op(phone);
        if(userinfo==null){
            //用户未注册
            res.put("info","0");
            //校验实名制认证
            try{
                PhoneAuthenticationParam pp = new PhoneAuthenticationParam();
                pp.setMobile(phone);
                JSONObject result = carserviceapi.dealRequestWithToken("/XunChengApi/phoneAuthentication", HttpMethod.POST, null, pp,JSONObject.class);
                if(result.getInt("status")== Retcode.OK.code){
                    boolean isreal = result.getBoolean("data");
                    if(!isreal){
                        //未实名
                        res.put("info","-1");
                    }
                }
            }catch(Exception e){
                logger.error("乘客端异常",e);
            }
        }else if(userinfo.getUserpassword()!=null&&!"".equalsIgnoreCase(userinfo.getUserpassword())){
            //设置了登录密码
            res.put("info","2");
        }else{
            //没有设置密码
            res.put("info","1");
        }
    }

    /**
     * 判断用户是否有机构身份
     * @param params
     * @return
     */
    public Map<String,Object> hasOrgIdentity(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) params.get("usertoken");
            String version = (String) params.get("version");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(version)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                //ios说这个必须给
                res.put("hasorgidentity",false);
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            OrgUser orguser = userdao.getUser4Org(account);
            if(orguser!=null&&orguser.getStatus()==1){
                //在职
                res.put("hasorgidentity",true);
            }else{
                res.put("hasorgidentity",false);
            }
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            //ios说这个必须给
            res.put("hasorgidentity",false);
        }
        return res;
    }

    /**
     * 更新用户密码
     * @param params
     * @return
     */
    @Transactional
    public Map<String,Object> updatePwd(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        params.put("account", account);
        String pwd = (String)params.get("pwd");
        if(StringUtils.isBlank(pwd)){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message","密码格式错误");
            return res;
        }
        params.put("pwd", PasswordEncoder.encode(pwd));
        //机构用户
        OrgUser orguser =  userdao.getUser4Org(account);
        if(orguser!=null){
            params.put("userid",orguser.getId());
            userdao.updatePwd4Org(params);
        }
        //个人用户
        PeUser peuser = userdao.getUser4Op(account);
        if(peuser!=null){
            params.put("userid", peuser.getId());
            userdao.updatePwd4Op(params);
        }
        return res;
    }

    /**
     * 更新用户密码
     * @param params
     * @return
     */
    @Transactional
    public Map<String,Object> updatePwd2(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        params.put("account", account);
        String encodepwd = (String)params.get("pwd");
        String pwd = RSAUtil.RSADecode(encodepwd);
        if(StringUtils.isBlank(pwd)){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message","密码格式错误");
            return res;
        }
        params.put("pwd", PasswordEncoder.encode(pwd));
        //机构用户
        OrgUser orguser =  userdao.getUser4Org(account);
        if(orguser!=null){
            params.put("userid",orguser.getId());
            userdao.updatePwd4Org(params);
        }
        //个人用户
        PeUser peuser = userdao.getUser4Op(account);
        if(peuser!=null){
            params.put("userid", peuser.getId());
            userdao.updatePwd4Op(params);
        }
        return res;
    }

    public void getAccountRules(Map<String, Object> param, HttpServletResponse res) {
        res.setContentType("text/html; charset=utf-8");
        String usertoken = (String) param.get("usertoken");
        String city =  (String) param.get("city");
        String rulestype =  (String) param.get("rulestype");
        String usetype =  (String) param.get("usetype");
        String companyname = null;
        List<AccountRules> rules = null;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("city", city);
        params.put("rulestype", rulestype);
        if(isOrgUser(usertoken)){
            //机构用户
            String companyid =   (String) param.get("companyid");
            params.put("companyid", companyid);
            if(Const.USETYPE_PUBLIC.equals(usetype)){
                OrgOrgan organ = orgdao.getOrgInfo(Const.getUserInfo(usertoken).get("account"));
                params.put("organid", organ.getId());
            }
            //因公用车获取个性化计费规则，因私获取标准计费规则
            rules = orgdao.getAccountRules(params);
            LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
            if(company!=null){
                companyname = "如约的士";
            }
        }else{
            //个人用户
            //获取运营端的计费规则
            rules = opdao.getAccountRules(params);
        }
        if(rules!=null){
            for(int i=0;i<rules.size();i++){
                AccountRules rule = rules.get(i);
                String logo = rule.getLogo();
                if(StringUtils.isNotBlank(logo)){
                    rule.setLogo(SystemConfig.getSystemProperty("fileserver")+ File.separator+logo);
                }
            }
        }
        VelocityContext context = new VelocityContext();
        context.put("rules", JSONArray.fromObject(rules));
        context.put("company", companyname);
        String vmpath = PassengerService.class.getClassLoader().getResource("accountrulesnew.vm").getPath();
        try {
            VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
        } catch (Exception e) {
            logger.error("乘客端异常",e);
        }
    }

    /**
     * 是否是机构用户
     * @param usertoken
     * @return
     */
    private boolean isOrgUser(String usertoken){
        return UserTokenManager.ORGUSERTYPE.equals(Const.getUserInfo(usertoken).get("usertype"));
    }

    /**
     * 静默登录
     * @param loginparam
     * @return
     */
    public Map<String,Object> defLogin(Map<String, Object> loginparam) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String usertoken = (String) loginparam.get("usertoken");
            String pwd = (String) loginparam.get("pwd");
            String uuid = (String) loginparam.get("uuid");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(pwd)||StringUtils.isBlank(uuid)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            Map<String,String> userinfo = Const.getUserInfo(usertoken);
            if(userinfo==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不合法");
                return res;
            }
            String useraccount = userinfo.get("account");
            OrgUser orguser = userdao.getUser4Org(useraccount);
            if(orguser==null){
                res.put("hasorguser",false);
                res.put("hasorgidentity",false);
            }else if(orguser.getStatus()!=1){
                //离职的
                res.put("hasorguser",true);
                res.put("hasorgidentity",false);
            }else{
                res.put("hasorguser",true);
                res.put("hasorgidentity",true);
            }
            if(dillWithDelLogin(res,loginparam)){
                if(orguser!=null&&orguser.getStatus()==1){
                    //有机构身份
                    String newusertoken = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,orguser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                    Map<String,Object> pp = new HashMap<String,Object>();
                    pp.put("userid", orguser.getId());
                    pp.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                    Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                    Map<String,Object> tokeninfo = new HashMap<String,Object>();
                    if(dbusertokeninfo==null){
                        tokeninfo.put("id", GUIDGenerator.newGUID());
                    }
                    tokeninfo.put("usertoken", newusertoken);
                    tokeninfo.put("userid", orguser.getId());
                    tokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                    tokeninfo.put("uuid", uuid);
                    userdao.createOrUpdateUsertoken(tokeninfo);
                    res.put("nickname", orguser.getNickName());
                    //同步token到个人用户的token信息
                    try{
                        Map<String,Object> pep = new HashMap<String,Object>();
                        PeUser peuser = userdao.getUser4Op(orguser.getAccount());
                        pep.put("userid", peuser.getId());
                        pep.put("usertype", Const.USERTOKENTYPE_PEUSER);
                        Map<String,Object> dbpeusertokeninfo = userdao.getUserTokenByUserId(pep);
                        Map<String,Object> petokeninfo = new HashMap<String,Object>();
                        if(dbpeusertokeninfo==null){
                            petokeninfo.put("id", GUIDGenerator.newGUID());
                        }
                        petokeninfo.put("usertoken", newusertoken);
                        petokeninfo.put("userid", peuser.getId());
                        petokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
                        petokeninfo.put("uuid", uuid);
                        res.put("nickname", peuser.getNickname());
                        userdao.createOrUpdateUsertoken(petokeninfo);
                    }catch (Exception e){}

                    byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                    String decodetoken = new String(usertokencode4);
                    String timetag = decodetoken.substring(32, 49);
                    List<String> tags = new ArrayList<String>();
                    tags.add(timetag);
                    tags.add("0");
                    res.put("tags", tags);
                    res.put("usertoken", newusertoken);
                    res.put("name", orguser.getNickName());
                    res.put("telphone", orguser.getAccount());
                    res.put("sex", orguser.getSex());
                    String imgpath = orguser.getHeadPortraitMax();
                    if(StringUtils.isNotBlank(imgpath)){
                        res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                    }
                }else{
                    PeUser peuser = userdao.getUser4Op(useraccount);
                    //个人用户身份
                    String newusertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,peuser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                    Map<String,Object> pp = new HashMap<String,Object>();
                    pp.put("userid", peuser.getId());
                    pp.put("usertype", Const.USERTOKENTYPE_PEUSER);
                    Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                    Map<String,Object> tokeninfo = new HashMap<String,Object>();
                    if(dbusertokeninfo==null){
                        tokeninfo.put("id", GUIDGenerator.newGUID());
                    }
                    tokeninfo.put("usertoken", newusertoken);
                    tokeninfo.put("userid", peuser.getId());
                    tokeninfo.put("uuid", uuid);
                    tokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
                    userdao.createOrUpdateUsertoken(tokeninfo);
                    res.put("name", peuser.getNickname());
                    //同步token到机构用户的token信息
                    try{
                        Map<String,Object> pep = new HashMap<String,Object>();
                        OrgUser orgusertemp = userdao.getUser4Org(peuser.getAccount());
                        if(orgusertemp!=null){
                            pep.put("userid", orgusertemp.getId());
                            pep.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                            Map<String,Object> dborgusertokeninfo = userdao.getUserTokenByUserId(pep);
                            Map<String,Object> orgtokeninfo = new HashMap<String,Object>();
                            if(dborgusertokeninfo==null){
                                orgtokeninfo.put("id", GUIDGenerator.newGUID());
                            }
                            orgtokeninfo.put("usertoken", newusertoken);
                            orgtokeninfo.put("userid", orgusertemp.getId());
                            orgtokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                            orgtokeninfo.put("uuid", uuid);
                            res.put("name", orgusertemp.getNickName());
                            userdao.createOrUpdateUsertoken(orgtokeninfo);
                        }
                    }catch (Exception e){}
                    byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                    String decodetoken = new String(usertokencode4);
                    String timetag = decodetoken.substring(32, 49);
                    List<String> tags = new ArrayList<String>();
                    tags.add(timetag);
                    tags.add("1");
                    res.put("tags", tags);
                    res.put("usertoken", newusertoken);
                    res.put("nickname", peuser.getNickname());
                    res.put("telphone", peuser.getAccount());
                    res.put("sex", peuser.getSex());
                    String imgpath = peuser.getHeadportraitmin();
                    if(StringUtils.isNotBlank(imgpath)){
                        res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                    }
                }
            }else{
                //失效了
                res.put("status",Retcode.FAILED.code);
                res.put("message", "静默失效");
                res.put("tags", new ArrayList());
                res.put("usertoken", "");
                res.put("nickname", "");
                res.put("telphone", "");
                res.put("sex", "");
                res.put("imgpath","");
            }

        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("info",e.getMessage());
        }
        return res;
    }

    public Map<String,Object> defLogin2(Map<String, Object> loginparam) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String usertoken = (String) loginparam.get("usertoken");
            String pwd = (String) loginparam.get("pwd");
            pwd = RSAUtil.RSADecode(pwd);
            loginparam.put("pwd",pwd);
            String uuid = (String) loginparam.get("uuid");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(pwd)||StringUtils.isBlank(uuid)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            Map<String,String> userinfo = Const.getUserInfo(usertoken);
            if(userinfo==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不合法");
                return res;
            }
            String useraccount = userinfo.get("account");
            OrgUser orguser = userdao.getUser4Org(useraccount);
            if(orguser==null){
                res.put("hasorguser",false);
                res.put("hasorgidentity",false);
            }else if(orguser.getStatus()!=1){
                //离职的
                res.put("hasorguser",true);
                res.put("hasorgidentity",false);
            }else{
                res.put("hasorguser",true);
                res.put("hasorgidentity",true);
            }
            if(dillWithDelLogin(res,loginparam)){
                if(orguser!=null&&orguser.getStatus()==1){
                    //有机构身份
                    String newusertoken = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,orguser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                    Map<String,Object> pp = new HashMap<String,Object>();
                    pp.put("userid", orguser.getId());
                    pp.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                    Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                    Map<String,Object> tokeninfo = new HashMap<String,Object>();
                    if(dbusertokeninfo==null){
                        tokeninfo.put("id", GUIDGenerator.newGUID());
                    }
                    tokeninfo.put("usertoken", newusertoken);
                    tokeninfo.put("userid", orguser.getId());
                    tokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                    tokeninfo.put("uuid", uuid);
                    userdao.createOrUpdateUsertoken(tokeninfo);
                    res.put("nickname", orguser.getNickName());
                    //同步token到个人用户的token信息
                    try{
                        Map<String,Object> pep = new HashMap<String,Object>();
                        PeUser peuser = userdao.getUser4Op(orguser.getAccount());
                        pep.put("userid", peuser.getId());
                        pep.put("usertype", Const.USERTOKENTYPE_PEUSER);
                        Map<String,Object> dbpeusertokeninfo = userdao.getUserTokenByUserId(pep);
                        Map<String,Object> petokeninfo = new HashMap<String,Object>();
                        if(dbpeusertokeninfo==null){
                            petokeninfo.put("id", GUIDGenerator.newGUID());
                        }
                        petokeninfo.put("usertoken", newusertoken);
                        petokeninfo.put("userid", peuser.getId());
                        petokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
                        petokeninfo.put("uuid", uuid);
                        res.put("nickname", peuser.getNickname());
                        userdao.createOrUpdateUsertoken(petokeninfo);
                    }catch (Exception e){}

                    byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                    String decodetoken = new String(usertokencode4);
                    String timetag = decodetoken.substring(32, 49);
                    List<String> tags = new ArrayList<String>();
                    tags.add(timetag);
                    tags.add("0");
                    res.put("tags", tags);
                    res.put("usertoken", newusertoken);
                    res.put("name", orguser.getNickName());
                    res.put("telphone", orguser.getAccount());
                    res.put("sex", orguser.getSex());
                    String imgpath = orguser.getHeadPortraitMax();
                    if(StringUtils.isNotBlank(imgpath)){
                        res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                    }
                }else{
                    PeUser peuser = userdao.getUser4Op(useraccount);
                    //个人用户身份
                    String newusertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,peuser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                    Map<String,Object> pp = new HashMap<String,Object>();
                    pp.put("userid", peuser.getId());
                    pp.put("usertype", Const.USERTOKENTYPE_PEUSER);
                    Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                    Map<String,Object> tokeninfo = new HashMap<String,Object>();
                    if(dbusertokeninfo==null){
                        tokeninfo.put("id", GUIDGenerator.newGUID());
                    }
                    tokeninfo.put("usertoken", newusertoken);
                    tokeninfo.put("userid", peuser.getId());
                    tokeninfo.put("uuid", uuid);
                    tokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
                    userdao.createOrUpdateUsertoken(tokeninfo);
                    res.put("name", peuser.getNickname());
                    //同步token到机构用户的token信息
                    try{
                        Map<String,Object> pep = new HashMap<String,Object>();
                        OrgUser orgusertemp = userdao.getUser4Org(peuser.getAccount());
                        if(orgusertemp!=null){
                            pep.put("userid", orgusertemp.getId());
                            pep.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                            Map<String,Object> dborgusertokeninfo = userdao.getUserTokenByUserId(pep);
                            Map<String,Object> orgtokeninfo = new HashMap<String,Object>();
                            if(dborgusertokeninfo==null){
                                orgtokeninfo.put("id", GUIDGenerator.newGUID());
                            }
                            orgtokeninfo.put("usertoken", newusertoken);
                            orgtokeninfo.put("userid", orgusertemp.getId());
                            orgtokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                            orgtokeninfo.put("uuid", uuid);
                            res.put("name", orgusertemp.getNickName());
                            userdao.createOrUpdateUsertoken(orgtokeninfo);
                        }
                    }catch (Exception e){}
                    byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                    String decodetoken = new String(usertokencode4);
                    String timetag = decodetoken.substring(32, 49);
                    List<String> tags = new ArrayList<String>();
                    tags.add(timetag);
                    tags.add("1");
                    res.put("tags", tags);
                    res.put("usertoken", newusertoken);
                    res.put("nickname", peuser.getNickname());
                    res.put("telphone", peuser.getAccount());
                    res.put("sex", peuser.getSex());
                    String imgpath = peuser.getHeadportraitmin();
                    if(StringUtils.isNotBlank(imgpath)){
                        res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                    }
                }
            }else{
                //失效了
                res.put("status",Retcode.FAILED.code);
                res.put("message", "静默失效");
                res.put("tags", new ArrayList());
                res.put("usertoken", "");
                res.put("nickname", "");
                res.put("telphone", "");
                res.put("sex", "");
                res.put("imgpath","");
            }

        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("info",e.getMessage());
        }
        return res;
    }

    private boolean dillWithDelLogin(Map<String,Object> res,Map<String, Object> loginparam) throws Exception {
        String usertoken = (String) loginparam.get("usertoken");
        String pwd = (String) loginparam.get("pwd");

        Map<String,Object> dbtokeninfo = userdao.getTokenInfo(usertoken);

        //不存在token信息，非法登录
        if(dbtokeninfo==null){
            res.put("detail", "静默失效原因，数据库没有次token了");
            return false;
        }

        String dbtoken = (String) dbtokeninfo.get("usertoken");
        //token不一样，被挤下线了
        if(!usertoken.equals(dbtoken)){
            res.put("detail", "静默失效原因，数据库和传递的不一样可能被挤下线了");
            return false;
        }
        //判断token的时间
        Date logintime = UserTokenManager.getLoginTimeFromToken(usertoken);
        Date currenttime = new Date();
        Date login7days = new Date(logintime.getTime()+7*24*60*60*1000);
        if(currenttime.after(login7days)){
            //7天后了失效
            res.put("detail", "静默失效原因，token超过7天没更新了");
            return false;
        }

        Map<String,String> userinfo = Const.getUserInfo(usertoken);
        String useraccount = userinfo.get("account");
        //静默失效的判断
        if(isOrgUser(usertoken)){
            //机构用户
            OrgUser orguser = userdao.getUser4Org(useraccount);
            if(orguser==null){
                res.put("detail", "静默失效原因，机构用户但是没有机构用户了");
                return false;
            }
            //离职失效
            if(orguser.getStatus()!=1){
                res.put("detail", "静默失效原因，机构用户但是机构身份被解除了");
                return false;
            }
            //密码不同失效
            if(!PasswordEncoder.matches(pwd, orguser.getUserPassword())){
                res.put("detail", "静默失效原因，密码改变了");
                return false;
            }
        }else{
            //个人用户
            PeUser peuser = userdao.getUser4Op(useraccount);
            if(peuser==null){
                res.put("detail", "静默失效原因，个人用户不存在");
                return false;
            }
            //密码不同失效
            if(!PasswordEncoder.matches(pwd, peuser.getUserpassword())){
                res.put("detail", "静默失效原因，密码改变了2");
                return false;
            }
            //有机构身份了失效
            OrgUser orguser = userdao.getUser4Org(useraccount);
            if(orguser!=null&&orguser.getStatus()==1){
                res.put("detail", "静默失效原因，新增了机构身份");
                return false;
            }
        }
        return true;
    }
    
    
    /**
	 * 个人用户获取网约车的预估费用
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEstimatedCost4OpNetCar(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			JSONObject obj = new JSONObject();
			res.put("costinfo", obj);
			String cityvalue = dicdao.getCityNo((String) params.get("city"));
			params.put("city", cityvalue);
			Map<String,Object> escostinfo = getEstimatedCost4Op(params,false);
			if((int)escostinfo.get("status")!=Retcode.OK.code){
				//预估失败
				escostinfo.put("costinfo",new HashMap<>());
				return escostinfo;
			}
			Map<String,Object> costinfo = (Map<String, Object>)escostinfo.get("costinfo");
			double cost = parseDouble(costinfo.get("cost"));
			obj.put("cost", cost);
            if(canZK4User()){
                //可以折扣
                double awardpercent = getZKPercent(false);
                if(awardpercent>0){
                    obj.put("cost", StringUtil.formatNum(cost*awardpercent,1));
                }
            }
//            if(canAward4User()){
//                double awardpercent = getAwardPercent(false);
//                obj.put("awardpint", Math.round(cost*awardpercent));
//            }else{
//                obj.put("awardpint", 0);
//            }
            obj.put("awardpint", 0);
			JSONArray paydetail = new JSONArray();
			JSONObject obj3 = new JSONObject();
			obj3.put("name", "起步价");
			obj3.put("price", costinfo.get("startprice")+"元");
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "里程费("+StringUtil.formatNum(((int)costinfo.get("mileage"))/1000, 1)+"公里"+")");
			obj4.put("price", costinfo.get("rangecost")+"元");
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "时长费("+StringUtil.formatCostTimeInSecond((int)costinfo.get("times"))+")");
			obj5.put("price", costinfo.get("timecost")+"元");
			paydetail.add(obj3);
			paydetail.add(obj4);
			paydetail.add(obj5);
			double mileage = StringUtil.formatNum(((int)costinfo.get("mileage"))/1000, 1);
			double deadheadmileage = parseDouble(costinfo.get("deadheadmileage"));
			if (mileage > deadheadmileage && parseDouble(costinfo.get("deadheadcost")) != 0) {
				JSONObject obj6 = new JSONObject();
				obj6.put("name", "空驶费("+StringUtil.formatNum(mileage-deadheadmileage,1)+"公里)");
				obj6.put("price", costinfo.get("deadheadcost")+"元");
				paydetail.add(obj6);
			}
			obj.put("paydetail",paydetail);
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("costinfo",new HashMap<>());
		}
		return res;
	}
	
	/**
	 * 个人用户获取预估费用
	 * @param params
	 * @return
	 */
	private Map<String,Object> getEstimatedCost4Op(Map<String,Object> params,boolean istaxi){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			if(istaxi){
				//出租车的预估
				Map<String,Object> accountrules = opdao.getOpOrderAccountRules4Taxi(params);
				if(accountrules==null){
					res.put("status", Retcode.NOSERVICESINCITY.code);
					res.put("message", Retcode.NOSERVICESINCITY.msg);
					return res;
				}
				BaiduApiQueryParam baqp = new BaiduApiQueryParam();
				baqp.setOrderStartLng(parseDouble(params.get("onaddrlng")));
				baqp.setOrderStartLat(parseDouble(params.get("onaddrlat")));
				baqp.setOrderEndLng(parseDouble(params.get("offaddrlng")));
				baqp.setOrderEndLat(parseDouble(params.get("offaddrlat")));
				Map<String,Object> direc = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baqp, Map.class);
				if(direc==null||((int)direc.get("status")) != Retcode.OK.code){
					res.put("status", Retcode.FAILED.code);
					res.put("message", Retcode.FAILED.msg);
					logger.error("获取预估里程失败！");
					return res;
				}
                //约车时限
                double tempprice = 0;
				try{
                    Map<String,Object> carsintervalinfo = opdao.getSendRule4ReverceTaxi(params);
                    String usetime = (String) params.get("usetime");
                    String isusenow = (String) params.get("isusenow");
                    if(carsintervalinfo!=null&&(StringUtils.isNotBlank(usetime)||"0".equals(isusenow))){
                        int carsinterval = parseInt(carsintervalinfo.get("carsinterval"));
                        if(StringUtils.isNotBlank(usetime)){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current = new Date();
                            Date yydate = new Date(current.getTime()+carsinterval*60*1000);
                            Date usetimeobj = format.parse(usetime);
                            if(usetimeobj.after(yydate)){
                                //预约时间
                                Map<String,Object> yyparams = new HashMap<String,Object>();
                                yyparams.put("type","个人用户出租车下单预约附加费用");
                                tempprice += parseDouble(getYYFJF(yyparams));
                            }
                        }else{
                            //预约时间
                            Map<String,Object> yyparams = new HashMap<String,Object>();
                            yyparams.put("type","个人用户出租车下单预约附加费用");
                            tempprice += parseDouble(getYYFJF(yyparams));
                        }
                    }
                }catch (Exception e){
				    logger.error("个人出租车预约用车计费规则加上预约费异常",e);
                }

				int mileage = (int)direc.get("distance");
				//打表来接的里程
				mileage = parseInt(params.get("meterrange"))*1000+mileage;
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算费用
				double startprice = parseDouble(accountrules.get("startprice"))+tempprice;
                accountrules.put("startprice",startprice);
				double startrange = parseDouble(accountrules.get("startrange"));
				double surcharge = parseDouble(accountrules.get("surcharge"));
				double emptytravelrate = parseDouble(accountrules.get("emptytravelrate"));
				double standardrange = parseDouble(accountrules.get("standardrange"));
				double renewalprice = parseDouble(accountrules.get("renewalprice"));
				
				double schedulefee = parseDouble(params.get("schedulefee"));
				double cost = 0;
				if((mileage/1000)<=startrange){
					//预估里程≤起租里程 --->预估费用=起租价+附加费+调度费
					cost = startprice+surcharge+schedulefee;
				}else if((mileage/1000)<=standardrange){
					//起租里程<预估里程≤标准里程--->预估费用=起租价+(预估里程-起租里程)*续租价+附加费+调度费
					cost = startprice+((mileage/1000)-startrange)*renewalprice+surcharge+schedulefee;
				}else{
					//预估里程>标准里程--->起租价+(预估里程-起租里程)*续租价+(预估里程-标准里程)*续租价*空驶费率+附加费+调度费
					cost = startprice+((mileage/1000)-startrange)*renewalprice+((mileage/1000)-standardrange)*renewalprice*(emptytravelrate/100)+surcharge+schedulefee;
				}
				accountrules.put("cost",StringUtil.formatNum(cost, 1));
				accountrules.put("schedulefee", schedulefee);
				accountrules.put("tourfee", StringUtil.formatNum(cost-schedulefee, 1));
				res.put("costinfo", accountrules);
			}else{
				//网约车的预估
				Map<String,Object> accountrules = opdao.getOpOrderAccountRules4NetCar(params);
				if(accountrules==null){
					res.put("status", Retcode.NOSERVICESINCITY.code);
					res.put("message", Retcode.NOSERVICESINCITY.msg);
					return res;
				}
				BaiduApiQueryParam baqp = new BaiduApiQueryParam();
				baqp.setOrderStartLng(parseDouble(params.get("onaddrlng")));
				baqp.setOrderStartLat(parseDouble(params.get("onaddrlat")));
				baqp.setOrderEndLng(parseDouble(params.get("offaddrlng")));
				baqp.setOrderEndLat(parseDouble(params.get("offaddrlat")));
				Map<String,Object> direc = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baqp, Map.class);
				if(direc==null||((int)direc.get("status")) != Retcode.OK.code){
					res.put("status", Retcode.FAILED.code);
					res.put("message", Retcode.FAILED.msg);
					logger.error("获取预估里程失败！");
					return res;
				}

				//约车时限
                double tempprice = 0;
                try{
                    Map<String,Object> carsintervalinfo = opdao.getSendRuleByCity4ReverceNetCar(params);
                    String usetime = (String) params.get("usetime");
                    String isusenow = (String) params.get("isusenow");
                    if(carsintervalinfo!=null&&(StringUtils.isNotBlank(usetime)||"0".equals(isusenow))){
                        int carsinterval = parseInt(carsintervalinfo.get("carsinterval"));
                        if(StringUtils.isNotBlank(usetime)){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current = new Date();
                            Date yydate = new Date(current.getTime()+carsinterval*60*1000);
                            Date usetimeobj = format.parse(usetime);
                            if(usetimeobj.after(yydate)){
                                //预约时间
                                Map<String,Object> yyparams = new HashMap<String,Object>();
                                yyparams.put("type","个人用户下单预约附加费用");
                                tempprice += parseDouble(getYYFJF(yyparams));
                            }
                        }else{
                            //预约时间
                            Map<String,Object> yyparams = new HashMap<String,Object>();
                            yyparams.put("type","个人用户下单预约附加费用");
                            tempprice += parseDouble(getYYFJF(yyparams));
                        }
                    }
                }catch (Exception e){
				    logger.error("个人用户网约车添加预约用车预约费失败",e);
                }

				int mileage = (int)direc.get("distance");
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算价格
				double startprice = parseDouble(accountrules.get("startprice"))+tempprice;
                accountrules.put("startprice",startprice);
				double rangeprice = parseDouble(accountrules.get("rangeprice"));
				double timeprice = parseDouble(accountrules.get("timeprice"));
				String timetype = (String) accountrules.get("timetype");
				double perhour = parseDouble(accountrules.get("perhour"));
				double deadheadmileage = parseDouble(accountrules.get("deadheadmileage"));
				double deadheadprice = parseDouble(accountrules.get("deadheadprice"));
				double rangecost = StringUtil.formatNum(mileage/1000, 1)*rangeprice;
				accountrules.put("rangecost", StringUtil.formatNum(rangecost,1));
				//低速模式计算累计时长(预估时仍按总时长计费)
				double timecost = (times%60>0?(times/60+1):times/60)*timeprice;
				accountrules.put("timecost",StringUtil.formatNum(timecost,1));				
				double deadheadcost = 0;
				if (StringUtil.formatNum(mileage/1000, 1) > deadheadmileage) {
					deadheadcost = (StringUtil.formatNum(mileage/1000, 1) - deadheadmileage)*deadheadprice;
					if (deadheadcost != 0) {
						accountrules.put("deadheadcost", StringUtil.formatNum(deadheadcost,1));
					}
				}
				accountrules.put("cost",StringUtil.formatNum(startprice+rangecost+timecost+deadheadcost,1));
				res.put("costinfo", accountrules);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
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
	
	private int parseInt(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Integer.parseInt(String.valueOf(value));
	}
	
	/**
	 * 获取预估费用
	 * @param params
	 * @return
	 */
	public Map<String,Object> getEstimatedCost(Map<String, Object> params) {
		Map<String,Object> ress = new HashMap<String,Object>();
		JSONObject obj = new JSONObject();
		ress.put("costinfo", obj);
		String usertoken = (String) params.get("usertoken");
		String companyid = (String) params.get("companyid");
		String cityvalue = dicdao.getCityNo((String) params.get("city"));
		params.put("city", cityvalue);
		JSONObject costinfo = null;
		double cost = 0;
		if(isOrgUser(usertoken)){
			//机构用户的订单
			params.put("orderprop", "0");
			//获取预估费用的时候，预估费用需要传递rulestype 0标准1个性化
			if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))){
				params.put("rulestype", "1");
			}else{
				params.put("rulestype", "0");
			}
			
			//现获取机构在租赁公司的可用额度
			OrgUser orguser = userdao.getUser4Org(Const.getUserInfo(usertoken).get("account"));
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("companyid",companyid);
			param.put("orgid", orguser.getOrganId());
			double balance = (double) userdao.getOrgBalance(param);
			try{
				params.put("userid", orguser.getId());
				costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))&&costinfo!=null&&costinfo.getInt("status")==0){
				//判断支付方式有哪些可用
				JSONArray paytypes = new JSONArray();
				JSONObject obj1 = new JSONObject();
				obj1.put("paymethod", "1");
				obj1.put("payname", "个人垫付");
				paytypes.add(obj1);
				
				String coststr = (String) costinfo.get("cost");
				cost = Double.parseDouble(coststr.substring(0, coststr.length()-1));
				JSONObject obj2 = new JSONObject();
				obj2.put("paymethod", 2);
				obj2.put("payname", "机构支付");
				if(balance<cost){
					//不能机构支付
					obj2.put("able", "false");
				}else{
					obj2.put("able", "true");
				}
				paytypes.add(obj2);
				obj.put("paytypes",paytypes);
			}
		}
		
		//估价明细
		if(costinfo!=null&&costinfo.getInt("status")==0){
			String coststr = (String) costinfo.get("cost");
			cost = Double.parseDouble(coststr.substring(0, coststr.length()-1));
			obj.put("cost", cost);
			
			JSONArray paydetail = new JSONArray();
			JSONObject obj3 = new JSONObject();
			obj3.put("name", "起步价");
			obj3.put("price", costinfo.get("startprice"));
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "里程("+costinfo.get("mileage")+")");
			obj4.put("price", costinfo.get("rangecost"));
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "时长费("+costinfo.get("times")+")");
			obj5.put("price", costinfo.get("timecost"));
			paydetail.add(obj3);
			paydetail.add(obj4);
			paydetail.add(obj5);
			if (costinfo.get("deadheadcost") != null && StringUtils.isNotBlank(String.valueOf(costinfo.get("deadheadcost")))) {
				JSONObject obj6 = new JSONObject();
				obj6.put("name", "空驶费("+costinfo.get("deadheadmileage")+")");
				obj6.put("price", costinfo.get("deadheadcost"));
				paydetail.add(obj6);
			}
			obj.put("paydetail",paydetail);
			ress.put("status", Retcode.OK.code);
			ress.put("message", Retcode.OK.msg);
		}else{
			return costinfo;
		}
		return ress;
	}
	
	/**
	 * 获取订单详细信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOder4Op(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String orderno = (String) param.get("orderno");
			String ordertype = (String) param.get("ordertype");
			String usetype = (String) param.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(usetype)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("order",new HashMap<>());
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(!"2".equals(usetype)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("order",new HashMap<>());
			}else if("2".equals(usetype)){
				//个人用户
				Map<String,Object> orderinfo = null;
				if(StringUtils.isBlank(ordertype)){
					//ordertype没有传递，兼容之前的
					//orderinfo = orderdao.getOrder4OpTaxi(param);
					//if(orderinfo==null){
						orderinfo = orderdao.getOrder4OpNetCar(param);
					//}
				}else{
					//有ordertype,根据ordertype区分订单类型
					if(isTaxiOrder(ordertype)){
						orderinfo = orderdao.getOrder4OpTaxi(param);
					}else{
						orderinfo = orderdao.getOrder4OpNetCar(param);
					}
				}
				if(orderinfo==null){
					orderinfo = new HashMap<>();
				}else{
					dillOrder(orderinfo,usertoken,true);
				}
				res.put("order",orderinfo);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("order",new HashMap<>());
		}
		return res;
	}
	
	/**
	 * 判断是否是taxiorder
	 * @param orderno
	 * @return
	 */
	private boolean isTaxiOrder(String ordertype){
		return OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype);
	}
	
	/**
	 * 处理订单信息
	 * @param order
	 */
	private void dillOrder(Map<String, Object> orderinfo,String usertoken,boolean isdetail){
		//数字显示一位小数
//		dillNumShow(orderinfo);
		try{
			double mileage = parseDouble(orderinfo.get("mileage"));
			orderinfo.put("mileagestr", StringUtil.formatNum(mileage/1000,1)+"公里");
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		if(isdetail){
			try{
				//订单的计费信息
				addCostInfo(orderinfo, usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			//详情的时候才加入的属性
			try{
				//添加司机信息
				addDriverPosition(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			try{
				//添加取消订单的信息
				addCancelOrderInfo(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			try{
				//待出发的距离出发时间
				addRemainTime(orderinfo, usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
		}
		try{
			//用户是否评论
			String userrate = (String) orderinfo.get("userrate");
			if(StringUtils.isNotBlank(userrate)){
				orderinfo.put("hasComment", true);
			}else{
				orderinfo.put("hasComment", false);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//上下车城市显示值
			String oncity = (String) orderinfo.get("oncity");
			String oncitycaption = dicdao.getCityCaption(oncity);
			orderinfo.put("oncity", oncitycaption==null?"":oncitycaption);
			
			String offcity = (String) orderinfo.get("offcity");
			String offcitycaption = dicdao.getCityCaption(offcity);
			orderinfo.put("offcity", offcitycaption==null?"":offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		//地址路径加上绝对路径
		addAbsolutPath(orderinfo);
		//添加订单的显示类型
		addOrderTypeCaption(orderinfo);
		//处理时间类型的最后的点
		dillTimeField(orderinfo);
		//处理待接单的剩余时间
		dillLeftTimes(orderinfo);
		//特殊字段处理
		dillSpecailField(orderinfo);
		//处理空值
		dillNull2BlankStr(orderinfo);
	}
	
	/**
	 * 添加订单的消费信息
	 * @param order
	 * @param usertoken
	 */
	private void addCostInfo(Map<String, Object> orderinfo, String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String ordertype = (String) orderinfo.get("ordertype");
		if(isTaxiOrder(ordertype)){
			orderinfo.put("startprice", 0);
			orderinfo.put("rangeprice", 0);
			orderinfo.put("timeprice", 0);
			orderinfo.put("rangecost", 0);
			orderinfo.put("timecost", 0);
			orderinfo.put("times", 0);
			orderinfo.put("mileage",0);
			return ;
		}
		String orderno = (String) orderinfo.get("orderno");
		OrderCostParam params = new OrderCostParam();
		params.setHasunit(true);
		params.setOrderid(orderno);
		params.setOrdertype(ordertype);
		params.setUsetype((String) orderinfo.get("usetype"));
		JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
		if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
			costinfo.remove("status");
			costinfo.remove("message");
			String amount = costinfo.getString("cost");
            String orderstatus = (String) orderinfo.get("orderstatus");
			if(StringUtils.isNotBlank(amount)){
			    if(OrderState.SERVICEDONE.state.equals(orderstatus)){
			        //行程结束
                    double srcorderamount = getSrcOrderAmount(costinfo);
 //                 orderinfo.put("orderamount", srcorderamount);
//                  orderinfo.put("orderamountint", (int)Math.round(parseDouble(amount.replaceAll("元", ""))));
                    orderinfo.put("orderamountint",(int)Math.round(StringUtil.formatNum(parseDouble(orderinfo.get("orderamount")),1)));
                    orderinfo.put("orderamount", StringUtil.formatNum(srcorderamount,1));
//                  orderinfo.put("orderamount", StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
//                  order.setOrderamountint((int)Math.round(StringUtil.formatNum(order.getOrderamount(),1)));
//                  order.setOrderamount(StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
                }else{
			        //行程未结束
                    orderinfo.put("orderamount", StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
                }
			}else{
                orderinfo.put("orderamount", 0);
                orderinfo.put("orderamountint", 0);
            }
			String mileagestr = costinfo.getString("mileage");
			orderinfo.put("startprice", costinfo.getString("startprice"));
			orderinfo.put("rangeprice", costinfo.getString("rangeprice"));
			orderinfo.put("timeprice", costinfo.getString("timeprice"));
			orderinfo.put("rangecost", costinfo.getString("rangecost"));
			orderinfo.put("timecost", costinfo.getString("timecost"));
			orderinfo.put("times", costinfo.getString("times"));
			orderinfo.put("mileagestr", StringUtils.isBlank(mileagestr)?"":mileagestr);
			orderinfo.put("mileage", parseDouble(StringUtils.isBlank(mileagestr)?"":mileagestr.replaceAll("公里", "")));
			String copyprice = (String) orderinfo.get("pricecopy");
			if(StringUtils.isNotBlank(copyprice)){
				OrderCost cost = (OrderCost)JSONObject.toBean(JSONObject.fromObject(copyprice), OrderCost.class);
				String timetype = StringUtil.formatTimeType(cost);
				orderinfo.put("timetype", timetype);
				if(cost.getTimetype()!=0){
					//低速用时
					orderinfo.put("times", StringUtil.formatCostTime(cost.getSlowtimes()));
				}
			}
			
			JSONObject costinfoobj = addCostInfo(costinfo);
			orderinfo.put("costinfo", costinfoobj);
		}
	}


    private double getSrcOrderAmount(JSONObject costinfo){
        if(costinfo==null){
            return 0;
        }
        String startpricestr = costinfo.getString("startprice");
        double startprice = parseDouble(startpricestr==null?null:startpricestr.replaceAll("元", ""));

        String rangecoststr = costinfo.getString("rangecost");
        double rangecost = parseDouble(rangecoststr==null?null:rangecoststr.replaceAll("元", ""));

        String timecoststr = costinfo.getString("timecost");
        double timecost = parseDouble(timecoststr==null?null:timecoststr.replaceAll("元", ""));

        String deadheadcoststr = costinfo.getString("deadheadcost");
        double deadheadcost = parseDouble(deadheadcoststr==null?null:deadheadcoststr.replaceAll("元", ""));

        String nightcoststr = costinfo.getString("nightcost");
        double nightcost = parseDouble(nightcoststr==null?null:nightcoststr.replaceAll("元", ""));
        return startprice+rangecost+deadheadcost+timecost+nightcost;
    }
	
	private JSONObject addCostInfo(JSONObject costinfo) {
		JSONArray detail = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("name", "起步价");
		obj1.put("value", costinfo.getString("startprice"));
		JSONObject obj2 = new JSONObject();
		obj2.put("name", "里程费("+costinfo.getString("mileage")+")");
		obj2.put("value", costinfo.getString("rangecost"));
		JSONObject obj3 = new JSONObject();
		obj3.put("name", "时长费("+costinfo.get("times")+")");
		obj3.put("value", costinfo.get("timecost"));
		detail.add(obj1);
		detail.add(obj2);
		detail.add(obj3);
		if (costinfo.get("deadheadcost") != null && parseDouble(costinfo.get("deadheadcost").toString().replace("元", "")) != 0) {
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "空驶费("+costinfo.get("realdeadheadmileage")+")");
			obj4.put("value", costinfo.get("deadheadcost"));
			detail.add(obj4);
		}
		if (costinfo.get("nightcost") != null && parseDouble(costinfo.get("nightcost").toString().replace("元", "")) != 0) {
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "夜间费("+costinfo.get("mileage")+")");
			obj5.put("value", costinfo.get("nightcost"));
			detail.add(obj5);
		}
		
		JSONArray amountdetail = new JSONArray();
		JSONObject obj6 = new JSONObject();
		obj6.put("name", "起步价");
		obj6.put("value", costinfo.getString("startprice"));
		JSONObject obj7 = new JSONObject();
		obj7.put("name", "里程费");
		obj7.put("value", costinfo.getString("rangeprice"));
		JSONObject obj8 = new JSONObject();
		obj8.put("name", "时长费");
		obj8.put("value", costinfo.getString("timeprice"));
		JSONObject obj9 = new JSONObject();
		obj9.put("name", "回空里程");
		obj9.put("value", costinfo.getString("deadheadmileage"));
		JSONObject obj10 = new JSONObject();
		obj10.put("name", "回空费价");
		obj10.put("value", costinfo.getString("deadheadprice"));
		JSONObject obj11 = new JSONObject();
		obj11.put("name", "夜间征收时段");
		obj11.put("value", parseTime(costinfo.get("nightstarttime")) + "-" + parseTime(costinfo.get("nightendtime")));
		JSONObject obj12 = new JSONObject();
		obj12.put("name", "夜间费价");
		obj12.put("value", costinfo.getString("nighteprice"));		
		amountdetail.add(obj6);
		amountdetail.add(obj7);
		amountdetail.add(obj8);
		amountdetail.add(obj9);
		amountdetail.add(obj10);
		amountdetail.add(obj11);
		amountdetail.add(obj12);

		JSONObject obj = new JSONObject();
		obj.put("detail", detail);
		obj.put("amountdetail", amountdetail);
		return obj;
	}
	
	private String parseTime(Object value) {
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return "00:00";
		}
		return String.valueOf(value);
	}
	
	private void addDriverPosition(Map<String, Object> orderinfo,String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String driverid = (String) orderinfo.get("driverid");
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> driverinfo = getDriverPosition(usertoken,orderinfo);
			if(driverinfo!=null){
				driverinfo.remove("status");
				driverinfo.remove("message");
				//将订单中的实时金额放入信息中
				double orderamount = parseDouble(orderinfo.get("orderamount"));
				driverinfo.put("orderamount",orderamount+"元");
				orderinfo.put("driverposition", driverinfo);
			}
		}
	}
	
	/**
	 * 获取司机的位置信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	public Map<String, Object> getDriverPosition(String usertoken,Map<String, Object> orderinfo) {
		String driverid = (String) orderinfo.get("driverid");
		String orderid = (String) orderinfo.get("orderno");
		String ordertype = (String) orderinfo.get("ordertype");
		String usetype = (String) orderinfo.get("usetype");
		Map<String,Object> res = dicdao.getDriverPosition(driverid);
		if(res==null||StringUtils.isBlank(orderid)||StringUtils.isBlank(ordertype)){
			if(res==null){
				res = new HashMap<String,Object>();
			}
			res.put("hintmessage", "");
			res.put("orderamount", "");
			res.put("lefttime", "");
		}else{
			//需要根据订单信息查出正在服务中的订单，如果没有就不显示下面的信息
			String orderstatus = null;
			double offaddrlat = 0;
			double offaddrlng = 0;
			Date usetime = null;
			if(!"2".equals(usetype)){
				//机构用户
				//获取订单相关的消费信息
				try{
					PassengerOrder order = orderdao.getOrderByOrderno4Org(orderid);
					orderstatus = order.getOrderstatus();
					offaddrlat = order.getOffaddrlat();
					offaddrlng = order.getOffaddrlng();
					usetime = order.getUsetime();
					OrderCostParam params = new OrderCostParam();
					params.setHasunit(true);
					params.setOrderid(orderid);
					params.setOrdertype(order.getOrdertype());
					params.setUsetype(order.getUsetype());
					JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, null, params, JSONObject.class);
					if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
						costinfo.remove("status");
						costinfo.remove("message");
						String amount = costinfo.getString("cost");
						res.put("orderamount", amount);
					}else{
						res.put("orderamount", "0.00元");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				//个人用户
				if(isTaxiOrder(ordertype)){
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("orderno", orderid);
					Map<String,Object> order = orderdao.getOrder4OpTaxi(param);
					orderstatus = (String) order.get("orderstatus");
					offaddrlat = parseDouble(order.get("offaddrlat"));
					offaddrlng = parseDouble(order.get("offaddrlng"));
					usetime = (Date) order.get("usetime");
				}else{
					//获取实时费用
					try{
						PassengerOrder order = orderdao.getOrderByOrderno4Op(orderid);
						orderstatus = order.getOrderstatus();
						offaddrlat = order.getOffaddrlat();
						offaddrlng = order.getOffaddrlng();
						usetime = order.getUsetime();
						OrderCostParam params = new OrderCostParam();
						params.setHasunit(true);
						params.setOrderid(orderid);
						params.setOrdertype(order.getOrdertype());
						params.setUsetype(order.getUsetype());
						JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, null, params, JSONObject.class);
						if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
							costinfo.remove("status");
							costinfo.remove("message");
							String amount = costinfo.getString("cost");
							res.put("orderamount", amount);
						}else{
							res.put("orderamount", "0.00元");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			res.put("orderstatus", orderstatus);
			if(OrderState.INSERVICE.state.equalsIgnoreCase(orderstatus)||OrderState.WAITMONEY.state.equalsIgnoreCase(orderstatus)){
				String dorderinfo = JedisUtil.getString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderid);
				if(StringUtils.isNotBlank(dorderinfo)){
					JSONObject json = JSONObject.fromObject(dorderinfo);
					int lefttime = json.getInt("lefttime");
					int leftkm = json.getInt("leftkm");
					double dis =  ((Integer)leftkm).doubleValue()/1000;
					dis = StringUtil.formatNum(dis, 1);
                    if(dis<=0){
                        dis=0.1;
                    }
					String durstr = StringUtil.formatCostTimeInSecond(lefttime);
					res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
				}else{
					//调用接口
					BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
					baiduparam.setOrderStartLat((double) res.get("lat"));
					baiduparam.setOrderStartLng((double) res.get("lng"));
					baiduparam.setOrderEndLat(offaddrlat);
					baiduparam.setOrderEndLng(offaddrlng);
					Map<String,Object> hintinfo = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baiduparam, Map.class);
					if(hintinfo!=null&&(Integer)hintinfo.get("status")==Retcode.OK.code){
						Object distance = hintinfo.get("distance");
						double dis = 0;
						if(distance!=null){
							dis =  ((Integer)distance).doubleValue()/1000;
							dis = StringUtil.formatNum(dis, 1);
						}
                        if(dis<=0){
                            dis=0.1;
                        }
						Object duration = hintinfo.get("duration");
						String durstr = "1分钟";
						if(duration!=null){
							durstr = StringUtil.formatCostTimeInSecond((int) duration);
						}
						res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
					}else{
						res.put("hintmessage", "正在获取信息中。。。");
					}
				}
			}else if(OrderState.WAITSTART.state.equalsIgnoreCase(orderstatus)){
				//带出发给出带出发的
				if(usetime!=null){
					Date current = new Date();
					int lefttime = (int)((usetime.getTime() - current.getTime())/(1000*60));
					String lefttimestr = StringUtil.formatCostTime(lefttime<=0?1:lefttime);
					res.put("lefttime", lefttimestr);
				}else{
					res.put("lefttime", "");
				}
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
	/**
	 * 添加取消订单的信息
	 * @param order
	 */
	private void addCancelOrderInfo(Map<String, Object> orderinfo,String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String cancelparty = (String) orderinfo.get("cancelparty");
		String orderstatus = (String) orderinfo.get("orderstatus");
		//取消订单才有取消人和取消时间
		if(OrderState.CANCEL.state.equalsIgnoreCase(orderstatus)&&StringUtils.isNotBlank(cancelparty)){
			switch (cancelparty) {
			case "0":
				//租赁端
				orderinfo.put("cancelname", "租赁公司管理员");
				break;
			case "1":
				//运管取消
				orderinfo.put("cancelname", "运管端管理员");
				break;
			case "2":
				//机构取消
				orderinfo.put("cancelname", "您");
				break;
			case "3":
				//app取消
				orderinfo.put("cancelname", "您");
				break;
			default:
				orderinfo.put("cancelname", "系统派单失败");
				break;
			}
		}
	}
	
	private void addRemainTime(Map<String, Object> orderinfo, String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String orderstatus = (String) orderinfo.get("orderstatus");
		if(OrderState.WAITSTART.state.equalsIgnoreCase(orderstatus)){
			Date usertime = (Date) orderinfo.get("usetime");
			long currenttime = System.currentTimeMillis();
			if(usertime!=null){
				int remain = (int)((usertime.getTime()-currenttime)/(1000*60));
				remain = remain<=0?1:remain;
				orderinfo.put("remaintime", StringUtil.formatCostTime(remain));
			}
		}
	}
	
	/**
	 * 添加绝对地址
	 * @param orders
	 */
	private void addAbsolutPath(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		
		//车型路径
		String selectedmodelpath = (String) orderinfo.get("selectedmodelpath");
		if(StringUtils.isNotBlank(selectedmodelpath)){
			orderinfo.put("selectedmodellogo", SystemConfig.getSystemProperty("fileserver")+File.separator+selectedmodelpath);
		}else{
			orderinfo.put("selectedmodellogo","");
		}
		orderinfo.remove("selectedmodelpath");
		//司机图片
		String driverimg = (String) orderinfo.get("driverimg");
		if(StringUtils.isNotBlank(driverimg)){
			orderinfo.put("driverimg", SystemConfig.getSystemProperty("fileserver")+File.separator+driverimg);
		}
	}
	
	/**
	 * 添加订单的显示类型
	 * @param orderinfo
	 */
	private void addOrderTypeCaption(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		boolean isusenow = (boolean)orderinfo.get("isusenow");
		String ordertypecaption = "";
		if(isusenow){
			ordertypecaption += "即刻用车·";
		}else{
			//预约
			ordertypecaption += "预约用车·";
		}
		String orderstyle = (String) orderinfo.get("orderstyle");
		if("1".equals(orderstyle)){
			//出租车
			ordertypecaption += "出租车";
		}else{
			//网约车
			String ordertype = (String)orderinfo.get("ordertype");
			if("1".equalsIgnoreCase(ordertype)){
				ordertypecaption += "约车";
			}else if("2".equalsIgnoreCase(ordertype)){
				ordertypecaption += "接机";
			}else{
				ordertypecaption += "送机";
			}
		}
		orderinfo.put("ordertypecaption",ordertypecaption);
	}
	
	/**
	 * 处理时间字段最后的时间点
	 * @param orderinfo
	 */
	private void dillTimeField(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
//		String usetime = (String) orderinfo.get("usetime");
//		if(StringUtils.isNotBlank(usetime)&&usetime.length()>19){
//			orderinfo.put("usetime", usetime.substring(0, usetime.length()-2));
//		}
		String undertime = (String) orderinfo.get("undertime");
		if(StringUtils.isNotBlank(undertime)&&undertime.length()>19){
			orderinfo.put("undertime", undertime.substring(0, undertime.length()-2));
		}
		String ordertime = (String) orderinfo.get("ordertime");
		if(StringUtils.isNotBlank(ordertime)&&ordertime.length()>19){
			orderinfo.put("ordertime", ordertime.substring(0, ordertime.length()-2));
		}
		String starttime = (String) orderinfo.get("starttime");
		if(StringUtils.isNotBlank(starttime)&&starttime.length()>19){
			orderinfo.put("starttime", starttime.substring(0, starttime.length()-2));
		}
		String endtime = (String) orderinfo.get("endtime");
		if(StringUtils.isNotBlank(endtime)&&endtime.length()>19){
			orderinfo.put("endtime", endtime.substring(0, endtime.length()-2));
		}
		String canceltime = (String) orderinfo.get("canceltime");
		if(StringUtils.isNotBlank(canceltime)&&canceltime.length()>19){
			orderinfo.put("canceltime", canceltime.substring(0, canceltime.length()-2));
		}
	}
	
	/**
	 * 处理待接单剩余时间
	 * @param orderinfo
	 */
	private void dillLeftTimes(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		String orderstatus = (String) orderinfo.get("orderstatus");
//		if(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus)){
			Date autocanceltime = (Date) orderinfo.get("autocanceltime");
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				orderinfo.put("lefttime", lefttime<0?0:lefttime);
			}else{
				orderinfo.put("lefttime", 0);
			}
//		}
	}
	
	/**
	 * 处理特殊字段
	 * @param orderinfo
	 */
	private void dillSpecailField(Map<String, Object> orderinfo) {
		Object value = orderinfo.get("mileage");
		if(value==null||((value instanceof String) &&StringUtils.isBlank((String)value))){
			orderinfo.put("mileage", 0);
		}
		Object orderamount = orderinfo.get("orderamount");
		if(orderamount==null||((orderamount instanceof String) &&StringUtils.isBlank((String)orderamount))){
			orderinfo.put("orderamount", 0);
		}
		Object estimatedtime = orderinfo.get("estimatedtime");
		if(estimatedtime==null||((estimatedtime instanceof String) &&StringUtils.isBlank((String)estimatedtime))){
			orderinfo.put("estimatedtime", 0);
		}
		Object estimatedmileage = orderinfo.get("estimatedmileage");
		if(estimatedmileage==null||((estimatedmileage instanceof String) &&StringUtils.isBlank((String)estimatedmileage))){
			orderinfo.put("estimatedmileage", 0);
		}
		Object estimatedcost = orderinfo.get("estimatedcost");
		if(estimatedcost==null||((estimatedcost instanceof String) &&StringUtils.isBlank((String)estimatedcost))){
			orderinfo.put("estimatedcost", 0);
		}
		Object lefttime = orderinfo.get("lefttime");
		if(lefttime==null||((lefttime instanceof String) &&StringUtils.isBlank((String)lefttime))){
			orderinfo.put("lefttime", 0);
		}
	}
	
	/**
	 * 处理map的空值问题
	 * @param valueinfo
	 */
	private void dillNull2BlankStr(Map<String, Object> valueinfo){
		if(valueinfo==null){
			return ;
		}
		Iterator<String> keyit = valueinfo.keySet().iterator();
		while(keyit.hasNext()){
			String key = keyit.next();
			Object value = valueinfo.get(key);
			if(value!=null){
				continue ;
			}
			valueinfo.put(key,"");
		}
	}
	
	/**
	 * 获取订单详细信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOder(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全");
				res.put("order", "");
				return res;
			}
			PassengerOrder orderinfo = null;
			if(!"2".equals(usetype)){
				//机构用户
				orderinfo = orderdao.getOrderByOrderno4Org(orderno);
			}/*else{
				//个人用户
				orderinfo = orderdao.getOrderByOrderno4Op(orderno);
			}*/
			try{
				dillWithOrderInfo(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", Retcode.EXCEPTION.msg);
				res.put("order", "");
				return res;
			}
			res.put("order", orderinfo==null?"":orderinfo);
		}catch(Exception e){
			logger.error("获取订单信息出错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", "");
		}
		return res;
	}
	
	/**
	 * 订单额外显示信息
	 */
	private void dillWithOrderInfo(PassengerOrder order,String usertoken){
		if(order==null||usertoken==null){
			return ;
		}
		if(OrderEnum.ORDERTYPE_TAXI.code.equals(order.getOrdertype())){
			order.setOrderstyle("1");
		}else{
			order.setOrderstyle("0");
		}
		double amount = order.getOrderamount();
		String orderstatus = order.getOrderstatus();
		//行程结束但是钱是0就表明还未真正结束
//		if(OrderState.SERVICEDONE.state.equalsIgnoreCase(orderstatus)&&amount<=0){
//			order.setOrderstatus(OrderState.INSERVICE.state);
//		}
		//添加订单状态显示值
		addOrderStatusCaption4Order(order);
		//用户是否评论
		String userrate = order.getUserrate();
		if(StringUtils.isNotBlank(userrate)){
			order.setHasComment(true);
		}
		
		try{
			String oncity = order.getOncity();
			String oncitycaption = dicdao.getCityCaption(oncity);
			order.setOncity(oncitycaption);
			
			String offcity = order.getOffcity();
			String offcitycaption = dicdao.getCityCaption(offcity);
			order.setOffcity(offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//订单的计费信息
			addCostInfo(order, usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//待出发的距离出发时间
			addRemainTime(order, usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加取消订单的信息
			addCancelOrderInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加订单的客服信息
			addOrderServiceInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//订单车型logo
			addOrderCarLogoInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加司机信息
			addDriverPosition(usertoken,order);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			Date autocanceltime = order.getAutocanceltime();
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				order.setLefttime(lefttime<0?0:lefttime);
			}else{
				order.setLefttime(0);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
	}
	
	/**
	 * 添加订单状态的显示值
	 * @param order
	 */
	private void addOrderStatusCaption4Order(PassengerOrder order){
		switch (order.getOrderstatus()) {
		case "0":
			order.setOrderstatuscaption("待接单");	
			break;
		case "1":
			order.setOrderstatuscaption("待接单");	
			break;
		case "2":
			order.setOrderstatuscaption("待出发");	
			break;
		case "3":
			order.setOrderstatuscaption("行程中");	
			break;
		case "4":
			order.setOrderstatuscaption("行程中");	
			break;
		case "5":
			order.setOrderstatuscaption("行程中");	
			break;
		case "6":
			order.setOrderstatuscaption("行程中");	
			break;
		case "8":
			order.setOrderstatuscaption("已取消");	
			break;
		case "7":
			switch (order.getPaymentstatus()) {
			case "0":
				order.setOrderstatuscaption("待付款");
				break;
			default:
				order.setOrderstatuscaption("已完成");
				break;
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 添加订单的消费信息
	 * @param order
	 * @param usertoken
	 */
	private void addCostInfo(PassengerOrder order, String usertoken) {
		OrderCostParam params = new OrderCostParam();
		params.setHasunit(true);
        params.setCompanyid(order.getCompanyid());
		params.setOrderid(order.getOrderno());
		JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
		if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
			costinfo.remove("status");
			costinfo.remove("message");
			String amount = costinfo.getString("cost");
			if(StringUtils.isNotBlank(amount)){
			    if(OrderState.SERVICEDONE.state.equals(order.getOrderstatus())){
			        //行程结束
                    double srcorderamount = getSrcOrderAmount(costinfo);
                    order.setOrderamountint((int)Math.round(StringUtil.formatNum(order.getOrderamount(),1)));
    //				order.setOrderamount(StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
                    order.setOrderamount(StringUtil.formatNum(srcorderamount,1));
                }else{
			        //行程未结束
                    order.setOrderamount(StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
                }

			}
			order.setStartprice(costinfo.getString("startprice"));
			order.setRangeprice(costinfo.getString("rangeprice"));
			order.setTimeprice(costinfo.getString("timeprice"));
			order.setRangecost(costinfo.getString("rangecost"));
			order.setTimecost(costinfo.getString("timecost"));
			order.setTimes(costinfo.getString("times"));
			order.setMileagestr(costinfo.getString("mileage"));
			String copyprice = order.getPricecopy();
			if(StringUtils.isNotBlank(copyprice)){
				OrderCost cost = (OrderCost)JSONObject.toBean(JSONObject.fromObject(copyprice), OrderCost.class);
				String timetype = StringUtil.formatTimeType(cost);
				order.setTimetype(timetype);
				if(cost.getTimetype()!=0){
					//低速用时
					order.setTimes(StringUtil.formatCostTime(cost.getSlowtimes()));
				}
			}
			
			JSONObject costinfoobj = addCostInfo(costinfo);
			order.setCostinfo(costinfoobj);
		}
	}
	
	private void addRemainTime(PassengerOrder order, String usertoken) {
		if(OrderState.WAITSTART.state.equalsIgnoreCase(order.getOrderstatus())){
			Date usertime = order.getUsetime();
			long currenttime = System.currentTimeMillis();
			if(usertime!=null){
				int remain = (int)((usertime.getTime()-currenttime)/(1000*60));
				remain = remain<=0?1:remain;
				order.setRemaintime(StringUtil.formatCostTime(remain));
			}
		}
	}
	
	/**
	 * 添加取消订单的信息
	 * @param order
	 */
	private void addCancelOrderInfo(PassengerOrder order,String usertoken) {
		String cancelparty = order.getCancelparty();
		//取消订单才有取消人和取消时间
		if(OrderState.CANCEL.state.equalsIgnoreCase(order.getOrderstatus())&&StringUtils.isNotBlank(cancelparty)){
			switch (cancelparty) {
			case "0":
				//租赁端
				order.setCancelname("租赁公司管理员");
				break;
			case "1":
				//运管取消
				order.setCancelname("运管端管理员");
				break;
			case "2":
				//机构取消
				order.setCancelname("您");
				break;
			case "3":
				//app取消
				order.setCancelname("您");
				break;
			default:
				order.setCancelname("系统派单失败");
				break;
			}
		}
	}
	
	/**
	 * 添加订单的客服信息
	 * @param order
	 * @param usertoken
	 */
	private void addOrderServiceInfo(PassengerOrder order, String usertoken) {
		if(isOrgUser(usertoken)){
			//机构订单，查询每个租赁公司的客服信息
			String companyid = order.getCompanyid();
			Map<String,Object> companyinfo = orderdao.getLeCompanyInfo(companyid);
			order.setServicephone((String) companyinfo.get("servicesphone"));
		}else{
			//查询运管客服信息
			Map<String,Object> opinfo = orderdao.getOpInfo();
			order.setServicephone((String) opinfo.get("servcietel"));
		}
	}
	
	private void addOrderCarLogoInfo(PassengerOrder order, String usertoken) {
		String logo = order.getSelectedmodellogo();
		if(StringUtils.isNotBlank(logo)){
			order.setSelectedmodellogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
		}
	}
	
	private void addDriverPosition(String usertoken,PassengerOrder order) {
		String driverid = order.getDriverid();
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> driverinfo = getDriverPosition(usertoken, driverid, order.getOrderno());
			if(driverinfo!=null){
				driverinfo.remove("status");
				driverinfo.remove("message");
				//将订单中的实时金额放入信息中
				driverinfo.put("orderamount", order.getOrderamount()+"元");
				order.setDriverposition(driverinfo);
			}
		}
	}
	
	/**
	 * 获取司机的位置信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	public Map<String, Object> getDriverPosition(String usertoken, String driverid,String orderid) {
		Map<String,Object> res = dicdao.getDriverPosition(driverid);
		if(res==null){
			res = new HashMap<String,Object>();
		}else{
			//获取实时费用
			OrderCostParam params = new OrderCostParam();
			params.setHasunit(true);
			params.setOrderid(orderid);
			try{
				JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
				if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
					costinfo.remove("status");
					costinfo.remove("message");
					String amount = costinfo.getString("cost");
					res.put("orderamount", amount);
				}else{
					res.put("orderamount", "0.00元");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//需要根据订单信息查出正在服务中的订单，如果没有就不显示下面的信息
			PassengerOrder order = null;
			if(isOrgUser(usertoken)){
				//机构用户
				//获取订单相关的消费信息
				order = orderdao.getOrderByOrderno4Org(orderid);
			}else{
				//个人用户
				order = orderdao.getOrderByOrderno4Op(orderid);
			}
			if(order!=null){
				res.put("orderstatus", order.getOrderstatus());
				if(OrderState.INSERVICE.state.equalsIgnoreCase(order.getOrderstatus())){
					String orderinfo = JedisUtil.getString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderid);
					if(StringUtils.isNotBlank(orderinfo)){
						JSONObject json = JSONObject.fromObject(orderinfo);
						int lefttime = json.getInt("lefttime");
						int leftkm = json.getInt("leftkm");
						double dis =  ((Integer)leftkm).doubleValue()/1000;
						dis = StringUtil.formatNum(dis, 1);
						if(dis<=0){
                            dis=0.1;
                        }
						String durstr = StringUtil.formatCostTimeInSecond(lefttime);
						res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
					}else{
						//调用接口
						BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
						baiduparam.setOrderStartLat((double) res.get("lat"));
						baiduparam.setOrderStartLng((double) res.get("lng"));
						baiduparam.setOrderEndLat(order.getOffaddrlat());
						baiduparam.setOrderEndLng(order.getOffaddrlng());
						Map<String,Object> hintinfo = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, usertoken, baiduparam, Map.class);
						if(hintinfo!=null&&(Integer)hintinfo.get("status")==Retcode.OK.code){
							Object distance = hintinfo.get("distance");
							double dis = 0;
							if(distance!=null){
								dis =  ((Integer)distance).doubleValue()/1000;
								dis = StringUtil.formatNum(dis, 1);
							}
                            if(dis<=0){
                                dis=0.1;
                            }
							Object duration = hintinfo.get("duration");
							String durstr = "1分钟";
							if(duration!=null){
								durstr = StringUtil.formatCostTimeInSecond((int)duration);
							}
							res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
						}
					}
				}else if(OrderState.WAITSTART.state.equalsIgnoreCase(order.getOrderstatus())){
					//带出发给出带出发的
					Date usetime = order.getUsetime();
					Date current = new Date();
					int lefttime = (int)((usetime.getTime() - current.getTime())/(1000*60));
					String lefttimestr = StringUtil.formatCostTime(lefttime<=0?1:lefttime);
					res.put("lefttime", lefttimestr);
				}
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

    /**
     * 跳转到其他界面
     * @param params
     * @return
     */
    public Map<String,Object> go2OtherPage(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String usertoken = (String) params.get("usertoken");
            String uuid = (String) params.get("uuid");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(uuid)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            Map<String,String> userinfo = Const.getUserInfo(usertoken);
            if(userinfo==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不合法");
                return res;
            }
            String useraccount = userinfo.get("account");
            OrgUser orguser = userdao.getUser4Org(useraccount);
            if(orguser==null){
                res.put("hasorguser",false);
                res.put("hasorgidentity",false);
            }else if(orguser.getStatus()!=1){
                //离职的
                res.put("hasorguser",true);
                res.put("hasorgidentity",false);
            }else{
                res.put("hasorguser",true);
                res.put("hasorgidentity",true);
            }
            if(!isOrgUser(usertoken)){
                //非机构的token说明是跳转到因公界面
                if(orguser==null){
                    //没有机构用户，不能跳转到机构这个是异常
                    res.put("status", Retcode.EXCEPTION.code);
                    res.put("message",Retcode.EXCEPTION.msg);
                    res.put("hasorgidentity",false);
                    res.put("usertoken",false);
                    res.put("tags",new ArrayList<>());
                    res.put("nickname","");
                    res.put("telphone","");
                    res.put("imgpath","");
                    res.put("hasorguser",false);
                    return res;
                }
                String newusertoken = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,orguser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                Map<String,Object> pp = new HashMap<String,Object>();
                pp.put("userid", orguser.getId());
                pp.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                Map<String,Object> tokeninfo = new HashMap<String,Object>();
                if(dbusertokeninfo==null){
                    tokeninfo.put("id", GUIDGenerator.newGUID());
                }
                tokeninfo.put("usertoken", newusertoken);
                tokeninfo.put("userid", orguser.getId());
                tokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
                tokeninfo.put("uuid", uuid);
                userdao.createOrUpdateUsertoken(tokeninfo);
                byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                String decodetoken = new String(usertokencode4);
                String timetag = decodetoken.substring(32, 49);
                List<String> tags = new ArrayList<String>();
                tags.add(timetag);
                tags.add("0");
                res.put("tags", tags);
                res.put("usertoken", newusertoken);
                res.put("nickname", orguser.getNickName());
                res.put("telphone", orguser.getAccount());
                res.put("sex", orguser.getSex());
                String imgpath = orguser.getHeadPortraitMax();
                if(StringUtils.isNotBlank(imgpath)){
                    res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                }
            }else{
                //机构的token说明是跳转到因私界面
                PeUser peuser = userdao.getUser4Op(useraccount);
                //个人用户身份
                String newusertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,peuser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
                Map<String,Object> pp = new HashMap<String,Object>();
                pp.put("userid", peuser.getId());
                pp.put("usertype", Const.USERTOKENTYPE_PEUSER);
                Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
                Map<String,Object> tokeninfo = new HashMap<String,Object>();
                if(dbusertokeninfo==null){
                    tokeninfo.put("id", GUIDGenerator.newGUID());
                }
                tokeninfo.put("usertoken", newusertoken);
                tokeninfo.put("userid", peuser.getId());
                tokeninfo.put("uuid", uuid);
                tokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
                userdao.createOrUpdateUsertoken(tokeninfo);
                byte[] usertokencode4 = Base64.decodeBase64(newusertoken);
                String decodetoken = new String(usertokencode4);
                String timetag = decodetoken.substring(32, 49);
                List<String> tags = new ArrayList<String>();
                tags.add(timetag);
                tags.add("1");
                res.put("tags", tags);
                res.put("usertoken", newusertoken);
                res.put("nickname", peuser.getNickname());
                res.put("telphone", peuser.getAccount());
                res.put("sex", peuser.getSex());
                String imgpath = peuser.getHeadportraitmin();
                if(StringUtils.isNotBlank(imgpath)){
                    res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
                }
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("hasorgidentity",false);
            res.put("usertoken",false);
            res.put("tags",new ArrayList<>());
            res.put("nickname","");
            res.put("telphone","");
            res.put("imgpath","");
            res.put("hasorguser",false);
        }
        return res;
    }

    /**
     * 回执设置极光的注册id
     * @param params
     * @return
     */
    public Map<String,Object> postRegisterId(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String usertoken = (String) params.get("usertoken");
            String registerid = (String) params.get("registerid");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(registerid)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            Map<String,String> userinfo = Const.getUserInfo(usertoken);
            if(userinfo==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不合法");
                return res;
            }
            String useraccount = userinfo.get("account");
            Map<String,Object> updateparams = new HashMap<String,Object>();
            updateparams.put("account",useraccount);
            updateparams.put("registerid",registerid);
            if(isOrgUser(usertoken)){
                //机构用户
                userdao.updateRegisterId4Org(updateparams);
            }else{
                //个人用户
                userdao.updateRegisterId4Op(updateparams);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取服务车企列表
     * v3.0.1的时候查询的车企是定制化的车企
     * @param params
     * @return
     */
    public Map<String,Object> getServiceCompanys(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String type = (String) params.get("type");
            if(StringUtils.isBlank(type)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            List<Map<String,Object>> companys = new ArrayList<Map<String,Object>>();
            res.put("companys",companys);
            if(Const.ORDERTYPE_ORG.equals(type)){
                //机构订单
                //只查找如约，最老的那个租赁公司就默认是如约
                List<Map<String,Object>> ruyuecompanys = orgdao.getOldestServiceCompanys();
                if(ruyuecompanys!=null&&ruyuecompanys.size()>0){
                    ruyuecompanys.get(0).put("name","如约的士");
                }
                companys.addAll(ruyuecompanys);
            }else{
                //个人订单
                Map<String,Object> all = new HashMap<String,Object>();
                all.put("id","");
                all.put("name","如约的士");
                companys.add(all);
                List<Map<String,Object>> belongcompanys = dicdao.getBelongCompanys();
                companys.addAll(belongcompanys);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("companys",new ArrayList<>());
        }
        return res;
    }

    /**
     * 获取网约车预估费用
     * @param params
     * @return
     */
    public Map<String,Object> getEstimatedCost4NetCar(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String type = (String) params.get("type");
            if(StringUtils.isBlank(type)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            if(Const.ORDERTYPE_ORG.equals(type)){
                //机构订单
                res = getEstimatedCost4OrgNetCar(params);
            }else{
                //个人订单
                res = getEstimatedCost4OpNetCar(params);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取预估费用
     * @param params
     * @return
     */
    public Map<String,Object> getEstimatedCost4OrgNetCar(Map<String, Object> params) {
        Map<String,Object> ress = new HashMap<String,Object>();
        JSONObject obj = new JSONObject();
        ress.put("costinfo", obj);
        String usertoken = (String) params.get("usertoken");
        String companyid = (String) params.get("companyid");
        String cityvalue = dicdao.getCityNo((String) params.get("city"));
        params.put("city", cityvalue);
        JSONObject costinfo = null;
        double cost = 0;
        //机构用户的订单
        params.put("orderprop", "0");
        //获取预估费用的时候，预估费用需要传递rulestype 0标准1个性化
        if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))){
            params.put("rulestype", "1");
        }else{
            if("0".equalsIgnoreCase((String)params.get("type"))){
                params.put("rulestype", "1");
                params.put("usetype", Const.USETYPE_PUBLIC);
            }else{
                params.put("rulestype", "0");
            }
        }

        //现获取机构在租赁公司的可用额度
        OrgUser orguser = userdao.getUser4Org(Const.getUserInfo(usertoken).get("account"));
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("companyid",companyid);
        param.put("orgid", orguser.getOrganId());
        double balance = (double) userdao.getOrgBalance(param);
        try{
        	if("0".equals(params.get("isusenow"))){
        		params.put("isusenow", false);
        	}else{
        		params.put("isusenow", true);
        	}
            params.put("userid", orguser.getId());
            costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
        }catch(Exception e){
            logger.error("乘客端异常",e);
        }
        //估价明细
        if(costinfo!=null&&costinfo.getInt("status")==0){
            String coststr = (String) costinfo.get("cost");
            cost = Double.parseDouble(coststr.substring(0, coststr.length()-1));
            obj.put("cost", cost);
//            if(canZK4User()){
//                //可以折扣
//                double awardpercent = getZKPercent(true);
//                if(awardpercent>0){
//                    obj.put("cost", StringUtil.formatNum(cost*awardpercent,1));
//                }
//            }
//            if(canAward4User()){
//                double awardpercent = getAwardPercent(true);
//                obj.put("awardpint", Math.round(cost*awardpercent));
//            }else{
//                obj.put("awardpint", 0);
//            }
            obj.put("awardpint", 0);
            JSONArray paydetail = new JSONArray();
            JSONObject obj3 = new JSONObject();
            obj3.put("name", "起步价");
            obj3.put("price", costinfo.get("startprice"));
            JSONObject obj4 = new JSONObject();
            obj4.put("name", "里程费("+costinfo.get("mileage")+")");
            obj4.put("price", costinfo.get("rangecost"));
            JSONObject obj5 = new JSONObject();
            obj5.put("name", "时长费("+costinfo.get("times")+")");
            obj5.put("price", costinfo.get("timecost"));
            paydetail.add(obj3);
            paydetail.add(obj4);
            paydetail.add(obj5);
            if (costinfo.get("deadheadcost") != null && StringUtils.isNotBlank(String.valueOf(costinfo.get("deadheadcost")))) {
                String pricestr = (String) costinfo.get("deadheadcost");
                if(parseDouble(pricestr.replace("元",""))>0){
                    JSONObject obj6 = new JSONObject();
                    obj6.put("name", "空驶费("+costinfo.get("realdeadheadmileage")+")");
                    obj6.put("price", pricestr);
                    paydetail.add(obj6);
                }
            }
            obj.put("paydetail",paydetail);
            ress.put("status", Retcode.OK.code);
            ress.put("message", Retcode.OK.msg);
        }else{
            return costinfo;
        }
        return ress;
    }

    /**
     * 是否可以折扣
     * @return
     */
    private boolean canZK4User(){
        Map<String,Object> onoff = dicdao.getZKOnOff();
        if(onoff!=null&&"1".equals(onoff.get("value"))){
            return true;
        }
        return false;
    }

    /**
     * 是否可以返回积分
     * @return
     */
    private boolean canAward4User(){
        Map<String,Object> onoff = dicdao.getAwardOnOff();
        if(onoff!=null&&"1".equals(onoff.get("value"))){
            Map<String,Object> stopinfo = dicdao.getAwardStopInfo();
            if(stopinfo!=null&&"0".equals(stopinfo.get("value"))){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取返回积分比例
     * @param isorg
     * @return
     */
    private double getAwardPercent(boolean isorg){
        Map<String,Object> params = new HashMap<String,Object>();
        if(isorg){
            params.put("type","机构用户订单支付返还比例");
        }else{
            params.put("type","个人用户订单支付返还比例");
        }
        Map<String,Object> awardpointinfo = dicdao.getAwardPoint(params);
        if(awardpointinfo!=null){
            return parseDouble(awardpointinfo.get("value"));
        }
        return 0;
    }

    /**
     * 获取折扣比例
     * @param isorg
     * @return
     */
    private double getZKPercent(boolean isorg){
        Map<String,Object> params = new HashMap<String,Object>();
        if(isorg){
            params.put("type","机构用户订单支付折扣比例");
        }else{
            params.put("type","个人用户订单支付折扣比例");
        }
        Map<String,Object> zkpointinfo = dicdao.getZKInfo(params);
        if(zkpointinfo!=null){
            return parseDouble(zkpointinfo.get("value"));
        }
        return 0;
    }

    /**
     * 获取出租车预估费用
     * @param params
     * @return
     */
    public Map<String,Object> getEstimatedCost4Taxi(Map<String, Object> params) {
    	Map<String,Object> res = new HashMap<String,Object>();
		try{
			JSONObject obj = new JSONObject();
			String cityvalue = dicdao.getCityNo((String) params.get("city"));
	        params.put("city", cityvalue);
			res = getEstimatedCost4Op(params,true);
			if((int)res.get("status")!=Retcode.OK.code){
				//预估失败
				res.put("costinfo",new HashMap<>());
				return res;
			}
			Map<String,Object> costinfo = (Map<String, Object>)res.get("costinfo");
			double cost = parseDouble(costinfo.get("cost"));
			obj.put("cost", cost);
//            if(canZK4User()){
//                //可以折扣
//                double awardpercent = getZKPercent(false);
//                if(awardpercent>0){
//                    obj.put("cost", StringUtil.formatNum(cost*awardpercent,1));
//                }
//            }
			//出租车不返回积分
//            if(canAward4User()&&false){
//                double awardpercent = getAwardPercent(false);
//                obj.put("awardpint", Math.round(cost*awardpercent));
//            }else{
//                obj.put("awardpint", 0);
//            }
            obj.put("awardpint", 0);
			JSONArray paydetail = new JSONArray();
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "行程费");
			obj1.put("price", costinfo.get("tourfee")+"元");
			JSONObject obj2 = new JSONObject();
			obj2.put("name", "调度费");
			obj2.put("price", costinfo.get("schedulefee")+"元");
			paydetail.add(obj1);
			paydetail.add(obj2);
			obj.put("paydetail",paydetail);
			res.put("costinfo", obj);
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("costinfo",new HashMap<>());
		}
		return res;
    }

    /**
     * 获取消息列表
     * @param params
     * @return
     */
    public Map<String,Object> getMessages(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        res.put("currentcount",0);
        try{
            String usertoken = (String) params.get("usertoken");
            String iDisplayStart = (String) params.get("iDisplayStart");
            String iDisplayLength = (String) params.get("iDisplayLength");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("messages",new ArrayList<>());
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            params.put("account", account);
            if(!params.containsKey("aboveread")){
                params.put("aboveread", true);
            }
            Object aboveread = params.get("aboveread");
            if(aboveread instanceof String&&"false".equalsIgnoreCase((String)aboveread)){
                params.put("aboveread", false);
            }else if(aboveread instanceof String&&"true".equalsIgnoreCase((String)aboveread)){
                params.put("aboveread", true);
            }

            List<Map<String,Object>> messages = userdao.getMessages(params);

            if(messages==null){
                messages = new ArrayList<>();
            }
            res.put("messages",dillWithMessages(messages));
            res.put("currentcount", messages.size());
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("messages",new ArrayList<>());
        }
        return res;
    }

    private List<Map<String, Object>> dillWithMessages(List<Map<String, Object>> messages) {
        if(messages==null||messages.size()<=0){
            return messages;
        }
        List<Map<String, Object>> newmessages = new ArrayList<Map<String, Object>>();
        for(int i=0;i<messages.size();i++){
            Map<String, Object> message = messages.get(i);
            try{
                message.put("content", JSONObject.fromObject(message.get("content")));
                newmessages.add(message);
            }catch(Exception e){
                logger.error("乘客端异常",e);
            }
        }
        return newmessages;
    }

    /**
	 * 获取订单列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOders(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String iDisplayStart = (String) params.get("iDisplayStart");
		String iDisplayLength = (String) params.get("iDisplayLength");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			res.put("currentcount",0);
			res.put("orders",new ArrayList<>());
			return res;
		}
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		//List<Order4List> orders = null;
		//“0”表示机构订单，“1”表示个人订单
		if(UserTokenManager.ORGUSERTYPE.equals((String) params.get("type"))){
			//机构用户
			List<Order4List> orders = orderdao.getOrders4Org(params);
			dillOrdersInfo(orders);
			if(orders==null){
				orders = new ArrayList<>();
			}
			res.put("currentcount",orders.size());
			res.put("orders", orders);
		}else{
			//个人用户
			List<Map<String,Object>> orders = orderdao.getOrders4OpSec(params);
			dillOrders(orders,usertoken);
			if(orders==null){
				orders = new ArrayList<>();
			}
			res.put("currentcount",orders.size());
			res.put("orders",orders);
		}

		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);

		return res;
	}

	private void dillOrdersInfo(List<Order4List> orders){
		if(orders==null||orders.size()<=0){
			return ;
		}
		for(int i=0;i<orders.size();i++){
			dillWithOrderInfo(orders.get(i));
		}
	}

	/**
	 * 添加订单列表中订单状态的显示值
	 * @param order
	 */
	private void dillWithOrderInfo(Order4List order){
		if(order==null){
			return ;
		}
		if(OrderEnum.ORDERTYPE_TAXI.code.equals(order.getOrdertype())){
			order.setOrderstyle("1");
		}else{
			order.setOrderstyle("0");
		}
		switch (order.getOrderstatus()) {
		case "0":
			order.setOrderstatuscaption("待接单");
			break;
		case "1":
			order.setOrderstatuscaption("待接单");
			break;
		case "2":
			order.setOrderstatuscaption("待出发");
			break;
		case "3":
			order.setOrderstatuscaption("行程中");
			break;
		case "4":
			order.setOrderstatuscaption("行程中");
			break;
		case "5":
			order.setOrderstatuscaption("行程中");
			break;
		case "6":
			order.setOrderstatuscaption("行程中");
			break;
		case "8":
			order.setOrderstatuscaption("已取消");
			String canceltime = order.getCanceltime();
			if(StringUtils.isNotBlank(canceltime)&&canceltime.length()>10){
				order.setCanceltime(canceltime.substring(0,canceltime.length()-5));
			}
			break;
		case "7":
			switch(order.getPaymentstatus()) {
				case "0":
					order.setOrderstatuscaption("待付款");
					break;
				default:
					order.setOrderstatuscaption("已完成");
					break;
			}
			break;
		default:
			break;
		}

		try{
			String oncity = order.getOncity();
			String oncitycaption = dicdao.getCityCaption(oncity);
			order.setOncity(oncitycaption);

			String offcity = order.getOffcity();
			String offcitycaption = dicdao.getCityCaption(offcity);
			order.setOffcity(offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}

		//用户是否评论
		String userrate = order.getUserrate();
		if(StringUtils.isNotBlank(userrate)){
			order.setHasComment(true);
		}

		String driverimg = order.getDriverimg();
		if(StringUtils.isNotBlank(driverimg)){
			order.setDriverimg(SystemConfig.getSystemProperty("fileserver")+File.separator+driverimg);
		}

		String usetype = order.getUsetype();
		String ordertype = order.getOrdertype();
		String ordertypecaption = "";
		if("0".equalsIgnoreCase(usetype)){
			ordertypecaption += "因公用车·";
		}else if("1".equalsIgnoreCase(usetype)){
			ordertypecaption += "因私用车·";
		}
		if("1".equalsIgnoreCase(ordertype)){
			ordertypecaption += "约车";
		}else if("2".equalsIgnoreCase(ordertype)){
			ordertypecaption += "接机";
		}else{
			ordertypecaption += "送机";
		}
		order.setOrdertypecaption(ordertypecaption);
		double mileage = order.getMileage();
		String mileagestr = StringUtil.formatNum(mileage/1000,1)+"公里";
		order.setMileagestr(mileagestr);
		String orderstatus = order.getOrderstatus();
//		if(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus)){
			Date autocanceltime = order.getAutocanceltime();
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				order.setLefttime(lefttime<0?0:lefttime);
			}else{
				order.setLefttime(0);
			}
//		}
//		try{
//			//界面显示的费用时长里程等显示一位小数
//			dillNumShow(order);
//		}catch(Exception e){
//			logger.error("乘客端异常",e);
//		}
	}

	/**
	 * 处理查询出来的订单信息
	 * @param orders
	 */
	private void dillOrders(List<Map<String, Object>> orders,String usertoken) {
		if(orders==null){
			return ;
		}
		for(int i=0;i<orders.size();i++){
			Map<String,Object> orderinfo = orders.get(i);
			dillOrder(orderinfo,usertoken,false);
		}
	}

    /**
     * 获取上车城市列表
     * @param params
     * @return
     */
    public Map<String,Object> getGetOnCitys(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status",Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String ordertype = (String) params.get("ordertype");
            if(StringUtils.isBlank(ordertype)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                return res;
            }
            //获取服务的城市列表
            List<String> servicecitys = new ArrayList<String>();
            String usertoken = (String) params.get("usertoken");
            List<String> netcarservicecitys = getServiceCitys4NetCar(usertoken,params);
            if("1".equals(ordertype)){
                //约车时，是有出租车的
                List<String> taxiservicecitys = getServiceCitys4Taxi(usertoken,params);
                dillAllServiceCitys(servicecitys,taxiservicecitys,netcarservicecitys);
            }else{
                dillAllServiceCitys(servicecitys,null,netcarservicecitys);
            }

            //处理
            List<Map<String,List<Map<String,Object>>>> citys = new ArrayList<Map<String,List<Map<String,Object>>>>();
            List<Map<String,Object>> citysinfo = dicdao.getAllCitys(params);
            dillCityinfos(citys,citysinfo,servicecitys);
            res.put("citys", citys);
            //处理hotcitys
            List<Map<String,List<Map<String,Object>>>> hotcitys = new ArrayList<Map<String,List<Map<String,Object>>>>();
            List<Map<String,Object>> hotcitysinfo = dicdao.getHotCitys(params);
            dillCityinfos(hotcitys,hotcitysinfo,servicecitys);
            res.put("hotcitys", hotcitys);
        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("citys", new ArrayList<>());
        }
        return res;
    }

    /**
     * 获取出租车提供服务的城市名称
     * @param usertoken
     * @param params
     * @return
     */
    private List<String> getServiceCitys4Taxi(String usertoken, Map<String, Object> params) {
        List<String> servicecitys = new ArrayList<String>();
        List<String> opservicecitys = null;
        List<String> orgsericecitys = null;
        List<String> opsendrulecitys = opdao.getSendRuleCitys4Taxi();
        if(opsendrulecitys!=null&&opsendrulecitys.size()>0){
            List<String> opaccountrulecitys = opdao.getAccountRuleCitys4Taxi(params);
            opservicecitys = getUnionCitys(opsendrulecitys,opaccountrulecitys);
        }
        if(StringUtils.isNotBlank(usertoken)&&isOrgUser(usertoken)){
            //传递了usertoken并且usertoken是机构用户的，那么就同时获取机构车企提供服务的城市
            //机构暂无出租车
        }
        dillAllServiceCitys(servicecitys,opservicecitys,orgsericecitys);
        return servicecitys;
    }

    /**
     * 获取网约车提供服务的城市名称
     * @param usertoken
     * @return
     */
    private List<String> getServiceCitys4NetCar(String usertoken,Map<String,Object> params){
        List<String> servicecitys = new ArrayList<String>();
        //op和org的服务城市集合
        List<String> opservicecitys = null;
        List<String> orgsericecitys = null;
        //获取运管端的服务城市列表
        //获取有派单规则的城市集合
        List<String> opsendrulecitys = opdao.getSendRuleCitys();
        if(opsendrulecitys!=null&&opsendrulecitys.size()>0){
            List<String> opaccountrulecitys = opdao.getAccountRuleCitys(params);
            opservicecitys = getUnionCitys(opsendrulecitys,opaccountrulecitys);
        }
        if(StringUtils.isNotBlank(usertoken)&&isOrgUser(usertoken)){
            //传递了usertoken并且usertoken是机构用户的，那么就同时获取机构车企提供服务的城市
            params.put("account",Const.getUserInfo(usertoken).get("account"));
            //个性化计费规则
            params.put("ruletype", "1");
            List<Map<String,Object>> ruyuecompanys = orgdao.getOldestServiceCompanys();
            if(ruyuecompanys!=null&&ruyuecompanys.size()>0){
                params.put("companyid",ruyuecompanys.get(0).get("id"));
                List<String> orgsendrulecitys = orgdao.getSendRuleCitys(params);
                if(orgsendrulecitys!=null&&orgsendrulecitys.size()>0){
                    List<String> orgaccountrulecitys = orgdao.getAccountRuleCitys(params);
                    orgsericecitys = getUnionCitys(orgsendrulecitys,orgaccountrulecitys);
                }
            }
        }
        dillAllServiceCitys(servicecitys,opservicecitys,orgsericecitys);
        return servicecitys;
    }

    /**
     * 求两个集合的交集
     * @param opsendrulecitys
     * @param opaccountrulecitys
     * @return
     */
    private List<String> getUnionCitys(List<String> sendrulecitys, List<String> accountrulecitys) {
        List<String> union = new ArrayList<String>();
        if(sendrulecitys!=null&&accountrulecitys!=null){
            for(int i=0;i<accountrulecitys.size();i++){
                String city = accountrulecitys.get(i);
                if(sendrulecitys.contains(city)){
                    //两个集合都存在
                    if(!union.contains(city)){
                        union.add(city);
                    }
                }
            }
        }
        return union;
    }

    /**
     * 讲两个集合中求并集存入servicecitys中
     * @param servicecitys
     * @param opservicecitys
     * @param orgsericecitys
     */
    private void dillAllServiceCitys(List<String> servicecitys, List<String> opservicecitys, List<String> orgsericecitys) {
        if(opservicecitys!=null){
            servicecitys.addAll(opservicecitys);
        }
        if(orgsericecitys!=null){
            for(int i=0;i<orgsericecitys.size();i++){
                String city = orgsericecitys.get(i);
                if(!servicecitys.contains(city)){
                    servicecitys.add(city);
                }
            }
        }
    }

    /**
     * 处理城市信息
     * @param citys
     * @param citysinfo
     * @param servicecitys
     */
    private void dillCityinfos(List<Map<String, List<Map<String,Object>>>> citys, List<Map<String, Object>> citysinfo, List<String> servicecitys){
        if(citysinfo==null){
            return ;
        }
        Map<String,List<String>> temp = new LinkedHashMap<String,List<String>>();
        Map<String,List<Map<String,Object>>> tempinfo = new LinkedHashMap<String,List<Map<String,Object>>>();
        for(int i=0;i<citysinfo.size();i++){
            Map<String,Object> cityinfo = citysinfo.get(i);
            String citypy = (String) cityinfo.get("cityinitials");
            String cityvalue = (String) cityinfo.get("city");
            List<String> cityvalues = temp.get(citypy);
            if(cityvalues==null){
                cityvalues = new ArrayList<String>();
                tempinfo.put(citypy,new ArrayList<Map<String,Object>>());
                temp.put(citypy, cityvalues);
            }
            if(!cityvalues.contains(cityvalue)){
                cityvalues.add(cityvalue);
                Map<String,Object> cityobj = new HashMap<String,Object>();
                cityobj.put("city",cityvalue);
                if(servicecitys.contains(cityvalue)){
                    cityobj.put("able",true);
                }else{
                    cityobj.put("able",false);
                }
                tempinfo.get(citypy).add(cityobj);
            }
        }
        Iterator<String> it = tempinfo.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            List<Map<String,Object>> value = tempinfo.get(key);
            Map<String,List<Map<String,Object>>> lv = new HashMap<String,List<Map<String,Object>>>();
            lv.put(key, value);
            citys.add(lv);
        }
    }

    /**
     * 获取下车地址城市
     * @param params
     * @return
     */
    public Map<String,Object> getGetOffCitys(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status",Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            //获取服务的城市列表
            List<String> servicecitys = dicdao.getAllCityNames(params);
            //处理
            List<Map<String,List<Map<String,Object>>>> citys = new ArrayList<Map<String,List<Map<String,Object>>>>();
            List<Map<String,Object>> citysinfo = dicdao.getAllCitys(params);
            dillCityinfos(citys,citysinfo,servicecitys);
            res.put("citys", citys);
            //处理hotcitys
            List<Map<String,List<Map<String,Object>>>> hotcitys = new ArrayList<Map<String,List<Map<String,Object>>>>();
            List<Map<String,Object>> hotcitysinfo = dicdao.getHotCitys(params);
            dillCityinfos(hotcitys,hotcitysinfo,servicecitys);
            res.put("hotcitys", hotcitys);
        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("citys", new ArrayList<>());
        }
        return res;
    }

    /**
     * 获取可用的城市列表
     * @param params
     * @return
     */
    public Map<String,Object> getValidCity(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status",Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            //获取服务的城市列表
            String type = (String) params.get("type");
            String ordertype = (String) params.get("ordertype");
            String usertoken = (String) params.get("usertoken");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(type)||StringUtils.isBlank(ordertype)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("citys",new ArrayList<>());
                return res;
            }
            if(Const.ORDERTYPE_ORG.equals(type)){
                //机构订单
                params.put("account",Const.getUserInfo(usertoken).get("account"));
                //个性化计费规则
                params.put("ruletype", "1");
                List<String> accountrulecitys = orgdao.getAccountRuleCitys(params);
                if(accountrulecitys==null){
                    accountrulecitys = new ArrayList<>();
                }
                res.put("citys",accountrulecitys);
            }else{
                //个人订单
                if("4".equals(ordertype)){
                    //出租车
                    List<String> accountrulecitys = opdao.getAccountRuleCitys4Taxi(params);
                    if(accountrulecitys==null){
                        accountrulecitys = new ArrayList<>();
                    }
                    res.put("citys",accountrulecitys);
                }else{
                    List<String> accountrulecitys = opdao.getAccountRuleCitys(params);
                    if(accountrulecitys==null){
                        accountrulecitys = new ArrayList<>();
                    }
                    res.put("citys",accountrulecitys);
                }
            }
        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("citys", new ArrayList<>());
        }
        return res;
    }

    /**
     * 初步预估费用
     * @param params
     * @return
     */
    public Map<String,Object> getEstimatedCostFirst(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status",Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            //获取服务的城市列表
            String city = (String) params.get("city");
            String ordertype = (String) params.get("ordertype");
            String onaddrlng = (String) params.get("onaddrlng");
            String onaddrlat = (String) params.get("onaddrlat");
            String offaddrlng = (String) params.get("offaddrlng");
            String offaddrlat = (String) params.get("offaddrlat");
            if(StringUtils.isBlank(city)||StringUtils.isBlank(ordertype)
                    ||StringUtils.isBlank(onaddrlng)||StringUtils.isBlank(onaddrlat)
                    ||StringUtils.isBlank(offaddrlng)||StringUtils.isBlank(offaddrlat)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                return res;
            }
            Map<String,Object> taxiparams = new HashMap<String,Object>();
            taxiparams.putAll(params);
            Map<String,Object> netcarcostinfo = new HashMap<String,Object>();
            params.put("type",Const.ORDERTYPE_PERSONAL);
            //查找最低的车型
            String lowestcartype = opdao.getLowestCarType(params);
            if(StringUtils.isNotBlank(lowestcartype)){
                params.put("cartype",lowestcartype);
                try{
                    Map<String,Object> costinfo = getEstimatedCost4NetCar(params);
                    if(costinfo!=null&&(int)costinfo.get("status")==Retcode.OK.code){
                        JSONObject costinfoobj = (JSONObject) costinfo.get("costinfo");
                        netcarcostinfo.put("cost",costinfoobj.get("cost"));
                        netcarcostinfo.put("able",true);
                        netcarcostinfo.put("costinfo",costinfoobj);
                        netcarcostinfo.put("message","成功返回");
                    }else{
                        netcarcostinfo.put("cost",0);
                        netcarcostinfo.put("able",false);
                        netcarcostinfo.put("message","费用暂无法预估，请重试");
                        netcarcostinfo.put("info","预估费用失败");
                    }
                }catch (Exception e){
                    netcarcostinfo.put("cost",0);
                    netcarcostinfo.put("able",false);
                    netcarcostinfo.put("message","费用暂无法预估，请重试");
                    netcarcostinfo.put("info","预估费用失败");
                }
            }else{
                netcarcostinfo.put("cost",0);
                netcarcostinfo.put("able",false);
                netcarcostinfo.put("message","费用暂无法预估，请重试");
                netcarcostinfo.put("info","没有可用车型");
            }

            Map<String,Object> taxicostinfo = new HashMap<String,Object>();
            if("1".equals(ordertype)||"4".equals(ordertype)){
                //约车才有出租车
                try{
                    Map<String,Object> costinfo = getEstimatedCost4Taxi(taxiparams);
                    if(costinfo!=null&&(int)costinfo.get("status")==Retcode.OK.code){
                        Map<String,Object> costinfoobj =  (Map<String,Object>)costinfo.get("costinfo");
                        taxicostinfo.put("cost",costinfoobj.get("cost"));
                        taxicostinfo.put("costinfo",costinfoobj);
                        taxicostinfo.put("able",true);
                        taxicostinfo.put("message","成功返回");
                    }else{
                        taxicostinfo.put("cost",0);
                        taxicostinfo.put("able",false);
                        taxicostinfo.put("message","费用暂无法预估，请重试");
                        taxicostinfo.put("info","预估费用失败");
                    }
                }catch (Exception e){
                    taxicostinfo.put("cost",0);
                    taxicostinfo.put("able",false);
                    taxicostinfo.put("message","费用暂无法预估，请重试");
                    taxicostinfo.put("info","预估费用失败");
                }
            }else{
                taxicostinfo.put("cost",0);
                taxicostinfo.put("able",false);
                taxicostinfo.put("message","接送机没有出租车");
                taxicostinfo.put("info","接送机没有出租车");
            }
            res.put("taxicostinfo",taxicostinfo);
            res.put("netcarcostinfo",netcarcostinfo);
        }catch (Exception e){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取网约车服务车型
     * @param params
     * @return
     */
    public Map<String,Object> getCarMoudels4NetCar(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String type = (String) params.get("type");
            String city = (String) params.get("city");
            String ordertype = (String) params.get("ordertype");
            String companyid = (String) params.get("companyid");
            if(StringUtils.isBlank(type)||StringUtils.isBlank(city)||StringUtils.isBlank(ordertype)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            if(Const.ORDERTYPE_ORG.equals(type)){
                //机构订单
                res = getCarMoudels4OrgNetCar(params);
            }else{
                //个人订单
                res = getCarMoudels4OpNetCar(params);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取网约车服务车型
     * @param params
     * @return
     */
    public Map<String,Object> getCarMoudels4OrgNetCar(Map<String, Object> params) {
    	Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		addPubInfos(res);
    	String usertoken = (String) params.get("usertoken");
    	String city = (String) params.get("city");
        String ordertype = (String) params.get("ordertype");
        String companyid = (String) params.get("companyid");
        List<VehicleModels> carmodels = null;
    	//因公用车
		String account = Const.getUserInfo(usertoken).get("account");
		OrgOrgan organ = orgdao.getOrgInfo(account);
		if(organ!=null&&organ.getStatus()!=0){
			Map<String,Object> sparam = new HashMap<String,Object>();
			sparam.put("organid", organ.getId());
			sparam.put("companyid", companyid);
			sparam.put("city", city);
			sparam.put("type", "1");
			sparam.put("ordertype", ordertype);
			List<VehicleModels> tempcarmodels = orgdao.getCarMoudels(sparam);
			if(tempcarmodels==null||tempcarmodels.size()<=0){
				//没有服务
				res.put("message","当前租赁公司当前城市没有用车服务");
			}else{
				//在根据个人用车规则过滤
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("companyid", companyid);
				param.put("ordertype", ordertype);
				param.put("organid", organ.getId());
				param.put("account", Const.getUserInfo(usertoken).get("account"));
				List<String> vehiclemodels = orgdao.getOrgUsercar4Rules(param);
				if(tempcarmodels!=null){
					carmodels = new ArrayList<VehicleModels>();
					for(int i=0;i<tempcarmodels.size();i++){
						VehicleModels ve = tempcarmodels.get(i);
						if(vehiclemodels.contains(ve.getId())){
							carmodels.add(ve);
						}
					}
					if(carmodels==null||carmodels.size()<=0){
						//没有权限
						res.put("message","您在当前城市、当前租赁公司没有用车权限");
					}
				}
			}
		}
		dillWithCarMoudelsLogo(carmodels);
		res.put("carmodels", carmodels);
		return res;
    }

    /**
	 * 为车型logo加上绝对地址
	 * @param carmodels
	 */
	private void dillWithCarMoudelsLogo(List<VehicleModels> carmodels){
		if(carmodels==null){
			return ;
		}
		for(int i=0;i<carmodels.size();i++){
			VehicleModels ve = carmodels.get(i);
			String logo = ve.getLogo();
			if(StringUtils.isNotBlank(logo)){
				ve.setLogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
			}
		}
	}

	/**
	 * 个人用户获取网约车车型
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCarMoudels4OpNetCar(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			List<Map<String,Object>> carmodels = opdao.getCarMoudels4OpNetCar(params);
			if(carmodels==null){
				carmodels = new ArrayList<>();
			}else{
				dillWithCarMoudelsLogo2(carmodels);
			}
			if(carmodels==null||carmodels.size()<=0){
				//没有服务
				res.put("message","当前城市没有用车服务");
			}
			res.put("carmodels",carmodels);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("carmodels",new ArrayList<>());
		}
		return res;
	}

	/**
	 * 为车型logo加上绝对地址
	 * @param carmodels
	 */
	private void dillWithCarMoudelsLogo2(List<Map<String,Object>> carmodels){
		if(carmodels==null){
			return ;
		}
		for(int i=0;i<carmodels.size();i++){
			Map<String,Object> ve = carmodels.get(i);
			String logo = (String) ve.get("logo");
			if(StringUtils.isNotBlank(logo)){
				ve.put("logo", SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
			}
		}
	}

    /**
     * 获取充值的方式有哪些
     * @param param
     * @return
     */
    public Map<String,Object> getRechargeTypes(Map<String, Object> param) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        List<Map<String,Object>> rechargeinfo = new ArrayList<Map<String,Object>>();
        res.put("rechargeinfo",rechargeinfo);
        try{
            String usertoken = (String) param.get("usertoken");
            if(StringUtils.isBlank(usertoken)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                return res;
            }
            //充值方式全部都是个人用户的
            Map<String,Object> opinfo = orderdao.getOpInfo();
            if(opinfo!=null){
                String wechatstatus = (String) opinfo.get("wechatstatus");
                if("1".equals(wechatstatus)
                        &&StringUtils.isNotBlank((String)opinfo.get("wechatappid"))
                        &&StringUtils.isNotBlank((String)opinfo.get("wechatmerchantno"))
                        &&StringUtils.isNotBlank((String)opinfo.get("wechatsecretkey"))){
                    //微信可用
                    Map<String,Object> info = new HashMap<String,Object>();
                    info.put("type", "2");
                    String wechataccount = (String)opinfo.get("wechataccount");
                    info.put("account", StringUtils.isNotBlank(wechataccount)?wechataccount:"");
                    rechargeinfo.add(info);
                }
                String alipaystatus = (String) opinfo.get("alipaystatus");
                if("1".equals(alipaystatus)
                        &&StringUtils.isNotBlank((String)opinfo.get("alipayappid"))
                        &&StringUtils.isNotBlank((String)opinfo.get("alipayprivatekey"))
                        &&StringUtils.isNotBlank((String)opinfo.get("alipaypublickey"))){
                    //支付宝可用
                    Map<String,Object> info = new HashMap<String,Object>();
                    info.put("type", "1");
                    String alipayaccount = (String)opinfo.get("alipayaccount");
                    info.put("account", StringUtils.isNotBlank(alipayaccount)?alipayaccount:"");
                    rechargeinfo.add(info);
                }
            }
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取钱包余额
     * @param param
     * @return
     */
    public Map<String,Object> getBalance4Third(Map<String, Object> param) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) param.get("usertoken");
            if(StringUtils.isBlank(usertoken)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("totalmoney",0);
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            //个人用户
            param.put("account", account);
            double totalmoney = userdao.getBalanceMoney4Op(param);
            res.put("totalmoney",totalmoney);
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("totalmoney",0);
        }
        return res;
    }

    /**
     *
     * @param param
     * @return
     */
    public Map<String,Object> getBalanceDetail(Map<String, Object> param) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) param.get("usertoken");
            String iDisplayStart = (String) param.get("iDisplayStart");
            String iDisplayLength = (String) param.get("iDisplayLength");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("details",new ArrayList<>());
                res.put("currentcount",0);
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            param.put("account", account);
            //v3.0.1的钱包明显都是个人用户
//            List<Map<String,Object>> details = userdao.getBalanceDetail4OpSec(param);
            List<Map<String,Object>> details = userdao.getBalanceDetail4OpThird(param);
            if(details==null){
                details = new ArrayList<>();
            }
            res.put("details",details);
            res.put("currentcount",details.size());
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("details",new ArrayList<>());
            res.put("currentcount",0);
        }
        return res;
    }

    /**
     * 获取交易明细
     * @param param
     * @return
     */
    public Map<String,Object> getDealDetail(Map<String, Object> param) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) param.get("usertoken");
            String iDisplayStart = (String) param.get("iDisplayStart");
            String iDisplayLength = (String) param.get("iDisplayLength");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("details",new ArrayList<>());
                res.put("currentcount",0);
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            param.put("account", account);
            //v3.0.1交易明细是个人用户和机构用户的集合
//            List<Map<String,Object>> details = userdao.getDealDetail4Op(param);
            List<Map<String,Object>> details = userdao.getDealDetail4OpThird(param);
            if(details==null){
                details = new ArrayList<>();
            }
            res.put("details",details);
            res.put("currentcount",details.size());
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("details",new ArrayList<>());
            res.put("currentcount",0);
        }
        return res;
    }

	/**
	 * 更新订单状态
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateOderState(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String param = (String) params.get("param");
			String orderno = (String)params.get("orderno");
			String ordertype = (String)params.get("ordertype");
			String usetype = (String)params.get("usetype");
			if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(param)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			Map<String,Object> updateparam = new HashMap<String,Object>();
			updateparam.put("orderno", orderno);
			if("cancel".equals(param)){
				updateparam.put("orderstate", OrderState.CANCEL.state);
				updateparam.put("cancelparty", CancelParty.PASSENGER.code);
			}else{
				updateparam.put("userhidden", Const.USER_ORDERSTATUS_USERHIDDEN);
			}
			if(!"2".equals(usetype)){
				//机构用户
				if("cancel".equals(param)){
					PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
					String orderstatus = order.getOrderstatus();
					if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
						res.put("status", Retcode.OK.code);
						res.put("message", "订单已经不能取消！");
						return res;
					}
					OrderApiParam orderparam = new OrderApiParam();
					orderparam.setOrderid(order.getOrderno());
					orderparam.setOrderstate(OrderState.CANCEL.state);
					orderparam.setReqsrc(CancelParty.PASSENGER.code);
					orderparam.setOrdertype(ordertype);
					orderparam.setUsetype(usetype);
					JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
					res.put("status", jsonres.get("status"));
					res.put("message", jsonres.get("message"));
				}else{
					try{
						orderdao.updateOrderState4Org(updateparam);
					}catch(Exception e){
						logger.error("乘客端更改订单状态",e);
						res.put("status", Retcode.FAILED.code);
						res.put("message", "订单状态更改失败！");
					}
				}
			}else if("2".equals(usetype)){
				//个人用户
				boolean istaxiorder = isTaxiOrder(ordertype);
				if("cancel".equals(param)){
					//订单取消的时候在changeOrderState时已经判断了，此处分为出租车订单和网约车订单所以不需要判断
//					PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
//					String orderstatus = order.getOrderstatus();
//					if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
//						res.put("status", Retcode.OK.code);
//						res.put("message", "订单已经不能取消！");
//						return res;
//					}
					OrderApiParam orderparam = new OrderApiParam();
					orderparam.setOrderid(orderno);
					orderparam.setOrderstate(OrderState.CANCEL.state);
					orderparam.setReqsrc(CancelParty.PASSENGER.code);
					orderparam.setOrdertype(ordertype);
					orderparam.setUsetype(usetype);
					JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
					res.put("status", jsonres.get("status"));
					res.put("message", jsonres.get("message"));
				}else{
					try{
						if(istaxiorder){
							//个人用户出租车状态变更
							orderdao.updateOrderState4OpTaxi(updateparam);
						}else{
							//网约车之前的不变
							orderdao.updateOrderState4Op(updateparam);
						}
					}catch(Exception e){
						logger.error("乘客端更改订单状态",e);
						res.put("status", Retcode.EXCEPTION.code);
						res.put("message", Retcode.EXCEPTION.msg);
					}
				}
			}
		}catch(Exception e){
			logger.error("乘客端更改订单状态",e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}





    /**
     * 网约车下单
     * @param order
     * @param request
     * @return
     */
    public Map<String, Object> addOder4NetCar(PassengerOrder order, HttpServletRequest request) {
        Map<String,Object> res = null;
        String oncity = order.getOncity();
        order.setOncity(dicdao.getCityNo(order.getOncity()));
        order.setOffcity(dicdao.getCityNo(order.getOffcity()));
        String usertoken = request.getParameter("usertoken");
        if(!StringUtils.isNotBlank(usertoken)){
            usertoken = request.getHeader("usertoken");
        }
        if(StringUtils.isNotBlank(order.getOrdersource())){
            order.setOrdersource(order.getOrdersource());
        }

        //判断订单类型
        if(UserTokenManager.ORGUSERTYPE.equals(order.getType())) { //机构订单
            //存储的是用车事由在字典表中的id
            Map<String,String> infoparams = new HashMap<String,String>();
            infoparams.put("type", "用车事由");
            infoparams.put("value", order.getVehiclessubjecttype());
            Dictionary vehiclessubjecttypeinfo = dicdao.getDictionaryByTypeValue(infoparams);
            if(vehiclessubjecttypeinfo!=null){
                order.setVehiclessubjecttype(vehiclessubjecttypeinfo.getId());
            }

            order.setUsetype(Const.USETYPE_PUBLIC);
            order.setPaymethod(PayMethod.ORGAN.code);
            //机构订单
            String account = Const.getUserInfo(usertoken).get("account");
            OrgUser user = userdao.getUser4Org(account);
            order.setUserid(user.getId());
            order.setOrganid(user.getOrganId());
            res = carserviceapi.dealRequestWithToken("/OrderApi/CreateOrgOrder", HttpMethod.POST, null, order, Map.class);
        } else { //个人订单
            order.setBelongleasecompany(order.getCompanyid());
            order.setCompanyid(null);
            String account = Const.getUserInfo(usertoken).get("account");
            PeUser user = userdao.getUser4Op(account);
            if("1".equalsIgnoreCase(user.getDisablestate())){
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("action", "0");
                params.put("userid", user.getId());
                Map<String,Object> disableinfo = userdao.getUserLastDisableInfo4Op(params);
                Date starttime = (Date) disableinfo.get("starttime");
                Date current = new Date();
                if(current.after(starttime)){
                    Date endtime = (Date) disableinfo.get("endtime");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if(res==null){
                        res = new HashMap<String,Object>();
                    }
                    res.put("status", Retcode.USERNOTSUPPORT.code);
                    res.put("message", "您已被禁止下单，禁止时间为"+format.format(starttime)+"至"+format.format(endtime));
                    return res;
                }
            }
            if(StringUtils.isBlank(order.getSelectedmodel())) {
            	Map<String, Object> params = new HashMap<String, Object>();
            	params.put("city", oncity);
            	params.put("ordertype", order.getOrdertype());
            	order.setSelectedmodel(opdao.getLowestCarType(params));
            }
            order.setUserid(user.getId());
            res = carserviceapi.dealRequestWithToken("/OrderApi/CreateOpOrder", HttpMethod.POST, null, order, Map.class);
            if(order.isSavepassenger()){
                PubMostcontact object = new PubMostcontact();
                object.setName(order.getPassengers());
                object.setPhone(order.getPassengerphone());
                object.setUserphone(user.getAccount());
                insertPubMostcontact(object);
            }
        }
        if(null == res){
            res = new HashMap<String,Object>();
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 出租车下单
     * @param order
     * @param request
     * @return
     */
    public Map<String, Object> addOder4Taxi(OpTaxiOrder taxiorder, HttpServletRequest request) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        taxiorder.setBelongleasecompany(taxiorder.getCompanyid());
        taxiorder.setCompanyid(null);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        if(StringUtils.isNotBlank(taxiorder.getOrdersource())){
            taxiorder.setOrdersource(taxiorder.getOrdersource());
        }
        try{
            String usertoken = request.getParameter("usertoken");
            if(!StringUtils.isNotBlank(usertoken)){
                usertoken = request.getHeader("usertoken");
            }
            if(StringUtils.isBlank(usertoken)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("orderno","");
                res.put("sendinterval",0);
                res.put("isusenow",false);
                return res;
            }
            if(UserTokenManager.ORGUSERTYPE.equals(taxiorder.getType())){
                //机构用户
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","这个接口是给个人端用的");
                res.put("orderno","");
                res.put("sendinterval",0);
                res.put("isusenow",false);
            }else{
                //个人用户
                taxiorder.setOncity(dicdao.getCityNo(taxiorder.getOncity()));
                taxiorder.setOffcity(dicdao.getCityNo(taxiorder.getOffcity()));
                String account = Const.getUserInfo(usertoken).get("account");
                PeUser user = userdao.getUser4Op(account);
                if("1".equalsIgnoreCase(user.getDisablestate())){
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("action", "0");
                    params.put("userid", user.getId());
                    Map<String,Object> disableinfo = userdao.getUserLastDisableInfo4Op(params);
                    Date starttime = (Date) disableinfo.get("starttime");
                    Date current = new Date();
                    if(current.after(starttime)){
                        Date endtime = (Date) disableinfo.get("endtime");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        res.put("status", Retcode.USERNOTSUPPORT.code);
                        res.put("message", "您已被禁止下单，禁止时间为"+format.format(starttime)+"至"+format.format(endtime));
                        res.put("orderno","");
                        res.put("sendinterval",0);
                        res.put("isusenow",false);
                        return res;
                    }
                }
                taxiorder.setUserid(user.getId());
                Map<String,Object> tempres = carserviceapi.dealRequestWithToken("/OrderApi/CreateOpTaxiOrder", HttpMethod.POST, usertoken, taxiorder, Map.class);
                if((int)tempres.get("status")!=0){
                    res.put("sendinterval",0);
                    res.put("isusenow",false);
                }
                if(!res.containsKey("orderno")){
                    //下单错误。没有orderno时ios说要给
                    res.put("orderno","");
                }
                res.putAll(tempres);
                if(taxiorder.isSavepassenger()){
                    PubMostcontact object = new PubMostcontact();
                    object.setName(taxiorder.getPassengers());
                    object.setPhone(taxiorder.getPassengerphone());
                    object.setUserphone(user.getAccount());
                    insertPubMostcontact(object);
                }
            }
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("orderno","");
            res.put("sendinterval",0);
            res.put("isusenow",false);
        }
        return res;
    }

    /**
     * 添加常用联系人
     * @param params
     * @return
     */
    public Map<String, Object> addMostContact(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();

        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        String name = (String) params.get("name");
        String phone = (String) params.get("phone");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(phone)) {
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "参数不全");
            return res;
        }

        PubMostcontact mostcontact = new PubMostcontact();
        mostcontact.setUserphone(account);
        mostcontact.setName(name);
        mostcontact.setPhone(phone);
        insertPubMostcontact(mostcontact);

        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 删除常用联系人
     * @param params
     * @return
     */
    public Map<String, Object> deleteMostContact(Map<String, Object> params) {
        mostcontactDao.deletePubMostcontactById((String) params.get("contactid"));
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 获取常用联系人
     * @param params
     * @return
     */
    public Map<String, Object> getMostContact(String usertoken) {
        Map<String, Object> res = new HashMap<String, Object>();
        //获取登录用户账号信息
        String account = Const.getUserInfo(usertoken).get("account");

        //查询常用联系人
        List<Map<String, Object>> mostcontactList = mostcontactDao.getPubMostcontactByUserphone(account);
        res.put("mostcontact", mostcontactList);
        if(null != mostcontactList && !mostcontactList.isEmpty()) {
            res.put("currentcount", mostcontactList.size());
        } else {
            res.put("currentcount", 0);
        }
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 添加常用地址
     * @param params
     * @return
     */
    public Map<String, Object> addMostAddress(Map<String, Object> params) {
        //查询登录用户账号信息
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");

        //验证常用地址类型是否已存在，若存在则覆盖原数据
        PubMostaddress mostaddress = new PubMostaddress();
        mostaddress.setUserphone(account);
        mostaddress.setAddresstype((String) params.get("addresstype"));
        mostaddress = mostaddressDao.getPubMostaddressByUserphone(mostaddress);
        if(null == mostaddress) {
            mostaddress = new PubMostaddress();
        }
        mostaddress.setUserphone(account);
        mostaddress.setAddresstype((String) params.get("addresstype"));
        mostaddress.setCity(dicdao.getCityNo((String) params.get("city")));
        mostaddress.setTitle((String) params.get("title"));
        mostaddress.setAddress((String) params.get("address"));
        mostaddress.setLng((Double) params.get("lng"));
        mostaddress.setLat((Double) params.get("lat"));
        //不存在该类地址时新增数据，存在时覆盖原数据
        if(StringUtils.isBlank(mostaddress.getId())) {
            mostaddress.setId(GUIDGenerator.newGUID());
            mostaddressDao.insertPubMostaddress(mostaddress);
        } else {
            mostaddressDao.updatePubMostaddressById(mostaddress);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 删除常用地址
     * @param params
     * @return
     */
    public Map<String, Object> deleteMostAddress(Map<String, Object> params) {
        mostaddressDao.deletePubMostaddressById((String) params.get("addressid"));
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 获取常用地址
     * @param params
     * @return
     */
    public Map<String, Object> getMostAddress(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        //查询登录用户账号信息
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");

        List<Map<String, Object>> mostaddresseList = mostaddressDao.getPubMostaddressListByUserphone(account);
        res.put("mostaddrs", mostaddresseList);
        if(null != mostaddresseList && !mostaddresseList.isEmpty()) {
            res.put("currentcount", mostaddresseList.size());
        } else {
            res.put("currentcount", 0);
        }
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 获取附近司机
     * @param params
     * @return
     */
    public Map<String, Object> getNearDrivers(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);

        //获取参数
        String city = (String) params.get("city");
        double lat = parseDouble(params.get("lat"));
        double lng = parseDouble(params.get("lng"));
        if(StringUtils.isBlank(city)) {
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "参数不完整");
            return res;
        }

        //查询附近司机
        int radius = 3000;
        int amount = 15;
        //查询附近司机参数
        Map<String, Object> dictionary = dicdao.getNearDriverParam();
        if(null != dictionary) {
        	String nearDriverParam = dictionary.get("value").toString();
        	if(StringUtils.isNotBlank(nearDriverParam) && nearDriverParam.split(",").length == 2) {
        		String[] nearDriverParams = nearDriverParam.split(",");
        		radius = Integer.valueOf(nearDriverParams[0]);
        		amount = Integer.valueOf(nearDriverParams[1]);
        	}
        }
        double[] rangeinfo = BaiduUtil.getRange(lng, lat, radius);
        PubDriverInBoundParam pubparam = new PubDriverInBoundParam(rangeinfo);
        List<PubDriver> pubDriverList = driverDao.getNearDrivers(pubparam);
        //对司机排序
        if(null != pubDriverList && !pubDriverList.isEmpty()) {
            pubDriverList.sort(new Comparator<PubDriver>() {
                @Override
                public int compare(PubDriver firDriver, PubDriver secDriver) {
                    double firDistance = BaiduUtil.getLongDistance(lng, lat, firDriver.getLng(), firDriver.getLat());
                    double secDistance = BaiduUtil.getLongDistance(lng, lat, secDriver.getLng(), secDriver.getLat());
                    return (int) (firDistance - secDistance);
                }
            });
            //拼装数据
            List<Map<String, Object>> driverList = new ArrayList<Map<String, Object>>();
            int count = pubDriverList.size() > amount ? amount : pubDriverList.size(); //最多返回二十条数据
            for (int m = 0;m < count;m++) {
                PubDriver driver = pubDriverList.get(m);
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("drivertype", driver.getVehicletype());
                data.put("lng", driver.getLng());
                data.put("lat", driver.getLat());
                data.put("gpsspeed", driver.getGpsspeed());
                data.put("gpsdirection", (int) driver.getGpsdirection());
                driverList.add(data);
            }

            res.put("drivers", driverList);
            res.put("currentcount", driverList.size());
        } else {
            res.put("drivers", null);
            res.put("currentcount", 0);
        }
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 添加常用联系人
     * @param object
     */
    private void insertPubMostcontact(PubMostcontact object) {
        //验证该联系人是否已存在
        PubMostcontact mostcontact = mostcontactDao.getPubMostcontactExist(object);
        if(null == mostcontact) {
            object.setId(GUIDGenerator.newGUID());
            mostcontactDao.insertPubMostcontact(object);
        }
    }

    /**
     * v.3.0.1充值
     * @param param
     * @return
     */
    public Map<String,Object> recharge(Map<String, Object> param,HttpServletRequest request) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) param.get("usertoken");
            double amount =  parseDouble(param.get("amount"));
            String paytype = (String) param.get("paytype");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(paytype)||amount<=0){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                res.put("payorderinfo","");
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            param.put("account", account);
            //v3.0.1充值都是个人用户
            Map<String,Object> payinfo = dicdao.getPayInfo4Op();
            if(payinfo==null){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","充值信息不全");
                res.put("payorderinfo","");
                return res;
            }
            if("1".equalsIgnoreCase(paytype)){
                //支付宝充值
                String alipaystatus = (String) payinfo.get("alipaystatus");
                if(!"1".equals(alipaystatus)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","暂不支持支付宝充值");
                    res.put("payorderinfo","");
                    return res;
                }
                String appid = (String) payinfo.get("alipayappid");
                String alipubkey = (String) payinfo.get("alipaypublickey");
                String privatekey = (String) payinfo.get("alipayprivatekey");
                if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","充值信息不全");
                    res.put("payorderinfo","");
                    return res;
                }
                PeUser user = userdao.getUser4Op(account);
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                //获取订单的交易号 (时间加上5位随机码)
                String out_trade_no = format.format(date)+UNID.getUNID();
                Map<String,Object> tradeparam = new HashMap<String,Object>();
                tradeparam.put("out_trade_no", out_trade_no);
                tradeparam.put("userid", user.getId());
                tradeparam.put("type","0");
                tradeparam.put("paymenttype","0");
                tradeparam.put("validatekey", alipubkey);
                tradeparam.put("tradingstatus","0");
                tradeparam.put("amount",amount);
                //添加交易号记录流水
                userdao.addTradingrecord4Op(tradeparam);
                String zfbchargesubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargesubject"))?"账户充值":SystemConfig.getSystemProperty("zfbchargesubject");
                String zfbchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargebody"))?"充值交易":SystemConfig.getSystemProperty("zfbchargebody");
                amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
                Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", amount, zfbchargesubject, zfbchargebody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBCharge4Op");
                String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
                String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
                String payorderinfo = payorderstr + "&" + sign;
                res.put("payorderinfo", payorderinfo);
            }else{
                //微信充值
                String wechatstatus = (String) payinfo.get("wechatstatus");
                if(!"1".equals(wechatstatus)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","暂不支持微信充值");
                    res.put("payorderinfo","");
                    return res;
                }
                String appid = (String) payinfo.get("wechatappid");
                String chantno = (String) payinfo.get("wechatmerchantno");
                String secretkey = (String) payinfo.get("wechatsecretkey");
                if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","充值信息不全");
                    res.put("payorderinfo","");
                    return res;
                }
                PeUser user = userdao.getUser4Op(account);
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                //获取订单的交易号 (时间加上5位随机码)
                String out_trade_no = format.format(date)+UNID.getUNID();
                Map<String,Object> tradeparam = new HashMap<String,Object>();
                tradeparam.put("out_trade_no", out_trade_no);
                tradeparam.put("userid", user.getId());
                tradeparam.put("type","0");
                tradeparam.put("paymenttype","1");
                tradeparam.put("validatekey", secretkey);
                tradeparam.put("tradingstatus","0");
                tradeparam.put("amount",amount);
                //添加交易号记录流水
                userdao.addTradingrecord4Op(tradeparam);
                String tempipadd = request.getRemoteAddr();
                String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
                amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
                String chargemoney = new java.text.DecimalFormat("#").format(amount*100);
                String wxchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxchargebody"))?"账户充值":SystemConfig.getSystemProperty("wxchargebody");
                Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxchargebody, out_trade_no, chargemoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXCharge4Op", "APP",ipadd);
                Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
                Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
                if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
                    String prepay_id = preres.get("prepay_id");
                    Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
                    String sign = WXOrderUtil.getSign(payorderparam, secretkey);
                    payorderparam.put("sign", sign);
                    payorderparam.put("out_trade_no", out_trade_no);
                    res.put("payorderinfo", payorderparam);
                }else{
                    res.put("status", Retcode.EXCEPTION.code);
                    res.put("message", "充值异常");
                    res.put("payorderinfo","");
                }
            }
        }catch(Exception e){
            logger.equals(e);
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
            res.put("payorderinfo","");
        }
        return res;
    }

    /**
     * v3.0.1获取支付信息
     * @param params
     * @return
     */
    public Map<String,Object> getPayAccounts(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String usertoken = (String) params.get("usertoken");
            String account = Const.getUserInfo(usertoken).get("account");
            String orderno = (String) params.get("orderno");

            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status", Retcode.FAILED.code);
                res.put("message", "参数不全！");
                return res;
            }

            List<Map<String, Object>> payinfo = new ArrayList<Map<String, Object>>();
            List<Map<String,Object>> infos = null;
            if(!"2".equals(usetype)){
                //机构现在不用支付
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "机构支付为什么有私人付钱！");
                return res;
            }else{
                //兼容之前没有传递orderno，完善后去除
                Map<String,Object> validinfo = checkOrderValid4Op(orderno, ordertype);
                if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
                    return validinfo;
                }
                Object order = validinfo.get("order");
                //个人用户
                PeUser peuser = userdao.getUser4Op(account);
                double balance = userdao.getUserBalance4Op(peuser.getId());
                Map<String,Object> balanceinfo = new HashMap<String,Object>();
                balanceinfo.put("type", "使用账户余额支付");
                balanceinfo.put("id", "3");
                balanceinfo.put("balanceamount", balance);
                if(order!=null){
                    double amount = 0;
                    if(isTaxiOrder(ordertype)){
                        //出租车
                        Map<String,Object> optaxiorder = (Map<String, Object>) order;
                        amount = parseDouble(optaxiorder.get("schedulefee"));
                        String paymentmethod = (String) optaxiorder.get("paymentmethod");
                        if("0".equalsIgnoreCase(paymentmethod)){
                            //线上支付
                            amount += parseDouble(optaxiorder.get("orderamount"));
                        }
                    }else{
                        //网约车
                        amount = ((PassengerOrder)order).getOrderamount();
                    }
                    if(amount<=balance){
                        balanceinfo.put("validmoney", true);
                    }else{
                        balanceinfo.put("validmoney", false);
                    }
                    payinfo.add(balanceinfo);
                }
                Map<String,Object> tempinfo = userdao.getPayAccounts4Op();
                if(tempinfo!=null){
                    infos = new ArrayList<Map<String,Object>>();
                    String wechatstatus = (String) tempinfo.get("wechatstatus");
                    if("1".equals(wechatstatus)){
                        Map<String,Object> wxinfo = new HashMap<String,Object>();
                        wxinfo.put("type","微信支付");
                        wxinfo.put("id","2");
                        wxinfo.put("payaccount",tempinfo.get("wechatpayaccount"));
//						wxinfo.put("validmoney", true);
                        infos.add(wxinfo);
                    }
                    String alipaystatus = (String) tempinfo.get("alipaystatus");
                    if("1".equals(alipaystatus)){
                        Map<String,Object> alinfo = new HashMap<String,Object>();
                        alinfo.put("type","支付宝支付");
                        alinfo.put("id","1");
                        alinfo.put("payaccount",tempinfo.get("alipayaccount"));
//						alinfo.put("validmoney", true);
                        infos.add(alinfo);
                    }
                }
            }
            if(infos!=null){
                for(int i=0;i<infos.size();i++){
                    payinfo.add(infos.get(i));
                }
            }
            res.put("payinfo", payinfo);
        }catch(Exception e){
            logger.error("获取支付方式出错", e);

        }
        return res;
    }

    /**
     * 检车个人订单
     * @param orderno
     * @param ordertype
     * @return
     */
    private Map<String, Object> checkOrderValid4Op(String orderno, String ordertype) {
        Map<String, Object> res = new HashMap<String,Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            if(isTaxiOrder(ordertype)){
                //出租车订单
                return checkOpOrderValid(orderno);
            }
            PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
            if(order==null){
                res.put("status", Retcode.ORDERNOTEXIT.code);
                res.put("message", Retcode.ORDERNOTEXIT.msg);
                return res;
            }
            if(!OrderState.SERVICEDONE.state.equalsIgnoreCase(order.getOrderstatus())){
                res.put("status", Retcode.INVALIDORDERSTATUS.code);
                res.put("message", "订单行程还未结束");
                return res;
            }
            if(PayState.PAYED.state.equalsIgnoreCase(order.getPaymentstatus())){
                res.put("status", Retcode.INVALIDORDERSTATUS.code);
                res.put("message", "订单已经支付过");
                return res;
            }
            res.put("order", order);
        }catch(Exception e){
            logger.error("检查订单状态出错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 检查出租车订单是否可以支付
     * @param orderno
     * @return
     */
    private Map<String, Object> checkOpOrderValid(String orderno) {
        Map<String, Object> res = new HashMap<String,Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("orderno", orderno);
            Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
            if(optaxiorder!=null){
                String orderstatus = (String) optaxiorder.get("orderstatus");
                if(!OrderState.SERVICEDONE.state.equalsIgnoreCase(orderstatus)){
                    res.put("status", Retcode.INVALIDORDERSTATUS.code);
                    res.put("message", "订单行程还未结束");
                    return res;
                }
                String paymentstatus = (String) optaxiorder.get("paymentstatus");
                if(PayState.PAYED.state.equalsIgnoreCase(paymentstatus)||PayState.DRIVERNOPAY.state.equalsIgnoreCase(paymentstatus)||PayState.PAYOVER.state.equalsIgnoreCase(paymentstatus)){
                    res.put("status", Retcode.INVALIDORDERSTATUS.code);
                    res.put("message", "订单已经支付过");
                    return res;
                }
                res.put("order", optaxiorder);
            }else{
                res.put("status", Retcode.ORDERNOTEXIT.code);
                res.put("message", Retcode.ORDERNOTEXIT.msg);
                return res;
            }
        }catch(Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * v3.0.1订单支付
     * @param params
     * @param req
     * @return
     */
    public Map<String,Object> payOder(Map<String, Object> params, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        addPubInfos(res);
        try{
            String usertoken = (String) params.get("usertoken");
            String orderno = (String) params.get("orderno");
            String paytype = (String) params.get("paytype");
            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(paytype)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status", Retcode.FAILED.code);
                res.put("message", "支付失败，参数不全");
                return res;
            }
            if(!"2".equals(usetype)){
                //机构订单不可能有支付
                res.put("status", Retcode.FAILED.code);
                res.put("message", "机构订单不可能有支付,全是机构支付");
                return res;
            }
            Map<String,Object> validinfo = checkOrderValid4Op(orderno, ordertype);
            if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
                return validinfo;
            }
            if("3".equalsIgnoreCase(paytype)){
                //钱包支付
                return payOrderByBalance4Op(params);
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //获取订单的交易号 (时间加上5位随机码)
            String out_trade_no = format.format(date)+UNID.getUNID();
            //个人订单
            boolean isoptaxiorder = isTaxiOrder(ordertype);
            if(isoptaxiorder){
                //出租车支付
                return payOpTaxiOrder(orderno, paytype, request);
            }else{
                Map<String,Object> payinfo = dicdao.getPayInfo4Op();
                if(payinfo==null){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","账户信息不全");
                    res.put("payorderinfo","");
                    return res;
                }
                //网约车支付
                if("1".equalsIgnoreCase(paytype)){
                    //支付宝充值
                    String alipaystatus = (String) payinfo.get("alipaystatus");
                    if(!"1".equals(alipaystatus)){
                        res.put("status",Retcode.EXCEPTION.code);
                        res.put("message","暂不支持支付宝支付");
                        res.put("payorderinfo","");
                        return res;
                    }
                    String appid = (String) payinfo.get("alipayappid");
                    String alipubkey = (String) payinfo.get("alipaypublickey");
                    String privatekey = (String) payinfo.get("alipayprivatekey");
                    if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
                        res.put("status",Retcode.EXCEPTION.code);
                        res.put("message","账户信息不全");
                        res.put("payorderinfo","");
                        return res;
                    }
                    //添加交易号记录流水
                    Map<String,String> orderinfo = new HashMap<String,String>();
                    orderinfo.put("out_trade_no", out_trade_no);
                    orderinfo.put("orderno", orderno);
                    orderinfo.put("paymenttype", "0");
                    orderinfo.put("validatekey", alipubkey);
                    orderdao.addTradeNo4OpOrder(orderinfo);
                    //创建支付信息
                    PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
                    double orderamount = order.getOrderamount();
                    orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
                    String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
                    zfbsubject += orderno;
                    String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
                    Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
                    String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
                    String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
                    String payorderinfo = payorderstr + "&" + sign;
                    res.put("payorderinfo", payorderinfo);
                }else{
                    //微信充值
                    String wechatstatus = (String) payinfo.get("wechatstatus");
                    if(!"1".equals(wechatstatus)){
                        res.put("status",Retcode.EXCEPTION.code);
                        res.put("message","暂不支持微信支付");
                        res.put("payorderinfo","");
                        return res;
                    }
                    String appid = (String) payinfo.get("wechatappid");
                    String chantno = (String) payinfo.get("wechatmerchantno");
                    String secretkey = (String) payinfo.get("wechatsecretkey");
                    if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
                        res.put("status",Retcode.EXCEPTION.code);
                        res.put("message","账户信息不全");
                        res.put("payorderinfo","");
                        return res;
                    }
                    //添加交易流水记录
                    Map<String,String> orderinfo = new HashMap<String,String>();
                    orderinfo.put("out_trade_no", out_trade_no);
                    orderinfo.put("orderno", orderno);
                    orderinfo.put("paymenttype", "1");
                    orderinfo.put("validatekey", secretkey);
                    orderdao.addTradeNo4OpOrder(orderinfo);

                    //创建支付信息
                    String tempipadd = request.getRemoteAddr();
                    String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
                    PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
                    double orderamount = order.getOrderamount();
                    orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
                    String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
                    String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
                    Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4Op", "APP",ipadd);
                    Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
                    Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
                    if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
                        String prepay_id = preres.get("prepay_id");
                        Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
                        String sign = WXOrderUtil.getSign(payorderparam, secretkey);
                        payorderparam.put("sign", sign);
                        payorderparam.put("out_trade_no", out_trade_no);
                        res.put("payorderinfo", payorderparam);
                    }else{
                        res.put("status", Retcode.EXCEPTION.code);
                        res.put("message", "支付异常");
                        res.put("payorderinfo","");
                    }
                }
            }
        }catch(Exception e){
            logger.error("订单支付",e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "支付异常");
        }
        return res;
    }

    /**
     * 使用账户余额支付订单
     * @param params
     * @return
     */
    public Map<String, Object> payOrderByBalance4Op(Map<String,Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try {
            String usertoken = (String) params.get("usertoken");
            String orderno = (String) params.get("orderno");
            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if (StringUtils.isBlank(usertoken) || StringUtils.isBlank(orderno) || StringUtils.isBlank(ordertype) || StringUtils.isBlank(usetype)) {
                res.put("status", Retcode.FAILED.code);
                res.put("message", "支付失败，参数不全");
            }
            String account = Const.getUserInfo(usertoken).get("account");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("orderno", orderno);
            //个人用户
            PeUser peuser = userdao.getUser4Op(account);
            double balance = userdao.getUserBalance4Op(peuser.getId());
            if (isTaxiOrder(ordertype)) {
                Map<String, Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
                return payOptaxiOrderByBalance(optaxiorder, peuser, balance);
            }
            PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
            double orderamount = order.getOrderamount();
            if (balance < orderamount) {
                //不够支付
                res.put("status", Retcode.NOTENOUGHBALANCE.code);
                res.put("message", "余额不足！");
            } else {
                //更新钱包
                Map<String, Object> updatebalancep = new HashMap<String, Object>();
                updatebalancep.put("uid", GUIDGenerator.newGUID());
                updatebalancep.put("money", orderamount);
                updatebalancep.put("userid", peuser.getId());
                updatebalancep.put("isout", 1);
                userdao.updateUserBalance4OpSec(updatebalancep);
                //添加支付记录
                Map<String, Object> expenses = new HashMap<String, Object>();
                expenses.put("logid", GUIDGenerator.newGUID());
                expenses.put("userid", peuser.getId());
                expenses.put("expensetype", "4");
                expenses.put("money", orderamount);
                expenses.put("remark", "余额支付");
                expenses.put("tradetype", "1");
                expenses.put("detailtype", "0");
                expenses.put("operateresult", "0");
                userdao.addExpenses4OpSec(expenses);
                //更新订单状态
                Map<String, Object> orderparam = new HashMap<String, Object>();
                orderparam.put("paymentstatus", "1");
                orderparam.put("orderno", orderno);
                orderparam.put("paytype", "1");
                orderdao.payed4OpOrder(orderparam);
//                //网约车返现
//                try{
//                    Map<String,Object> awardparams = new HashMap<String,Object>();
//                    awardparams.put("usertype","1");
//                    awardparams.put("passengerphone",order.getPassengerphone());
//                    awardparams.put("money",orderamount);
//                    awardparams.put("userphone",peuser.getAccount());
//                    carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//                }catch (Exception e){
//                    logger.error("返现出错了",e);
//                }
            }
        } catch (Exception e) {
            logger.error("钱包支付报错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 使用钱包支付出租车订单
     * @param optaxiorder
     * @param peuser
     * @param balance
     * @return
     */
    private Map<String, Object> payOptaxiOrderByBalance(Map<String, Object> optaxiorder, PeUser peuser, double balance) {
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String orderno = (String) optaxiorder.get("orderno");
            double orderamount = parseDouble(optaxiorder.get("schedulefee"));
            String paymentmethod = (String) optaxiorder.get("paymentmethod");
            if("0".equalsIgnoreCase(paymentmethod)){
                //线上支付
                orderamount += parseDouble(optaxiorder.get("orderamount"));
            }
            if(balance<orderamount){
                //不够支付
                res.put("status", Retcode.NOTENOUGHBALANCE.code);
                res.put("message", "余额不足！");
            }else{
                //更新钱包
                Map<String,Object> updatebalancep = new HashMap<String,Object>();
                updatebalancep.put("uid", GUIDGenerator.newGUID());
                updatebalancep.put("money", orderamount);
                updatebalancep.put("userid", peuser.getId());
                updatebalancep.put("isout", 1);
                userdao.updateUserBalance4OpSec(updatebalancep);
                //添加支付记录
                Map<String,Object> expenses = new HashMap<String,Object>();
                expenses.put("logid", GUIDGenerator.newGUID());
                expenses.put("userid",peuser.getId());
                expenses.put("expensetype","4");
                expenses.put("money",orderamount);
                expenses.put("remark","余额支付");
                expenses.put("tradetype","1");
                expenses.put("detailtype","0");
                expenses.put("operateresult","0");
                userdao.addExpenses4OpSec(expenses);
                //更新订单状态
                Map<String, Object> orderparam = new HashMap<String,Object>();
                orderparam.put("paymentstatus", "1");
                orderparam.put("orderno", orderno);
                orderparam.put("paytype", "1");
                orderdao.payed4OpTaxiOrder(orderparam);
                if("0".equalsIgnoreCase(paymentmethod)){
                    //线上支付
                    //给司机钱包加钱并且加收入明细
                    addMoney4Driver(optaxiorder);
//                    //出租车返现
//                    try{
//                        Map<String,Object> awardparams = new HashMap<String,Object>();
//                        awardparams.put("usertype","1");
//                        awardparams.put("passengerphone",optaxiorder.get("passengerphone"));
//                        awardparams.put("money",orderamount);
//                        awardparams.put("userphone",peuser.getAccount());
//                        carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//                    }catch (Exception e){
//                        logger.error("返现出错了",e);
//                    }
                }
            }
        }catch(Exception e){
            logger.error("钱包支付出租车订单", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 给司机添加订单金额到钱包并且添加明细
     * @param optaxiorder
     */
    private void addMoney4Driver(Map<String, Object> optaxiorder) {
        if(optaxiorder==null){
            return ;
        }
        try{
            String paymentmethod = (String) optaxiorder.get("paymentmethod");
            if("0".equalsIgnoreCase(paymentmethod)){
                //线上支付
                double orderamount = parseDouble(optaxiorder.get("orderamount"));
                //给司机钱包加钱并且加收入明细
                String driverid = (String) optaxiorder.get("driverid");
                String companyid = (String) optaxiorder.get("companyid");
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("driverid",driverid);
                params.put("companyid",companyid);
                Map<String, Object> platinfo = dicdao.getPayInfo4Op();
                String platid = (String) platinfo.get("id");
                if(platid==null||platid.equals(companyid)){
                    params.put("platformtype",0);
                }else{
                    params.put("platformtype",1);
                }
                params.put("orderamount",orderamount);
                Map<String,Object> balanceinfo = userdao.getDriverBalance(params);
                if(balanceinfo==null){
                    //司机不存在钱包，创建
                    params.put("id",GUIDGenerator.newGUID());
                    userdao.createDriverBalanceInfo(params);
                }else{
                    //更新钱包
                    userdao.updateDriverBalanceInfo(params);
                }
                Map<String,Object> infolog = new HashMap<String,Object>();
                infolog.put("id",GUIDGenerator.newGUID());
                infolog.put("companyid",companyid);
                infolog.put("driverid",driverid);
                infolog.put("tradetype","4");
                infolog.put("remark","");
                infolog.put("expensetype","5");
                infolog.put("amount",orderamount);
                infolog.put("detailtype","0");
                infolog.put("operateresult","0");
                infolog.put("platformtype",params.get("platformtype"));
                userdao.addDriverExpenses(infolog);
            }
        }catch (Exception e){
            logger.error("给司机添加钱包钱时出错",e);
        }
    }

    /**
     * 个人用户出租车订单微信和支付宝支付
     * @param orderno
     * @param paytype
     * @return
     */
    private Map<String, Object> payOpTaxiOrder(String orderno,String paytype,HttpServletRequest request) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            Map<String,Object> payinfo = dicdao.getPayInfo4Op();
            if(payinfo==null){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","账户信息不全");
                res.put("payorderinfo","");
                return res;
            }
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //获取订单的交易号 (时间加上5位随机码)
            String out_trade_no = format.format(date)+UNID.getUNID();
            if("1".equalsIgnoreCase(paytype)){
                //支付宝
                String alipaystatus = (String) payinfo.get("alipaystatus");
                if(!"1".equals(alipaystatus)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","暂不支持支付宝支付");
                    res.put("payorderinfo","");
                    return res;
                }
                String appid = (String) payinfo.get("alipayappid");
                String alipubkey = (String) payinfo.get("alipaypublickey");
                String privatekey = (String) payinfo.get("alipayprivatekey");
                if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","账户信息不全");
                    res.put("payorderinfo","");
                    return res;
                }
                //添加交易号记录流水
                Map<String,String> orderinfo = new HashMap<String,String>();
                orderinfo.put("out_trade_no", out_trade_no);
                orderinfo.put("orderno", orderno);
                orderinfo.put("paymenttype", "0");
                orderinfo.put("validatekey", alipubkey);
                orderdao.addTradeNo4OpTaxiOrder(orderinfo);
                //创建支付信息
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("orderno", orderno);
                Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
                double orderamount = parseDouble(optaxiorder.get("schedulefee"));
                String paymentmethod = (String) optaxiorder.get("paymentmethod");
                if("0".equalsIgnoreCase(paymentmethod)){
                    //线上支付
                    orderamount += parseDouble(optaxiorder.get("orderamount"));
                }
                orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
                String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
                zfbsubject += orderno;
                String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
                Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
                String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
                String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
                String payorderinfo = payorderstr + "&" + sign;
                res.put("payorderinfo", payorderinfo);
            }else{
                //微信支付
                String wechatstatus = (String) payinfo.get("wechatstatus");
                if(!"1".equals(wechatstatus)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","暂不支持微信支付");
                    res.put("payorderinfo","");
                    return res;
                }
                String appid = (String) payinfo.get("wechatappid");
                String chantno = (String) payinfo.get("wechatmerchantno");
                String secretkey = (String) payinfo.get("wechatsecretkey");
                if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
                    res.put("status",Retcode.EXCEPTION.code);
                    res.put("message","账户信息不全");
                    res.put("payorderinfo","");
                    return res;
                }
                //添加交易流水记录
                Map<String,String> orderinfo = new HashMap<String,String>();
                orderinfo.put("out_trade_no", out_trade_no);
                orderinfo.put("orderno", orderno);
                orderinfo.put("paymenttype", "1");
                orderinfo.put("validatekey", secretkey);
                orderdao.addTradeNo4OpTaxiOrder(orderinfo);
                //创建支付信息
                String tempipadd = request.getRemoteAddr();
                String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("orderno", orderno);
                Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
                double orderamount = parseDouble(optaxiorder.get("schedulefee"));
                String paymentmethod = (String) optaxiorder.get("paymentmethod");
                if("0".equalsIgnoreCase(paymentmethod)){
                    //线上支付
                    orderamount += parseDouble(optaxiorder.get("orderamount"));
                }
                orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
                String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
                String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
                Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4Op", "APP",ipadd);
                Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
                Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
                if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
                    String prepay_id = preres.get("prepay_id");
                    Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
                    String sign = WXOrderUtil.getSign(payorderparam, secretkey);
                    payorderparam.put("sign", sign);
                    payorderparam.put("out_trade_no", out_trade_no);
                    res.put("payorderinfo", payorderparam);
                }else{
                    res.put("status", Retcode.EXCEPTION.code);
                    res.put("message", "支付异常");
                    res.put("payorderinfo","");
                }
            }
        }catch(Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 当前城市是否提供服务
     * @param params
     * @return
     */
    public Map<String,Object> hasServiceInfo(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            res.put("hasnetcarservice", false);
            res.put("hastaxiservice", false);
            String usertoken = (String) params.get("usertoken");
            String city = (String) params.get("city");
            String ordertype = (String) params.get("ordertype");
            if(StringUtils.isBlank(city)||StringUtils.isBlank(ordertype)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不全");
                return res;
            }
            Map<String,Object> serviceparams = new HashMap<String,Object>();
            serviceparams.put("ordertype",ordertype);
            List<String> netservicecitys = getServiceCitys4NetCar(usertoken,serviceparams);
            if(netservicecitys!=null&&netservicecitys.size()>0&&netservicecitys.contains(city)){
                //网约车有服务
                res.put("hasnetcarservice", true);
            }
            if("1".equals(ordertype)){
//                约车才有出租车
                List<String> taxiservicecitys = getServiceCitys4Taxi(usertoken,serviceparams);
                if(taxiservicecitys!=null&&taxiservicecitys.size()>0&&taxiservicecitys.contains(city)){
                    //出租车有服务
                    res.put("hastaxiservice", true);
                }
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
	 * 获取未支付的订单信息
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	public Map<String,Object> getUnpayOders(String usertoken, String companyid) {
		long start = System.currentTimeMillis();
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("orderstatus",OrderState.SERVICEDONE.state);
		params.put("paymentstatus",PayState.NOTPAY.state);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			// 机构用户机构订单时直接机构支付了，只有个人订单存在未支付
			// 个人用户
			// 获取未支付的网约车订单
			Map<String, Object> netcarorderinfo = orderdao.getUnpayOrders4OpNetCar(params);
			if (netcarorderinfo != null) {
				res.put("order", netcarorderinfo);
				return res;
			}
			// 获取未支付的出租车订单
			Map<String, Object> taxiorderinfo = orderdao.getUnpayOrders4OpTaxi(params);
			res.put("order", taxiorderinfo);
		}catch(Exception e){
			logger.error(e.getMessage());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", null);
			return res;
		}
		long end = System.currentTimeMillis();
		System.out.println("*******************************************************"+(end-start));
		return res;
	}

    /**
     * 获取网约车计费规则
     * @param param
     * @param res
     */
    public void getAccountRules4NetCar(Map<String, Object> param, HttpServletResponse res) {
        res.setContentType("text/html; charset=utf-8");
        String usertoken = (String) param.get("usertoken");
        String city =  (String) param.get("city");
        String rulestype =  (String) param.get("rulestype");
        String usetype =  (String) param.get("usetype");
        String type = (String) param.get("type");
        String companyname = null;
        List<AccountRules> rules = null;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("city", city);
        params.put("rulestype", rulestype);
        Map<String,Object> yyparams = new HashMap<String,Object>();
        if(Const.ORDERTYPE_ORG.equals(type)){
            yyparams.put("type","机构用户下单预约附加费用");
            //机构
            String companyid =   (String) param.get("companyid");
            params.put("companyid", companyid);
            //v3.0.1都是机构因公
//            if(Const.USETYPE_PUBLIC.equals(usetype)){
                OrgOrgan organ = orgdao.getOrgInfo(Const.getUserInfo(usertoken).get("account"));
                params.put("organid", organ.getId());
//            }
            //因公用车获取个性化计费规则，因私获取标准计费规则
            rules = orgdao.getAccountRules(params);
            LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
            if(company!=null){
                companyname = "如约的士";
            }
        }else{
            //个人
            yyparams.put("type","个人用户下单预约附加费用");
            //获取运营端的计费规则
            rules = opdao.getAccountRules(params);
        }
        if(rules!=null){
            for(int i=0;i<rules.size();i++){
                AccountRules rule = rules.get(i);
                String logo = rule.getLogo();
                if(StringUtils.isNotBlank(logo)){
                    rule.setLogo(SystemConfig.getSystemProperty("fileserver")+ File.separator+logo);
                }
            }
        }
        VelocityContext context = new VelocityContext();
        context.put("rules", JSONArray.fromObject(rules));
        context.put("company", companyname);
        context.put("yyfjf", getYYFJF(yyparams));
        String vmpath = PassengerService.class.getClassLoader().getResource("accountrulesnew.vm").getPath();
        try {
            VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
        } catch (Exception e) {
            logger.error("乘客端异常",e);
        }
    }

    /**
     * 获取预约附加费
     * @return
     */
    private double getYYFJF(Map<String,Object> params){
        Map<String,Object> yyfjf = dicdao.getYYFJF(params);
        if(yyfjf==null){
            return 0;
        }
        return parseDouble(yyfjf.get("value"));
    }

    /**
     * 出租车获取计费规则
     * @param param
     * @param res
     */
    public void getAccountRules4Taxi(Map<String, Object> param, HttpServletResponse res) {
        res.setContentType("text/html; charset=utf-8");
        String city =  (String) param.get("city");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("city", city);
        //个人
        //获取运营端的计费规则
        Map<String, Object> rule = opdao.getAccountRules4OpTaxi(params);
        if(rule!=null){
            String logo = (String) rule.get("logo");
            if(StringUtils.isNotBlank(logo)){
                rule.put("logo",SystemConfig.getSystemProperty("fileserver")+ File.separator+logo);
            }
//            Map<String,Object> yyparams = new HashMap<String,Object>();
//            yyparams.put("type","个人用户出租车下单预约附加费用");
//            rule.put("yyfjf",getYYFJF(yyparams));
        }
        VelocityContext context = new VelocityContext();
        context.put("rule", JSONObject.fromObject(rule));
        context.put("company", "");
        String vmpath = PassengerService.class.getClassLoader().getResource("accountrules301taxi.vm").getPath();
        try {
            VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
        } catch (Exception e) {
            logger.error("乘客端异常",e);
        }
    }

    /**
     * v3.0.1获取验证码接口
     * @param params
     * @return
     */
    public Map<String,Object> getVerificationCode(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String phone = (String) params.get("phone");
            String usertype = (String) params.get("usertype");
            String smstype = (String) params.get("smstype");
            if(StringUtils.isBlank(phone)||StringUtils.isBlank(usertype)||StringUtils.isBlank(smstype)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }

            try{
                String smscodeouttimestr = SystemConfig.getSystemProperty("smscodeouttime");
                int smscodeouttime = parseInt(smscodeouttimestr)<=0?5:parseInt(smscodeouttimestr);

                String smscodetimesintimestr = SystemConfig.getSystemProperty("smscodetimesintime");
                int smscodetimesintime = parseInt(smscodetimesintimestr)<=0?5:parseInt(smscodetimesintimestr);
                if("0".equals(smstype)){
                    String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone);
                    double gettimes = parseDouble(value);
                    if(gettimes>=smscodetimesintime){
                        //获取次数超限
                        res.put("status", Retcode.EXCEPTION.code);
                        res.put("message", "获取验证码次数超限");
                        return res;
                    }else{
                        //没有超限要递增
                        JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone);
                        if(gettimes<=0){
                            JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone,smscodeouttime*60);
                        }
                    }
                }else if("1".equals(smstype)){
                    String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone);
                    double gettimes = parseDouble(value);
                    if(gettimes>=smscodetimesintime){
                        //获取次数超限
                        res.put("status", Retcode.EXCEPTION.code);
                        res.put("message", "获取验证码次数超限");
                        return res;
                    }else{
                        //没有超限要递增
                        JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone);
                        if(gettimes<=0){
                            JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone,smscodeouttime*60);
                        }
                    }
                }else{
                    String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
                    double gettimes = parseDouble(value);
                    if(gettimes>=smscodetimesintime){
                        //获取次数超限
                        res.put("status", Retcode.EXCEPTION.code);
                        res.put("message", "获取验证码次数超限");
                        return res;
                    }else{
                        //没有超限要递增
                        JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
                        if(gettimes<=0){
                            JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone,smscodeouttime*60);
                        }
                    }
                }
            }catch (Exception e){
                logger.error("redis控制获取验证码次数失败了",e);
            }

            //获取一串随机的短信验证码
            String smscode = SMSCodeUtil.getRandCode();
            Map<String,String> smscodeobj = new HashMap<String,String>();
            smscodeobj.put("smstype", smstype);
            smscodeobj.put("username", phone);
            smscodeobj.put("smscode", smscode);
            //更新机构用户的验证码
            smscodeobj.put("usertype", Const.USERTOKENTYPE_ORGUSER);
            if(userdao.hasSMSCode(smscodeobj)){
                userdao.updateSMSCode(smscodeobj);
            }else{
                smscodeobj.put("id", GUIDGenerator.newGUID());
                userdao.saveSMSCode(smscodeobj);
            }
            //更新个人用户的验证码
            smscodeobj.put("usertype", Const.USERTOKENTYPE_PEUSER);
            if(userdao.hasSMSCode(smscodeobj)){
                userdao.updateSMSCode(smscodeobj);
            }else{
                smscodeobj.put("id", GUIDGenerator.newGUID());
                userdao.saveSMSCode(smscodeobj);
            }
            //发送短信到手机

//		String content = "验证码："+smscode+"，请在10分钟内输入，("+PlatformType.PASSENGER.msg+")";
            String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.passengerservice.smscode", smscode);
            List<String> userids = new ArrayList<String>();
            userids.add(phone);
            UserMessage usermessage = new UserMessage(userids, content, UserMessage.GETSMSCODE);
            MessageUtil.sendMessage(usermessage);
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * v3.0.1获取正在服务中的订单
     * 包括出租车订单和机构个人网约车的订单
     * 获取正在服务中的订单
     * @param params
     * @return
     */
    public Map<String,Object> getServiceOder(Map<String, Object> paramsrc) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        res.put("order",null);
        String usertoken = (String) paramsrc.get("usertoken");
        if(StringUtils.isBlank(usertoken)){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "参数不完整");
            return res;
        }
        Map<String,String> userinfo = Const.getUserInfo(usertoken);
        String account = userinfo.get("account");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("account",account);
        params.put("orderstatus", OrderState.INSERVICE.state);

        //个人用户
        //获取正在服务中的网约车订单
        Map<String,Object> netcarorderinfo = orderdao.getServiceOder4OpNetCar(params);
        if(netcarorderinfo!=null){
            dillOrder(netcarorderinfo,usertoken,true);
            res.put("order",netcarorderinfo);
            return res;
        }

        //获取正在服务中的出租车订单
        Map<String,Object> taxiorderinfo = orderdao.getServiceOder4OpTaxi(params);
        if(taxiorderinfo!=null){
            dillOrder(taxiorderinfo,usertoken,true);
            res.put("order",taxiorderinfo);
            return res;
        }

        //机构用户
        PassengerOrder serviceorder = orderdao.getOrder4Org(params);
        dillWithOrderInfo(serviceorder,usertoken);
        res.put("order", serviceorder);

        return res;
    }

    /**
     * 更改消息类型
     * @param params
     * @return
     */
    public Map<String,Object> updateMessageState(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String infotype = (String) params.get("infotype");
            String messageid = (String) params.get("messageid");
            String param = (String) params.get("param");
            if(StringUtils.isBlank(infotype)||StringUtils.isBlank(messageid)||StringUtils.isBlank(param)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                res.put("info", "infotype是不是少了");
                return res;
            }
            if("0".equals(infotype)){
                //机构用户
                userdao.updateMessageState4Org(params);
            }else{
                //个人用户
                userdao.updateMessageStateOp(params);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 读取所有的消息
     * @param params
     * @return
     */
    public Map<String,Object> readMessageAll(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String usertoken = (String) params.get("usertoken");
            Map<String,String> userinfo = Const.getUserInfo(usertoken);
            String account = userinfo.get("account");
            //机构用户
            userdao.readMessageAll4Org(account);
            //个人用户
            userdao.readMessageAll4Op(account);
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * v3.0.1更新用户信息
     * @param userinfos
     * @return
     */
    public Map<String,Object> updatePassengerInfo(Map<String, Object> userinfo) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        String usertoken = (String) userinfo.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        try {
            //个人用户
            PeUser peuser = userdao.getUser4Op(account);
            if(peuser!=null){
                userinfo.put("userid", peuser.getId());
                userdao.updateUser4Op(userinfo);
            }
            //机构用户
            OrgUser orguser = userdao.getUser4Org(account);
            if (orguser!=null) {
                userinfo.put("userid", orguser.getId());
                userinfo.remove("nickname");
                userdao.updateUser4Org(userinfo);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * v3.0.1报警管理
     * @param param
     * @return
     */
    public Map<String,Object> registerAlarm(Map<String, Object> param) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        try{
            String usertoken = (String) param.get("usertoken");
            String orderno = (String) param.get("orderno");
            Object lng = param.get("lng");
            Object lat = param.get("lat");
            String ordertype = (String) param.get("ordertype");
            String usetype = (String) param.get("usetype");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||lng==null||lat==null||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status",Retcode.EXCEPTION.code);
                res.put("message","参数不全");
                return res;
            }
            String account = Const.getUserInfo(usertoken).get("account");
            param.put("account", account);

            SavePubAlarmprocessDto dto = new SavePubAlarmprocessDto();
            dto.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_PASSENGER.code);
            dto.setAlarmtime(StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            dto.setAlarmtype(AlarmProcessEnum.ALARMTYPE_INSERVICE.code);
            dto.setLat(lat+"");
            dto.setLng(lng+"");
            dto.setOrderno(orderno);
            boolean istaxuorder = isTaxiOrder(ordertype);
//            if(istaxuorder){
//                dto.setOrdertype("1");
//            }else{
//                dto.setOrdertype("0");
//            }
            dto.setOrdertype(ordertype);
            String apiurl = null;
            if(!"2".equals(usetype)){
                //机构用户
                dto.setUsertype(UserType.ORGUSER.code);
                dto.setPlatformtype(PlatformTypeByDb.LEASE.code);
                OrgUser user = userdao.getUser4Org(account);
                if(user!=null){
                    dto.setUserid(user.getId());
                    dto.setCreater(user.getId());
                }
                PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
                if(order!=null){
                    dto.setLeasecompanyid(order.getCompanyid());
                    dto.setDriverid(order.getDriverid());
                }
                apiurl = SystemConfig.getSystemProperty("leaseApi");
            }else{
                //个人用户
                dto.setUsertype(UserType.PERSONAL.code);
                dto.setPlatformtype(PlatformTypeByDb.OPERATING.code);
                PeUser user = userdao.getUser4Op(account);
                if(user!=null){
                    dto.setUserid(user.getId());
                }
                if(istaxuorder){
                    //出租车
                    Map<String,Object> orderinfo = orderdao.getOrder4OpTaxi(param);
                    if(orderinfo!=null){
                        dto.setDriverid((String)orderinfo.get("driverid"));
                    }
                }else{
                    //网约车
                    Map<String,Object> orderinfo = orderdao.getOrder4OpNetCar(param);
                    if(orderinfo!=null){
                        dto.setDriverid((String)orderinfo.get("driverid"));
                    }
                }
                Map<String,Object> opinfo = orderdao.getOpInfo();
                if(opinfo!=null){
                    dto.setLeasecompanyid((String) opinfo.get("id"));
                }
                apiurl = SystemConfig.getSystemProperty("operateApiUrl");
            }
            JSONObject result = templateHelper.dealRequestWithFullUrlToken(apiurl+"/pubAlarmprocess/apply",
                    HttpMethod.POST,
                    usertoken,
                    dto,
                    JSONObject.class
            );
            if(result.getInt("status") != Retcode.OK.code){
                logger.error("报警申请失败:"+result);
                res.put("status", Retcode.FAILED.code);
                res.put("message", "报警申请失败");
                return res;
            }
            logger.info("报警申请完成");
        }catch(Exception e){
            logger.error("行程报警异常",e);
            res.put("info",e.getMessage());
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 获取用户信息
     * @param params
     * @return
     */
    public Map<String,Object> getPassengerInfo(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        String usertype = (String) params.get("usertype");
        if(isOrgUser(usertoken)&&(StringUtils.isBlank(usertype)||Const.ORGUSER.equals(usertype))){
            //机构用户
            OrgUser userinfo = userdao.getUser4Org(account);
            if(userinfo!=null){
                Map<String,Object> result = new HashMap<String,Object>();
                String img = userinfo.getHeadPortraitMin();
                if(StringUtils.isNotBlank(img)){
                    result.put("headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+img);
                }else{
                    result.put("headportraitmin","");
                }
                result.put("account", userinfo.getAccount());
                result.put("name", userinfo.getNickName());
                result.put("sex", userinfo.getSex());
                result.put("company",userinfo.getOrgcaption());
                result.put("dpet",userinfo.getDeptcation());
                result.put("nickname","");
                try{
                    PeUser peuserinfo = userdao.getUser4Op(account);
                    if(peuserinfo!=null){
                        result.put("nickname",peuserinfo.getNickname());
                        String peimg = peuserinfo.getHeadportraitmin();
                        if(StringUtils.isNotBlank(peimg)){
                            result.put("headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+peimg);
                        }
                    }
                }catch (Exception e){}
                res.put("userinfo",result);
            }
        }else{
            //个人用户
            PeUser userinfo = userdao.getUser4Op(account);
            Map<String,Object> result = new HashMap<String,Object>();
            String img = userinfo.getHeadportraitmin();
            if(StringUtils.isNotBlank(img)){
                result.put("headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+img);
            }else{
                result.put("headportraitmin","");
            }
            result.put("account", userinfo.getAccount());
            result.put("nickname", userinfo.getNickname());
            result.put("name", userinfo.getNickname());
            result.put("sex", userinfo.getSex());
            res.put("userinfo",result);
        }
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        return res;
    }

    /**
     * v3.0.1获取服务器时间
     * @param params
     * @return
     */
    public Map<String,Object> getCurrentTime(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        String type = (String) params.get("type");
        String city = (String) params.get("city");
        if(StringUtils.isBlank(city)){
            res.put("status",Retcode.EXCEPTION.code);
            res.put("message","参数不全");
            return res;
        }
        int milute = 30;
        String cityvalue = dicdao.getCityNo(city);
        params.put("city",cityvalue);
        try{
            if(StringUtils.isBlank(type)||"1".equals(type)){
                //个人，获取运管端的约车时限
                String ordertype = (String) params.get("ordertype");
                Map<String,Object> revercerule = null;
                if(StringUtils.isBlank(ordertype)){
                    //没有传递类型
                    revercerule = opdao.getSendRuleByCity4ReverceNetCar(params);
                    if(revercerule==null){
                        revercerule = opdao.getSendRule4ReverceTaxi(params);
                    }
                }else if("4".equals(ordertype)){
                    //获取出租车的
                    revercerule = opdao.getSendRule4ReverceTaxi(params);
                }else{
                    //网约车的
                    revercerule = opdao.getSendRuleByCity4ReverceNetCar(params);
                }
                if(revercerule!=null){
                    milute = parseInt( revercerule.get("carsinterval"));
                }
                res.put("revercetime",milute*60);
            }else{
                //机构，获取如约的约车时限
                List<Map<String,Object>> ruyue = orgdao.getOldestServiceCompanys();
                if(ruyue.size()>0){
                    Map<String,Object> companyinfo = ruyue.get(0);
                    String companyid = (String) companyinfo.get("id");
                    params.put("companyid",companyid);
                    Map<String,Object> revercerule = orgdao.getSendRuleByCity4ReverceNetCar(params);
                    if(revercerule!=null){
                        milute = parseInt(revercerule.get("carsinterval"));
                    }
                    res.put("revercetime",milute*60);
                }
            }
        }catch (Exception e){
            res.put("revercetime",milute*60);
        }
        res.put("status",Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        return res;
    }

    /**
     * 获取订单轨迹接口
     * @param params
     * @return
     */
    public Map<String,Object> getOrbit(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            PassengerOrder order = null;
            String usertoken = (String) params.get("usertoken");
            String orderno = (String) params.get("orderno");
            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status", Retcode.FAILED.code);
                res.put("message", "参数不全");
                return res;
            }
            if(!"2".equals(usetype)){
                order = orderdao.getOrderByOrderno4Org(orderno);
            }else{
                if(isTaxiOrder(ordertype)){
                    return getOrbit4OpTaxi(orderno,ordertype,usetype);
                }
                order = orderdao.getOrderByOrderno4Op(orderno);
            }
            if(order==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "无法获取到订单信息");
                return res;
            }
            res = carserviceapi.dealRequestWithToken("/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}", HttpMethod.GET, usertoken, null, Map.class, orderno,ordertype,usetype);
            if(res==null){
                res = new HashMap<String,Object>();
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "无法获取到轨迹信息");
                return res;
            }
        }catch(Exception e){
            logger.error("获取轨迹出错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "无法获取到轨迹信息");
        }
        return res;
    }

    /**
     * 获取个人出租车订单的轨迹
     * @param orderno
     * @return
     */
    private Map<String, Object> getOrbit4OpTaxi(String orderno,String ordertype,String usetype) {
        Map<String,Object> res = new HashMap<String,Object>();
        try{
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("orderno", orderno);
            Map<String,Object> order = orderdao.getOrder4OpTaxi(param);
            if(order==null){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("status", "无法获取到订单信息");
                return res;
            }
            String starttime = (String) order.get("starttime");
            if(StringUtils.isNotBlank(starttime)&&starttime.length()>19){
                starttime = starttime.substring(0, starttime.length()-2);
            }
            String endtime = (String) order.get("endtime");
            if(StringUtils.isNotBlank(endtime)&&endtime.length()>19){
                endtime = endtime.substring(0, endtime.length()-2);
            }
            res = carserviceapi.dealRequestWithToken("/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}", HttpMethod.GET, null, null, Map.class, orderno,ordertype,usetype);
        }catch(Exception e){
            logger.error("获取个人出租车订单轨迹出错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("status", "无法获取到轨迹信息");
        }
        return res;
    }

    /**
     * 获取服务信息
     * @param params
     * @return
     */
    public Map<String,Object> getServiceInfo(Map<String, Object> params) {
        Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        Map<String,Object> serviceinfo = new HashMap<String,Object>();
        res.put("serviceinfo", serviceinfo);
        String usertoken = (String) params.get("usertoken");
        String usetype = (String) params.get("usetype");
        if(!"2".equals(usetype)){
            //机构用户
            String companyid = (String) params.get("companyid");
            Map<String,Object> companyinfo = orderdao.getLeCompanyInfo(companyid);
            if(companyinfo!=null){
                serviceinfo.put("servicephone", companyinfo.get("servicesphone")==null?"":companyinfo.get("servicesphone"));
            }else{
                serviceinfo.put("servicephone","");
            }
        }else{
            //个人用户
            Map<String,Object> opinfo = orderdao.getOpInfo();
            if(opinfo!=null){
                serviceinfo.put("servicephone", opinfo.get("servcietel")==null?"":opinfo.get("servcietel"));
            }else{
                serviceinfo.put("servicephone","");
            }
        }
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        return res;
    }

    /**
     * 评价订单
     * @param params
     * @return
     */
    public Map<String,Object> doComment(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            String usertoken = (String) params.get("usertoken");
            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status", Retcode.FAILED.code);
                res.put("message", "参数不全");
                return res;
            }
            String driverid = null;
            if(!"2".equals(usetype)){
                //机构用户
                orderdao.doComment4Org(params);
                PassengerOrder order = orderdao.getOrderByOrderno4Org((String)params.get("orderno"));
                if(order!=null){
                    driverid = order.getDriverid();
                }
            }else{
                //个人用户
                if(isTaxiOrder(ordertype)){
                    //出租车订单
                    orderdao.doComment4OpTaxi(params);
                    Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(params);
                    if(optaxiorder!=null){
                        driverid = (String) optaxiorder.get("driverid");
                    }
                }else{
                    //网约车订单
                    orderdao.doComment4Op(params);
                    String orderno = (String) params.get("orderno");
                    PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
                    if(order!=null){
                        driverid = order.getDriverid();
                    }
                }
            }
            if(StringUtils.isNotBlank(driverid)){
                Object userrateobj = params.get("userrate");
                int userrate =  (userrateobj instanceof String)?Integer.parseInt((String)userrateobj):(int)userrateobj;
                Map<String,Object> driverrateinfo = dicdao.getDriverRateInfo(driverid);
                Object avgrateobj = driverrateinfo.get("avgrate");
                Object ordercountobj = driverrateinfo.get("ordercount");
                double avgrate = 4.5;
                int ordercount = 1;
                if(avgrateobj!=null){
                    avgrate = (double)avgrateobj;
                }
                if(ordercountobj!=null){
                    ordercount = (int)ordercountobj;
                }
                if(ordercount<=0){
                    ordercount = 1;
                }
                double newavgrate = (avgrate*(ordercount-1)+userrate)/ordercount;
                Map<String,Object> newrateparam = new HashMap<String,Object>();
                String newavgratestr = new java.text.DecimalFormat("#.000000").format(newavgrate);
                newrateparam.put("avgrate", newavgratestr);
                newrateparam.put("driverid", driverid);
                dicdao.updateDriverRate(newrateparam);
            }
        }catch(Exception e){
            logger.error("评价司机报错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }

        return res;
    }

    /**
     * 验证密码
     * @param params
     * @return
     */
    public Map<String, Object> validatePwd(Map<String, Object> params) {
        Map<String, Object> res = new HashMap<String, Object>();
        String usertoken = (String) params.get("usertoken");
        String account = Const.getUserInfo(usertoken).get("account");
        String pwd = (String) params.get("oldpwd");
        pwd = RSAUtil.RSADecode(pwd);
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        if(isOrgUser(usertoken)){
            //机构用户
            OrgUser  orguer= userdao.getUser4Org(account);
            if(!PasswordEncoder.matches(pwd, orguer.getUserPassword())){
                res.put("status", Retcode.PASSWORDWRONG.code);
                res.put("message", Retcode.PASSWORDWRONG.msg);
            }
        }else{
            //个人用户
            PeUser peuser = userdao.getUser4Op(account);
            if(!PasswordEncoder.matches(pwd, peuser.getUserpassword())){
                res.put("status", Retcode.PASSWORDWRONG.code);
                res.put("message", Retcode.PASSWORDWRONG.msg);
            }
        }
        return res;
    }
}
