package com.szyciov.op.param;

public class QueryTreeParam extends QueryBaseParam{
	/**
	 * 当前树节点id，若没有值则为请求所有公司节点，若为0 则请求该公司子部门根节点，其余则为请求该公司部门的子部门
	 */
	private String id;

	/**
	 * 公司id
	 */
	private String companyId;

	/**
	 * 是否允许为空
	 */
	private Boolean allowEmpty;

	private String type;

	public String sourceId;

	public Integer sourceType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public QueryTreeParam() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getAllowEmpty() {
		return allowEmpty;
	}

	public void setAllowEmpty(Boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

}
