package com.szyciov.carservice.mapper;

import java.util.Map;

/**
 * Created by zhu on 2017/6/21.
 */
public interface AwardPassengerMapper {

    Map<String,Object> getAwardOnOff();

    Map<String,Object> getAwardPercent(Map<String, Object> awardpercentparams);

    Map<String,Object> getAwardCurrentTotal();

    Map<String,Object> getAwardTotal();

    void addAwardPoint(Map<String, Object> awardparams);

    void updateCurrentAward(Map<String, Object> updatecurrentaward);

    Map<String,Object> getPeUserInfo(String phone);

    Map<String,Object> getPeUserAccount(Map<String, Object> peaccountparams);

    void addPeUser(Map<String, Object> addperuserparams);

    void createPeUserAccount(Map<String, Object> peaccountparams);

    void addExpenses4User(Map<String, Object> awardparams);

    void stopAward();
}
