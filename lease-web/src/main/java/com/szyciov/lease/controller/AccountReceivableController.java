package com.szyciov.lease.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class AccountReceivableController extends BaseController {
	private static final Logger logger = Logger.getLogger(AccountReceivableController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/AccountReceivable/Index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
				null,LeLeasescompany.class,id);
		mav.addObject("leLeasescompany", leLeasescompany);
        mav.setViewName("resource/accountReceivable/index");  
        return mav; 
	}

	@RequestMapping(value = "/AccountReceivable/AllocationIndex")
	public ModelAndView allocationIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
				null,LeLeasescompany.class,id);
		mav.addObject("leLeasescompany", leLeasescompany);
		String allocation = request.getParameter("allocation");
		if(allocation.equals("alipay")){
			mav.setViewName("resource/accountReceivable/alipay");  
		}else if(allocation.equals("wechat")){
			mav.setViewName("resource/accountReceivable/wechat");  
		}
        return mav; 
	}
	
	@RequestMapping("/AccountReceivable/UpdateAlipay")
	@ResponseBody
	public Map<String, String> updateAlipay(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leLeasescompany.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/AccountReceivable/UpdateAlipay", HttpMethod.POST, userToken, leLeasescompany,
				Map.class);
	}


	
	@RequestMapping("/AccountReceivable/UpdateWechat")
	@ResponseBody
	public Map<String, String> updateWechat(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leLeasescompany.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/AccountReceivable/UpdateWechat", HttpMethod.POST, userToken, leLeasescompany,
				Map.class);
	}

    @RequestMapping("/AccountReceivable/driver/UpdateAlipay")
    @ResponseBody
    public Map<String, String> updateDriverAlipay(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
                                                  HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        leLeasescompany.setUpdater(this.getLoginLeUser(request).getId());
        return templateHelper.dealRequestWithToken("/AccountReceivable/driver/UpdateAlipay", HttpMethod.POST, userToken, leLeasescompany,
                Map.class);
    }

    @RequestMapping("/AccountReceivable/driver/UpdateWechat")
    @ResponseBody
    public Map<String, String> updateDriverWechat(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
                                            HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        leLeasescompany.setUpdater(this.getLoginLeUser(request).getId());
        return templateHelper.dealRequestWithToken("/AccountReceivable/driver/UpdateWechat", HttpMethod.POST, userToken, leLeasescompany,
                Map.class);
    }



	/**
	 * 前台判断，看leleasecompany中支付宝、微信是否有数据，
	 * */
	@RequestMapping("/AccountReceivable/CheckAlipayOrWechat")
	@ResponseBody
	public LeLeasescompany checkAlipayOrWechat(HttpServletRequest request,
			HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		return templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
				null,LeLeasescompany.class,id);
	}
	
	@RequestMapping("/AccountReceivable/OpenOrCloseAlipay")
	@ResponseBody
	public Map<String, String> openOrCloseAlipay(@RequestParam String obj,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		LeLeasescompany leLeasescompany = new LeLeasescompany();
		leLeasescompany.setId(this.getLoginLeUser(request).getLeasescompanyid());
		leLeasescompany.setUpdater(id);
		leLeasescompany.setAlipaystatus(obj);
		templateHelper.dealRequestWithToken("/AccountReceivable/OpenOrCloseAlipay", HttpMethod.POST, userToken, leLeasescompany,
				void.class);
		Map<String, String> map =  new HashMap();
		if(obj.equals("1")){
			map.put("succ","开启成功");
		}else{
			map.put("succ","禁用成功");
		}
		return map;
	}
	
	@RequestMapping("/AccountReceivable/OpenOrCloseWechat")
	@ResponseBody
	public Map<String, String> openOrCloseWechat(@RequestParam String obj,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		LeLeasescompany leLeasescompany = new LeLeasescompany();
		leLeasescompany.setId(this.getLoginLeUser(request).getLeasescompanyid());
		leLeasescompany.setUpdater(id);
		leLeasescompany.setWechatstatus(obj);
		templateHelper.dealRequestWithToken("/AccountReceivable/OpenOrCloseWechat", HttpMethod.POST, userToken, leLeasescompany,
				void.class);
		Map<String, String> map =  new HashMap();
		if(obj.equals("1")){
			map.put("succ","开启成功");
		}else{
			map.put("succ","禁用成功");
		}
		return map;
	}

    /**
     * 进入司机收款账户管理界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/AccountReceivable/driver/Index")
    public ModelAndView driverindex(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = this.getLoginLeUser(request).getId();
        LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
                null,LeLeasescompany.class,id);
        mav.addObject("leLeasescompany", leLeasescompany);
        mav.setViewName("resource/accountReceivable/driver/index");
        return mav;
    }

    /**
     * 进入司机收款账户管理修改界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/AccountReceivable/driver/AllocationIndex")
    public ModelAndView allocationDriverIndex(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = this.getLoginLeUser(request).getId();
        LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
                null,LeLeasescompany.class,id);
        mav.addObject("leLeasescompany", leLeasescompany);
        String allocation = request.getParameter("allocation");
        if(allocation.equals("alipay")){
            mav.setViewName("resource/accountReceivable/driver/alipay");
        }else if(allocation.equals("wechat")){
            mav.setViewName("resource/accountReceivable/driver/wechat");
        }
        return mav;
    }



    @RequestMapping("/AccountReceivable/driver/OpenOrCloseAlipay")
    @ResponseBody
    public Map<String, String> openOrCloseDriverAlipay(@RequestParam String obj,HttpServletRequest request,HttpServletResponse response){
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = this.getLoginLeUser(request).getId();
        LeLeasescompany leLeasescompany = new LeLeasescompany();
        leLeasescompany.setId(this.getLoginLeUser(request).getLeasescompanyid());
        leLeasescompany.setUpdater(id);
        leLeasescompany.setDriveralipaystatus(obj);
        templateHelper.dealRequestWithToken("/AccountReceivable/driver/OpenOrCloseAlipay", HttpMethod.POST, userToken, leLeasescompany,
                void.class);
        Map<String, String> map =  new HashMap();
        if(obj.equals("1")){
            map.put("succ","开启成功");
        }else{
            map.put("succ","禁用成功");
        }
        return map;
    }

    @RequestMapping("/AccountReceivable/driver/OpenOrCloseWechat")
    @ResponseBody
    public Map<String, String> openOrCloseDriverWechat(@RequestParam String obj,HttpServletRequest request,HttpServletResponse response){
        String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        String id = this.getLoginLeUser(request).getId();
        LeLeasescompany leLeasescompany = new LeLeasescompany();
        leLeasescompany.setId(this.getLoginLeUser(request).getLeasescompanyid());
        leLeasescompany.setUpdater(id);
        leLeasescompany.setDriverwechatstatus(obj);
        templateHelper.dealRequestWithToken("/AccountReceivable/driver/OpenOrCloseWechat", HttpMethod.POST, userToken, leLeasescompany,
                void.class);
        Map<String, String> map =  new HashMap();
        if(obj.equals("1")){
            map.put("succ","开启成功");
        }else{
            map.put("succ","禁用成功");
        }
        return map;
    }

}
