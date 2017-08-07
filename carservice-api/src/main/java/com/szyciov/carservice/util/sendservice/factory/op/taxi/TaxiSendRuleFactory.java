package com.szyciov.carservice.util.sendservice.factory.op.taxi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.ForceSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.GrabSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.GrabSingleSendRuleImp;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.sendservice.factory.SendRuleFactory;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.entity.AbstractOrder;

/**
 * 出租车派单规则类
 * @author zhu
 *
 */
@Service("TaxiSendRuleFactory")
public class TaxiSendRuleFactory extends SendRuleFactory{
	
	private SendInfoService sendInfoService;

	@Resource
	public void setSendInfoService(SendInfoService sendInfoService) {
		this.sendInfoService = sendInfoService;
	}

	/**
	 * 根据订单信息获取派单规则
	 */
	@Override
	public SendRuleHelper createSendRule(AbstractOrder orderinfo) {
		String city = orderinfo.getOncity();
		Date usetime = orderinfo.getUsetime();
		if(StringUtils.isBlank(city)||usetime==null){
			return null;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("city", city);
		param.put("usetime", usetime);
		Map<String,Object> reserveruleinfo = sendInfoService.getReserveRuleInfo4OpTaxi(param);
		if(reserveruleinfo==null){
			//当前城市没有预约用车的派单规则，所有下单都是即可单
			Map<String,Object> usenowruleinfo = sendInfoService.getUseNowRuleInfo4OpTaxi(param);
			if(usenowruleinfo==null){
				return null;
			}
			return convert2Imp(usenowruleinfo);
		}
		int carsinterval = ParseInt(reserveruleinfo.get("carsinterval"));
		Date currentobj = new Date();
		Date temptime = new Date(currentobj.getTime() + carsinterval * 60 * 1000);
		if(usetime.after(temptime)){
			//用车时间大,预约用车
			return convert2Imp(reserveruleinfo);
		}else{
			Map<String,Object> usenowruleinfo = sendInfoService.getUseNowRuleInfo4OpTaxi(param);
			if(usenowruleinfo==null){
				return convert2Imp(reserveruleinfo);
			}
			return convert2Imp(usenowruleinfo);
		}
	}

	/**
	 * 转换int
	 * @param object
	 * @return
	 */
	private int ParseInt(Object object) {
		if(object==null){
			return 0;
		}
		return Integer.parseInt(String.valueOf(object));
	}

	private SendRuleHelper convert2Imp(Map<String, Object> ruleinfo) {
		String sendtype = (String) ruleinfo.get("sendtype");
		SendRuleHelper sendrule = null;
		if(SendRuleHelper.SEND_TYPE_FORCE.equalsIgnoreCase(sendtype)){
			//强派
			sendrule = convert2ForceSendRuleImp(ruleinfo);
		}else if(SendRuleHelper.SEND_TYPE_GRAB.equalsIgnoreCase(sendtype)){
			//抢派
			sendrule = convert2GrabSendRuleImp(ruleinfo);
		}else if(SendRuleHelper.SEND_TYPE_GRABSINGLE.equalsIgnoreCase(sendtype)){
			//抢单
			sendrule = GrabSingleSendRuleImp(ruleinfo);
		}
		return sendrule;
	}

	private SendRuleHelper convert2ForceSendRuleImp(Map<String, Object> ruleinfo) {
		ForceSendRuleImp sendrule = new ForceSendRuleImp();
		sendrule.setCarsinterval(parseInt(ruleinfo.get("carsinterval")));
		sendrule.setCity((String) ruleinfo.get("city"));
		sendrule.setCitycaption((String) ruleinfo.get("citycaption"));
		sendrule.setShortname((String) ruleinfo.get("shortname"));
		sendrule.setIncreratio(parseDouble(ruleinfo.get("increratio")));
		sendrule.setInitsendradius(parseDouble(ruleinfo.get("initsendradius")));
		sendrule.setMaxsendradius(parseDouble(ruleinfo.get("maxsendradius")));
		sendrule.setPersonsendinterval(parseInt(ruleinfo.get("personsendinterval")));
		sendrule.setPushlimit((String) ruleinfo.get("pushlimit"));
		sendrule.setPushnum(parseInt(ruleinfo.get("pushnum")));
		sendrule.setPushnumlimit((String) ruleinfo.get("pushnumlimit"));
		sendrule.setSendmodel((String) ruleinfo.get("sendmodel"));
		sendrule.setSystemsendinterval(parseInt(ruleinfo.get("systemsendinterval")));
		sendrule.setUsetype((String) ruleinfo.get("usetype"));
		return sendrule;
	}

	private SendRuleHelper convert2GrabSendRuleImp(Map<String, Object> ruleinfo) {
		GrabSendRuleImp sendrule = new GrabSendRuleImp();
		sendrule.setCarsinterval(parseInt(ruleinfo.get("carsinterval")));
		sendrule.setCity((String) ruleinfo.get("city"));
		sendrule.setCitycaption((String) ruleinfo.get("citycaption"));
		sendrule.setDriversendinterval(parseInt(ruleinfo.get("driversendinterval")));
		sendrule.setIncreratio(parseDouble(ruleinfo.get("increratio")));
		sendrule.setInitsendradius(parseDouble(ruleinfo.get("initsendradius")));
		sendrule.setMaxsendradius(parseDouble(ruleinfo.get("maxsendradius")));
		sendrule.setPersonsendinterval(parseInt(ruleinfo.get("personsendinterval")));
		sendrule.setPushlimit((String) ruleinfo.get("pushlimit"));
		sendrule.setPushnum(parseInt(ruleinfo.get("pushnum")));
		sendrule.setPushnumlimit((String) ruleinfo.get("pushnumlimit"));
		sendrule.setSendmodel((String) ruleinfo.get("sendmodel"));
		sendrule.setShortname((String) ruleinfo.get("shortname"));
		sendrule.setSystemsendinterval(parseInt(ruleinfo.get("systemsendinterval")));
		sendrule.setUsetype((String) ruleinfo.get("usetype"));
		return sendrule;
	}

	private SendRuleHelper GrabSingleSendRuleImp(Map<String, Object> ruleinfo) {
		GrabSingleSendRuleImp sendrule = new GrabSingleSendRuleImp();
		sendrule.setCarsinterval(parseInt(ruleinfo.get("carsinterval")));
		sendrule.setCity((String) ruleinfo.get("city"));
		sendrule.setCitycaption((String) ruleinfo.get("citycaption"));
		sendrule.setDriversendinterval(parseInt(ruleinfo.get("driversendinterval")));
		sendrule.setIncreratio(parseDouble(ruleinfo.get("increratio")));
		sendrule.setInitsendradius(parseDouble(ruleinfo.get("initsendradius")));
		sendrule.setMaxsendradius(parseDouble(ruleinfo.get("maxsendradius")));
		sendrule.setPersonsendinterval(parseInt(ruleinfo.get("personsendinterval")));
		sendrule.setPushlimit((String) ruleinfo.get("pushlimit"));
		sendrule.setPushnum(parseInt(ruleinfo.get("pushnum")));
		sendrule.setPushnumlimit((String) ruleinfo.get("pushnumlimit"));
		sendrule.setSendmodel((String) ruleinfo.get("sendmodel"));
		sendrule.setShortname((String) ruleinfo.get("shortname"));
		sendrule.setSystemsendinterval(parseInt(ruleinfo.get("systemsendinterval")));
		sendrule.setUsetype((String) ruleinfo.get("usetype"));
		return sendrule;
	}
	
	private int parseInt(Object value){
		int newvalue = 0;
		if(value==null){
			return newvalue;
		}
		try{
			newvalue = Integer.parseInt(String.valueOf(value));
		}catch(Exception e){
		}
		return newvalue;
	}
	
	private double parseDouble(Object value){
		double newvalue = 0d;
		if(value==null){
			return newvalue;
		}
		try{
			newvalue = Double.parseDouble(String.valueOf(value));
		}catch(Exception e){
		}
		return newvalue;
	}
}
