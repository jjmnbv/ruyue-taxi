package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.dto.pubVehInsur.PubVehInsurQueryDto;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.PubVehInsur;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.pubVehInsurance.AddPubVehInsurs;
import com.szyciov.lease.param.pubVehInsurance.PubVehInsurQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelDataSequenceVehicke;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelImp;
import com.szyciov.util.ExcelRuleImport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class PubVehInsurController  extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehInsurController.class);
    
	private TemplateHelper templateHelper = new TemplateHelper();
	
	/**
	 * 车辆保险信息首页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PubVehInsur/Index",method = RequestMethod.GET)
	public ModelAndView getPubVehInsurIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> insurType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=保险类型", HttpMethod.GET,
				userToken, null, List.class,"保险类型");
		mav.addObject("insurType",insurType);
		mav.setViewName("resource/pubVehInsur/index");  
		return mav;
	}
	
	/**
	 * 车辆保险编辑页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PubVehInsur/EditIndex",method = RequestMethod.GET)
	public ModelAndView editInsurIndex(@RequestParam("id") String id,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehInsurQueryDto pubInsur = templateHelper.dealRequestWithToken("/PubVehInsur/EditIndex/{id}", 
				HttpMethod.GET, userToken, null, PubVehInsurQueryDto.class, id);
		List<Dictionary> insurType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=保险类型", HttpMethod.GET,
				userToken, null, List.class,"保险类型");
		mav.addObject("pubInsur", pubInsur);
		mav.addObject("insurType",insurType);
    	mav.setViewName("resource/pubVehInsur/editInsur");
		return mav;
	}
	
	/**
	 * 保险信息查询
	 */
	@RequestMapping(value="/PubVehInsur/GetPubVehInsurByQuery")
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubVehInsurQueryParam pubVehInsurQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehInsur/GetPubVehInsurByQuery", HttpMethod.POST, userToken,
				pubVehInsurQueryParam,PageBean.class);
	}
	
	/**
	 * 删除车辆保险信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/PubVehInsur/DeletePubVehInsur")
	@ResponseBody
	public Map<String, String> deletePubVehcbrand(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehInsur/DeletePubVehInsur/{id}", HttpMethod.DELETE, userToken, 
				null,Map.class, id);
	}
	
	/**
	 * 更改车辆保险信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/PubVehInsur/UpdatePubVehInsur", method=RequestMethod.POST, 
		    consumes="application/json")
	@ResponseBody
	public Map<String, String> updatePubVehcbrand(@RequestBody PubVehInsur pubVehInsur, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehInsur.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehInsur/UpdatePubVehInsur", HttpMethod.POST, userToken, 
				pubVehInsur,Map.class);
	}
	
	/**
	 * 增加车辆保险信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/PubVehInsur/AddPubVehInsurs")
	@ResponseBody
	public  Map<String, String> addPubVehcbrand(@RequestBody AddPubVehInsurs pubInsurs, HttpServletRequest request, 
			HttpServletResponse response){
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehInsur/AddPubVehInsurs", HttpMethod.POST, userToken, 
				pubInsurs, Map.class);
	}
	
	/**
	 * 增加车辆保险页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/PubVehInsur/AddIndex",method = RequestMethod.GET)
	public ModelAndView addPubVehInsurIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);	
		List<Dictionary> plateNoProvince = templateHelper.dealRequestWithToken("/PubVehicle/GetPlateNoProvince", HttpMethod.GET,
				userToken, null, List.class);
		List<Dictionary> insurType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=保险类型", HttpMethod.GET,
				userToken, null, List.class,"保险类型");
		mav.addObject("plateNoProvince",plateNoProvince);
		mav.addObject("insurType",insurType);
		mav.setViewName("resource/pubVehInsur/addInsur");  
		return mav;
	}
	
	/**
	 * 下载模板
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/PubVehInsur/DownLoad")
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
		
		List<Map<String,Object>> insurType = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=保险类型", HttpMethod.GET,
				userToken, null, List.class,"保险类型");
		colData1.add("粤A930FZ");
		colData2.add("1234567");
		colData3.add("12345678901234567");
		colData4.add("泰和人寿保险有限公司");
		colData5.add(insurType.get(0).get("text").toString());
		colData6.add("FW4203456786");
		colData7.add("1000");
		colData9.add("2017-08-08");
		colData8.add("2017-08-08");
		
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("车辆保险.xls");
		List<String> colName = new ArrayList<String>();
		colName.add("车牌号码");
		colName.add("发动机号");
		colName.add("车辆识别码");
		colName.add("保险公司名称");
		colName.add("保险类型");
		colName.add("保险号");
		colName.add("保险金额");
		colName.add("保险有效期开始日期");
		colName.add("保险有效期结束日期");
		excel.setColName(colName);
		
		colData.put("车牌号码", colData1);
		colData.put("发动机号", colData2);
		colData.put("车辆识别码", colData3);
		colData.put("保险公司名称", colData4);
		colData.put("保险类型", colData5);
		colData.put("保险号", colData6);
		colData.put("保险金额", colData7);
		colData.put("保险有效期开始日期", colData8);
		colData.put("保险有效期结束日期", colData9);
		
		excel.setColData(colData);
		
		ExcelDataSequenceVehicke ee = new ExcelDataSequenceVehicke(request,response,excel);
		String[] s = {};
		String[] s1 = {};
		String[] s2 = {};
		//起始行序号，序列值，起始列序号，终止列序号
		ee.createExcel(tempFile,s,s1,s2);
	}
	
	/**
	 * 导出数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/PubVehInsur/ExportData")
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
		List<Object> colData8 = new ArrayList<Object>();
		

		String queryVehicleType = request.getParameter("queryVehicleType");
		String queryinsurType = request.getParameter("queryinsurType");
		String queryPlateNo = request.getParameter("queryPlateNo");
		String queryVin = request.getParameter("queryVin");
		String queryInsurNum = request.getParameter("queryInsurNum");
		String queryInsurCom = request.getParameter("queryInsurCom");
        
		PubVehInsurQueryParam param = new PubVehInsurQueryParam();
		param.setQueryVehicleType(queryVehicleType);
		param.setQueryinsurType(queryinsurType);
		param.setQueryPlateNo(queryPlateNo);
		param.setQueryVin(queryVin);
		param.setQueryInsurNum(queryInsurNum);
		param.setQueryInsurCom(queryInsurCom);
		
		List<Map> pubVehInsurs = templateHelper.dealRequestWithToken("/PubVehInsur/ExportExcel", HttpMethod.POST,
				userToken, param, List.class);
		
		for(int i = 0; i < pubVehInsurs.size(); i++){
			//车牌号
			if (pubVehInsurs.get(i).get("fullplateno") != null){
				colData1.add(pubVehInsurs.get(i).get("fullplateno"));
			}else{
				colData1.add("/");
			}
			//发动机号
			if (pubVehInsurs.get(i).get("engineid") != null){
				colData2.add(pubVehInsurs.get(i).get("engineid"));
			}else{
				colData2.add("/");
			}
			//车辆识别码
			if (pubVehInsurs.get(i).get("vin") != null){
				colData3.add(pubVehInsurs.get(i).get("vin"));
			}else{
				colData3.add("/");
			}
			//保险公司
			if (pubVehInsurs.get(i).get("insurcom") != null){
				colData4.add(pubVehInsurs.get(i).get("insurcom"));
			}else{
				colData4.add("/");
			}
			//保险类型
			if (pubVehInsurs.get(i).get("insurtypename")!= null){
				colData5.add(pubVehInsurs.get(i).get("insurtypename"));
			}else{
				colData5.add("/");
			}
			//保险号
			if (pubVehInsurs.get(i).get("insurnum")!= null){
				colData6.add(pubVehInsurs.get(i).get("insurnum"));
			}else{
				colData6.add("/");
			}
			//保险金额
			if (pubVehInsurs.get(i).get("insurcount")!= null){
				colData7.add(pubVehInsurs.get(i).get("insurcount"));
			}else{
				colData7.add("/");
			}
			//保险有效期开始日期
			if (pubVehInsurs.get(i).get("insurvalidate")!= null){
				colData8.add(pubVehInsurs.get(i).get("insurvalidate"));
			}else{
				colData8.add("/");
			}

		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("车辆保险.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("车牌号码");
		colName.add("发动机号");
		colName.add("车辆识别码");
		colName.add("保险公司名称");
		colName.add("保险类型");
		colName.add("保险号");
		colName.add("保险金额");
		colName.add("保险有效期");
		excel.setColName(colName);
		
		colData.put("车牌号码", colData1);
		colData.put("发动机号", colData2);
		colData.put("车辆识别码", colData3);
		colData.put("保险公司名称", colData4);
		colData.put("保险类型", colData5);
		colData.put("保险号", colData6);
		colData.put("保险金额", colData7);
		colData.put("保险有效期", colData8);
		
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);

	}
	
	/**
	 * excel 导入
	 */
	@RequestMapping("/PubVehInsur/ImportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void importExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		String[] titles = new String[]{"车牌号码","发动机号","车辆识别码","保险公司名称","保险类型","保险号","保险金额","保险有效期开始日期","保险有效期结束日期"};
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
					String plateNo = map.get("车牌号码");
					String engineid = map.get("发动机号");
					String vin = map.get("车辆识别码");
					String insurnum = map.get("保险号");
										
					PubVehicle pubVehicle = templateHelper.dealRequestWithToken("/PubVehInsur/checkPubVehicle?plateNo="+plateNo, HttpMethod.GET, userToken,null,
							PubVehicle.class);
					//车牌号检查
					if(StringUtils.isNotBlank(plateNo)){
						String regEx1 = "^[A-Za-z0-9]{5}$";
					    Pattern pattern1 = Pattern.compile(regEx1);
					    Matcher matcher1 = pattern1.matcher(plateNo.substring(2, plateNo.length()));
						if(!matcher1.matches()){
							//不满足
							rets.append("第"+index+"行 【"+plateNo+"】车牌号码格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
						
						if(pubVehicle == null){
							rets.append("第"+index+"行 【"+plateNo+"】车牌号码不存在<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}else{
						rets.append("第"+index+"行车牌号码为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					//保险公司检查					
					String insurcom = map.get("保险公司名称");
					if (StringUtils.isBlank(insurcom)){
						rets.append("第"+index+"行保险公司名称为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					//保险类型检查
					String insurTypeName = map.get("保险类型"); 
					String insurType = "";
					List<Map> insurTypeList = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=保险类型", HttpMethod.GET,
							userToken, null, List.class,"保险类型");
					if(insurTypeList != null && StringUtils.isNotBlank(insurTypeName)){
						for (int i = 0 ;i < insurTypeList.size(); i ++){
							if (insurTypeList.get(i).get("text").equals(insurTypeName)){
								insurType = insurTypeList.get(i).get("value").toString();
								break;
							}
						}
					}
					if (insurTypeList == null || StringUtils.isBlank(insurType)){
						rets.append("第"+index+"行 【"+insurTypeName+"】保险类型不存在<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}else if (StringUtils.isBlank(insurTypeName)){
						rets.append("第"+index+"行保险类型为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					//保险金额检查
					String insurcount = map.get("保险金额"); 
					if (StringUtils.isNotBlank(insurcount)){
						String regCount =  "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
						Pattern patternCount = Pattern.compile(regCount);
						Matcher matcherCount = patternCount.matcher(insurcount);
						if (!matcherCount.matches()){
							rets.append("第"+index+"行 【"+insurcount+"】保险金额格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}	
					}
					else{
						rets.append("第"+index+"行保险金额为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}

					//保险有效期日期检查
					String insureff = map.get("保险有效期开始日期");
					String insurexp = map.get("保险有效期结束日期");
					String regDate = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
					Pattern patternDate = Pattern.compile(regDate);
					Matcher macherDate;
					if (StringUtils.isNotBlank(insureff)){
						macherDate = patternDate.matcher(insureff);
						if (!macherDate.matches()){
							rets.append("第"+index+"行 【"+insureff+"】保险有效期开始日期格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}
					else{
						rets.append("第"+index+"行保险有效期开始日期为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					if (StringUtils.isNotBlank(insurexp)){
						macherDate = patternDate.matcher(insurexp);
						if (!macherDate.matches()){
							rets.append("第"+index+"行 【"+insurexp+"】保险有效期结束日期格式错误<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}
					}
					else{
						rets.append("第"+index+"行保险有效期开始日期为空<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					if (insureff.compareTo(insurexp) > 0){
						rets.append("第"+index+"行【"+insureff+"】开始日期大于【"+insurexp+"】结束日期<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
						return false;
					}
					
					try {
						
						AddPubVehInsurs pubInsurs = new AddPubVehInsurs();
						pubInsurs.setFullplateno(plateNo);
						pubInsurs.setEngineid(engineid);
						pubInsurs.setVin(vin);
						
						List<PubVehInsur> insurList = new ArrayList<PubVehInsur>();
						PubVehInsur insur = new PubVehInsur();
						insur.setInsurcom(insurcom);
						insur.setInsurcount(insurcount);
						insur.setInsurtype(insurType);
						insur.setInsureff(insureff);
						insur.setInsurexp(insurexp);
						insur.setInsurnum(insurnum);
						insurList.add(insur);
						
						pubInsurs.setInsurList(insurList);


						Map<String, String>  retMap = templateHelper.dealRequestWithToken("/PubVehInsur/AddPubVehInsurs", HttpMethod.POST, userToken, 
								pubInsurs, Map.class);
						if (retMap == null || retMap.get("ResultSign").equals("Error")){
							rets.append("第"+index+"行【"+insureff+"】导入失败<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",retMap.get("MessageKey"));
							return false;
						}
						ret.put("ResultSign", "Successfuls");
						return true;
					} catch (Exception e) {
						rets.append("第"+index+"行导入失败<br>");
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
}
