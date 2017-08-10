package com.szyciov.util;

import java.io.File;
import java.util.Date;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @ClassName CustomRollingFileAppender 
 * @author Efy Shu
 * @Description 重写DailyRollingFileAppender,使其支持只保留最近x天的记录
 * @date 2017年8月4日 上午11:45:01 
 */
public class CustomRollingFileAppender extends DailyRollingFileAppender {
	/**
	 * 保留天数(x天以前的日志将被删除)
	 */
	private int holdDays = 30;
	private Date minHoldDate;
	
	public CustomRollingFileAppender() {
		setDatePattern("'.'yyyy-MM-dd");
		activateOptions();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.log4j.DailyRollingFileAppender#subAppend(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	protected void subAppend(LoggingEvent event) {
		minHoldDate = new Date();
		//如果今天的日志也算在内用<=holdDays否则使用<holdDays
		//比如保留2天内的日志,
		//不算今天,那么保留 今天(当前正在写入的日志文件),昨天,前天
		//要算今天,那么保留 今天(当前正在写入的日志文件),昨天
		for(int i=0;i<holdDays;i++) minHoldDate = StringUtil.getYesterday(minHoldDate);
		File current = new File(fileName); 
		File path = new File(current.getParent());
		for(File temp : path.listFiles()){
			if(temp.isFile() && temp.getName().startsWith(current.getName()+".")){
				Date tempDate = StringUtil.parseDate(temp.getName().substring(temp.getName().lastIndexOf(".")+1), StringUtil.TIME_WITH_DAY);
				if(tempDate.before(minHoldDate)){
					temp.delete();
				}
			}
		}
		super.subAppend(event);
	}

	/**  
	 * 获取保留天数(x天以前的日志将被删除)  
	 * @return holdDays 保留天数(x天以前的日志将被删除)  
	 */
	public int getHoldDays() {
		return holdDays;
	}
	

	/**  
	 * 设置保留天数(x天以前的日志将被删除)  
	 * @param holdDays 保留天数(x天以前的日志将被删除)  
	 */
	public void setHoldDays(int holdDays) {
		this.holdDays = holdDays;
	}
}
