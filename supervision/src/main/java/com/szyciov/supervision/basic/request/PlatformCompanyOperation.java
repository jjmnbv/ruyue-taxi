package com.szyciov.supervision.basic.request;

import com.szyciov.supervision.util.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2017/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformCompanyOperation extends BaseEntity{
    private String symbol;
    private String companyId;
    private Integer vehicleNum;
    private Integer driverNum;
    private Integer flag;
    private Long updateTime;
}
