package com.szyciov.supervision.service.impl;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;
import com.szyciov.supervision.api.dto.evaluate.DriverCreditInfo;
import com.szyciov.supervision.api.dto.evaluate.DriverPenaltyInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerComplaintInfo;
import com.szyciov.supervision.api.dto.evaluate.PassengerEvaluationInformation;
import com.szyciov.supervision.mapper.EvaluteMapper;
import com.szyciov.supervision.service.EvaluteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 服务质量信息数据
 * Created by lzw on 2017/8/21.
 */
@Service("evaluteService")
public class EvalueServiceImpl implements EvaluteService {
    @Autowired
    private EvaluteMapper evaluteMapper;
    @Override
    public BaseApi execute(CommandEnum commandEnum, Map<String, String> map) {
        switch (commandEnum){
            case PassengerEvaluationInformation:
                return  this.passengerEvaluationInformation(map);
            case PassengerComplaintInfo:
                return this.passengerComplaintInfo(map);
            case DriverPenaltyInfo:
                return  this.driverPenaltyInfo(map);
            case DriverCreditInfo:
                return  this.driverCreditInfo(map);
        }
        return null;
    }

    /**
     * 乘客评价信息
     * @param map
     * @return
     */
    @Override
    public PassengerEvaluationInformation passengerEvaluationInformation(Map<String, String> map) {
        return null;
    }

    /**
     * 乘客投诉信息
     * @param map
     * @return
     */

    @Override
    public PassengerComplaintInfo passengerComplaintInfo(Map<String, String> map) {
        return null;
    }

    /**
     * 驾驶员处罚信息
     * @param map
     * @return
     */
    @Override
    public DriverPenaltyInfo driverPenaltyInfo(Map<String, String> map) {
        return null;
    }

    /**
     * 驾驶员信誉信息
     * @param map
     * @return
     */
    @Override
    public DriverCreditInfo driverCreditInfo(Map<String, String> map) {
        return null;
    }
}
