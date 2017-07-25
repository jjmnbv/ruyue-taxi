package com.ry.taxi.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ry.taxi.base.common.ErrorResponse;
import com.ry.taxi.base.constant.UrlRequestConstant;
import com.xunxintech.ruyue.coach.encryption.algorithm.DESUtils;
import com.xunxintech.ruyue.coach.encryption.algorithm.MD5;


@WebFilter(filterName = "ConfigFilter", urlPatterns = "/*")
@Component
public class ConfigFilter implements HandlerInterceptor {
	
	@Value("${spring.http.encoding.charset:utf-8}")
	private String encoding;
	
	@Value("${spring.http.content-type:text/html}")
	private String contentType;
	
	@Value("${DES.iv}")
	private String ivCode;
	
	private String contentStr = "content-type";
	
	private static List<String> permitList = new ArrayList<String>();
	
	private static Logger logger = LoggerFactory.getLogger(ConfigFilter.class);
	

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		response.setHeader(contentStr, contentType);
		response.setCharacterEncoding(encoding);
		request.setCharacterEncoding(encoding);
		
		String cmd  = request.getParameter(UrlRequestConstant.CMD);//功能命令字
		String key  = request.getParameter(UrlRequestConstant.KEY);//前端自定义八位字符串
		String userId = request.getParameter(UrlRequestConstant.USERID);//合作方账户
		String args = request.getParameter(UrlRequestConstant.ARGS);//业务参数
		String sign = request.getParameter(UrlRequestConstant.SIGN);//sign= MD5(DES(Args.trim(), Key（动态）, IV静态（后台公用))
	
		//访问接口过滤
		if(!permitList.contains(cmd)){
			ErrorResponse.printErrorMessage(response, cmd, 2, "无效请求");
			return false;
        }
		
		logger.debug("request parameter cmd:{},key:{},userId:{},args:{},sign:{}", cmd, key, userId, args, sign);
		
		if (cmd != null && key != null && userId != null && args != null &&sign != null ) {
			try {
				String checkSign = MD5.MD5Encode(DESUtils.encode(key, args, ivCode), encoding);
				if(checkSign.equals(sign))
					return true;
				else
				{  
					ErrorResponse.printErrorMessage(response, cmd, 2, "sign签名校验不通过");
				}
			} catch (Exception e) {
				logger.error("check encryption error,cmd:{},key:{},userId:{},args:{},sign:{},error:{}", cmd, key, userId, args, sign);
			}
		}
		
		logger.error("check auth error,cmd:{},key:{},userId:{},args:{},sign:{},error:{}", cmd, key, userId, args, sign);
		
		return false;
	}
	
	
	static{
		permitList.add(UrlRequestConstant.CMD_DRIVERTAKEORDER);//司机应邀通知
		permitList.add(UrlRequestConstant.CMD_DRIVERSTARTORDER);//司机执行订单通知
		permitList.add(UrlRequestConstant.CMD_DRIVERARRIVAL);//司机到达乘客起点通知
		permitList.add(UrlRequestConstant.CMD_DRIVERCANCELORDER);//司机取消订单通知
		permitList.add(UrlRequestConstant.CMD_STARTCALCULATION);//压表通知
		permitList.add(UrlRequestConstant.CMD_ENDCALCULATION);//起表通知
		permitList.add(UrlRequestConstant.CMD_PAYMENTCONFIRMATION);//支付确认通知
		permitList.add(UrlRequestConstant.CMD_DISTANCEUPLOAD);//里程回传
	}
}
