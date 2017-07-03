package com.szyciov.lease.controller;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubDriverSelectParam;
import com.szyciov.lease.param.PubVehicleQueryParam;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PubVehicleController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehicleController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubVehicle/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		List<City> pubCityaddr = templateHelper.dealRequestWithToken("/PubVehicle/GetPubCityaddr", HttpMethod.GET,
				userToken, null, List.class);
		List<PubVehicle> serviceCars = templateHelper.dealRequestWithToken("/PubVehicle/GetServiceCars/{leasesCompanyId}", HttpMethod.GET,
				userToken, null, List.class,leasesCompanyId);
		List<Dictionary> workStatus = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=服务状态", HttpMethod.GET,
				userToken, null, List.class,"服务状态");
		List<PubVehicle> city = templateHelper.dealRequestWithToken("/PubVehicle/GetCity/{leasesCompanyId}", HttpMethod.GET,
				userToken, null, List.class,leasesCompanyId);
		List<Dictionary> getPlateNoProvince = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateNoProvince", HttpMethod.GET,
				userToken, null, List.class);
		List<Dictionary> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
				userToken, null, List.class,"租赁公司");
		mav.addObject("pubCityaddr", pubCityaddr);
		mav.addObject("serviceCars", serviceCars);
		mav.addObject("workStatus", workStatus);
		mav.addObject("city", city);
		mav.addObject("getPlateNoProvince", getPlateNoProvince);
		mav.addObject("belongleasecompany", belongleasecompany);
        mav.setViewName("resource/pubVehicle/index");  
        return mav; 
	}
	
	@RequestMapping(value = "/PubVehicle/GetPlateNoCity")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Dictionary> getPlateNoCity(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/GetPlateNoCity/{id}", HttpMethod.GET,
				userToken, null, List.class,id);
	}
	
	@RequestMapping(value = "/PubVehicle/GetBrandCars")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getBrandCars(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setId(id);
		pubVehicle.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubVehicle/GetBrandCars", HttpMethod.POST,
				userToken, pubVehicle, List.class);
	}

	@RequestMapping("/PubVehicle/GetPubVehicleByQuery")
	@ResponseBody
	public PageBean getPubVehicleByQuery(@RequestBody PubVehicleQueryParam pubVehicleQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehicleQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubVehicle/GetPubVehicleByQuery", HttpMethod.POST, userToken,
				pubVehicleQueryParam,PageBean.class);
	}
	
	@RequestMapping("/PubVehicle/CreatePubVehicle")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubVehicle(@RequestBody PubVehicle pubVehicle, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehicle.setId(GUIDGenerator.newGUID());
		pubVehicle.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehicle.setCreater(this.getLoginLeUser(request).getId());
		pubVehicle.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehicle/CreatePubVehicle", HttpMethod.POST, userToken, pubVehicle,
				Map.class);
	}
	
	@RequestMapping("/PubVehicle/UpdatePubVehicle")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updatePubVehicle(@RequestBody PubVehicle pubVehicle, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BadHanyuPinyinOutputFormatCombination {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehicle.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehicle.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehicle/UpdatePubVehicle", HttpMethod.POST, userToken, pubVehicle,
				Map.class);
	}
	
	@RequestMapping("/PubVehicle/GetById")
	@ResponseBody
	public PubVehicle getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/GetById/{id}", HttpMethod.GET, userToken, null,
				PubVehicle.class, id);
	}
	
	@RequestMapping("/PubVehicle/CheckDelete")
	@ResponseBody
	public int checkDelete(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/CheckDelete/{id}", HttpMethod.GET, userToken, null,
				int.class, id);
	}
	
	@RequestMapping("/PubVehicle/DeletePubVehicle")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> deletePubVehcbrand(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/DeletePubVehicle/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}
	
	@RequestMapping("/PubVehicle/CheckPubVehicleVin")
	@ResponseBody
	public int checkPubVehicleVin(@RequestParam String id,@RequestParam String vin, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setId(id);
		pubVehicle.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehicle.setVin(vin);
		return templateHelper.dealRequestWithToken("/PubVehicle/CheckPubVehicle", HttpMethod.POST, userToken, pubVehicle,
				int.class);
	}
	
	@RequestMapping("/PubVehicle/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8= new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11= new ArrayList<Object>();
		List<Object> colData12= new ArrayList<Object>();
		
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		String queryBrandCars = request.getParameter("queryBrandCars");
		String queryPlateNo = request.getParameter("queryPlateNo");
		String queryWorkStatus = request.getParameter("queryWorkStatus");
		String queryCity = request.getParameter("queryCity");
		String queryServiceCars = request.getParameter("queryServiceCars");
		String queryVehicleType = request.getParameter("queryVehicleType");
		String queryVehicleStatus = request.getParameter("queryVehicleStatus");
		String queryBoundState = request.getParameter("queryBoundState");
		String belongleasecompanyQuery = request.getParameter("belongleasecompanyQuery");
		PubVehicleQueryParam pv = new PubVehicleQueryParam();
		pv.setLeasesCompanyId(leasesCompanyId);
		pv.setQueryBrandCars(queryBrandCars);
		pv.setQueryPlateNo(queryPlateNo);
		pv.setQueryWorkStatus(queryWorkStatus);
		pv.setQueryCity(queryCity);
		pv.setQueryServiceCars(queryServiceCars);
		pv.setQueryVehicleType(queryVehicleType);
		pv.setQueryVehicleStatus(queryVehicleStatus);
		pv.setQueryBoundState(queryBoundState);
		pv.setBelongleasecompanyQuery(belongleasecompanyQuery);
		List<Map> pubVehicle = templateHelper.dealRequestWithToken("/PubVehicle/ExportExcel", HttpMethod.POST,
				userToken, pv, List.class);
		for(int i=0;i<pubVehicle.size();i++){
			if(pubVehicle.get(i).get("vehicleType") != null){
				if(pubVehicle.get(i).get("vehicleType").toString().equals("1")){
					colData9.add("出租车");
				}else{
					colData9.add("网约车");
				}
			}else{
				colData9.add("");
			}
			if(pubVehicle.get(i).get("serviceCars") != null){
				colData4.add(pubVehicle.get(i).get("serviceCars"));
			}else{
				colData4.add("/");
			}
			if(pubVehicle.get(i).get("boundState") != null){
				if(pubVehicle.get(i).get("boundState").equals("0")){
					colData10.add("未绑定");
				}else{
					colData10.add("已绑定");
				}
			}else{
				colData10.add("");
			}
			if(pubVehicle.get(i).get("vehicleStatus") != null){
				if(pubVehicle.get(i).get("vehicleStatus").equals("0")){
					colData11.add("营运中");
				}else{
					colData11.add("维修中");
				}
			}else{
				colData11.add("");
			}
			if(pubVehicle.get(i).get("showPlateNo") != null){
				colData1.add(pubVehicle.get(i).get("showPlateNo"));
			}else{
				colData1.add("");
			}
			if(pubVehicle.get(i).get("brandCars") != null){
				colData3.add(pubVehicle.get(i).get("brandCars"));
			}else{
				colData3.add("");
			}
			if(pubVehicle.get(i).get("vin") != null){
				colData2.add(pubVehicle.get(i).get("vin"));
			}else{
				colData2.add("");
			}
			if(pubVehicle.get(i).get("color") != null){
				colData5.add(pubVehicle.get(i).get("color"));
			}else{
				colData5.add("");
			}
			if(pubVehicle.get(i).get("loads") != null){
				colData7.add(pubVehicle.get(i).get("loads"));
			}else{
				colData7.add("");
			}
			if(pubVehicle.get(i).get("city") != null){
				colData6.add(pubVehicle.get(i).get("city"));
			}else{
				colData6.add("");
			}
			if(pubVehicle.get(i).get("businessScope") != null){
				colData8.add(pubVehicle.get(i).get("businessScope"));
			}else{
				colData8.add("");
			}
			if(pubVehicle.get(i).get("belongleasecompanyName") != null){
				colData12.add(pubVehicle.get(i).get("belongleasecompanyName"));
			}else{
				colData12.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("车辆管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("车辆类型");
		colName.add("服务车型");
		colName.add("绑定状态");
		colName.add("营运状态");
		colName.add("车牌号");
		colName.add("品牌车系");
		colName.add("车架号");
		colName.add("颜色");
		colName.add("荷载人数");
		colName.add("登记城市");
		colName.add("归属车企");
		colName.add("经营区域");
		excel.setColName(colName);
		colData.put("车辆类型",colData9);
		colData.put("服务车型",colData4);
		colData.put("绑定状态",colData10);
		colData.put("营运状态",colData11);
		colData.put("车牌号",colData1);
		colData.put("品牌车系",colData3);
		colData.put("车架号",colData2);
		colData.put("颜色",colData5);
		colData.put("荷载人数",colData7);
		colData.put("登记城市",colData6);
		colData.put("归属车企",colData12);
		colData.put("经营区域",colData8);
		
//		colData.put("车牌号", colData1);
//		colData.put("车架号", colData2);
//		colData.put("品牌车系", colData3);
//		colData.put("服务车型", colData4);
//		colData.put("司机信息", colData8);
//		colData.put("颜色", colData5);
//		colData.put("所属城市", colData6);
//		colData.put("荷载人数", colData7);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
	@RequestMapping("/PubVehicle/ImportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void importExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Map<String, String> ret = new HashMap<String, String>();
		response.setContentType("text/html;charset=utf-8");
		JSONObject ret = new JSONObject();
		StringBuffer rets = new StringBuffer();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("importfile");
		String fileName = mulfile.getOriginalFilename();
		String fileNames = fileName.substring(fileName.length()-3);
		String[] titles = new String[]{"车辆类型","车牌号","品牌车系","车架号","颜色","荷载人数","登记城市","归属车企","经营区域"};
		if(fileNames.equals("xls")){
			new ExcelImp().excelImp(mulfile, new ExcelRuleImport(){
				@Override
				public boolean VerifyFirstTitle(String[] columns) {
					if(columns.length!=titles.length){
						ret.put("ResultSign", "Error");
						ret.put("MessageKey", "模板错误，请按照下载模板填写数据");
						return false;
					}else{
						for(int i = 0;i<titles.length;i++){
							if(!titles[i].equals(columns[i])){
								ret.put("ResultSign", "Error");
								ret.put("MessageKey", "模板错误，请按照下载模板填写数据");
								return false;
							}
						}
					}
					return true;
				}
				@Override
				public boolean excelRuleImport(int index, Map<String, String> map) {
//					if(map.get("车辆类型") == null && map.get("车牌号") == null && map.get("品牌车系") == null && map.get("车架号") == null && map.get("颜色") == null && map.get("荷载人数") == null && map.get("登记城市") == null && map.get("经营区域") == null){
//						ret.put("ResultSign", "Error");
//						ret.put("MessageKey", "模板错误，请按照下载模板填写数据");
//						return false;
//					}
					//导入的字段正则验证
					//车辆类型
					if(!map.get("车辆类型").equals("网约车") && !map.get("车辆类型").equals("出租车")){
//						ret.put("MessageKey", map.get("车辆类型")+"车辆类型不存在");
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】车辆类型格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					//车牌号  车架号
					PubVehicle pubVehicle = new PubVehicle();
					pubVehicle.setLeasesCompanyId(leasesCompanyId);
					pubVehicle.setShowPlateNo(map.get("车牌号"));
					int countPlateNo = templateHelper.dealRequestWithToken("/PubVehicle/CheckPubVehicle", HttpMethod.POST, userToken, pubVehicle,
							int.class);
					//车牌号
					if(map.get("车牌号") !=null && !map.get("车牌号").equals("")){
						String regEx1 = "^[A-Za-z0-9]{5}$";
					    Pattern pattern1 = Pattern.compile(regEx1);
					    Matcher matcher1 = pattern1.matcher(map.get("车牌号").toString().substring(2, map.get("车牌号").length()));
						if(matcher1.matches()){
							//满足
						}else{
							//不满足
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】车牌号格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
						if(countPlateNo > 0){
	//							ret.put("MessageKey", map.get("车牌号")+"车牌号已存在");
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】车牌号已存在<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}else{
						rets.append("第"+index+"行 【】车牌号为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					PubVehicle pubVehicle2 = new PubVehicle();
					pubVehicle2.setLeasesCompanyId(leasesCompanyId);
					pubVehicle2.setVin(map.get("车架号"));
					int countVin = templateHelper.dealRequestWithToken("/PubVehicle/CheckPubVehicle", HttpMethod.POST, userToken, pubVehicle2,
							int.class);
					//车架号
					if(map.get("车架号") !=null && !map.get("车架号").equals("")){
						String regEx2 = "^[A-Z0-9]{17}$";
					    Pattern pattern2 = Pattern.compile(regEx2);
					    Matcher matcher2 = pattern2.matcher(map.get("车架号"));
						if(matcher2.matches()){
							//满足
						}else{
							//不满足
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】车架号格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					    if(countVin > 0){
//								ret.put("MessageKey", map.get("车架号")+"车架号已存在");
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】车架号已存在<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}else{
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】车架号为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					//绑定车的id 品牌车系
					PubVehicle p = new PubVehicle();
					String vehcline = map.get("品牌车系");
					String leasesCompanyId = new BaseController().getLoginLeUser(request).getLeasescompanyid();
					p.setLeasesCompanyId(leasesCompanyId);
					p.setBrandCars(vehcline);
					String vehclineId = templateHelper.dealRequestWithToken("/PubVehicle/GetVehclineId", HttpMethod.POST,
							userToken, p, String.class);
					if(vehclineId == null){
//						ret.put("MessageKey", map.get("品牌车系")+"品牌车系不存在");
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】品牌车系格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}else{
					   vehclineId = vehclineId.substring(1, vehclineId.length()-1);
					}
					//颜色
					
					//荷载人数
					//城市编码  登记城市
					String city = map.get("登记城市");
					PubCityAddr cityCode = templateHelper.dealRequestWithToken("/PubVehicle/GetCityCode/{city}", HttpMethod.POST,
							userToken, null, PubCityAddr.class,city);
					if(cityCode == null){
//						ret.put("MessageKey", city+"不存在");
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】登记城市格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					//归属车企
					String belongleasecompanyValue = "";
					if(map.get("归属车企").equals("") || map.get("归属车企") == null){
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】归属车企为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}else{
						List<Map> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
								userToken, null, List.class,"租赁公司");
						boolean flag = true;
						for(int i=0;i<belongleasecompany.size();i++){
							if(belongleasecompany.get(i).get("text").equals(map.get("归属车企"))){
								flag = false;
								belongleasecompanyValue = (String) belongleasecompany.get(i).get("value");
							}
						}
						if(flag){
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】归属车企不存在<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}
					//经营区域
					String cityScope = map.get("经营区域");
					boolean flag = true;
					StringBuilder s = new StringBuilder();
					StringBuffer retCity = new StringBuffer();
					if(cityScope != null && !cityScope.equals("")){
						String[] s1 = cityScope.split("[/|\\||、|\\s+]");
						for(int i=0;i<s1.length;i++){
							PubCityAddr pca = getPubCityAddr(request,s1[i]);
							if(pca != null){
								s.append(pca.getId()+",");
							}else{
	//							ret.put("MessageKey", city+"不存在");
								retCity.append(s1[i]+"、");
								flag = false;
							}
						}
						if(!flag){
							rets.append("第"+index+"行 【"+map.get("车牌号")+"】城市格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}else{
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】经营范围为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					//获取车牌号
					String plateNo = (String)(map.get("车牌号"));
					//获取车牌号的第一个字符省,第二个字符市,最后为车牌号
					String plateOne = plateNo.substring(0, 1);
					//查找对应的省编码
					PubDictionary plateOneCode = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateCode/{plateOne}", HttpMethod.POST,
							userToken, null, PubDictionary.class,plateOne);
					if(plateOneCode.getValue() == null || plateOneCode.getValue().equals("")){
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】车牌号格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					String plateTow = plateNo.substring(1,2);
					//查找对应市的编码
					plateOneCode.setText(plateTow);
					String plateTowCity = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateCity", HttpMethod.POST,
							userToken, plateOneCode, String.class);
					if(plateTowCity == null || plateTowCity.equals("")){
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】车牌号格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					plateTowCity = plateTowCity.substring(1, plateTowCity.length()-1);
					String plateThree = plateNo.substring(2,plateNo.length());
					try {
						PubVehicle pubVehicle1 = new PubVehicle();
						pubVehicle1.setId(GUIDGenerator.newGUID());
						pubVehicle1.setLeasesCompanyId(new BaseController().getLoginLeUser(request).getLeasescompanyid());
						pubVehicle1.setCreater(new BaseController().getLoginLeUser(request).getId());
						pubVehicle1.setUpdater(new BaseController().getLoginLeUser(request).getId());
						pubVehicle1.setPlateNoProvince(plateOneCode.getValue());
						pubVehicle1.setPlateNoCity(plateTowCity);
						pubVehicle1.setPlateNo(plateThree);
						pubVehicle1.setVin(map.get("车架号"));
						pubVehicle1.setVehclineId(vehclineId);
						pubVehicle1.setColor(map.get("颜色"));
						pubVehicle1.setCity(cityCode.getId());
						pubVehicle1.setBusinessScope(cityCode.getId());
						pubVehicle1.setLoads(Integer.valueOf(map.get("荷载人数")));
						if(map.get("车辆类型").equals("网约车")){
							pubVehicle1.setVehicleType(DriverEnum.DRIVER_TYPE_CAR.code);
						}else if(map.get("车辆类型").equals("出租车")){
							pubVehicle1.setVehicleType(DriverEnum.DRIVER_TYPE_TAXI.code);
						}
						pubVehicle1.setBusinessScope(s.substring(0, s.length()-1));
						pubVehicle1.setBelongleasecompany(belongleasecompanyValue);
						templateHelper.dealRequestWithToken("/PubVehicle/CreatePubVehicle", HttpMethod.POST, userToken, pubVehicle1,
								Map.class);
//						ret.put("MessageKey", "导入成功");
//						rets.append("导入成功<br>");
						ret.put("ResultSign", "Successfuls");
//						ret.put("MessageKey",rets.toString());
						return true;
					} catch (Exception e) {
						rets.append("第"+index+"行 【"+map.get("车牌号")+"】导入失败<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey", rets.toString());
						return false;
					}
				}
			});
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "请导入***.xls类型文件");
		}

		if(ret.get("ResultSign").equals("Successful")){
			ret.put("MessageKey",ret.get("MessageKey").toString());
		}
		if(ret.get("MessageKey")!=null){
			ret.put("ResultSign","Successful");
			ret.put("MessageKey",ret.get("MessageKey").toString());
		}
		response.getWriter().write(ret.toString());
	}
	
	public PubCityAddr getPubCityAddr(HttpServletRequest request,String city){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/GetCityCode/{city}", HttpMethod.POST,
				userToken, null, PubCityAddr.class,city);
	}
	
	/**
	 * 下载模板
	 * **/
	@RequestMapping("/PubVehicle/DownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setLeasesCompanyId(leasesCompanyId);
		List<Map<String, Object>> brand = templateHelper.dealRequestWithToken("/PubVehcline/GetBrandCars", HttpMethod.POST,
				userToken, pubVehicle, List.class);
		List<Map<String,Object>> belongleasecompany = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司", HttpMethod.GET,
				userToken, null, List.class,"租赁公司");
		colData1.add("网约车");
		colData2.add("京A88888");
		colData3.add(brand.get(0).get("text").toString());
		colData4.add("D1234567891234567");
		colData5.add("红色");
		colData6.add("5");
		colData7.add("北京市");
		colData9.add(belongleasecompany.get(0).get("text").toString());
		colData8.add("北京市、武汉");
		
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("车辆管理.xls");
		List<String> colName = new ArrayList<String>();
		colName.add("车辆类型");
		colName.add("车牌号");
		colName.add("品牌车系");
		colName.add("车架号");
		colName.add("颜色");
		colName.add("荷载人数");
		colName.add("登记城市");
		colName.add("归属车企");
		colName.add("经营区域");
		excel.setColName(colName);
		
		colData.put("车辆类型", colData1);
		colData.put("车牌号", colData2);
		colData.put("品牌车系", colData3);
		colData.put("车架号", colData4);
		colData.put("颜色", colData5);
		colData.put("荷载人数", colData6);
		colData.put("登记城市", colData7);
		colData.put("归属车企", colData9);
		colData.put("经营区域", colData8);
		
		excel.setColData(colData);
		
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		//导出excel序列的值
		String[] s = {"网约车","出租车"}; 
		String[] s1 = new String[brand.size()]; 
		for(int i=0;i<brand.size();i++){
			s1[i] = brand.get(i).get("text").toString();
		}
		String[] s2 = new String[belongleasecompany.size()];
		for(int i=0;i<belongleasecompany.size();i++){
			s2[i] = (String)belongleasecompany.get(i).get("text");
		}
		//起始行序号，序列值，起始列序号，终止列序号
		ee.createExcel(tempFile,s,s1,s2);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}


	/**
	 * 返回车辆信息及绑定信息
	 * @return
	 */
	@RequestMapping(value = "/PubVehicle/bind/list")
	@ResponseBody
	public JSONObject listPubVehicleBind(@RequestBody PubVehicleQueryParam pubVehicleQueryParam, HttpServletRequest request) {

		String usertoken = getUserToken(request);

//		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);

		pubVehicleQueryParam.setPlatformtype(PlatformTypeByDb.LEASE.code);
		pubVehicleQueryParam.setLeasesCompanyId(user.getLeasescompanyid());


		JSONObject jsonObject =  templateHelper.dealRequestWithToken("/PubVehicle/bind/list", HttpMethod.POST, usertoken, pubVehicleQueryParam,
				JSONObject.class);


		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONObject json = jsonObject.getJSONObject("data");
			return json;
		}

		return jsonObject;
	}




	/**
	 * 根据车架号联想下拉框  返回车辆ID
	 * @param paramMap		下拉框查询参数
	 * @return
	 */
	@RequestMapping(value = "/Vehicle/listVehicleBySelect")
	@ResponseBody
	public JSONArray listVehicleBySelect(@RequestParam Map<String,String> paramMap, HttpServletRequest request) {

		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setPlatformtype(PlatformTypeByDb.LEASE.code);
		param.setLeasescompanyid(user.getLeasescompanyid());


		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/Vehicle/listVehicleBySelect",
				HttpMethod.POST, usertoken, param, JSONObject.class);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}


	/**
	 * 根据车牌号联想下拉框  返回车辆ID
	 * @param paramMap		下拉框查询参数
	 * @return
	 */
	@RequestMapping(value = "/Vehicle/listVehicleByPlateno")
	@ResponseBody
	public JSONArray listVehicleByPlateno(@RequestParam Map<String,String> paramMap, HttpServletRequest request) {

		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));
		param.setPlatformtype(PlatformTypeByDb.LEASE.code);
		param.setLeasescompanyid(user.getLeasescompanyid());


		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/Vehicle/listVehicleByPlateno",
				HttpMethod.POST, usertoken, param, JSONObject.class);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}


}
