/**
 * 
 */
package com.ry.taxi.sync.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title:GciSyncLog.java
 * @Package com.ry.taxi.sync.domain
 * @Description
 * @author zhangdd
 * @date 2017年7月20日 下午5:43:20
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GciSyncLog {
	
	private int id;
	/**
	 * 请求时间
	 */
	private String requesttime;
	/**
	 * 同步时间
	 */
	private String synctime;
	/**
	 * 更新数量
	 */
	private int refreshcount;
	/**
	 * 处理时长
	 */
	private Long processtime;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 错误信息
	 */
	private String errormsg;
	/**
	 * 创建日期
	 */
    private String createtime;
}
