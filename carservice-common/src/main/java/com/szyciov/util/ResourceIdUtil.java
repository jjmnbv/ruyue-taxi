package com.szyciov.util;

/**
 * 获取编码信息的工具类
 * @author zhu
 */
public class ResourceIdUtil {
	
	private static final String SEPARATOR = "$";

	public static String getParentResourceId(String currenresid,int level){
		if(currenresid==null||"".equalsIgnoreCase(currenresid.trim())||level<=0){
			return null;
		}
		String[] resids = currenresid.split(SEPARATOR);
		if(resids.length<level){
			throw new RuntimeException("当前部门编码没有"+level+"级ID");
		}
		StringBuffer res = new StringBuffer();
		for(int i=0;i<level;i++){
			res.append(resids[i]);
			if(i>0&&i+1!=level){
				res.append(SEPARATOR);
			}
		}
		return res.toString();
	}
	
	/**
	 * 获取一个唯一的资源id
	 * 用户标示的资源id
	 * （因为资源id每一级的unid只有5位所以需要验重）
	 * @param parentresid
	 * @return
	 */
	public static String getResourceId(String parentresid,String loginid){
		//没有父级就认为是公司的资源id
		if(parentresid==null||"".equalsIgnoreCase(parentresid.trim())){
			return UNID.getUNID(loginid);
		}
		return parentresid+SEPARATOR+UNID.getUNID(loginid);
	}
	
	/**
	 * 获取一个唯一的资源id
	 * （因为资源id每一级的unid只有5位所以需要验重）
	 * @param parentresid
	 * @return
	 */
	public static String getResourceId(String parentresid){
		//没有父级就认为是公司的资源id
		if(parentresid==null||"".equalsIgnoreCase(parentresid.trim())){
			return UNID.getUNID();
		}
		return parentresid+SEPARATOR+UNID.getUNID();
	}
}
