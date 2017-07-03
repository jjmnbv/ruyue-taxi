package com.szyciov.lease.controller;

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.param.PubVehclineQueryParam;
import com.szyciov.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
public class PubVehclineController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehclineController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubVehcline/Index")
	public ModelAndView getPubVehclineIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        mav.setViewName("resource/pubVehcline/index");  
        return mav; 
	}
	
	@RequestMapping(value = "/PubVehcline/GetBrandCars")
	@ResponseBody
	public List<Map<String, Object>> getBrandCars(@RequestParam String id, HttpServletRequest request) {
		PubVehcline pubVehcline = new PubVehcline();
		pubVehcline.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehcline.setId(id);
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcline/GetBrandCars", HttpMethod.POST,
				userToken, pubVehcline, List.class);
	}
	
	@RequestMapping("/PubVehcline/GetPubVehclineByQuery")
	@ResponseBody
	public PageBean getPubVehclineByQuery(@RequestBody PubVehclineQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		queryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcline/GetPubVehclineByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PubVehcline/CreatePubVehcline")
	@ResponseBody
	public Map<String, String> createPubVehcline(@RequestBody PubVehcline pubVehcline, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehcline.setId(GUIDGenerator.newGUID());
		pubVehcline.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehcline.setCreater(this.getLoginLeUser(request).getId());
		pubVehcline.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehcline/CreatePubVehcline", HttpMethod.POST, userToken, pubVehcline,
				Map.class);
	}
	
	@RequestMapping("/PubVehcline/UpdatePubVehcline")
	@ResponseBody
	public Map<String, String> updatePubVehcline(@RequestBody PubVehcline pubVehcline, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehcline.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehcline.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehcline/UpdatePubVehcline", HttpMethod.POST, userToken, pubVehcline,
				Map.class);
	}
	
	@RequestMapping("/PubVehcline/GetById")
	@ResponseBody
	public PubVehcline getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcline/GetById/{id}", HttpMethod.GET, userToken, null,
				PubVehcline.class, id);
	}
	
	@RequestMapping("/PubVehcline/DeletePubVehcline")
	@ResponseBody
	public Map<String, String> deletePubVehcline(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcline/DeletePubVehcline/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}
	@RequestMapping("/PubVehcline/ImportExcel")
	@ResponseBody
	public void importExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		String[] titles = new String[]{"品牌名称","车系名称"};
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
//					if(map.get("车系名称") == null && map.get("品牌名称") == null){
//						ret.put("ResultSign", "Error");
//						ret.put("MessageKey", "模板错误，请按照下载模板填写数据");
//						return false;
//					}
					
					PubVehcline pubVehcline = new PubVehcline();
					pubVehcline.setName(map.get("车系名称"));
					pubVehcline.setVehcBrandName(map.get("品牌名称"));
					pubVehcline.setLeasesCompanyId(new BaseController().getLoginLeUser(request).getLeasescompanyid());
					String name = map.get("品牌名称");
					
					PubVehcbrand pubVehcbrandTemp = new PubVehcbrand();
					pubVehcbrandTemp.setName(name);
					pubVehcbrandTemp.setLeasesCompanyId(new BaseController().getLoginLeUser(request).getLeasescompanyid());
					
					int checkv = templateHelper.dealRequestWithToken("/PubVehcline/CheckImprot", HttpMethod.POST, userToken, pubVehcline,
							int.class);
					int checkb = templateHelper.dealRequestWithToken("/PubVehcbrand/checkBrand", HttpMethod.POST, userToken, pubVehcbrandTemp,
							int.class);
					
					if(checkb>0){
						if(checkv>0){
							rets.append("第"+index+"行 【车系名称】已存在<br>");
							ret.put("ResultSign", "Successful");
							ret.put("MessageKey",rets.toString());
							return false;
						}else{
							// 正则表达式规则
						    String regEx = ".{1,8}";
						    // 编译正则表达式
						    Pattern pattern = Pattern.compile(regEx);
						    // 忽略大小写的写法
						    Matcher matcher = pattern.matcher(map.get("车系名称"));
							if(matcher.matches()){
								try {
									PubVehcbrand pubVehcbrand = templateHelper.dealRequestWithToken("/PubVehcbrand/GetByName", HttpMethod.POST, userToken, pubVehcbrandTemp,
											PubVehcbrand.class);
									PubVehcline pubVehcline1 = new PubVehcline();
									pubVehcline1.setId(GUIDGenerator.newGUID());
									pubVehcline1.setVehcBrandID(pubVehcbrand.getId());
									pubVehcline1.setLeasesCompanyId(new BaseController().getLoginLeUser(request).getLeasescompanyid());
									pubVehcline1.setName(map.get("车系名称"));
									pubVehcline1.setCreater(new BaseController().getLoginLeUser(request).getId());
									pubVehcline1.setUpdater(new BaseController().getLoginLeUser(request).getId());
									templateHelper.dealRequestWithToken("/PubVehcline/CreatePubVehcline", HttpMethod.POST, userToken, pubVehcline1,
											Map.class);
	//								rets.append("导入成功<br>");
									ret.put("ResultSign", "Successfuls");
	//								ret.put("MessageKey",rets.toString());
									return true;
								} catch (Exception e) {
									rets.append("第"+index+"行 导入失败<br>");
									ret.put("ResultSign", "Successful");
									ret.put("MessageKey", rets.toString());
									return false;
								}
							}else{
								rets.append("第"+index+"行 【"+map.get("车系名称")+"】车系名称格式错误<br>");
								ret.put("ResultSign", "Successful");
								ret.put("MessageKey",rets.toString());
								return false;
							}
						}
					}else{
						rets.append("第"+index+"行 【"+name+"】品牌不存在，请先维护<br>");
						ret.put("ResultSign", "Successful");
						ret.put("MessageKey",rets.toString());
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
	/**
	 * 查询所有车品牌按照字母分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/PubVehcline/GetBrand")
	@ResponseBody
	public JSONObject getBrand(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = this.getLoginLeUser(request).getLeasescompanyid();
		return templateHelper.dealRequestWithToken("/PubVehcline/GetBrand/{id}", HttpMethod.GET, userToken, null, JSONObject.class,id);
	}
	/**
	 * 下载模板
	 * **/
	@RequestMapping("/PubVehcline/DownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();

		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		JSONArray brand = templateHelper.dealRequestWithToken("/PubVehcline/GetBrands/{id}", HttpMethod.GET,
				userToken, null, JSONArray.class,leasesCompanyId);
		
		colData1.add(brand.getJSONObject(0).getString("name"));
		colData2.add("8系");
		
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("车系管理.xls");
		List<String> colName = new ArrayList<String>();
		colName.add("品牌名称");
		colName.add("车系名称");
		excel.setColName(colName);
		
		colData.put("品牌名称", colData1);
		colData.put("车系名称", colData2);
		
		excel.setColData(colData);
		
		ExcelDataSequence ee = new ExcelDataSequence(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		//导出excel序列的值
		String[] s = new String[brand.size()]; 
		for(int i=0;i<brand.size();i++){
			s[i] = brand.getJSONObject(i).getString("name");
		}
		//起始行序号，序列值，起始列序号，终止列序号
		ee.createExcel(tempFile,1,s,0,0);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
}
