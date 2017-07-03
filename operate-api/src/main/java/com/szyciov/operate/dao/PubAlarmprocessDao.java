package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.lease.entity.PubRoleId;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.operate.mapper.PubAlarmprocessMapper;

@Repository("PubAlarmprocessDao")
public class PubAlarmprocessDao {
	 public PubAlarmprocessDao(){
		}
	     private PubAlarmprocessMapper mapper;
	     @Resource
		 	public void setMapper(PubAlarmprocessMapper mapper) {
		 		this.mapper = mapper;
		 	}
	 public List<PubAlarmprocessParam> getPubAlarmprocessByQueryList(PubAlarmprocessParam pubAlarmprocessParam){
		 return mapper.getPubAlarmprocessByQueryList(pubAlarmprocessParam);
	 }
	 public int getPubAlarmprocessByQueryCount(PubAlarmprocessParam pubAlarmprocessParam){
		 return mapper.getPubAlarmprocessByQueryCount(pubAlarmprocessParam);
	 }
	 public PubAlarmprocessParam getPubAlarmprocessDetail(String id){
		 return mapper.getPubAlarmprocessDetail(id);
	 }
	 public int updataDetail(PubAlarmprocessParam pubAlarmprocessParam)  {
			return mapper.updataDetail(pubAlarmprocessParam);
		}
	 public List<Map<String, Object>> getPubAlarmprocessDriver(PubAlarmprocessParam pubAlarmprocessParam)  {
			return mapper.getPubAlarmprocessDriver(pubAlarmprocessParam);
		}
	 public List<Map<String, Object>> getPubAlarmprocessPassenger(PubAlarmprocessParam pubAlarmprocessParam)  {
			return mapper.getPubAlarmprocessPassenger(pubAlarmprocessParam);
		}
	 public List<Map<String, Object>> getPubAlarmprocessPlateno(PubAlarmprocessParam pubAlarmprocessParam)  {
			return mapper.getPubAlarmprocessPlateno(pubAlarmprocessParam);
		}
	 public int ordernoOK(PubRoleId pubRoleId)  {
			return mapper.ordernoOK(pubRoleId);
		}
	 public int carOrder(PubRoleId pubRoleId)  {
			return mapper.carOrder(pubRoleId);
		}
	 public int taxiOrder(PubRoleId pubRoleId)  {
			return mapper.taxiOrder(pubRoleId);
		}
	 public int handleOK(PubAlarmprocessParam pubAlarmprocessParam)  {
		    return mapper.handleOK(pubAlarmprocessParam);
	 }
	 public void save(PubAlarmprocessSaveDto pubAlarmprocessParam)  {
	     mapper.save(pubAlarmprocessParam);
 }

}
