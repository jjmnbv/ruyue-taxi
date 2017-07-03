package com.szyciov.op.entity;

/**
* 树节点结构
* <功能详细描述>
* 
* @author  袁金林
* @version  [版本号, 2017年4月14日]
* @see  [相关类/方法]
* @since  [产品/模块版本]
*/
public class QueryTreeNode {
	public String id;
	/**
	 * node名称
	 */
	public String name;
	/**
	 * 父节点id
	 */
	public String pId;
	/**
	 * 公司id
	 */
	public String companyId;
	/**
	 * 层级
	 */
	public Integer level;
	/**
	 * 对应图标
	 */
	public String icon;
	/**
	 * 是否选择
	 */
	public Boolean checked;
	/**
	 * 是否展开
	 */
	public Boolean open;
	/**
	 * 是否父节点
	 */
	public Boolean isParent;
	/**
	 * 是否可选
	 */
	public Boolean chkDisabled;
	
	/**
	 * 备注
	 */
	public String remark;
	
	/**
	 * 需要添加一个编号，认为部门名称可以相同
	 * 在导入操作的时候给出的模板中有部门编号的一项
	 * 部门编号不许重复
	 */
	private String Num;
	
	private boolean nocheck;
	
	/**
	 * 联系方式
	 */
	public String telphone;

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

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNum() {
		return Num;
	}

	public void setNum(String num) {
		Num = num;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
