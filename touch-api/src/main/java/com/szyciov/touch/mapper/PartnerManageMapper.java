package com.szyciov.touch.mapper;

import com.szyciov.touch.dto.OrderSimplesResultDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/5/10.
 */
public interface PartnerManageMapper {


    List<Map<String,Object>> getUnPaidOrders(Map<String, Object> params);
}
