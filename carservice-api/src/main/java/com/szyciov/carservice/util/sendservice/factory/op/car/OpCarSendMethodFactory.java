package com.szyciov.carservice.util.sendservice.factory.op.car;


import com.szyciov.carservice.util.sendservice.factory.SendMethodFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarGrabSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpCarSystemSingleSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendmethod.op.car.OpForceSendMethodImp;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出租车派单方法类
 * @author zhu
 *
 */
@Service("OpCarSendMethodFactory")
public class OpCarSendMethodFactory extends SendMethodFactory{
    @Autowired
    private OpForceSendMethodImp opForceSendMethodImp;
    @Autowired
    private OpCarGrabSingleSendMethodImp opCarGrabSingleSendMethodImp;
    @Autowired
    private OpCarSystemSingleSendMethodImp opCarSystemSingleSendMethodImp;

	/**
	 * 根据派单规则获取派单方法
	 */
	@Override
	public SendMethodHelper createSendMethod(SendRuleHelper sendrule) {
		//获取派单方法
		if(SendRuleHelper.SEND_TYPE_FORCE.equals(sendrule.getSendtype())){
			//强派类型
			return opForceSendMethodImp;
		}else if(SendRuleHelper.SEND_TYPE_GRABSINGLE.equals(sendrule.getSendtype())){
			//抢单类型
			return opCarGrabSingleSendMethodImp;
		} else if(SendRuleHelper.SEND_TYPE_SYSTEMSINGLE.equals(sendrule.getSendtype())){
			//纯人工
			return opCarSystemSingleSendMethodImp;
		}
		return null;
	}

}
