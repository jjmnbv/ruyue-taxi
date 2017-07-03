package com.szyciov.passenger.dao;

import com.szyciov.entity.PubMostcontact;
import com.szyciov.passenger.mapper.PubMostcontactMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/6/12.
 */
@Repository("PubMostcontactDao")
public class PubMostcontactDao {

    @Resource
    private PubMostcontactMapper mapper;

    public PubMostcontact getPubMostcontactExist(PubMostcontact object) {
        return mapper.getPubMostcontactExist(object);
    }

    public void insertPubMostcontact(PubMostcontact object) {
        mapper.insertPubMostcontact(object);
    }

    public void deletePubMostcontactById(String contactid) {
        mapper.deletePubMostcontactById(contactid);
    }

    public List<Map<String, Object>> getPubMostcontactByUserphone(String userphone) {
        return mapper.getPubMostcontactByUserphone(userphone);
    }

}
