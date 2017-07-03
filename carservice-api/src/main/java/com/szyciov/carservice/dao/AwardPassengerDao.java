package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.AwardPassengerMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhu on 2017/6/21.
 */
@Repository("AwardPassengerDao")
public class AwardPassengerDao {

    private AwardPassengerMapper mapper;

    @Resource
    public void setAwardPassengerMapper(AwardPassengerMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String,Object> getAwardOnOff() {
        return mapper.getAwardOnOff();
    }

    public Map<String,Object> getAwardPercent(Map<String, Object> awardpercentparams) {
        return mapper.getAwardPercent(awardpercentparams);
    }

    public Map<String,Object> getAwardCurrentTotal() {
        return mapper.getAwardCurrentTotal();
    }

    public Map<String,Object> getAwardTotal() {
        return mapper.getAwardTotal();
    }

    public void addAwardPoint(Map<String, Object> awardparams) {
        mapper.addAwardPoint(awardparams);
    }

    public void updateCurrentAward(Map<String, Object> updatecurrentaward) {
        mapper.updateCurrentAward(updatecurrentaward);
    }

    public Map<String,Object> getPeUserInfo(String phone) {
        return mapper.getPeUserInfo(phone);
    }

    public Map<String,Object> getPeUserAccount(Map<String, Object> peaccountparams) {
        return mapper.getPeUserAccount(peaccountparams);
    }

    public void addPeUser(Map<String, Object> addperuserparams) {
        mapper.addPeUser(addperuserparams);
    }

    public void createPeUserAccount(Map<String, Object> peaccountparams) {
        mapper.createPeUserAccount(peaccountparams);
    }

    public void addExpenses4User(Map<String, Object> awardparams) {
        mapper.addExpenses4User(awardparams);
    }

    public void stopAward() {
        mapper.stopAward();
    }
}
