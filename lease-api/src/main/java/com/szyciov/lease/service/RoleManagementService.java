package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.lease.dao.RoleManagementDao;
import com.szyciov.lease.entity.Menu;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.RoleManagement;
import com.szyciov.lease.entity.TreeNode;
import com.szyciov.lease.param.RoleManagementQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("roleManagementService")
public class RoleManagementService {
	private RoleManagementDao dao;

	@Resource(name = "RoleManagementDao")
	public void setDao(RoleManagementDao dao) {
		this.dao = dao;
	}

	public PageBean getRoleManagementByQuery(RoleManagementQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<RoleManagement> list = getRoleManagementListByQuery(queryParam);
		int iTotalRecords = getRoleManagementListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private List<RoleManagement> getRoleManagementListByQuery(RoleManagementQueryParam queryParam) {
		return dao.getRoleManagementListByQuery(queryParam);
	}

	private int getRoleManagementListCountByQuery(RoleManagementQueryParam queryParam) {
		return dao.getRoleManagementListCountByQuery(queryParam);
	}

	public Map<String, String> deleteRoleManagement(String id) {
		Map<String, String> ret = new HashMap<String, String>();
		if(dao.hasUser4ThisRole(id)){
			ret.put("status", "fail");
			ret.put("message", "角色已分配给了用户，请先解除绑定！");
			return ret;
		}
		ret.put("status", "success");
		ret.put("message", "删除角色成功");
		dao.deleteRoleManagement(id);
		return ret;
	}

	public RoleManagement getById(String id) {
		return dao.getById(id);
	}

	public Map<String, String> createRoleManagement(RoleManagement roleManagement) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("status", "success");
		ret.put("message", "角色创建成功");
		Map<String,String> params = new HashMap<String,String>();
		params.put("rolename", roleManagement.getRolename());
		params.put("leasescompanyid", roleManagement.getLeasescompanyid());
		int count = dao.getByRoleName(params);
		if(count>0){
			ret.put("status", "fail");
			ret.put("message", "角色名称已经存在");
		}else{
			ret.put("status", "success");
			ret.put("message", "角色创建成功");
			roleManagement.setId(GUIDGenerator.newGUID());
			dao.createRoleManagement(roleManagement);
		}
		return ret;
	}

	public Map<String, String> updateRoleManagement(RoleManagement roleManagement) {
		Map<String, String> ret = new HashMap<String, String>();
		//验证角色名称是否重复
		RoleManagement management = new RoleManagement();
		management.setRolename(roleManagement.getRolename());
		management.setLeasescompanyid(roleManagement.getLeasescompanyid());
		List<RoleManagement> managementList = dao.getRolemanagementByName(management);
		if(null != managementList && !managementList.isEmpty()) {
			for (RoleManagement object : managementList) {
				if(!object.getId().equals(roleManagement.getId())) {
					ret.put("status", "fail");
					ret.put("message", "角色名称已经存在");
					return ret;
				}
			}
		}
		ret.put("status", "success");
		ret.put("message", "角色修改成功");
		dao.updateRoleManagement(roleManagement);
		return ret;
	}

	public Map<String, Object> getDataTreeById(String id) {
		Map<String, Object> res = new HashMap<String, Object>();
		RoleManagement rolemanagement = getById(id);
		res.put("rolename", rolemanagement.getRolename());
		res.put("roleid", id);
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		res.put("treenodelist", treeNodeList);
		List<OrgOrgan> authirgs = rolemanagement.getOrg4lease();
		List<String> orgids = new ArrayList<String>();
		if(authirgs!=null&&authirgs.size()>0){
			for(int i=0;i<authirgs.size();i++){
				orgids.add(authirgs.get(i).getId());
			}
		}
		List<OrgOrgan> listorg = dao.getAvailableOrgList(rolemanagement.getLeasescompanyid());
		Map<String,TreeNode> temp = new HashMap<String,TreeNode>();
		if(listorg!=null&&listorg.size()>0){
			for(int i=0;i<listorg.size();i++){
				OrgOrgan organ = listorg.get(i);
				String city = organ.getCitycaption();
				String cityid = organ.getCity();
				if(!temp.containsKey(city)){
					TreeNode citynode = new TreeNode();
					citynode.setName(city);
					citynode.setId(cityid);
					citynode.setOpen(true);
					citynode.setIsParent(true);
					citynode.setNocheck(true);
					List<TreeNode> children = new ArrayList<TreeNode>();
					citynode.setChildren(children);
					treeNodeList.add(citynode);
					temp.put(city, citynode);
				}
				TreeNode parentnode = temp.get(city);
				List<TreeNode> children = parentnode.getChildren();
				TreeNode selfnode = new TreeNode();
				selfnode.setId(organ.getId());
				selfnode.setName(organ.getFullName());
				selfnode.setIsParent(false);
				selfnode.setNocheck(false);
				if(orgids.contains(organ.getId())){
					selfnode.setChecked(true);
				}
				selfnode.setOpen(true);
				selfnode.setPid(cityid);
				children.add(selfnode);
			}
		}
		return res;
	}

