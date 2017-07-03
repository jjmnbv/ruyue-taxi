package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.List;
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

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.PubVehicleQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
public class PubVehicleScopeController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehicleScopeController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping("/PubVehicleScope/CheckLoadAsDefault")
	@ResponseBody
	public int checkLoadAsDefault(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Dictionary dictionary = new Dictionary();
		dictionary.setType("经营区域");
		dictionary.setValue(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubVehicleScope/CheckLoadAsDefault", HttpMethod.POST, userToken, dictionary,
				int.class);
	}
	
	@RequestMapping(value = "/PubVehicleScope/LoadAsDefault")
	@ResponseBody
	public List<City> loadAsDefault(HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Dictionary dictionary = new Dictionary();
		dictionary.setType("经营区域");
		dictionary.setValue(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubVehicleScope/LoadAsDefault", HttpMethod.POST,
				userToken, dictionary, List.class);
	}

}
