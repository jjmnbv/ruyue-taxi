package com.supervision.api.operation;


import com.supervision.api.BaseApi;
import com.supervision.enums.InterfaceType;
import lombok.Data;

/**
 * 营运数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OperationApi extends BaseApi {
    public OperationApi() {
        super();
        setApiType(InterfaceType.OPERATION);
    }

}
