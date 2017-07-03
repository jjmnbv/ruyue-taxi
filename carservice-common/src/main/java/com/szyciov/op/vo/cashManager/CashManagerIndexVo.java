package com.szyciov.op.vo.cashManager;

import com.szyciov.enums.WithdrawEnum;
import com.szyciov.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 提现管理首页
 * Created by LC on 2017/3/30.
 */
public class CashManagerIndexVo {

    //提现申请ID
    private String id;
    //申请人ID
    private String userid;
    //申请人类型
    private String usertype;
    //提现金额
    private Double amount;
    //银行账号
    private String creditcardnum;
    //账户名称
    private String creditcardname;
    //开户银行
    private String bankname;
    //申请时间
    private Date applytime;
    //申请时间
    private String applytimeStr;
    //司机账户名
    private String driverLogin;
    //乘客账户名
    private String userLogin;
    //显示账户名
    private String login;
    //处理结果
    private String processresult;
    //处理原因
    private String processreason;
    //处理时间
    private Date processtime;
    //处理时间str
    private String processtimeStr;
    //处理人姓名
    private String processusername;
    //序号
    private String rownum;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsertype() {
        if(StringUtils.isNotEmpty(this.usertype)){
            return WithdrawEnum.getTypeMsg(this.usertype);
        }
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCreditcardnum() {
        return creditcardnum;
    }

    public void setCreditcardnum(String creditcardnum) {
        this.creditcardnum = creditcardnum;
    }

    public String getCreditcardname() {
        return creditcardname;
    }

    public void setCreditcardname(String creditcardname) {
        this.creditcardname = creditcardname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Date getApplytime() {
        return applytime;
    }

    public void setApplytime(Date applytime) {
        this.applytime = applytime;
    }

    public String getDriverLogin() {
        return driverLogin;
    }

    public void setDriverLogin(String driverLogin) {
        this.driverLogin = driverLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getLogin() {
        if(WithdrawEnum.USER_TYPE_DRIVER.code.equals(this.usertype)){
            return driverLogin;
        }else{
            return userLogin;
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessresult() {
        if(StringUtils.isNotEmpty(this.processresult)){
            return WithdrawEnum.getResultMsg(this.processresult);
        }
        return processresult;
    }

    public void setProcessresult(String processresult) {
        this.processresult = processresult;
    }

    public String getProcessreason() {
        if(StringUtils.isEmpty(this.processreason)){
            return "/";
        }
        return processreason;
    }

    public void setProcessreason(String processreason) {
        this.processreason = processreason;
    }

    public String getProcessusername() {
        return processusername;
    }

    public void setProcessusername(String processusername) {
        this.processusername = processusername;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    public String getProcesstimeStr() {
        if(this.getProcesstime()!=null){
            return DateUtil.format(this.getProcesstime());
        }
        return processtimeStr;
    }

    public void setProcesstimeStr(String processtimeStr) {
        this.processtimeStr = processtimeStr;
    }

    public String getApplytimeStr() {
        if(this.getApplytime()!=null){
            return DateUtil.format(this.getApplytime());
        }
        return applytimeStr;
    }

    public void setApplytimeStr(String applytimeStr) {
        this.applytimeStr = applytimeStr;
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(java.sql.Date processtime) {
        this.processtime = processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }
}
 