package com.szyciov.supervision.service;


import com.szyciov.supervision.api.dto.evaluate.DriverCreditInfo;
import com.szyciov.supervision.api.dto.evaluate.DriverPenaltyInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerComplaintInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerEvaluationInformation;

import java.util.Map;

/**
 * 服务质量
 * Created by lzw on 2017/8/21.
 */
public interface EvaluteService extends CommonApiService {
    /**
     * 乘客评价信息
     * @param map
     * @return
     */
    PassengerEvaluationInformation passengerEvaluationInformation(Map<String,String> map);

    /**
     * 乘客投诉信息
     * @param map
     * @return
     */
    PassengerComplaintInfo passengerComplaintInfo(Map<String,String> map);

    /**
     * 驾驶员处罚信息
     * @param map
     * @return
     */
    DriverPenaltyInfo driverPenaltyInfo(Map<String,String> map);

    /**
     * 驾驶员信誉信息
     * @param map
     * @return
     */
    DriverCreditInfo driverCreditInfo(Map<String,String> map);

}
