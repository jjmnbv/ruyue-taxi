package com.szyciov.carservice.util.sendservice.factory.le;

import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.sendservice.factory.SendRuleFactory;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarForceSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarGrabSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarGrabSingleSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarSystemSingleSendRuleImp;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubSendrules;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.org.entity.OrgOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 租赁端网约车派单规则类
 * @author zhu
 *
 */
@Service("LeCarSendRuleFactory")
public class LeCarSendRuleFactory extends SendRuleFactory{
	
	private SendInfoService sendInfoService;

	@Resource
	public void setSendInfoService(SendInfoService sendInfoService) {
		this.sendInfoService = sendInfoService;
	}

	/**
	 * 根据订单信息获取派单规则
	 */
	public SendRuleHelper createSendRule(AbstractOrder orderinfo) {
		PubSendrules rule = new PubSendrules();
		rule.setLeasescompanyid(orderinfo.getCompanyid());
		rule.setCity(orderinfo.getOncity());
		rule.setUsetype(orderinfo.getUsetype());
		rule.setVehicletype(ParseInt(DriverEnum.DRIVER_TYPE_CAR.code));
        if(orderinfo instanceof OrgOrder) {
            rule.setPlatformtype(PlatformTypeByDb.LEASE.code);
        } else {
            rule.setPlatformtype(PlatformTypeByDb.OPERATING.code);
        }
		PubSendrules sendrule = null;
		if(orderinfo.isIsusenow()){
			sendrule = sendInfoService.getUseNowRuleInfo4LeCar(rule);
		}else{
			sendrule = sendInfoService.getReserveRuleInfo4LeCar(rule);
		}
		//如果没有用车规则,返回失败
		if(sendrule ==null) return null;
		return convert2Imp(sendrule);
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

	private SendRuleHelper convert2Imp(PubSendrules ruleinfo) {
		String sendtype = ruleinfo.getSendtype() + "";
		SendRuleHelper sendrule = null;
		if(SendRuleHelper.SEND_TYPE_FORCE.equalsIgnoreCase(sendtype)){
			//强派
			sendrule = convert2ForceSendRuleImp(ruleinfo);
		}else if(SendRuleHelper.SEND_TYPE_GRAB.equalsIgnoreCase(sendtype)){
			//抢派
			sendrule = convert2GrabSendRuleImp(ruleinfo);
		}else if(SendRuleHelper.SEND_TYPE_GRABSINGLE.equalsIgnoreCase(sendtype)){
			//抢单
			sendrule = convert2GrabSingleSendRuleImp(ruleinfo);
		}else if(SendRuleHelper.SEND_TYPE_SYSTEMSINGLE.equalsIgnoreCase(sendtype)){
			//抢单
			sendrule = convert2SystemSingleSendRuleImp(ruleinfo);
		}
		return sendrule;
	}

	private SendRuleHelper convert2ForceSendRuleImp(PubSendrules ruleinfo) {
		LeCarForceSendRuleImp sendrule = new LeCarForceSendRuleImp();
		sendrule.setCarsinterval(ruleinfo.getCarsinterval());
		sendrule.setCity(ruleinfo.getCity());
		sendrule.setCitycaption(ruleinfo.getCitycaption());
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setIncreratio(ruleinfo.getIncreratio());
		sendrule.setInitsendradius(ruleinfo.getInitsendradius());
		sendrule.setMaxsendradius(ruleinfo.getMaxsendradius());
		sendrule.setPersonsendinterval(ruleinfo.getPersonsendinterval());
		sendrule.setPushnum(ruleinfo.getPushnum());
		sendrule.setPushlimit(ruleinfo.getPushlimit());
		sendrule.setPushnumlimit(ruleinfo.getPushnumlimit());
		sendrule.setSendmodel(ruleinfo.getSendmodel()+"");
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setVehicleupgrade(ruleinfo.getVehicleupgrade()+"");
		return sendrule;
	}

	private SendRuleHelper convert2GrabSendRuleImp(PubSendrules ruleinfo) {
		LeCarGrabSendRuleImp sendrule = new LeCarGrabSendRuleImp();
		sendrule.setCarsinterval(ruleinfo.getCarsinterval());
		sendrule.setCity(ruleinfo.getCity());
		sendrule.setCitycaption(ruleinfo.getCitycaption());
		sendrule.setDriversendinterval(ruleinfo.getDriversendinterval());
		sendrule.setIncreratio(ruleinfo.getIncreratio());
		sendrule.setInitsendradius(ruleinfo.getInitsendradius());
		sendrule.setMaxsendradius(ruleinfo.getMaxsendradius());
		sendrule.setPersonsendinterval(ruleinfo.getPersonsendinterval());
		sendrule.setPushlimit(ruleinfo.getPushlimit());
		sendrule.setPushnum(ruleinfo.getPushnum());
		sendrule.setPushnumlimit(ruleinfo.getPushnumlimit());
		sendrule.setSendmodel(ruleinfo.getSendmodel() + "");
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setSpecialinterval(ruleinfo.getSpecialinterval());
		sendrule.setVehicleupgrade(ruleinfo.getVehicleupgrade()+"");
		return sendrule;
	}

	private SendRuleHelper convert2GrabSingleSendRuleImp(PubSendrules ruleinfo) {
		LeCarGrabSingleSendRuleImp sendrule = new LeCarGrabSingleSendRuleImp();
		sendrule.setCarsinterval(ruleinfo.getCarsinterval());
		sendrule.setCity(ruleinfo.getCity());
		sendrule.setCitycaption(ruleinfo.getCitycaption());
		sendrule.setDriversendinterval(ruleinfo.getDriversendinterval());
		sendrule.setIncreratio(ruleinfo.getIncreratio());
		sendrule.setInitsendradius(ruleinfo.getInitsendradius());
		sendrule.setMaxsendradius(ruleinfo.getMaxsendradius());
		sendrule.setPersonsendinterval(ruleinfo.getPersonsendinterval());
		sendrule.setPushlimit(ruleinfo.getPushlimit());
		sendrule.setPushnum(ruleinfo.getPushnum());
		sendrule.setPushnumlimit(ruleinfo.getPushnumlimit());
		sendrule.setSendmodel(ruleinfo.getSendmodel()+"");
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setSpecialinterval(ruleinfo.getSpecialinterval());
		sendrule.setVehicleupgrade(ruleinfo.getVehicleupgrade()+"");
		return sendrule;
	}
	
	private SendRuleHelper convert2SystemSingleSendRuleImp(PubSendrules ruleinfo) {
		LeCarSystemSingleSendRuleImp sendrule = new LeCarSystemSingleSendRuleImp();
		sendrule.setCarsinterval(ruleinfo.getCarsinterval());
		sendrule.setCity(ruleinfo.getCity());
		sendrule.setCitycaption(ruleinfo.getCitycaption());
		sendrule.setDriversendinterval(ruleinfo.getDriversendinterval());
		sendrule.setIncreratio(ruleinfo.getIncreratio());
		sendrule.setInitsendradius(ruleinfo.getInitsendradius());
		sendrule.setMaxsendradius(ruleinfo.getMaxsendradius());
		sendrule.setPersonsendinterval(ruleinfo.getPersonsendinterval());
		sendrule.setPushlimit(ruleinfo.getPushlimit());
		sendrule.setPushnum(ruleinfo.getPushnum());
		sendrule.setPushnumlimit(ruleinfo.getPushnumlimit());
		sendrule.setSendmodel(ruleinfo.getSendmodel()+"");
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setSpecialinterval(ruleinfo.getSpecialinterval());
		sendrule.setVehicleupgrade(ruleinfo.getVehicleupgrade()+"");
		return sendrule;
	}
}
