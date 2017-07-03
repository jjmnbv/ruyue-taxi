package com.szyciov.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.szyciov.annotation.SzycValid;
import com.szyciov.annotation.ValidateRule;

/**
 * @ClassName ValidateUtil 
 * @author Efy Shu
 * @Description 校验工具类
 * @date 2017年3月7日 下午6:36:20 
 */
public class ValidateUtil {
	/**
	 * 字段规则映射表
	 * Key : 字段名 Value : 规则列表
	 */
	private Map<String, String[]> fieldRules = new HashMap<>();
	/**
	 * 字段值
	 * Key : 字段名 Value : 字段值
	 */
	private Map<String, Object> fieldValue = new HashMap<>();
	/**
	 * 校验方法列表
	 * Key : 规则名 Value : 对应规则实体
	 */
	private Map<String, RuleBean> validateRules = new HashMap<>();
	/**
	 * 错误信息(校验失败时会保存相应信息)
	 * Key : 字段名 Value : 提示文本
	 */
	private Map<String, String> errors = new TreeMap<>();
	/**
	 * 校验方法类列表(包含校验方法的类)
	 */
	private List<Object> rulesClass = new ArrayList<>();
	
	/**
	 * 加载所有规则
	 */
	public void loadRules(){
		//初始化校验规则(类中包含ValidateRule注解的方法)
		initRules();
	}
	
	/**
	 * 添加校验元素
	 * @param name
	 * @param value
	 * @param rules
	 */
	public void addElement(String name,Object value,String[] rules){
		if(rules == null) rules = new String[]{};
		fieldRules.put(name, rules);
		fieldValue.put(name, value);
	}
	
	/**
	 * 注册校验规则
	 * @param ruleName
	 * @param ownerClass
	 */
	public void addRule(String ruleName,Object ownerClass){
		addRule(ruleName,ownerClass,"");
	}
	
	/**
	 * 注册校验规则
	 * @param ruleName         规则名称
	 * @param ownerClass       方法所属Class
	 * @param errorMessage   校验失败时的提示文本
	 */
	public void addRule(String ruleName,Object ownerClass,String errorMessage){
		RuleBean bean = new RuleBean(ruleName, ownerClass, errorMessage);
		validateRules.put(ruleName, bean);
	}
	
	/**
	 * 注册校验规则类
	 * @param clazz 包含校验规则的类
	 */
	public void addRuleClass(Object clazz){
		//不添加重复规则
		if(rulesClass.contains(clazz)) return;
		rulesClass.add(clazz);
	}
	
	/**
	 * 注册规则错误提示信息
	 * @param ruleName
	 * @param errorMessage
	 */
	public void addErrorMessage(String ruleName,String errorMessage){
		validateRules.get(ruleName).setErrorMessage(errorMessage);
	}
	
	/**
	 * 校验目标元素
	 * @param target
	 * @return
	 * @see {@link com.szyciov.test.ValidateDemo ValidateDemo}
	 */
	public boolean valid(Object target,String... requreParam){
		errors = new HashMap<String, String>();
		//系统校验规则为空,初始化
		if(validateRules.isEmpty()) loadRules();
		//初始化目标所需规则(实体中包含@SzycValid注解的字段)
		initTargetRules(target);
		//如果请求参数列表为空则校验全部元素
		if(requreParam == null){
			for(String eleName : fieldValue.keySet()){
				checkElement(eleName);
			}
		//否则按顺序校验指定元素
		}else{
			for(String eleName : requreParam){
				checkElement(eleName);
			}
		}
		return errors.isEmpty();
	}
	
	/**
	 * 校验目标(错误信息的字段为temp)
	 * @param param
	 * @param rules
	 * @return
	 * @see {@link com.szyciov.test.ValidateDemo ValidateDemo}
	 */
	public boolean validTarget(Object param,String[] rules){
		errors = new HashMap<String, String>();
		//系统校验规则为空,初始化
		if(validateRules.isEmpty()) loadRules();
		fieldRules.put("temp", rules);
		fieldValue.put("temp", param);
		return checkElement("temp");
	}
	
	/**
	 * 获取已加载的校验规则
	 * @return
	 */
	public Map<String, RuleBean> getRules(){
		return validateRules;
	}
	
	/**
	 * 获取错误字段列表
	 * @return
	 */
	public String[] getErrorFields(){
		String[] keys = new String[]{};
		return errors.keySet().toArray(keys);
	}
	
	/**
	 * 获取字段错误信息
	 * @param field
	 * @return
	 */
	public String getError(String field){
		return errors.get(field);
	}
	
