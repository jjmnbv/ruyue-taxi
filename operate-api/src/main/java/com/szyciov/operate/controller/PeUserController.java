package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserdisablelog;
import com.szyciov.op.param.PeUserQueryParam;
import com.szyciov.operate.service.PeUserService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 用户管理
 */
@Controller
public class PeUserController extends BaseController {
	private static final Logger logger = Logger.getLogger(PeUserController.class);

	public PeUserService peUserService;

	@Resource(name = "PeUserService")
	public void setPeUserService(PeUserService peUserService) {
		this.peUserService = peUserService;
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/GetPeUserByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPeUserByQuery(@RequestBody PeUserQueryParam queryParam) {
		return peUserService.getPeUserByQuery(queryParam);
	}
	
	@RequestMapping(value = "api/PeUser/GetPeUserListCountByQuery", method = RequestMethod.POST)
	@ResponseBody
	public int getPeUserListCountByQuery(@RequestBody PeUserQueryParam queryParam) {
		return peUserService.getPeUserListCountByQuery(queryParam);
	}
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/ResetPassword/{id}/{orgUserAccount}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> resetPassword(@PathVariable String id,@PathVariable String orgUserAccount) {
		return peUserService.resetPassword(id,orgUserAccount);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/Enable/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> enable(@PathVariable String id) {
		return peUserService.enable(id);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PeUser getById(@PathVariable String id) {
		return peUserService.getById(id);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/GetPeUserdisablelogByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPeUserdisablelogByQuery(@RequestBody QueryParam queryParam) {
		return peUserService.getPeUserdisablelogByQuery(queryParam);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<PeUser> exportData(@RequestBody PeUserQueryParam peUserQueryParam) {
		return peUserService.exportData(peUserQueryParam);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/PeUser/CreatePeUserdisablelog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPeUserdisablelog(@RequestBody PeUserdisablelog peUserdisablelog) {
		return peUserService.createPeUserdisablelog(peUserdisablelog);
	}
}
