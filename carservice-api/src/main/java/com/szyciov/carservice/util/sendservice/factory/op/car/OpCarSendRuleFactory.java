package com.szyciov.carservice.util.sendservice.factory.op.car;

import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.sendservice.factory.SendRuleFactory;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.car.OpCarGrabSingleSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.car.OpCarSystemSingleSendRuleImp;
import com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi.ForceSendRuleImp;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubSendrules;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.SendRulesEnum;
import com.szyciov.param.PubSendRuleQueryParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 运管端网约车派单规则类
 * @author zhu
 *
 */
@Service("OpCarSendRuleFactory")
public class OpCarSendRuleFactory extends SendRuleFactory{

	private Logger logger = LoggerFactory.getLogger(OpCarSendRuleFactory.class);

	@Resource
	private SendInfoService sendInfoService;


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
			//即刻单
		if(orderinfo.isIsusenow()){
			return convert2Imp(getRules(SendRulesEnum.USETYPE_INSTANTLY.code,orderinfo.getOncity()));
		}else{
			//预约单
			return convert2Imp(getRules(SendRulesEnum.USETYPE_APPOINTMENT.code,orderinfo.getOncity()));
		}
	}

	/**
	 * 返回派单规则
	 * @param useType	用车类型，即刻/预约
	 * @return
	 */
	private PubSendrules getRules(String useType,String cityCode){
		PubSendRuleQueryParam queryParam = new PubSendRuleQueryParam();
		//系统类型 运管
		queryParam.setPlatformType(PlatformTypeByDb.OPERATING.code);
		//用车类型 预约/即刻
		queryParam.setUseType(useType);
		//车型 网约车/出租车
		queryParam.setVehicleType(DriverEnum.DRIVER_TYPE_CAR.code);
		//城市编码
		queryParam.setCityCode(cityCode);

		return sendInfoService.getSendRule(queryParam);
	}


	private SendRuleHelper convert2Imp(PubSendrules ruleinfo) {
		if(ruleinfo==null){
			logger.warn("派单规则不存在");
			return null;
		}else {
			String sendtype = String.valueOf(ruleinfo.getSendtype());
			SendRuleHelper sendrule = null;
			if (SendRuleHelper.SEND_TYPE_FORCE.equalsIgnoreCase(sendtype)) {
				//强派
				sendrule = convert2ForceSendRuleImp(ruleinfo);
			} else if (SendRuleHelper.SEND_TYPE_GRABSINGLE.equalsIgnoreCase(sendtype)) {
				//抢单
				sendrule = grabSingleSendRuleImp(ruleinfo);
			}else if (SendRuleHelper.SEND_TYPE_SYSTEMSINGLE.equalsIgnoreCase(sendtype)) {
				//人工
				sendrule = systemSingleSendRuleImp(ruleinfo);
			}
			return sendrule;
		}
	}

	private SendRuleHelper convert2ForceSendRuleImp(PubSendrules ruleinfo) {
		ForceSendRuleImp sendrule = new ForceSendRuleImp();
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
		sendrule.setSendmodel(String.valueOf(ruleinfo.getSendmodel()));
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setUpgrade((SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(String.valueOf(ruleinfo.getVehicleupgrade())))?true:false);

		return sendrule;
	}


	private SendRuleHelper grabSingleSendRuleImp(PubSendrules ruleinfo) {
		OpCarGrabSingleSendRuleImp sendrule = new OpCarGrabSingleSendRuleImp();
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
		sendrule.setSendmodel(String.valueOf(ruleinfo.getSendmodel()));
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setSystemsendinterval(ruleinfo.getSystemsendinterval());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setUpgrade((SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(String.valueOf(ruleinfo.getVehicleupgrade())))?true:false);
		return sendrule;
	}


	private SendRuleHelper systemSingleSendRuleImp(PubSendrules ruleinfo) {
		OpCarSystemSingleSendRuleImp sendrule = new OpCarSystemSingleSendRuleImp();
		sendrule.setCarsinterval(ruleinfo.getCarsinterval());
		sendrule.setCity(ruleinfo.getCity());
		sendrule.setCitycaption(ruleinfo.getCitycaption());
		sendrule.setPersonsendinterval(ruleinfo.getPersonsendinterval());
		sendrule.setPushlimit(ruleinfo.getPushlimit());
		sendrule.setShortname(ruleinfo.getShortname());
		sendrule.setUsetype(ruleinfo.getUsetype());
		sendrule.setUpgrade((SendRulesEnum.VEHICLEUPGRADE_UPGRADE.code.equals(String.valueOf(ruleinfo.getVehicleupgrade())))?true:false);
		return sendrule;
	}


}
