package com.szyciov.carservice.util.sendservice.factory.op.taxi;

import com.szyciov.carservice.util.sendservice.factory.SendMethodFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.ForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.GrabSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.taxi.GrabSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出租车派单方法类
 * @author zhu
 *
 */
@Service("TaxiSendMethodFactory")
public class TaxiSendMethodFactory extends SendMethodFactory{

	@Autowired
	private ForceSendMethodImp forceSendMethodImp;
	@Autowired
	private GrabSendMethodImp grabSendMethodImp;
	@Autowired
	private GrabSingleSendMethodImp grabSingleSendMethodImp;

	/**
	 * 根据派单规则获取派单方法
	 */
	@Override
	public SendMethodHelper createSendMethod(SendRuleHelper sendrule) {
		//获取派单方法
		if(SendRuleHelper.SEND_TYPE_FORCE.equals(sendrule.getSendtype())){
			//强派类型
			return forceSendMethodImp;
		}else if(SendRuleHelper.SEND_TYPE_GRAB.equals(sendrule.getSendtype())){
			//抢派类型
			return grabSendMethodImp;
		}else if(SendRuleHelper.SEND_TYPE_GRABSINGLE.equals(sendrule.getSendtype())){
			//抢单类型
//			return new GrabMethodImp();
			return grabSingleSendMethodImp;
		}
		return null;
	}

}
