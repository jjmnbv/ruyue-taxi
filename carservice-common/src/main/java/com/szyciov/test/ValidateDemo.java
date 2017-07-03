package com.szyciov.test;
import com.szyciov.util.ValidateRules;
import com.szyciov.util.ValidateUtil;


/**
 * @ClassName ValidateDemo 
 * @author Efy Shu
 * @Description 校验框架示例
 * @date 2017年3月7日 下午7:48:40 
 */
public class ValidateDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ValidateEntity tt = new ValidateEntity();
		tt.setTestStr2("6666汉字");
		//添加校验规则类(此方法需在loadRules之前使用,或重新调用loadRules方法)
		ValidateUtil util = new ValidateUtil();
		util.addRuleClass(new ValidateRules());
		//加载规则
		util.loadRules();
		//添加单个校验规则和失败时的提示文案
		util.addRule("checkNull", new ValidateRules(), "字段为null");
		//变更提示文案
		util.addErrorMessage("isFltNO", "航班号不正确");
		//校验规则列表
		for(String ruleName : util.getRules().keySet()){
			System.out.println("规则名:" + ruleName);
		}
		//校验单个字段
		util.valid("77", new String[]{"checkNull","isFltNO"});
		//校验元素(false表示校验失败,失败信息在errors里)
		util.valid(tt);
		for(String field : util.getErrorFields()){
			System.out.println("字段:"+ field + "校验失败.");
			System.out.println(util.getError(field));
		}
	}
}
