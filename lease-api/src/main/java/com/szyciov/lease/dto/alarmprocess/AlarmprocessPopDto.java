package com.szyciov.lease.dto.alarmprocess;

public class AlarmprocessPopDto {
	/**
	 * 报警管理弹窗dto
	 *
	 */

    private String title;

    private String id;

    private String alarmsource;

    private String alarmtype;
    
    private String leasecompanyid;
    

	public String getLeasecompanyid() {
		return leasecompanyid;
	}

	public void setLeasecompanyid(String leasecompanyid) {
		this.leasecompanyid = leasecompanyid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlarmsource() {
		return alarmsource;
	}

	public void setAlarmsource(String alarmsource) {
		this.alarmsource = alarmsource;
	}

	public String getAlarmtype() {
		return alarmtype;
	}

	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}

	@Override
	public String toString() {
		return "AlarmprocessPopDto [title=" + title + ", id=" + id + ", alarmsource=" + alarmsource + ", alarmtype="
				+ alarmtype + "]";
	}

}
