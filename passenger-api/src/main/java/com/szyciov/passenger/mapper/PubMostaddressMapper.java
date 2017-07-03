package com.szyciov.passenger.mapper;

import com.szyciov.entity.PubMostaddress;

import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/6/13.
 */
public interface PubMostaddressMapper {

    PubMostaddress getPubMostaddressByUserphone(PubMostaddress object);

    void insertPubMostaddress(PubMostaddress object);

    void updatePubMostaddressById(PubMostaddress object);

    void deletePubMostaddressById(String addressid);

    List<Map<String, Object>> getPubMostaddressListByUserphone(String userphone);

}
