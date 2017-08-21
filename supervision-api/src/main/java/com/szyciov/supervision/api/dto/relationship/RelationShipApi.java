package com.szyciov.supervision.api.dto.relationship;


import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;

/**
 * 人车对应关系信息
 * Created by 林志伟 on 2017/7/7.
 */

public class RelationShipApi extends BaseApi {
    public RelationShipApi() {
        super();
        setApiType(InterfaceType.RELATIONSHIP);
    }
}
