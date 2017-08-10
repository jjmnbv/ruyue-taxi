package com.szyciov.supervision.token.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by admin on 2017/7/6.
 */
@Data
@AllArgsConstructor
public class TokenRequest {
    private String platform;
    private String key;
}
