package com.szyciov.op.dto.request;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/5/24.
 */
public class GetTaxiManualDriverRequest extends PagingRequest {
    private String name; // 司机姓名
    private String phone; // 司机手机号
    private String token; // 当前用户token
    private String jobNum; // 资格证号
    private String plateNo; // 车牌号
    private String leaseCompanyId; // 所属车企
    private String keyword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getLeaseCompanyId() {
        return leaseCompanyId;
    }

    public void setLeaseCompanyId(String leaseCompanyId) {
        this.leaseCompanyId = leaseCompanyId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
