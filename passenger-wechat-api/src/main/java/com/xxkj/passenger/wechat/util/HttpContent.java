package com.xxkj.passenger.wechat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 林志伟 on 2017/7/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpContent {
    private Integer status;
    private String Content;
}
