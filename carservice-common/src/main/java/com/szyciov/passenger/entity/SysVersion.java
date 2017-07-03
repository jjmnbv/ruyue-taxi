package com.szyciov.passenger.entity;

public class SysVersion {

	private String curversion;
	
	private int versionno;
	
	private int maxversionno;
	
	private String changelog;
	
	private String publishtime;
	
	private String downloadhref;

	public String getCurversion() {
		return curversion;
	}

	public void setCurversion(String curversion) {
		this.curversion = curversion;
	}

	public int getVersionno() {
		return versionno;
	}

	public void setVersionno(int versionno) {
		this.versionno = versionno;
	}

	public int getMaxversionno() {
		return maxversionno;
	}

	public void setMaxversionno(int maxversionno) {
		this.maxversionno = maxversionno;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getDownloadhref() {
		return downloadhref;
	}

	public void setDownloadhref(String downloadhref) {
		this.downloadhref = downloadhref;
	}
}
