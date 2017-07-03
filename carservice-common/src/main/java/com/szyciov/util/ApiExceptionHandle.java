/**
 * 
 */
package com.szyciov.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.annotation.NeedRelease;
import com.szyciov.entity.Retcode;
import com.szyciov.exception.EmptyException;

import net.sf.json.JSONObject;

/**
 * @ClassName ExceptionHandleController 
 * @author Efy Shu
 * @Description Api项目异常处理类
 * @date 2017年3月2日 下午2:59:26 
 */

public class ApiExceptionHandle extends BaseController {
	public Logger logger;
	@NeedRelease
	public ThreadLocal<Long> starttime = new ThreadLocal<>();

	/**
	 * 处理返回值为空的异常
	 * @param ex
	 * @param response
	 */
	@ResponseBody
	@ExceptionHandler(EmptyException.class)
	public void handleEmptyResult(EmptyException ex,HttpServletResponse response){
		JSONObject exMess = new JSONObject();
		exMess.put("status", Retcode.OK.code);
		exMess.put("message", Retcode.OK.msg);
		exMess.put("servertime", new Date());
		long endtime = System.currentTimeMillis();
		double accesstime = ((double)(endtime - (starttime.get() == null ? endtime : starttime.get())))/1000;
		exMess.put("accesstime", accesstime+"s");
		printResult(checkResult(exMess).toString(),response);
	}
	
	/**
	 * 处理没有请求体的异常
	 * @param ex
	 * @param response
	 */
	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void handleNORequestBody(HttpMessageNotReadableException ex,HttpServletResponse response){
		JSONObject exMess = new JSONObject();
		exMess.put("status", Retcode.FAILED.code);
		exMess.put("message", "请求体不能为空");
		exMess.put("servertime", new Date());
		long endtime = System.currentTimeMillis();
		double accesstime = ((double)(endtime - (starttime.get() == null ? endtime : starttime.get())))/1000;
		exMess.put("accesstime", accesstime+"s");
		printResult(checkResult(exMess).toString(),response);
	}
	
	/**
	 * 处理其他异常
	 * @param ex
	 * @param response
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public void handleAllException(Exception ex,HttpServletResponse response){
		if(logger == null) logger = LoggerFactory.getLogger(this.getClass());
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("侦听到方法执行出错====================\r\n");
		sb.append("目标位置:\r\n");
		for(StackTraceElement ste : ex.getStackTrace()){
			if(ste.toString().contains("com.szyciov")){
				sb.append("类名:"+ste.getClassName()+"\r\n");
				sb.append("方法名:"+ste.getMethodName()+"\r\n");
				sb.append("行号:"+ste.getLineNumber()+"\r\n");
				sb.append(ste.toString().replace("Unknown Source", "未知来源")).append("\r\n\r\n");
//				break;
			}
		}
		sb.append("原因:"+ex.getClass().getName() + ": " +ex.getMessage() + "\r\n");
		sb.append("异常输出完成=========================\r\n");
		logger.error(sb.toString(),ex);
		JSONObject exMess = new JSONObject();
		exMess.put("status", Retcode.EXCEPTION.code);
		exMess.put("message", Retcode.EXCEPTION.msg);
		exMess.put("servertime", new Date());
		long endtime = System.currentTimeMillis();
		double accesstime = ((double)(endtime - (starttime.get() == null ? endtime:starttime.get())))/1000;
		exMess.put("accesstime", accesstime+"s");
		printResult(checkResult(exMess).toString(),response);
	}
	
	public JSONObject checkResult(JSONObject result){
		if (result == null) {
			result = new JSONObject();
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
			result.put("servertime", new Date());
		}else if(result.isEmpty()){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
			result.put("servertime", new Date());
		}else{
			if (!result.has("status")) {
				result.put("status", Retcode.OK.code);
			}
			if(!result.has("message")){
				result.put("message", Retcode.OK.msg);
			}
			if(!result.has("servertime")){
				 result.put("servertime", new Date());
			}
		}
		long endtime = System.currentTimeMillis();
		double accesstime = ((double)(endtime - (starttime.get() == null ? endtime : starttime.get())))/1000;
		result.put("accesstime", accesstime+"s");
		releaseResource(this);
		InvokeUtil.removeNullObejct(result,true,"yyyy-MM-dd HH:mm:ss");
		return result;
	}
	
	/**
	 * 将对象中所有标记为需要释放的资源进行释放,避免内存消耗过大
	 * @param param
	 */
	public void releaseResource(Object param){
		if(logger == null) logger = LoggerFactory.getLogger(this.getClass());
		logger.debug(param.getClass().getName() + "释放资源...");
		for(Class<?> clazz = param.getClass();clazz != Object.class;clazz=clazz.getSuperclass()){
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				try {
					if(f.getAnnotation(NeedRelease.class) == null) continue;
					else{
						f.setAccessible(true);
						if(f.get(param) != null && ThreadLocal.class.cast(f.get(param)).get() != null){
							ThreadLocal.class.cast(f.get(param)).remove();
							logger.debug("资源" + f.getName() + "被释放");
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {}
			}
		}
		logger.debug(param.getClass().getName() + "释放资源完成");
	}
	
	public void printResult(String result,HttpServletResponse response){
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			response.getWriter().write(result);
			releaseResource(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
