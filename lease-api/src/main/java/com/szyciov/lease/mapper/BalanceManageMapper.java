package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.lease.dto.balance.BalanceDto;
import com.szyciov.lease.param.BalanceManageQueryParam;
import com.szyciov.op.param.TourFeeManagementQueryParam;

public interface BalanceManageMapper {

    List<BalanceDto> queryBalanceList(BalanceManageQueryParam queryParam);
	
	int queryBalanceListCount(BalanceManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrderNo(@Param("orderno") String orderno,@Param("lecompanyid") String lecompanyid);
	
	List<Map<String, Object>> getDriverByNameOrPhone(@Param("driver") String driver,@Param("lecompanyid") String lecompanyid);
	
	List<Map<String, Object>> getCompanyNameById(String lecompanyid);
	
	List<Map<String, Object>> getJobNum(@Param("jobnum") String jobnum,@Param("lecompanyid") String lecompanyid);
	
	List<BalanceDto> getBalanceListExport(BalanceManageQueryParam queryParam);

	List<Map<String, Object>> getFullPlateno(@Param("fullplateno") String fullplateno,@Param("lecompanyid") String lecompanyid);
}
