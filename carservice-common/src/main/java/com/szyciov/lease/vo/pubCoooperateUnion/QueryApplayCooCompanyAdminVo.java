package com.szyciov.lease.vo.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/17.
 */
public class QueryApplayCooCompanyAdminVo {
    private String leaseCompany; // 公司全称
    private String phone; // 手机号码
    private String adminId; // 管理员ID

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLeaseCompany() {
        return leaseCompany;
    }

    public void setLeaseCompany(String leaseCompany) {
        this.leaseCompany = leaseCompany;
    }
}
