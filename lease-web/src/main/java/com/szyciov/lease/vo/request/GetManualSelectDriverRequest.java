package com.szyciov.lease.vo.request;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/5/19.
 */
public class GetManualSelectDriverRequest extends PagingRequest {
    private String keyword;
    private String phone; // 司机手机号
    private String jobNum; // 资格证号
    private String plateNo; // 车牌号

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
