package com.szyciov.touch.dao;

import com.szyciov.touch.dto.OrderSimplesResultDTO;
import com.szyciov.touch.mapper.PartnerManageMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/5/10.
 */

@Component("PartnerManageDao")
public class PartnerManageDao {

    private PartnerManageMapper partnerManageMapper;
    @Resource
    public void setPartnerManageMapper(PartnerManageMapper partnerManageMapper) {
        this.partnerManageMapper = partnerManageMapper;
    }

    public List<Map<String,Object>> getUnPaidOrders(Map<String, Object> params) {
        return this.partnerManageMapper.getUnPaidOrders(params);
    }
}