	/**
	 * 初始化对象所需校验规则
	 * @param target
	 */
	private void initTargetRules(Object target){
		initTargetFieldRules(target);
		initTargetFiledValue(target);
	}
	
	/**
	 * 初始化规则列表
	 */
	private void initRules(){
		//如果不存在默认规则,将默认规则添加至首位,重名规则用后添加的覆盖
		if(!rulesClass.contains(new ValidateRules())) rulesClass.add(0,new ValidateRules());
		for(Object clazz : rulesClass){
			for(Class<?> currClazz = clazz.getClass();currClazz != Object.class;currClazz = currClazz.getSuperclass()){
				Method[] ms = currClazz.getDeclaredMethods();
				for(Method m : ms){
					ValidateRule rule = m.getAnnotation(ValidateRule.class);
					if(rule != null){
						addRule(m.getName(),clazz,rule.msg());
					}
				}
			}
		}
	}
	
	/**
	 * 初始化对象字段所需校验规则
	 * @param target
	 */
	private void initTargetFieldRules(Object target){
		for(Class<?> clazz = target.getClass();clazz != Object.class;clazz = clazz.getSuperclass()){
			Field[] fileds = clazz.getDeclaredFields();
			for(Field filed : fileds){
				SzycValid validRules = filed.getAnnotation(SzycValid.class);
				//如果已经存在该值则跳过,存在即说明有调用addElement方法添加了参数
				if(validRules != null && !fieldRules.containsKey(filed.getName())){
					fieldRules.put(filed.getName(), validRules.rules());
				}
			}
		}
	}
	
	/**
	 * 初始化对象字段值
	 * @param target
	 */
	private void initTargetFiledValue(Object target){
		for(Class<?> clazz = target.getClass();clazz != Object.class;clazz = clazz.getSuperclass()){
			Field[] fileds = clazz.getDeclaredFields();
			for(Field filed : fileds){
				//如果已经存在该值则跳过,存在即说明有调用addElement方法添加了参数
				if(fieldValue.containsKey(filed.getName())) continue;
				try {
					filed.setAccessible(true); //关闭安全性检查
					Object value = filed.get(target);
					fieldValue.put(filed.getName(), value);
				} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 校验元素
	 * @param eleName
	 * @param target
	 * @return
	 */
	private boolean checkElement(String eleName) {
		boolean result = checkFieldElement(eleName);
		return result;
	}
	
	/**
	 * 校验对象字段
	 * @param eleName
	 * @param target
	 * @return
	 */
	private boolean checkFieldElement(String eleName){
		if(fieldRules.get(eleName) == null) return true;
		for(String rule : fieldRules.get(eleName)){
			if(validateRules.get(rule) ==null || validateRules.get(rule).getOwnerClass() == null) continue;
			try {
				Object value = fieldValue.get(eleName);
				Object ruleClazz = validateRules.get(rule).getOwnerClass();
				Method m = getMethod(rule,ruleClazz);
				if(m == null) return true;
				m.setAccessible(true); //关闭安全性检查
				boolean result = (boolean)m.invoke(ruleClazz,value);
				if(!result){
					errors.put(eleName,validateRules.get(rule).getErrorMessage());
					return false;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| SecurityException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * 获取规则类中的校验方法
	 * @param rule
	 * @param ruleClazz
	 * @return
	 */
	private Method getMethod(String rule,Object ruleClazz){
		Method m = null;
		for(Class<?> currClazz = ruleClazz.getClass();currClazz != Object.class;currClazz = currClazz.getSuperclass()){
			Method[] dMethods = currClazz.getDeclaredMethods();
			for(Method method : dMethods){
				if(method.getName().equals(rule)){
					m = method;
					break;
				}
			}
		}
		return m;
	}
}

class RuleBean{
	private String methodName;
	private Object ownerClass;
	private String errorMessage;
	
	public RuleBean(String methodName,Object ownerClass,String errorMessage) {
		this.errorMessage = errorMessage;
		this.methodName = methodName;
		this.ownerClass = ownerClass;
	}
	/**  
	 * 获取methodName  
	 * @return methodName methodName  
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**  
	 * 获取ownerClass  
	 * @return ownerClass ownerClass  
	 */
	public Object getOwnerClass() {
		return ownerClass;
	}
	
	/**  
	 * 获取errorMessage  
	 * @return errorMessage errorMessage  
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**  
	 * 设置errorMessage  
	 * @param errorMessage errorMessage  
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}