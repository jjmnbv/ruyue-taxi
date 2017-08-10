package api.operation;

import api.BaseApi;
import lombok.Data;

/**
 * 营运数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OperationApi extends BaseApi {
    public OperationApi() {
        super();
        setCommand("OPERATION");
    }

}
