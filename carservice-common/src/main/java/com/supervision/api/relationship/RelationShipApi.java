package com.supervision.api.relationship;


import com.supervision.api.BaseApi;
import com.supervision.enums.InterfaceType;
import lombok.Data;

/**
 * 人车对应关系信息
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class RelationShipApi extends BaseApi {
    public RelationShipApi() {
        super();
        setApiType(InterfaceType.RELATIONSHIP);
    }
}
