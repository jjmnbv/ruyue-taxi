package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubVehcbrandQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PYTools;
import com.szyciov.util.PageBean;
import com.szyciov.util.PinyinUtils;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Controller
public class PubVehcbrandController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehcbrandController.class);

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubVehcbrand/Index")
	public ModelAndView getPubVehcbrandIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();  
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        mav.setViewName("resource/pubVehcbrand/index");  
        return mav; 
	}
	
	@RequestMapping(value = "/PubVehcbrand/GetBrand")
	@ResponseBody
	public List<Map<String, Object>> getBrand(@RequestParam String id,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubVehcbrand pubVehcbrand = new PubVehcbrand();
		pubVehcbrand.setId(id);
		pubVehcbrand.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/PubVehcbrand/GetBrand", HttpMethod.POST,
				userToken, pubVehcbrand, List.class);
	}
	
	@RequestMapping("/PubVehcbrand/GetPubVehcbrandByQuery")
	@ResponseBody
	public PageBean getPubVehcbrandByQuery(@RequestBody PubVehcbrandQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		queryParam.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcbrand/GetPubVehcbrandByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PubVehcbrand/CreatePubVehcbrand")
	@ResponseBody
	public Map<String, String> createPubVehcbrand(@RequestBody PubVehcbrand pubVehcbrand, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BadHanyuPinyinOutputFormatCombination {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehcbrand.setId(GUIDGenerator.newGUID());
		String initials= PinyinUtils.getPinYin(pubVehcbrand.getName()).substring(0,1);
		pubVehcbrand.setInitials(initials);
		pubVehcbrand.setLeasesCompanyId(this.getLoginLeUser(request).getLeasescompanyid());
		pubVehcbrand.setCreater(this.getLoginLeUser(request).getId());
		pubVehcbrand.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehcbrand/CreatePubVehcbrand", HttpMethod.POST, userToken, pubVehcbrand,
				Map.class);
	}
	
	@RequestMapping("/PubVehcbrand/UpdatePubVehcbrand")
	@ResponseBody
	public Map<String, String> updatePubVehcbrand(@RequestBody PubVehcbrand pubVehcbrand, HttpServletRequest request,
			HttpServletResponse response) throws IOException, BadHanyuPinyinOutputFormatCombination {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubVehcbrand.setUpdater(this.getLoginLeUser(request).getId());
		return templateHelper.dealRequestWithToken("/PubVehcbrand/UpdatePubVehcbrand", HttpMethod.POST, userToken, pubVehcbrand,
				Map.class);
	}
	
	@RequestMapping("/PubVehcbrand/GetById")
	@ResponseBody
	public PubVehcbrand getById(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcbrand/GetById/{id}", HttpMethod.GET, userToken, null,
				PubVehcbrand.class, id);
	}
	
	@RequestMapping("/PubVehcbrand/DeletePubVehcbrand")
	@ResponseBody
	public Map<String, String> deletePubVehcbrand(@RequestParam String id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubVehcbrand/DeletePubVehcbrand/{id}", HttpMethod.DELETE, userToken, null,
				Map.class, id);
	}
	
	@RequestMapping("/PubVehcbrand/UploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		 Map<String, Object> res = FileUtil.upload2FileServer(request,response);
//		 res.put("basepath", SystemConfig.getSystemProperty("fileserver"));
//		 return res;
		
		response.setContentType("text/html;charset=utf-8");
		JSONObject result = new JSONObject();
		
		MultipartHttpServletRequest mRequest = null;
		mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = mRequest.getFileMap();
		MultipartFile mulfile = files.get("file");
		if (!mulfile.isEmpty()) { 
			String fileName = mulfile.getOriginalFilename();
			String fileName1 = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			Map<String, Object> res = new HashMap<>();
			if(fileName1.equals("jpg") || fileName1.equals("png") || fileName1.equals("jpeg") || fileName1.equals("gif")){
				long size = mulfile.getSize();
				if(size > 5*1024*1024L){
					result.put("error", "您选择的图片大小超过限制5M，上传失败，请压缩后重新上传。");
				}else{
					res = FileUtil.upload2FileServer(request,response);
		//			res.put("basepath", SystemConfig.getSystemProperty("fileserver"));
					result.put("status", res.get("status"));
					result.put("message", res.get("message"));
					result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
				}
			}else{
				result.put("error", "请上传正确的文件格式");
			}
		} 
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping("/PubVehcbrand/ExportData")
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Object> colData1 = new ArrayList<Object>();
		String leasesCompanyId = this.getLoginLeUser(request).getLeasescompanyid();
		String key = request.getParameter("key");
		Map<String, String> map = new HashMap<>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("key", key);
		List<Map> pubVehcbrand = templateHelper.dealRequestWithToken("/PubVehcbrand/ExportData", HttpMethod.POST,
				userToken, map, List.class);
		for(int i=0;i<pubVehcbrand.size();i++){
			if(pubVehcbrand.get(i).get("name") != null){
				colData1.add(pubVehcbrand.get(i).get("name"));
			}else{
				colData1.add("");
			}
			
		}
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("品牌管理.xls");
		List<String> colName = new ArrayList<String>();
		colName.add("品牌名称");
		excel.setColName(colName);
		colData.put("品牌名称", colData1);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
	
}
 