/**
 * 
 */
package com.ry.taxi.base.exception;

import com.ry.taxi.base.constant.ErrorEnum;

/**
 * @Title:RealTaxiMonitor.java
 * @Package com.ry.taxi.sync.monitor
 * @Description
 * @author zhangdd
 * @date 2017年7月10日 下午4:17:37
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class RyTaxiException extends RuntimeException{

	private static final long serialVersionUID = -8436042608795665144L;
	
	private Integer errorNum;    

    public RyTaxiException(Integer errorNum, String message) {    
        super(message);    
        this.errorNum = errorNum;
    } 
   
    public RyTaxiException(ErrorEnum errorNum, String message) {    
        super(message);    
        this.errorNum = errorNum.getValue();
    } 
    
 
	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

}
