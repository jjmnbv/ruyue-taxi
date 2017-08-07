/**
 * 
 */
package com.ry.taxi.base.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.taxi.base.constant.UrlRequestConstant;
import com.ry.taxi.base.query.BaseResult;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;


/**
 * @Title:ErrorResponse.java
 * @Package com.ry.taxi.base.common
 * @Description
 * @author zhangdd
 * @date 2017年7月12日 上午11:33:39
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class ErrorResponse {
	
	public static void printErrorMessage(HttpServletResponse response,String cmd ,int errorCode , String errorMsg) throws Exception{
		BaseResult<Object> errorResult = new BaseResult<Object>();
		errorResult.setCmd(cmd);
		errorResult.setResult(errorCode);
		errorResult.setRemark(errorMsg);
		response.getWriter().print(JSONUtil.toJackson(errorResult));
	}
	
	public static String returnErrorMessage(HttpServletRequest request, int errorCode, String errorMsg){
		String cmd = request.getParameter(UrlRequestConstant.CMD);//功能命令字
		BaseResult<String > errorResult = new BaseResult<String>();
		errorResult.setCmd(cmd);
		errorResult.setResult(errorCode);
		errorResult.setRemark(errorMsg);
		try {
			return JSONUtil.toJackson(errorResult);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
