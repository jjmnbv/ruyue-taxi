package com.szyciov.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.szyciov.annotation.ValidateRule;

/**
 * @ClassName ValidMethod 
 * @author Efy Shu
 * @Description 校验规则 修饰符不能为private和protected,返回值为boolean,参数唯一
 * @date 2017年3月7日 下午7:49:21 
 */
public class ValidateRules {
	/**
	 * 检查是否为空
	 * @param obj
	 * @return
	 */
	@ValidateRule(msg="缺少参数字段")
	public boolean checkNull(Object obj){
		return obj != null;
	}
	
	/**
	 * 检测是否为航班号
	 * @param fltno
	 * @return
	 */
	@ValidateRule(msg="请输入正确的航班号")
	public boolean isFltNO(String fltno){
		return fltno != null && fltno.matches("^[\\w\\d]{6}$");
	}
	
	/**
	 * 检查是否是正整数
	 * @param num
	 * @return
	 */
	@ValidateRule(msg="请输入正整数,最小为1")
	public boolean isSignlessInt(int num){
		return num > 0;
	}
	
	/**
	 * 检查手机号格式是否正确
	 * @param mobile
	 * @return
	 */
	@ValidateRule(msg="请输入正确的手机号")
	public boolean isMobile(String mobile){
		String reg = 
				"^((13[\\d])|"
				+ "(14[5|7|9])|"
				+ "(15[^4,\\D])|"
				+ "(17[^2,^4,^9,\\D])|"
				+ "(18[\\d]))"
				+ "\\d{8}$"; 
		return mobile != null && mobile.matches(reg);
	}
	
	/**
	 * 检查是否是银行卡号
	 * @param bankNO
	 * @return
	 */
	@ValidateRule(msg="卡号错误")
	public boolean isBankNO(String bankNO){
		return !BankUtil.getBankName(bankNO).isEmpty();
	}
	
	/**
	 * 检查日期格式是否正确
	 * @param dateStr
	 * @return
	 */
	@ValidateRule(msg="日期格式不正确")
	public boolean isDate(String dateStr){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		ValidateRules rules = new ValidateRules();
		System.out.println(rules.isMobile("14812345678"));
	}
}
