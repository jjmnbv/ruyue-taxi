package api;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 单个api相应结果
 * Created by 林志伟 on 2017/7/6.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiResult {
    @JsonIgnore
    private int success;
    private String reason;


}
