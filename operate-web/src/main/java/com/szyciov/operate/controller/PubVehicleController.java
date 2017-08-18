package com.szyciov.operate.controller;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.param.PubDriverSelectParam;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.operate.service.PubVehicleService;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

	@Autowired
	private PubVehicleService pubVehicleService;

	@RequestMapping(value = "/PubVehicle/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<City> pubCityaddr = templateHelper.dealRequestWithToken("/PubVehicle/GetPubCityaddr", HttpMethod.GET,
				userToken, null, List.class);
		List<PubVehicle> serviceCars = templateHelper.dealRequestWithToken("/PubVehicle/GetServiceCars/{platformType}", HttpMethod.GET,
				userToken, null, List.class,PlatformTypeByDb.OPERATING.code);
		List<Dictionary> workStatus = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=服务状态", HttpMethod.GET,
				userToken, null, List.class,"服务状态");

		List<Dictionary> getPlateNoProvince = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateNoProvince", HttpMethod.GET,
				userToken, null, List.class);

		//服务车型
		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
		List<Map<String, String>> listBrandCars =  templateHelper.dealRequestWithToken("/PubVehicle/GetBrandCars", HttpMethod.POST,
				userToken, pubVehicle, List.class);



		mav.addObject("pubCityaddr", pubCityaddr);
		mav.addObject("serviceCars", serviceCars);
		mav.addObject("workStatus", workStatus);
		mav.addObject("listBrandCars", listBrandCars);
		mav.addObject("getPlateNoProvince", getPlateNoProvince);
		mav.setViewName("resource/pubVehicle/index");
		return mav;
	}

	@RequestMapping(value = "/PubVehicle/getCity")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCity(@RequestParam String queryText, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehicleQueryParam param = new PubVehicleQueryParam();
		param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		param.setQueryCity(queryText);
		List<Map<String,String>> citys = templateHelper.dealRequestWithToken("/PubVehicle/GetCity", HttpMethod.POST,
				userToken, param, List.class);

		List<Map<String, String>> list = new ArrayList<>();
		for(int i=0;i<citys.size();i++){
			Map<String,String> hashMap = new HashMap<>();
			hashMap.put("id",citys.get(i).get("id"));
			hashMap.put("text",citys.get(i).get("city"));
			list.add(hashMap);
		}
		return list;
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
//		pubVehicle.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubVehicle/GetBrandCars", HttpMethod.POST,
				userToken, pubVehicle, List.class);
	}

	@RequestMapping("/PubVehicle/GetPubVehicleByQuery")
	@ResponseBody
	public PageBean getPubVehicleByQuery(@RequestBody VehicleIndexQueryParam param, HttpServletRequest request,
										 HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		pubVehicleQueryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		param.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		return templateHelper.dealRequestWithToken("/PubVehicle/GetPubVehicleByQuery", HttpMethod.POST, userToken,
				param,PageBean.class);
	}

	@RequestMapping("/PubVehicle/CreatePubVehicle")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubVehicle(@RequestBody PubVehicle pubVehicle, HttpServletRequest request,
												HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehicle.setId(GUIDGenerator.newGUID());
		OpUser opUser = this.getLoginOpUser(request);
		pubVehicle.setCreater(opUser.getId());
		pubVehicle.setUpdater(opUser.getId());
		pubVehicle.setLeasesCompanyId(opUser.getOperateid());
		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
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
		OpUser opUser = this.getLoginOpUser(request);
		pubVehicle.setLeasesCompanyId(opUser.getOperateid());
		pubVehicle.setUpdater(this.getLoginOpUser(request).getId());

		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
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
	/**
	 * <p>
	 * 返回车辆数据
	 * </p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/PubVehicle/getEditVehicle/{id}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getEditVehicle(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return  templateHelper.dealRequestWithToken("/PubVehicle/getEditVehicle/{id}", HttpMethod.GET, userToken, null,
				JSONObject.class, id) ;
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
	public JSONObject deletePubVehcbrand(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehicle/DeletePubVehicle/{id}", HttpMethod.DELETE, userToken, null,
				JSONObject.class, id);
	}

	@RequestMapping("/PubVehicle/CheckPubVehicleVin")
	@ResponseBody
	public int checkPubVehicleVin(@RequestParam String id,@RequestParam String vin, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setId(id);
		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
		pubVehicle.setVin(vin);
		return templateHelper.dealRequestWithToken("/PubVehicle/CheckPubVehicle", HttpMethod.POST, userToken, pubVehicle,
				int.class);
	}

	@RequestMapping("/PubVehicle/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		String queryBrandCars = request.getParameter("queryBrandCars");
		String queryPlateNo = request.getParameter("queryPlateNo");
		String queryWorkStatus = request.getParameter("queryWorkStatus");
		String queryCity = request.getParameter("queryCity");
		String queryServiceCars = request.getParameter("queryServiceCars");
		String queryVehicleType = request.getParameter("queryVehicleType");
		String queryVehicleStatus = request.getParameter("queryVehicleStatus");
		String queryBoundState = request.getParameter("queryBoundState");
		VehicleIndexQueryParam pv = new VehicleIndexQueryParam();
		pv.setPlatenoStr(queryPlateNo);
		pv.setCity(queryCity);
		pv.setBoundstate(queryBoundState);
		pv.setVehiclestatus(queryVehicleStatus);
		pv.setVehicletype(queryVehicleType);
		pv.setWorkstatus(queryWorkStatus);
		pv.setBrandCars(queryBrandCars);
		pv.setServiceCars(queryServiceCars);
		pv.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		// excel文件
		File tempFile = new File("车辆管理.xls");

		Excel excel =pubVehicleService.exportExcel(pv,userToken) ;

		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);

	}
	@RequestMapping("/PubVehicle/ImportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public JSONObject importExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		Map<String, String> ret = new HashMap<String, String>();

		response.setContentType("text/html;charset=utf-8");
		JSONObject ret = new JSONObject();
		StringBuffer rets = new StringBuffer();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("importfile");
		String fileName = mulfile.getOriginalFilename();
		String fileNames = fileName.substring(fileName.length()-3);
		String[] titles = new String[]{"车辆类型","车牌号","品牌车系","车架号","颜色","荷载人数","登记城市","经营区域"};
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

					//以下是正文
					//导入的字段正则验证
					//车辆类型
					if (!map.get("车辆类型").equals("网约车") && !map.get("车辆类型").equals("出租车")) {
//						ret.put("MessageKey", map.get("车辆类型")+"车辆类型不存在");
						rets.append("第" + index + "行 【" + map.get("车牌号") + "】车辆类型格式错误<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey", rets.toString());
						return false;
					}

					try {

						//车牌号  车架号
						PubVehicle pubVehicle = new PubVehicle();
						pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
						pubVehicle.setShowPlateNo(map.get("车牌号"));

						//车牌号
						if (map.get("车牌号") != null && !map.get("车牌号").equals("")) {
							String regEx1 = "^[A-Za-z0-9]{5}$";
							Pattern pattern1 = Pattern.compile(regEx1);
							Matcher matcher1 = pattern1.matcher(map.get("车牌号").toString().substring(2, map.get("车牌号").length()));
							if (!matcher1.matches()) {
								//不满足
								rets.append("第" + index + "行 【" + map.get("车牌号") + "】车牌号格式错误<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey", rets.toString());
								return false;
							}
							int countPlateNo = getPubVehicleCount(pubVehicle, userToken);
							if (countPlateNo > 0) {
								rets.append("第" + index + "行 【"+map.get("车牌号")+"】已存在<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey", rets.toString());
								return false;
							}
						} else {
							rets.append("第" + index + "行 " + "车牌号为空<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						PubVehicle pubVehicle2 = new PubVehicle();
						pubVehicle2.setPlatformType(PlatformTypeByDb.OPERATING.code);
						pubVehicle2.setVin(map.get("车架号"));

						//车架号
						if (map.get("车架号") != null && !map.get("车架号").equals("")) {
							String regEx2 = "^[A-Z0-9]{1,17}$";
							Pattern pattern2 = Pattern.compile(regEx2);
							Matcher matcher2 = pattern2.matcher(map.get("车架号"));
							if (!matcher2.matches()) {
								//不满足
								rets.append("第" + index + "行 【" + map.get("车牌号") + "】车架号格式错误<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey", rets.toString());
								return false;
							}


							int countVin = getPubVehicleCount(pubVehicle2, userToken);
							if (countVin > 0) {
//								ret.put("MessageKey", map.get("车架号")+"车架号已存在");
								rets.append("第" + index + "行 【"+map.get("车牌号")+"】车架号已存在<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey", rets.toString());
								return false;
							}
						} else {
							rets.append("第" + index + "行 " + "车架号为空<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						//绑定车的id 品牌车系
						PubVehicle p = new PubVehicle();
						String vehcline = map.get("品牌车系");
						p.setPlatformType(PlatformTypeByDb.OPERATING.code);
						p.setBrandCars(vehcline);
						String vehclineId = templateHelper.dealRequestWithToken("/PubVehicle/GetVehclineId", HttpMethod.POST,
								userToken, p, String.class);
						if (vehclineId == null) {
							rets.append("第" + index + "行 【" + map.get("车牌号") + "】品牌车系格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						} else {
							vehclineId = vehclineId.substring(1, vehclineId.length() - 1);
						}
						//颜色

						//荷载人数
						//城市编码  登记城市
						String city = map.get("登记城市");
						PubCityAddr cityCode = templateHelper.dealRequestWithToken("/PubVehicle/GetCityCode/{city}", HttpMethod.POST,
								userToken, null, PubCityAddr.class, city);
						if (cityCode == null) {
//						ret.put("MessageKey", city+"不存在");
							rets.append("第" + index + "行 【" + city + "】登记城市格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						//经营区域
						String cityScope = map.get("经营区域");
						boolean flag = true;
						StringBuilder s = new StringBuilder();
						StringBuffer retCity = new StringBuffer();
						if (cityScope != null && !cityScope.equals("")) {
							String[] s1 = cityScope.split("[/|\\||、|\\s+]");
							for (int i = 0; i < s1.length; i++) {
								PubCityAddr pca = getPubCityAddr(request, s1[i]);
								if (pca != null) {
									s.append(pca.getId() + ",");
								} else {
									//							ret.put("MessageKey", city+"不存在");
									retCity.append(s1[i] + "、");
									flag = false;
								}
							}
							if (!flag) {
								rets.append("第" + index + "行 【" + retCity.toString().substring(0, retCity.toString().length() - 1) + "】城市格式错误<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey", rets.toString());
								return false;
							}
						} else {
							rets.append("第" + index + "行 " + "经营范围为空<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						//获取车牌号
						String plateNo = map.get("车牌号");
						//获取车牌号的第一个字符省,第二个字符市,最后为车牌号
						String plateOne = plateNo.substring(0, 1);
						//查找对应的省编码
						PubDictionary plateOneCode = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateCode/{plateOne}", HttpMethod.POST,
								userToken, null, PubDictionary.class, plateOne);
						if (plateOneCode.getValue() == null || plateOneCode.getValue().equals("")) {
							rets.append("第" + index + "行 【" + map.get("车牌号") + "】车牌号格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						String plateTow = plateNo.substring(1, 2);
						//查找对应市的编码
						plateOneCode.setText(plateTow);
						String plateTowCity = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateCity", HttpMethod.POST,
								userToken, plateOneCode, String.class);
						if (plateTowCity == null || plateTowCity.equals("")) {
							rets.append("第" + index + "行 【" + map.get("车牌号") + "】车牌号格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey", rets.toString());
							return false;
						}
						plateTowCity = plateTowCity.substring(1, plateTowCity.length() - 1);
						String plateThree = plateNo.substring(2, plateNo.length());
						OpUser opUser = new BaseController().getLoginOpUser(request);
						PubVehicle saveVehicle = new PubVehicle();
						saveVehicle.setId(GUIDGenerator.newGUID());
						saveVehicle.setLeasesCompanyId(opUser.getOperateid());
						saveVehicle.setCreater(opUser.getId());
						saveVehicle.setUpdater(opUser.getId());
						saveVehicle.setPlateNoProvince(plateOneCode.getValue());
						saveVehicle.setPlateNoCity(plateTowCity);
						saveVehicle.setPlateNo(plateThree);
						saveVehicle.setVin(map.get("车架号"));
						saveVehicle.setVehclineId(vehclineId);
						saveVehicle.setColor(map.get("颜色"));
						saveVehicle.setCity(cityCode.getId());
						saveVehicle.setLoads(Integer.valueOf(map.get("荷载人数")));
						saveVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
						if (map.get("车辆类型").equals("网约车")) {
							saveVehicle.setVehicleType(DriverEnum.DRIVER_TYPE_CAR.code);
						} else if (map.get("车辆类型").equals("出租车")) {
							saveVehicle.setVehicleType(DriverEnum.DRIVER_TYPE_TAXI.code);
						}
						if(StringUtils.isNotEmpty(s.toString())){
							saveVehicle.setBusinessScope(s.substring(0, s.length() - 1));
						}
						templateHelper.dealRequestWithToken("/PubVehicle/CreatePubVehicle", HttpMethod.POST, userToken, saveVehicle,
								Map.class);
//					ret.put("MessageKey", "导入成功");
//					rets.append("导入成功<br>");
						ret.put("ResultSign", "Successfuls");
//					ret.put("MessageKey",rets.toString());
						return true;
					}catch (Exception e){
						logger.error("车辆导入失败!",e);
						rets.append("第" + index + "行 保存失败！");
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
			ret.put("MessageKey",ret.get("MessageKey"));
		}
		if(ret.get("MessageKey")!=null){
			ret.put("ResultSign","Successful");
			ret.put("MessageKey",ret.get("MessageKey").toString());
		}
//		response.getWriter().write(ret.toString());
		return ret;

	}

	private int getPubVehicleCount(PubVehicle pubVehicle,String userToken){
		return  templateHelper.dealRequestWithToken("/PubVehicle/CheckPubVehicle",
				HttpMethod.POST, userToken, pubVehicle,
				int.class);

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
		System.out.println("------------------------车辆管理——下载模板：-----------------------------");

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();

		PubVehicle pubVehicle = new PubVehicle();
		pubVehicle.setPlatformType(PlatformTypeByDb.OPERATING.code);
		List<Map<String, Object>> brand = templateHelper.dealRequestWithToken("/PubVehicle/GetBrandCars", HttpMethod.POST,
				userToken, pubVehicle, List.class);

		colData1.add("网约车");
		colData2.add("京A88888");
		colData3.add(brand.get(0).get("text").toString());
		colData4.add("D1234567891234567");
		colData5.add("红色");
		colData6.add("5");
		colData7.add("北京市");
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
		colName.add("经营区域");
		excel.setColName(colName);

		colData.put("车辆类型", colData1);
		colData.put("车牌号", colData2);
		colData.put("品牌车系", colData3);
		colData.put("车架号", colData4);
		colData.put("颜色", colData5);
		colData.put("荷载人数", colData6);
		colData.put("登记城市", colData7);
		colData.put("经营区域", colData8);

		excel.setColData(colData);
		System.out.println("---------------------模板内容："+GsonUtil.toJson(colData));
		logger.debug("------------------------车辆管理——下载模板：-----------------------------");
		System.out.println("------------------------车辆管理——下载模板：-----------------------------");
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		//导出excel序列的值
		String[] s = {"网约车","出租车"};
		String[] s1 = new String[brand.size()];
		for(int i=0;i<brand.size();i++){
			s1[i] = brand.get(i).get("text").toString();
		}
		String[] s2 = {};
		//起始行序号，序列值，起始列序号，终止列序号
		ee.createExcel(tempFile,s,s1,s2);
	}


	/**
	 * 返回车辆信息及绑定信息
	 * @return
	 */
	@RequestMapping(value = "/PubVehicle/bind/list")
	@ResponseBody
	public JSONObject listPubVehicleBind(@RequestBody PubVehicleQueryParam pubVehicleQueryParam, HttpServletRequest request) {

		String usertoken = getUserToken(request);

//		OpUser user = getLoginOpUser(request);

		pubVehicleQueryParam.setPlatformtype(PlatformTypeByDb.OPERATING.code);


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
		PubDriverSelectParam param = new PubDriverSelectParam();
		param.setQueryText(paramMap.get("queryText"));

		param.setPlatformtype(PlatformTypeByDb.OPERATING.code);


		JSONObject jsonObject =  templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/Vehicle/listVehicleBySelect",
				HttpMethod.POST, usertoken, param, JSONObject.class);

		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			JSONArray json = jsonObject.getJSONArray("data");
			return json;
		}
		return new JSONArray();
	}


	/**
	 * 根据用户查询拥有权限的所有车辆信息
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value = "/PubVehicle/GetPubVehicleSelectByUser")
	@ResponseBody
	public List<Map<String, Object>> getPubVehicleSelectByUser(@RequestParam Map<String,String> paramMap, HttpServletRequest request) {
		String usertoken = getUserToken(request);
		OpUser opUser = this.getLoginOpUser(request);
		paramMap.put("userid",opUser.getId());
		paramMap.put("usertype",opUser.getUsertype());
		List<Map<String,Object>> resultMap =  templateHelper.dealRequestWithToken("/PubVehicle/GetPubVehicleSelectByUser",
				HttpMethod.POST, usertoken, paramMap,
				List.class);
		return resultMap;
	}
}
