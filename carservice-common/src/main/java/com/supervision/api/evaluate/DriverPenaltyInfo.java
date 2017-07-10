package com.supervision.api.evaluate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3.6.3	驾驶员处罚信息（JSYCF）
 * Created by 林志伟 on 2017/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverPenaltyInfo extends EvaluateApi {


    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 处罚时间	YYYYMMDDHHMMSS
     */
    private String punishTime;
    /**
     * 处罚原因
     */
    private String punishReason;
    /**
     *处罚结果
     */
    private String punishResult;
}
