package com.szyciov.util;

import java.lang.reflect.Field;

/**
 * 反射类成相应的字符串
 * @author 袁金林
 * @version 2017/4/24
 */
public class ReflectClassField {
private static String field="";
	
	/**
	 * 当前类属性和相应值拼接成Get请求字符串
	 * @param obj
	 * @return
	 */
	public static String getFieldsValue(Object obj) { 
		 Field[] fields = obj.getClass().getDeclaredFields();
		 field="";
		 getFieldString(fields,obj,1);
		 
		  if(field!="") {
			  field = field.substring(0,field.length()-1);
		  }
		  return field;
	 }
	 
	 /**
	 * 当前类 及 父类 属性和相应值拼接成Get请求字符串
	 * @param obj
	 * @return
	 */ 
	public static String getMoreFieldsValue(Object obj) {  
		field="";
		String strField = getProperty(obj.getClass(),obj);  
     	if(strField!="") {
     		strField = strField.substring(0,strField.length()-1);
		}
        return strField;  
     } 
	
	public static void getParameters(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();  
		for(Field f : fields){  
			System.out.println(f.getName());  
		}  
	}  
	
	 @SuppressWarnings("rawtypes")
	private static String getProperty(Class _class,Object bean){ 
        Field[] fields = _class.getDeclaredFields();  
        Field.setAccessible(fields, true); 
        
        getFieldString(fields,bean,0);
        
        if(_class.getGenericSuperclass()!=null){ 
            getProperty(_class.getSuperclass(), bean);  
        }  
		  
        return field;  
    }  
	 
	private static void getFieldString(Field[] fields,Object bean,int isErgodic){
		 /** 
		   * 基本类型、包装类型、String类型 
		   */  
		  String[] types = {"java.lang.Integer",  
		    "java.lang.Double",  
		    "java.lang.Float",  
		    "java.lang.Long",  
		    "java.lang.Short",  
		    "java.lang.Byte",  
		    "java.lang.Boolean",  
		    "java.lang.Character",  
		    "java.lang.String",  
		    "int","double","long","short","byte","boolean","char","float"};  
		  for(Field f : fields){
			  if(isErgodic>0){//大于0表示不需要递归上级类
				  f.setAccessible(true); 
			  }
			   try {  
				    for(String str : types) {  
					     if(f.getType().getName().equals(str)) {
					    	 if(f.get(bean)!=null){
					    		 field+=f.getName() + "="+ f.get(bean)+"&"; 
					    	 }
					     }
				    }  
			   } catch (IllegalArgumentException e) {  
				   e.printStackTrace();  
			   } catch (IllegalAccessException e) {  
				   e.printStackTrace();  
			   }
		  }
	 }
}