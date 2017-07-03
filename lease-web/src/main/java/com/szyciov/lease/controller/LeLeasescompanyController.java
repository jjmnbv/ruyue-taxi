package com.szyciov.lease.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.TemplateHelper;

@Controller
public class LeLeasescompanyController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeLeasescompanyController.class);
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String regEx_link = "<link[^>]*?>[\\s\\S]*?<\\/link>"; // 定义link的正则表达式  
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/LeLeasescompany/Index")
	public ModelAndView getLeVehiclemodelsIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getId();
		LeLeasescompany leLeasescompany = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,
				null,LeLeasescompany.class,id);
		mav.addObject("leLeasescompany", leLeasescompany);
        mav.setViewName("resource/leLeasescompany/index");  
        return mav; 
	}
	@RequestMapping("/LeLeasescompany/CheckPassword")
	@ResponseBody
	public Map<String, String> checkPassword(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> ret = new HashMap<String, String>();
		String password = PasswordEncoder.encode(user.getOldPassword());
		String id = this.getLoginLeUser(request).getId();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user1 = templateHelper.dealRequestWithToken("/User/GetById/{id}", HttpMethod.GET, userToken, null, User.class, id);
		if(password.equals(user1.getUserpassword())){
			user.setId(this.getLoginLeUser(request).getId());
			user.setAccount(user1.getAccount());
			templateHelper.dealRequestWithToken("/LeLeasescompany/UpdatePassword", HttpMethod.POST, userToken, user, void.class);
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "旧密码错误");
		}
		return ret;
	}
	
	@RequestMapping("/LeLeasescompany/UpdateLeLeasescompany")
	@ResponseBody
	public Map<String, String> updateLeLeasescompany(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leLeasescompany.setUpdater(this.getLoginLeUser(request).getNickname());
		leLeasescompany.setUserId(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/LeLeasescompany/UpdateLeLeasescompany", HttpMethod.POST, userToken, leLeasescompany,
				Map.class);
	}
	
	@RequestMapping("/LeLeasescompany/UpdateToC")
	@ResponseBody
	public Map<String, String> updateToC(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leLeasescompany.setUpdater(this.getLoginLeUser(request).getNickname());
		leLeasescompany.setTocState("1");//申请加入toc
		leLeasescompany.setUserId(this.getLoginLeUser(request).getId());
		if(getLeCompanyAgreement(request,response)!=null){
			return templateHelper.dealRequestWithToken("/LeLeasescompany/UpdateToC", HttpMethod.POST, userToken, leLeasescompany,
					Map.class);
		}else{
			Map<String, String> map = new HashMap<>();
			map.put("ResultSign", "Error");
			map.put("MessageKey", "没有《加入toC协议》，请和运管端管理员联系");
			return map;
		}
	}
	@RequestMapping("/LeLeasescompany/OutToC")
	@ResponseBody
	public Map<String, String> outToC(@RequestBody LeLeasescompany leLeasescompany, HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		leLeasescompany.setUpdater(this.getLoginLeUser(request).getNickname());
		leLeasescompany.setTocState("3");//申请加入toc
		leLeasescompany.setUserId(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/LeLeasescompany/OutToC", HttpMethod.POST, userToken, leLeasescompany,
				Map.class);
	}
	
	@RequestMapping("/LeLeasescompany/GetLeCompanyAgreement")
	@ResponseBody
	public LeCompanyAgreement getLeCompanyAgreement(HttpServletRequest request,
			HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getLeasescompanyid();
		LeCompanyAgreement leCompanyAgreement = templateHelper.dealRequestWithToken("/LeLeasescompany/GetLeCompanyAgreement/{id}", HttpMethod.GET, userToken, null,
				LeCompanyAgreement.class,id);
		if(leCompanyAgreement.getContent() != null ){
			leCompanyAgreement.setContent(delHTMLTag(leCompanyAgreement.getContent()));
		}
		return leCompanyAgreement;
	}
    /** 
     * @param htmlStr 
     * @return 
     *  删除Html标签 
     */  
    public static String delHTMLTag(String htmlStr) {  
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
        
        Pattern p_link = Pattern.compile(regEx_link, Pattern.CASE_INSENSITIVE);  
        Matcher m_link = p_link.matcher(htmlStr);  
        htmlStr = m_link.replaceAll(""); // 过滤link标签  
  
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
        Matcher m_html = p_html.matcher(htmlStr);  
        htmlStr = m_html.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
        return htmlStr.trim(); // 返回文本字符串  
    } 
}
