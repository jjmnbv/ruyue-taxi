package com.szyciov.supervision.api.evaluate;


import com.szyciov.supervision.api.BaseApi;
import com.szyciov.supervision.enums.InterfaceType;

import lombok.Data;

/**
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class EvaluateApi  extends BaseApi {
    public EvaluateApi() {
        super();
        setApiType(InterfaceType.EVALUATE);
    }

}
