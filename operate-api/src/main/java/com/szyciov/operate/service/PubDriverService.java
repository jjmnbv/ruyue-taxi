package com.szyciov.operate.service;


import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubDriverAccount;
import com.szyciov.op.param.PubDriverQueryParam;
import com.szyciov.op.vo.pubdriver.ExportDriverVo;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.PubDriverAccountDao;
import com.szyciov.operate.dao.PubDriverDao;
import com.szyciov.org.entity.OrgOrganDriverRef;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PubDriverService")
public class PubDriverService {
	private PubDriverDao dao;

	private TemplateHelper templateHelper = new TemplateHelper();
	@Resource(name = "PubDriverDao")
	public void setDao(PubDriverDao dao) {
		this.dao = dao;
	}
	
	private PubDriverAccountDao orgDriverAccountDao;
	@Resource(name = "PubDriverAccountDao")
	public void setDao(PubDriverAccountDao dao) {
		this.orgDriverAccountDao = dao;
	}

	@Autowired
	private OpInformationSetDao opInfodao;



	public Map<String, String> createPubDriver(PubDriver pubDriver) {
		Map<String, String> ret = new HashMap<String, String>();

		String result = this.verifyDriver(pubDriver);

		if(StringUtils.isNotEmpty(result)){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", result);
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "创建成功");//PasswordEncoder.encode(
			String password =UNID.getUNID(null, 6);
			pubDriver.setUserPassword(PasswordEncoder.encode(password));
			//判断是出租车就没有司机身份
			if(pubDriver.getVehicleType().equals("1")){
				pubDriver.setIdEntityType("2");
			}
			//绑定状态默认为0
			pubDriver.setBoundState(BindingStateEnum.UN_BINDING.code);
			//锁定状态默认未锁定
			pubDriver.setLockStatus(DriverEnum.LOCK_STATUS_UNLOCK.code);
			dao.createPubDriver(pubDriver);
			//添加司机账户
			PubDriverAccount orgDriverAccount = new PubDriverAccount();
			orgDriverAccount.setId(GUIDGenerator.newGUID());
			orgDriverAccount.setDriverid(pubDriver.getId());
			orgDriverAccount.setLeasescompanyid(pubDriver.getLeasesCompanyId());
			orgDriverAccount.setBalance(0);
			orgDriverAccount.setPlatformtype(Integer.parseInt(PlatformTypeByDb.OPERATING.code));
			orgDriverAccountDao.createOrgDriverAccount(orgDriverAccount);

			List<String> list = new ArrayList<String>();
			list.add(pubDriver.getPhone());
			//"模板：“您的司机账号：XXXXXX;随机密码：888888，请使用账号密码登录司机端（租赁公司简称）”
//			String content = "您的司机账号"+pubDriver.getPhone()+";随机密码："+password+"，请使用账号密码登录司机端（"+getLeLeasescompany(pubDriver.getLeasesCompanyId()).getShortName()+"）";



			String content =SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.pubdriverservice.adddriver", pubDriver.getPhone(),password);
			UserMessage um = new UserMessage(list,content,UserMessage.ADDUSER);
			MessageUtil.sendMessage(um);

		}
		return ret;
	}
	
	public Map<String, String> updatePubDriver(PubDriver pubDriver){
		Map<String, String> ret = new HashMap<String, String>();

		String result = this.verifyDriver(pubDriver);

		if(StringUtils.isNotEmpty(result)){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", result);
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
			//判断是出租车就没有司机身份
			dao.updatePubDriver(pubDriver);
		}
		return ret;
	};
	public JSONObject returns(UserNewsParam unp){
		return templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				unp,JSONObject.class);
	}
	public int checkPubDriver(PubDriver pubDriver){
		return dao.checkPubDriver(pubDriver);
	}
	
	public PubDriver getByPubDriverId(String id){
		return dao.getByPubDriverId(id);
	}
	
	public Map<String, String> resetPassword(String id){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "重置成功");
		PubDriver pubDriver = new PubDriver();
		String password = UNID.getUNID(null, 6);
		pubDriver.setId(id);
		pubDriver.setUserPassword(PasswordEncoder.encode(password));
		dao.resetPassword(pubDriver);


		PubDriver pub = getById(id);
		List<String> list = new ArrayList<String>();
		list.add(pub.getPhone());
		//"模板：“您的司机账号XXXXXX密码已重置为：{X}，请使用新密码登录,详情请咨询{0}（租赁公司简称）”
		//X：密码是随机生成
		//0：租赁端客服电话"
