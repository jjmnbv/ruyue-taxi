package com.szyciov.test;
import com.szyciov.annotation.SzycValid;

/**
 * @ClassName TestCheckValid 
 * @author Efy Shu
 * @Description 校验框架Demo所用的实体类
 * @date 2017年3月7日 下午7:51:46
 * <p>在需要校验的字段上使用@SzycValid注解,参数为rules,表示规则列表,与js的验证框架一致</p>
 */
public class ValidateEntity {
	@SzycValid(rules="checkNull")
	private String testStr;
	
	@SzycValid(rules={"checkNull","isFltNO"})
	private String testStr2;

	/**  
	 * 获取testStr
	 * @return testStr testStr  
	 */
	public String getTestStr() {
		return testStr;
	}

	/**  
	 * 设置testStr  
	 * @param testStr testStr  
	 */
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	/**  
	 * 获取testStr2  
	 * @return testStr2 testStr2  
	 */
	public String getTestStr2() {
		return testStr2;
	}
	
	/**  
	 * 设置testStr2  
	 * @param testStr2 testStr2  
	 */
	public void setTestStr2(String testStr2) {
		this.testStr2 = testStr2;
	}
}
