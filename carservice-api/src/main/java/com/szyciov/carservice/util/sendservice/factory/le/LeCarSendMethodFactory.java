package com.szyciov.carservice.util.sendservice.factory.le;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.util.sendservice.factory.SendMethodFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarGrabSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarGrabSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.le.LeCarSystemSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;

/**
 * 租赁端网约车派单方法类
 * @author Efy
 *
 */
@Service("LeCarSendMethodFactory")
public class LeCarSendMethodFactory extends SendMethodFactory{

	/**
	 * 根据派单规则获取派单方法
	 */
	public SendMethodHelper createSendMethod(SendRuleHelper sendrule) {
		//获取派单方法
		if(SendRuleHelper.SEND_TYPE_FORCE.equals(sendrule.getSendtype())){
			//强派类型
			return new LeCarForceSendMethodImp();
		}else if(SendRuleHelper.SEND_TYPE_GRAB.equals(sendrule.getSendtype())){
			//抢派类型
			return new LeCarGrabSendMethodImp();
		}else if(SendRuleHelper.SEND_TYPE_GRABSINGLE.equals(sendrule.getSendtype())){
			//抢单类型
			return new LeCarGrabSingleSendMethodImp();
		}else if(SendRuleHelper.SEND_TYPE_SYSTEMSINGLE.equals(sendrule.getSendtype())){
			//纯人工类型
			return new LeCarSystemSingleSendMethodImp();
		}
		return null;
	}

}
