package api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义api相关参数数据
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class BaseApi implements Serializable {
    //  api 类型,不转换成json
    @JsonIgnore
    private String apiType;
    //  api 命令
    @JsonIgnore
    private String command;

    //    公司标识，与交通部一致,平台标识。
    private String companyId;


}

