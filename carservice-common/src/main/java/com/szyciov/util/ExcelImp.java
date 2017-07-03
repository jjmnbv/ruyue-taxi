package com.szyciov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 解析excel调用的方法
 * 
 */
public class ExcelImp {
	/**
	 * mulfile Excel路径
	 * */
	public Map<Integer, Map> excelImp(MultipartFile mulfile) throws IOException{
		InputStream is = mulfile.getInputStream();
		try {
			if(mulfile.getOriginalFilename().endsWith("xlsx")){
				return new ExcelXss().excelAllImport(is);
			}else if(mulfile.getOriginalFilename().endsWith("xls")){
				return new ExcelHss().excelAllImport(is);
			}else{
				System.out.println("不可解析文件");
			}
			is = null;
		}finally {
			is.close();
		}
		return new HashMap<Integer, Map>();
	}
	/**
	 * mulfile Excel路径
	 * nullable 允许为空的list
	 * */
	public Map<String, Map> excelImp(MultipartFile mulfile,List<String> nullable) throws IOException{
		InputStream is = mulfile.getInputStream();
		try {
			if(mulfile.getOriginalFilename().endsWith("xlsx")){
				ExcelXss xss = new ExcelXss();
				xss.setExtraParam(nullable);
				return xss.excelImport(is);
			}else if(mulfile.getOriginalFilename().endsWith("xls")){
				ExcelHss hss = new ExcelHss();
				hss.setExtraParam(nullable);
				return hss.excelImport(is);
			}else{
				System.out.println("不可解析文件");
			}
			is = null;
		}finally {
			is.close();
		}
		return new HashMap<String, Map>();
	}
	/**
	 * 有规则接口
	 * */
	public Map<Integer, Map> excelImp(MultipartFile mulfile,ExcelRuleImport excelRuleImport) throws IOException{
		InputStream is = mulfile.getInputStream();
		try {
			if(mulfile.getOriginalFilename().endsWith("xlsx")){
				ExcelXss xss = new ExcelXss();
				if(excelRuleImport.VerifyFirstTitle(xss.getTitleXss(is))) {
					return xss.excelRuleImport(is, excelRuleImport);
				}
			}else if(mulfile.getOriginalFilename().endsWith("xls")){
				ExcelHss hss = new ExcelHss();
				if(excelRuleImport.VerifyFirstTitle(hss.getTitleHss(is))) {
					return hss.excelRuleImport(is, excelRuleImport);
				}
			}else{
				System.out.println("不可解析文件");
			}
			is = null;
		}finally {
			if(is!=null) {
				is.close();
			}
		}
		return new HashMap<Integer, Map>();
	}

	public static void main(String[] args) throws Exception {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		/*ExcelImp ep = new ExcelImp();
		String path = "D://111.xls";
		String path1 = "D://222.xlsx";
		ep.excelImp(path);*/
		ExcelXss e = new ExcelXss();
		String path = "D://222.xlsx";
		InputStream is = new FileInputStream(new File(path));
		e.excelRuleImport(is,new ExcelRuleImport(){

			@Override
			public boolean excelRuleImport(int index, Map<String, String> map) {
				if(map.get("法人住所地址") == null || "".equals(map.get("法人住所地址"))){
					return false;
				}
				return true;
			}

			@Override
			public boolean VerifyFirstTitle(String[] columns) {
				return true;
			}
		});
	}

}
