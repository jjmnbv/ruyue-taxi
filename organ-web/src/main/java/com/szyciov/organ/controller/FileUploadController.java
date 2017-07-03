package com.szyciov.organ.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.util.BaseController;
import com.szyciov.util.FileUtil;
import com.szyciov.util.SystemConfig;

import net.sf.json.JSONObject;

@Controller
public class FileUploadController extends BaseController {

	@RequestMapping("FileUpload/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 Map<String, Object> res = FileUtil.upload2FileServer(request,response);
		 res.put("basepath", SystemConfig.getSystemProperty("fileserver"));
		 return res;
	}
	
	/**
	 * ie9上传文件头部不支持json，不然会当做下载处理，头部设置成text/html
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("FileUpload/uploadFile_new")
	public void uploadFile_new(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 response.setContentType("text/html");
		 JSONObject result = new JSONObject();
		 Map<String, Object> res = FileUtil.upload2FileServer(request,response);
		 if(res!=null){
			 result.put("status", res.get("status"));
			 result.put("message", res.get("message"));
			 result.put("basepath", SystemConfig.getSystemProperty("fileserver"));
		 }else{
			 result.put("status", "fail");
		 }
		 response.getWriter().write(result.toString());
	}
	
}