//		String content = "您的司机账号"+pub.getPhone()+"密码已重置为："+password+",请使用新密码登录,详情请咨询"+getLeLeasescompany(leasesCompanyId).getServicesPhone()+"（"+getLeLeasescompany(leasesCompanyId).getShortName()+"）";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.pubdriverservice.resetpassword", pub.getPhone(),password,opInfodao.getOpPlatformInfo().getServcieTel());
		UserMessage um = new UserMessage(list,content,UserMessage.CHANGEPASSWORD);
		MessageUtil.sendMessage(um);

		return ret;
	};
	
	public int checkDelete(String id){
		return dao.checkDelete(id);
	};
	
	public Map<String, String> deletePubDriver(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "请解绑车辆在删除");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deletePubDriver(id);

			
		}
		return ret;
	};
	
	public List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListByQuery(pubDriverQueryParam);
	};
	
	public List<PubDriver> getPubDriverByBound(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListByBound(pubDriverQueryParam);
	};
	
	public int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam){
		return dao.getPubDriverListCountByQuery(pubDriverQueryParam);
	};
	

	
	public PageBean getPubDriverByQuery(PubDriverQueryParam pubDriverQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubDriverQueryParam.getsEcho());

		
		List<PubDriver> list = getPubDriverListByQuery(pubDriverQueryParam);
		for(PubDriver l:list){
//
			// 0男 1女
			if(l.getSex().equals("0")){
				l.setSex("男");
			}else{
				l.setSex("女");
			}

		}
		int iTotalRecords = getPubDriverListCountByQuery(pubDriverQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	public List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam){
		return dao.getCity(pubDriverQueryParam);
	};
	
	public PubDriver getById(String id){
		PubDriver pubDriver = dao.getById(id);
		return pubDriver;
	};
	
	public int judgeBinding(String id){
		return dao.judgeBinding(id);
	};

	public List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam){
		return dao.getSpecialDri(pubDriverQueryParam);
	};
	
	public List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName){
		return dao.getFullNameBySpecialDri(fullName);
	};
	
	public List<ExportDriverVo> exportData(PubDriverQueryParam pubDriverQueryParam){
		return dao.exportData(pubDriverQueryParam);
	}


	public int checkUnbundling(String driverId){
		return dao.checkUnbundling(driverId);
	};
	

	/**
	 * 验证司机状态
	 * @param driver
	 * @return
	 */
	public String verifyDriver(PubDriver driver){
		if(!verifyPhone(driver)){
			return "【"+driver.getPhone()+"】手机号码已存在";
		}
		if(!verifyDriversNum(driver)){
			return "【"+driver.getDriversNum()+"】驾驶证号码已存在";
		}
		if(!verifyIdCardNum(driver)){
			return "【"+driver.getIdCardNum()+"】身份证号码已存在";
		}
		if(!verifyJobNum(driver)){
			return "【"+driver.getJobNum()+"】资格证号已存在";
		}

		return null;
	}


	/**
	 * 验证手机号
	 * @param driver
	 * @return
	 */
	private boolean verifyPhone(PubDriver driver){
		PubDriver parm = new PubDriver();
		parm.setPhone(driver.getPhone());
		parm.setId(driver.getId());
		parm.setPlatformType(driver.getPlatformType());
		return (dao.checkPubDriverPhone(parm)>0)?false:true;
	}

	/**
	 * 验证驾驶证号重复
	 * @param driver
	 * @return
	 */
	private boolean verifyDriversNum(PubDriver driver){
		PubDriver parm = new PubDriver();
		parm.setDriversNum(driver.getDriversNum());
		parm.setId(driver.getId());
		parm.setPlatformType(driver.getPlatformType());
		return (dao.checkPubDriver(parm)>0)?false:true;
	}

	/**
	 * 验证身份证号重复
	 * @param driver
	 * @return
	 */
	private boolean verifyIdCardNum(PubDriver driver){
		PubDriver parm = new PubDriver();
		parm.setIdCardNum(driver.getIdCardNum());
		parm.setId(driver.getId());
		parm.setPlatformType(driver.getPlatformType());
		return (dao.checkPubDriver(parm)>0)?false:true;
	}

	/**
	 * 验证资格证号重复
	 * @param driver
	 * @return
	 */
	private boolean verifyJobNum(PubDriver driver){
		PubDriver parm = new PubDriver();
		parm.setJobNum(driver.getJobNum());
		parm.setId(driver.getId());
		parm.setPlatformType(driver.getPlatformType());
		return (dao.checkPubDriver(parm)>0)?false:true;
	}
	
}