	@Transactional
	public Map<String, String> assignDataAuthority(Map<String, Object> params) {
		Map<String, String> res = new HashMap<String, String>();
		String roleid = (String) params.get("roleid");
		try{
			//删除原有权限
			dao.deleteDataAuthorityByRole(roleid);
			//添加现在权限
			List<Map<String,String>> newparam = new ArrayList<Map<String,String>>();
			List<String> orgids = (List<String>) params.get("orgids");
			for(int i=0;i<orgids.size();i++){
				Map<String,String> roledata = new HashMap();
				roledata.put("id", GUIDGenerator.newGUID());
				roledata.put("roleid", roleid);
				roledata.put("orgid", orgids.get(i));
				newparam.add(roledata);
			}
			if(newparam.size()>0){
				dao.addDataAuthority(newparam);
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "分配数据权限失败");
			return res;
		}
		res.put("status", "success");
		res.put("message", "分配数据权限成功");
		return res;
	}
	
	public Map<String, Object> getMenuTreeById(String id) {
		Map<String, Object> res = new HashMap<String, Object>();
		RoleManagement rolemanagement = getById(id);
		res.put("rolename", rolemanagement.getRolename());
		res.put("roleid", id);
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		res.put("treenodelist", treeNodeList);

		//获取有权限的menu的功能id
		List<String> menufunctionids = dao.getMenuFunctionIdByRoleId(id);
		
		List<Menu> listmenu = dao.getAvailableMenuList();
		Map<String,TreeNode> temp = new HashMap<String,TreeNode>();
		for(int i=0;i<listmenu.size();i++){
			Menu menu = listmenu.get(i);
			boolean canadd = true;
			TreeNode treenode = new TreeNode();
			//menu的functionid如果重复可能在赋值权限的时候会出问题，但是现在的情况不可能有重复的
			treenode.setId(menu.getFunctionid());
			treenode.setName(menu.getMenuname());
			treenode.setNocheck(false);
			treenode.setOpen(true);
			
			if(menufunctionids!=null&&menufunctionids.contains(menu.getFunctionid())){
				treenode.setChecked(true);
			}
			treenode.setPid(menu.getParentid());
			if(menu.getParentid()!=null&&!"".equalsIgnoreCase(menu.getParentid())){
				TreeNode parentnode = temp.get(menu.getParentid());
				if(parentnode==null){
					//查询出来的数据没有按级次顺序
					throw new RuntimeException("获取菜单树形结构出错");
				}
				//父级节点没有选中问题修改
				if((parentnode.getChecked()==null||!parentnode.getChecked())&&(treenode.getChecked()!=null&&treenode.getChecked())){
					parentnode.setChecked(true);
				}
				List<TreeNode> childtreenodes = parentnode.getChildren();
				if(childtreenodes==null){
					childtreenodes = new ArrayList<TreeNode>();
					parentnode.setChildren(childtreenodes);
				}
				childtreenodes.add(treenode);
				canadd = false;
			}
			//查询所有菜单的时候必须是1级在前，2级在后等顺序，不然这个方法会有问题
			temp.put(menu.getId(), treenode);
			if (canadd) {
				treeNodeList.add(treenode);
			}
		}
		return res;
	}
	
	@Transactional
	public Map<String, String> assignFunctionAuthority(Map<String, Object> params) {
		Map<String, String> res = new HashMap<String, String>();
		String roleid = (String) params.get("roleid");
		try{
			//删除原有权限
			dao.deleteFunctionAuthorityByRole(roleid);
			//添加现在权限
			List<Map<String,String>> newparam = new ArrayList<Map<String,String>>();
			List<String> functionids = (List<String>) params.get("functionids");
			for(int i=0;i<functionids.size();i++){
				Map<String,String> roledata = new HashMap();
				roledata.put("id", GUIDGenerator.newGUID());
				roledata.put("roleid", roleid);
				roledata.put("functionid", functionids.get(i));
				newparam.add(roledata);
			}
			if(newparam.size()>0){
				dao.addFunctionAuthority(newparam);
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "分配功能权限失败");
			return res;
		}
		res.put("status", "success");
		res.put("message", "分配功能权限成功");
		return res;
	}

	/**
	 * 判断是否拥有权限
	 * @param params
	 * @return
	 */
	public boolean hasFeature(Map<String, Object> params) {
		return dao.hasFeature(params);
	}
	
	/**
	 * 查询租赁公司下的所有角色名称(select2)
	 * @param object
	 * @return
	 */
	public List<Map<String, String>> getLeRolemanagementBySelect(RoleManagement object) {
		return dao.getLeRolemanagementBySelect(object);
	}

	/**
	 * 返回 指定租赁公司下 拥有某个功能的所有角色ID
	 * @param leaseId		租赁公司ID
	 * @param functionName	功能名称
	 * @return
	 */
	public List<String> listRoleId(String leaseId,String functionName){
		return dao.listRoleId(leaseId,functionName);
	}
}
