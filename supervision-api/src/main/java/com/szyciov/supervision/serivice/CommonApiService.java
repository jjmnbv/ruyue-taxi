package com.szyciov.supervision.serivice;

import com.supervision.enums.CommandEnum;
import com.szyciov.supervision.api.dto.BaseApi;

import java.util.Map;

/**
 * Created by lzw on 2017/8/21.
 */
public interface CommonApiService {
    /**
     * 执行命令，根据不同的命令调用不同的实现
     * @param commandEnum
     * @param map
     * @return
     */
    BaseApi execute(CommandEnum commandEnum,Map<String,String> map);
}
