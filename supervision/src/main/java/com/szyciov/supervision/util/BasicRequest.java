package com.szyciov.supervision.util;

import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;
import com.szyciov.supervision.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by admin on 2017/7/6.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicRequest {
    private String result;
    private InterfaceType interfaceType;
    private CommandEnum command;
    private RequestType requestType;
    private String token;
}
