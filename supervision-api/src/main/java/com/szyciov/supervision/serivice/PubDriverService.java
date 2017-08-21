package com.szyciov.supervision.serivice;

import com.szyciov.supervision.api.dto.order.DriverOffWork;
import com.szyciov.supervision.api.dto.order.DriverOnWork;

import java.util.Map;

/**
 * Created by lzw on 2017/8/18.
 */
public interface PubDriverService {
    DriverOnWork onWork(Map<String,String> map);

    DriverOffWork offWork(Map<String, String> map);
}
