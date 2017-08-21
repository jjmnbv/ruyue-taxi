package com.szyciov.supervision.api.dto.evaluate;


import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;

/**
 * Created by 林志伟 on 2017/7/7.
 */

public class EvaluateApi  extends BaseApi {
    public EvaluateApi() {
        super();
        setApiType(InterfaceType.EVALUATE);
    }

}
