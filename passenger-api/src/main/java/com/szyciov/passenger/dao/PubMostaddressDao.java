package com.szyciov.passenger.dao;

import com.szyciov.entity.PubMostaddress;
import com.szyciov.passenger.mapper.PubMostaddressMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/6/13.
 */
@Repository("PubMostaddressDao")
public class PubMostaddressDao {

    @Resource
    private PubMostaddressMapper mapper;

    public PubMostaddress getPubMostaddressByUserphone(PubMostaddress object) {
        return mapper.getPubMostaddressByUserphone(object);
    }

    public void insertPubMostaddress(PubMostaddress object) {
        mapper.insertPubMostaddress(object);
    }

    public void updatePubMostaddressById(PubMostaddress object) {
        mapper.updatePubMostaddressById(object);
    }

    public void deletePubMostaddressById(String addressid) {
        mapper.deletePubMostaddressById(addressid);
    }

    public List<Map<String, Object>> getPubMostaddressListByUserphone(String userphone) {
        return mapper.getPubMostaddressListByUserphone(userphone);
    }

}
