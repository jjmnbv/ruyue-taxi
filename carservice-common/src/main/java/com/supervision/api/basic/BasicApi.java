package com.supervision.api.basic;


import com.supervision.api.BaseApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基础数据api
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class BasicApi extends BaseApi {



    //    唯一标识
    private String symbol;

    //    状态
    private Integer state;
//  操作标识
    private Integer flag;
//  更新时间
    private String updateTime;


}
