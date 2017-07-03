package com.szyciov.touch.controller;

import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.service.PartnerUseCarService;
import com.szyciov.touch.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 合作方用车接口
 * Created by shikang on 2017/5/10.
 */

@Controller
@RequestMapping("/partner")
public class PartnerUseCarController {

    private static final Logger logger = Logger.getLogger(PartnerUseCarController.class);

    private PartnerUseCarService partnerUseCarService;
    @Resource(name = "PartnerUseCarService")
    public void setPartnerUseCarService(PartnerUseCarService partnerUseCarService) {
        this.partnerUseCarService = partnerUseCarService;
    }

    /**
	 * 获取全量城市列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/getFullCityList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFullCityList(HttpServletRequest req) {
		try {
			return partnerUseCarService.getFullCityList(req);
		} catch (Exception e) {
			logger.error("获取全量城市列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
	
	/**
	 * 获取服务城市列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getServiceCityList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getServiceCityList(HttpServletRequest req) {
		try {
			return partnerUseCarService.getServiceCityList(req);
		} catch (Exception e) {
			logger.error("获取服务城市列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
	
	/**
	 * 获取城市机场列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getAirPortList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAirPortList(HttpServletRequest req) {
		try {
			return partnerUseCarService.getAirPortList(req);
		} catch (Exception e) {
			logger.error("获取城市机场列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
	
	/**
	 * 获取服务业务列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getServiceBusiness",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getServiceBusiness(HttpServletRequest req) {
		try {
			return partnerUseCarService.getServiceBusiness(req);
		} catch (Exception e) {
			logger.error("获取服务业务列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
	
	/**
	 * 获取服务车型列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getServiceModels",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getServiceModels(HttpServletRequest req) {
		try {
			return partnerUseCarService.getServiceModels(req);
		} catch (Exception e) {
			logger.error("获取服务车型列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
	
	/**
	 * 获取周边车辆列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getAroundVehicle",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAroundVehicle(HttpServletRequest req) {
		try {
			return partnerUseCarService.getAroundVehicle(req);
		} catch (Exception e) {
			logger.error("获取周边车辆列表出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
    
	/**
	 * 获取行程费用预估
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getCostEstimate",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCostEstimate(HttpServletRequest req) {
		try {
			return partnerUseCarService.getCostEstimate(req);
		} catch (Exception e) {
			logger.error("获取行程费用预估出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}
    
	
	/**
	 * 获取计价规则
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/getChargeRule",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getChargeRule(HttpServletRequest req) {
		try {
			return partnerUseCarService.getChargeRule(req);
		} catch (Exception e) {
			logger.error("获取计价规则出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
	}

    /**
     * 用车下单
     * @param req
     * @return
     */
    @RequestMapping(value = "/postInstantOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> postInstantOrder(HttpServletRequest req) {
        try {
            return partnerUseCarService.postInstantOrder(req);
        } catch (Exception e) {
            logger.error("用车下单出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 服务行程列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/getServiceTravel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getServiceTravel(HttpServletRequest req) {
        try {
            return partnerUseCarService.getServiceTravel(req);
        } catch (Exception e) {
            logger.error("服务行程列表出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 行程详情
     * @param req
     * @return
     */
    @RequestMapping(value = "/getServiceTravelDetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getServiceTravelDetail(HttpServletRequest req) {
        try {
            return partnerUseCarService.getServiceTravelDetail(req);
        } catch (Exception e) {
            logger.error("行程详情出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 行程轨迹
     * @param req
     * @return
     */
    @RequestMapping(value = "/getServiceTravelTrack", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getServiceTravelTrack(HttpServletRequest req) {
        try {
            return partnerUseCarService.getServiceTravelTrack(req);
        } catch (Exception e) {
            logger.error("行程轨迹出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 行程服务取消
     * @param req
     * @return
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> cancelOrder(HttpServletRequest req) {
        try {
            return partnerUseCarService.cancelOrder(req);
        } catch (Exception e) {
            logger.error("行程服务取消出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 行程服务报警
     * @param req
     * @return
     */
    @RequestMapping(value = "/travelAlarm", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> travelAlarm(HttpServletRequest req) {
        try {
            return partnerUseCarService.travelAlarm(req);
        } catch (Exception e) {
            logger.error("行程服务报警出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 行程支付确认
     * @param req
     * @return
     */
    @RequestMapping(value = "/paymentConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paymentConfirm(HttpServletRequest req) {
        try {
            return partnerUseCarService.paymentConfirm(req);
        } catch (Exception e) {
            logger.error("行程支付确认出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 司机评价
     * @param req
     * @return
     */
    @RequestMapping(value = "/orderEvaluate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderEvaluate(HttpServletRequest req) {
        try {
            return partnerUseCarService.orderEvaluate(req);
        } catch (Exception e) {
            logger.error("司机评价出错", e);
            return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
        }
    }

    /**
     * 应用授权认证
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="auth",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> auth(HttpServletRequest request){
    	try {
			return partnerUseCarService.auth(request);
		} catch (Exception e) {
			logger.error("应用授权认证有误",e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }

    /**
     * 授权时效延长(30分钟)
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="addAuthTimeLiness",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addAuthTimeLiness(HttpServletRequest request){
    	try {
			return partnerUseCarService.addAuthTimeLiness(request);
		} catch (Exception e) {
			logger.error("授权时效延长出错", e);
			return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }

    /**
     * 获取登录验证码
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="getCaptcha",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getCaptcha(HttpServletRequest request){
    	try {
    		return partnerUseCarService.getCaptcha(request);
		} catch (Exception e) {
		    logger.error("获取登录验证码出错",e);
		    return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }


    /**
     * 用户登录
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="login",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request){
    	try {
    		return partnerUseCarService.login(request);
		} catch (Exception e) {
		    logger.error("用户登录出错",e);
		    return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }

    /**
     * 获取用户详情
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="getUserDetail",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUserDetail(HttpServletRequest request){
    	try {
    		return partnerUseCarService.getUserDetail(request);
		} catch (Exception e) {
		    logger.error("获取用户详情出错",e);
		    return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }

    /**
     * 修改用户信息
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="modifyUser",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyUser(HttpServletRequest request){
    	try {
    		return partnerUseCarService.modifyUser(request);
		} catch (Exception e) {
		    logger.error("修改用户信息出错",e);
		    return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }

    /**
     * 用户头像上传
     * @param request
     * @return
     * @author xuxxtr
     */
    @RequestMapping(value="uploadUserImg",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadUserImg(HttpServletRequest request){
    	try {
    		return partnerUseCarService.uploadImg(request);
		} catch (Exception e) {
		    logger.error("用户头像上传出错",e);
		    return ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
		}
    }
}
