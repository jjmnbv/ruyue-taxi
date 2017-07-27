package com.ry.taxi.config;

import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.sf.json.JSONException;

import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.reflection.ReflectionException;
import org.mybatis.spring.MyBatisSystemException;
import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.taxi.base.common.ErrorResponse;
import com.ry.taxi.base.exception.RyTaxiException;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@ExceptionHandler({ SQLException.class, ParseException.class })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleSQLException(HttpServletRequest request, HttpServletResponse response, Exception ex){
		handleLog(request, ex);
		return ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"service exception!");
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleValidationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) {
		handleLog(request, ex);
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
		StringBuffer validBuff = new StringBuffer();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			validBuff.append("[");
			validBuff.append(constraintViolation.getMessage()).append("]");
		}
		return ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"parameter validation failure");
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleNullException(HttpServletRequest request, HttpServletResponse response, NullPointerException ex) {
		handleLog(request, ex);
		return ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"NullPointer");
	}
	

	@ExceptionHandler(SocketTimeoutException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleRequestTimeoutException(HttpServletRequest request, SocketTimeoutException ex) {
		handleLog(request, ex);
		return ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"Request Socket Timeout");
	}

	@ExceptionHandler(RyTaxiException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleRuntimeException(HttpServletRequest request, RyTaxiException ex) {
		ex.printStackTrace();
		handleLog(request, ex);
		return ErrorResponse.returnErrorMessage(request,2,"Request Socket Timeout");
	}


	@ExceptionHandler(JSONException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleRuntimeException(HttpServletRequest request, JSONException ex) {
		handleLog(request, ex);
		return ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"jsonParse error");
	}

	/**
	 * @Description 操作数据库出现异常:名称重复，外键关联
	 * @param request
	 * @param ex
	 * @return String
	 * @throws
	 */
	@ExceptionHandler(MyBatisSystemException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleRequestException(HttpServletRequest request, MyBatisSystemException ex) {
		handleLog(request, ex);
		return  ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"mybaits exception");
	}

	/**
	 * @Description 操作数据库出现异常:名称重复，外键关联
	 * @param request
	 * @param ex
	 * @return String
	 * @throws
	 */
	@ExceptionHandler(ReflectionException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleRequestException(HttpServletRequest request, ReflectionException ex) {
		handleLog(request, ex);
		return  ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"execute_sql_exception");
	}

	/**
	 * 
	 * @Title handleNotFoundException
	 * @Description 未找到对应的……
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 * @throws JsonProcessingException
	 *             String
	 * @throws
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleNotFoundException(HttpServletRequest request, HttpServletResponse response, NotFoundException ex) {
		handleLog(request, ex);
		return  ErrorResponse.returnErrorMessage(request,HttpStatus.NOT_FOUND.value(),"no found mapping");
	}

	/**
	 * 
	 * @Title handleSystemException
	 * @Description 系统错误
	 * @param request
	 * @param ex
	 * @return
	 * @throws JsonProcessingException
	 *             String
	 * @throws
	 */
	@ExceptionHandler(SystemException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleSystemException(HttpServletRequest request, SystemException ex) {
		handleLog(request, ex);
		return  ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"system exception");
	}

	/**
	 * 
	 * @Title handleAllException
	 * @Description json转换错误
	 * @param request
	 * @param ex
	 * @return String
	 * @throws
	 */
	@ExceptionHandler(JsonProcessingException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String handleAllException(HttpServletRequest request, JsonProcessingException ex) {
		handleLog(request, ex);
		return  ErrorResponse.returnErrorMessage(request,HttpStatus.INTERNAL_SERVER_ERROR.value(),"service jsonException");

	}

	private void handleLog(HttpServletRequest request, Exception ex) {
		StringBuffer logBuffer = new StringBuffer();
		if (request != null) {
			logBuffer.append("  request method=" + request.getMethod());
			logBuffer.append("  url=" + request.getRequestURL());
		}
		if (ex instanceof RyTaxiException) {
			logBuffer.append("  errorCode=" + ((RyTaxiException) ex).getErrorNum());
		}
		if (ex != null) {
			logBuffer.append("  exception:" + ex);
		}
		LOG.error(logBuffer.toString());
		if (LOG.isDebugEnabled()) {
			ex.printStackTrace();
		}
	}

}
