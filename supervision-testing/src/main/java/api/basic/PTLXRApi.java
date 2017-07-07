package api.basic;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.17	网约车平台联系人信息*(PTLXR)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class PTLXRApi extends BasicApi {
    public PTLXRApi(){
        super();
        setCommand("PTLXR");
    }
//    所属平台公司统一社会信用代码
    private String epCode;
//    联系人姓名
    private String contact;
//  联系人电话
    private String contactWay;

    @JsonIgnore
    private Integer state;


}
