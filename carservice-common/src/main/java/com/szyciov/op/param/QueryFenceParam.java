package com.szyciov.op.param;

/**
 * 栅栏列表(时间、电子、区域)查询参数 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryFenceParam extends BasePageParam {

	/**
	 * 栅栏ID
	 */
	private String id;
	/**
	 * 栅栏名称
	 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
