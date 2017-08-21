package com.szyciov.supervision.api.dto.operation;


import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;

/**
 * 营运数据
 * Created by 林志伟 on 2017/7/7.
 */

public class OperationApi extends BaseApi {
    public OperationApi() {
        super();
        setApiType(InterfaceType.OPERATION);
    }

}
