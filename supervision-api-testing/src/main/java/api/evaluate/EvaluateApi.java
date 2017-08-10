package api.evaluate;

import api.BaseApi;
import lombok.Data;

/**
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class EvaluateApi  extends BaseApi{
    public EvaluateApi() {
        super();
        setApiType("EVALUATE");
    }

}
