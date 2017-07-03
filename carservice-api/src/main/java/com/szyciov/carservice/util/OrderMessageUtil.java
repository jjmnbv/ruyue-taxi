package com.szyciov.carservice.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.szyciov.entity.AbstractOrder;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;

public class OrderMessageUtil {
	
	private OrderMessageUtil() {}
	
	private static Map<String, OrgOrder> orgOrderMap;
	
	private static Map<String, OpOrder> opOrderMap;
	
	private static Map<String, AbstractOrder> unRemindOrderMap;
	
	
	private static Map<String, OrgOrder> beArtificialOrgOrderMap;
	
	private static Map<String, OpOrder> beArtificialOpOrderMap;
	
	public synchronized static Map<String, OrgOrder> getOrgInstance() {
		if(orgOrderMap == null) {
			orgOrderMap = new ConcurrentHashMap<String, OrgOrder>();
		}
		
		return orgOrderMap;
	}
	
	public synchronized static Map<String, OpOrder> getOpInstance() {
		if(opOrderMap == null) {
			opOrderMap = new ConcurrentHashMap<String, OpOrder>();
		}
		
		return opOrderMap;
	}
	
	public synchronized static Map<String, AbstractOrder> getUnRemindOrderInstance() {
		if(unRemindOrderMap == null) {
			unRemindOrderMap = new ConcurrentHashMap<String, AbstractOrder>();
		}
		
		return unRemindOrderMap;
	}
	
	
	public synchronized static Map<String, OrgOrder> getbeArtificialOrgOrderInstance() {
		if(beArtificialOrgOrderMap == null) {
			beArtificialOrgOrderMap = new ConcurrentHashMap<String, OrgOrder>();
		}
		
		return beArtificialOrgOrderMap;
	}
	
	public synchronized static Map<String, OpOrder> getbeArtificialOpOrderInstance() {
		if(beArtificialOpOrderMap == null) {
			beArtificialOpOrderMap = new ConcurrentHashMap<String, OpOrder>();
		}
		
		return beArtificialOpOrderMap;
	}
}
