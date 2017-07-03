package com.szyciov.carservice.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.util.SystemConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.szyciov.carservice.service.FileUtilService;
import com.szyciov.carservice.util.fdfs.FastDFSFileStoreComponent;
import com.szyciov.util.BaseController;

@Controller
public class FileUtilController extends BaseController {
	private static final Logger logger = Logger.getLogger(FileUtilController.class);
	
	@Autowired
	private FastDFSFileStoreComponent fileStoreComponent;
	
	@Autowired
	private FileUtilService fileUtilService;

	@RequestMapping(value = "/api/FileUtil/UploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> filepathList = new ArrayList<String>();
		response.setContentType("application/json; charset=utf-8");
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = multiRequest.getFileMap();
		
		try {
			Iterator<String> ite = files.keySet().iterator();
			while(ite.hasNext()) {
				MultipartFile multipartFile = files.get(ite.next());
				File file = fileUtilService.multipartToFile(multipartFile);
				String maxFileSizeStr = SystemConfig.getSystemProperty("maxFileSize");
				int maxFileSize = 5;

				if(maxFileSizeStr != null && !"".equals(maxFileSizeStr)) {
					maxFileSize = Integer.parseInt(maxFileSizeStr);
				}

				if(file.length() > maxFileSize * 1024 * 1024) {
					resultMap.put("status", "fail");
					resultMap.put("type", "filetoolarge");
					resultMap.put("maxfilesize", maxFileSize);
					resultMap.put("message", "您选择的图片大小超过限制" + maxFileSize + "M，上传失败，请压缩后重新上传。");
					return resultMap;
				}

				String filePath = fileStoreComponent.saveFile(file);
				filepathList.add(filePath);
			}
			
			resultMap.put("status", "success");
			resultMap.put("message", filepathList);
		} catch (Exception e) {
			resultMap.put("status", "fail");
			logger.error("UploadFile Error", e);
		}
		
		return resultMap;
	}


	@RequestMapping(value = "/api/FileUtil/UploadFileWithLargeFile")
	@ResponseBody
	public Map<String, Object> uploadFileWithLargeFile(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> filepathList = new ArrayList<String>();
		response.setContentType("application/json; charset=utf-8");

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = multiRequest.getFileMap();

		try {
			Iterator<String> ite = files.keySet().iterator();
			while(ite.hasNext()) {
				MultipartFile multipartFile = files.get(ite.next());
				File file = fileUtilService.multipartToFile(multipartFile);
				String filePath = fileStoreComponent.saveFile(file);
				filepathList.add(filePath);
			}

			resultMap.put("status", "success");
			resultMap.put("message", filepathList);
		} catch (Exception e) {
			resultMap.put("status", "fail");
			logger.error("UploadFile Error", e);
		}

		return resultMap;
	}
	
	@RequestMapping(value = "/api/FileUtil/DeleteFile")
	@ResponseBody
	public Map<String, Object> deleteFile(@RequestParam String fileID, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		try {
			fileStoreComponent.deleteFile(fileID);
			resultMap.put("status", "success");
		} catch (Exception e) {
			resultMap.put("status", "fail");
			logger.error("UploadFile Error", e);
		}
		
		return resultMap;
	}
	
	
	@RequestMapping(value = "/api/FileUtil/UploadFileByByte")
	@ResponseBody
	public Map<String, Object> uploadFile(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> filepathList = new ArrayList<String>();
		response.setContentType("application/json; charset=utf-8");
		
		try {
			ByteArrayResource byteArrayResource = (ByteArrayResource) paramMap.get("file");
			String suffix = (String) paramMap.get("suffix");
			
			String filePath = fileStoreComponent.saveFile(byteArrayResource.getByteArray(), suffix);
			filepathList.add(filePath);
				
			resultMap.put("status", "success");
			resultMap.put("message", filepathList);
		} catch (Exception e) {
			resultMap.put("status", "fail");
			logger.error("UploadFile Error", e);
		}
		
		return resultMap;
	}
	

}
