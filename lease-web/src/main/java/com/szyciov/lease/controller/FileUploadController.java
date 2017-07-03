package com.szyciov.lease.controller;

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
	
}
