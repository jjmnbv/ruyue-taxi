package api.relationship;

import api.BaseApi;
import lombok.Data;

/**
 * 人车对应关系信息
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class RelationshipApi extends BaseApi {
    public RelationshipApi() {
        super();
        setApiType("RELATIONSHIP");
    }
}
