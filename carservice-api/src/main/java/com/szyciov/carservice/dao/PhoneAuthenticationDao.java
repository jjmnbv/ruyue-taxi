package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.PhoneAuthenticationMapper;
import com.szyciov.entity.PubPhoneAuthentication;
import com.szyciov.param.PhoneAuthenticationParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneAuthenticationDao {

    @Autowired
    private PhoneAuthenticationMapper mapper;


    public int save(PubPhoneAuthentication authentication){
        return mapper.save(authentication);
    }


    public int getAuthenticationCount(PhoneAuthenticationParam param){
        return mapper.getAuthenticationCount(param);
    }

}