package com.szyciov.carservice.mapper;

import com.szyciov.entity.PubPhoneAuthentication;
import com.szyciov.param.PhoneAuthenticationParam;


public interface PhoneAuthenticationMapper {

    int save(PubPhoneAuthentication authentication);

    int getAuthenticationCount(PhoneAuthenticationParam param);

}