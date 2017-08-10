package com.szyciov.coupon.param;

/**
 * 抵用券规则查询对象
 *
 * @author LC
 * @date 2017/7/27
 */
public class PubCouponRuleQueryParam {

    /**
     * 优惠券类型
     * 1-注册，2充值，3消费，4活动
     */
    private String type;

    /**
     * 用户类型
     * 1-机构客户，2-机构用户，3个人用户
     */
    private String usertype;

    /**
     * 租赁公司id/运管id
     */
    private String companyid;

    /**
     * 数据状态
     */
    private String status;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 