package com.szyciov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.entity.Retcode;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONObject;


public class FileUtil {
	
	public static final Logger logger = Logger.getLogger(FileUtil.class);
	
	private static Object getPropertyValueObject(String filePath, String key) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			
			return prop.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getPropertyValue(String filePath, String key) {
		Object value = getPropertyValueObject(filePath, key);
		
		return value.toString();
	}

	
	public static int getPropertyValueInt(String filePath, String key) {
		Object value = getPropertyValueObject(filePath, key);
		
		return Integer.parseInt(value.toString());
	}
	
	public static boolean getPropertyValueBoolean(String filePath, String key) {
		Object value = getPropertyValueObject(filePath, key);
		
		return Boolean.parseBoolean(value.toString());
	}
	
	/**
	 * 上传文件到文件服务器
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String, Object> upload2FileServer(HttpServletRequest req, HttpServletResponse res) {
		HttpClient httpclient = new DefaultHttpClient();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpPost httppost = new HttpPost(SystemConfig.getSystemProperty("carserviceApi")+"/FileUtil/UploadFile");
			MultipartEntity reqEntity = new MultipartEntity();
			Map<String, MultipartFile> files = ((MultipartHttpServletRequest)req).getFileMap();
			Iterator<String> it = files.keySet().iterator();
			while(it.hasNext()){
				String fileinputname = it.next();
				MultipartFile tagfile = files.get(fileinputname);
				String filename = tagfile.getOriginalFilename();
				//判断文件后缀
				if(!isValidImageFile(filename)&&!isValidExcelFile(filename)){
					result.put("status", Retcode.FAILED.code);
					result.put("message","上传文件后缀不合法");
					return result;
				}
				InputStreamBody body = new InputStreamBody(tagfile.getInputStream(), filename);
				FormBodyPart form = new FormBodyPart(filename, body);
				reqEntity.addPart(form);
			}
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("文件服务器正常响应.....");
				HttpEntity resEntity = response.getEntity();
				String resultstr = EntityUtils.toString(resEntity);
				System.out.println(resultstr);
				System.out.println(resEntity.getContent());
				JSONObject fres = JSONObject.fromObject(resultstr);
				EntityUtils.consume(resEntity);
				result.put("status", fres.get("status"));
				result.put("message", fres.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * 通过流上传文件
	 * @param in 文件的输入流
	 * @param filename 文件的名字
	 * @return
	 */
	public static Map<String, Object> upload2FileServer(InputStream in,String filename) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(SystemConfig.getSystemProperty("carserviceApiUrl")+"/FileUtil/UploadFile");
//		HttpPost httppost = new HttpPost("http://10.10.10.100:8002/carservice-api/FileUtil/UploadFile");
		MultipartEntity reqEntity = new MultipartEntity();
		//判断文件后缀
		if(!isValidImageFile(filename)&&!isValidExcelFile(filename)){
			result.put("status", Retcode.FAILED.code);
			result.put("message","上传文件后缀不合法");
			return result;
		}
		InputStreamBody body = new InputStreamBody(in, filename);
		FormBodyPart form = new FormBodyPart(filename, body);
		reqEntity.addPart(form);
		
		httppost.setEntity(reqEntity);
		try{
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				System.out.println("文件服务器正常响应.....");
				HttpEntity resEntity = response.getEntity();
				String resultstr = EntityUtils.toString(resEntity);
				System.out.println(resultstr);
				System.out.println(resEntity.getContent());
				JSONObject fres = JSONObject.fromObject(resultstr);
				EntityUtils.consume(resEntity);
				result.put("status", fres.get("status"));
				result.put("message", fres.get("message"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 保存文件
	 * @param filename
	 * @param bytes
	 */
	public static void saveFile(String fileName,byte[] bytes){
		if(fileName == null || fileName.isEmpty()) return;
		try {
			File file = new File(fileName);
			if(!file.exists()){
				file.mkdirs();
				file.delete();
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static void readFile(String fileName,byte[] bytes){
		if(fileName == null || fileName.isEmpty()) return;
		File file = new File(fileName);
		if(!file.exists()) return;
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytes);
			fis.close();
		} catch (Exception e) {
			
		}
		return;
	}

	/**
	 * 合法的图片后缀
	 */
	private static List<String> IMAGES_POSTFIX = new ArrayList<String>();

	/**
	 * 合法的excel后缀
	 */
	private static List<String> EXCELS_POSTFIX = new ArrayList<String>();

	static {
		//初始化合法的图片后缀
		IMAGES_POSTFIX.add("png");
		IMAGES_POSTFIX.add("img");
		IMAGES_POSTFIX.add("jpeg");
		IMAGES_POSTFIX.add("jpg");
		IMAGES_POSTFIX.add("jpe");
		IMAGES_POSTFIX.add("gif");
		IMAGES_POSTFIX.add("tga");
		IMAGES_POSTFIX.add("wmf");
		IMAGES_POSTFIX.add("bmp");
		//初始化合法的excel后缀
		EXCELS_POSTFIX.add("xls");
		EXCELS_POSTFIX.add("xlsx");
	}

	/**
	 * 是否是合法的图片文件
	 * @return
	 */
	public static boolean isValidImageFile(String filename){
		if(StringUtils.isBlank(filename)){
			return false;
		}
		int index = filename.lastIndexOf(".");
		if(index<=0||index>=filename.length()-1){
			return false;
		}
		String postfix = filename.substring(index+1);
		if(!IMAGES_POSTFIX.contains(postfix.toLowerCase())){
			return false;
		}
		return true;
	}

	/**
	 * 是否是合法的图片文件
	 * @return
	 */
	public static boolean isValidExcelFile(String filename){
		if(StringUtils.isBlank(filename)){
			return false;
		}
		int index = filename.lastIndexOf(".");
		if(index<=0||index>=filename.length()-1){
			return false;
		}
		String postfix = filename.substring(index+1);
		if(!EXCELS_POSTFIX.contains(postfix.toLowerCase())){
			return false;
		}
		return true;
	}

}
