package com.szyciov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.szyciov.entity.Excel;

import net.sf.json.JSONArray;

/**
 * @ClassName ExcelImport 
 * @author Efy Shu
 * @Description excel导入类
 * @date 2017年8月7日 上午11:01:07 
 */
public class ExcelImport {
	private String tempFilePath;                                  //解压文件临时目录
	private String picPath = File.separator + "pictures" + File.separator ;                     //图片文件目录
	private int titleRowCount = 0;                               //标题所占行数
	private int[] picColIndex = new int[]{8,9};            //图片所在列
	private Excel excel;

	/**
	 * 读取zip中的excel文件(包括外挂图片资源)
	 * @param excelFile
	 * @return
	 */
	public Excel readExcelFileInZip(String zipFilePath){
		try {
			File xlsFile = unzipFile(zipFilePath);
			tempFilePath = zipFilePath.substring(0,zipFilePath.lastIndexOf("."));
			picPath = tempFilePath + picPath;
			InputStream is = new FileInputStream(xlsFile);
			Workbook workbook = WorkbookFactory.create(is);
			excel = new Excel();
			if(workbook instanceof HSSFWorkbook){
				readXls((HSSFWorkbook)workbook,false);
			}else{
				readXlsx((XSSFWorkbook)workbook,false);
			}
			FileUtil.delFile(zipFilePath);
			FileUtil.delFile(tempFilePath);
		} catch (Exception e) {
			System.err.println("读取excel文件失败: ");
			e.printStackTrace();
		}
		return excel;
	}
	
	/**
	 * 读取excel文件(包括内嵌图片)
	 * @param excelFilePath
	 * @return
	 */
	public Excel readExcelFile(String excelFilePath){
		try {
			InputStream is = new FileInputStream(excelFilePath);
			Workbook workbook = WorkbookFactory.create(is);
			excel = new Excel();
			saveAllPics(workbook); //保存所有图片
			if(workbook instanceof HSSFWorkbook){
				readXls((HSSFWorkbook)workbook,true);
			}else{
				readXlsx((XSSFWorkbook)workbook,true);
			}
		} catch (Exception e) {
			System.err.println("读取excel文件失败: ");
			e.printStackTrace();
		}
		return excel;
	}
	
	/**
	 * 读取03.xls文件
	 * @param workbook
	 * @param isInlinePic 是否内嵌图片
	 */
	private void readXls(HSSFWorkbook workbook,boolean isInlinePic){
		try {
			// 循环每一页，并处理当前循环页
			for (int i=0;i<workbook.getNumberOfSheets();i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				if (sheet == null) continue;
				getColNames(sheet);   //获取栏目名
				// 处理当前页，循环读取每一行
				if(isInlinePic){
					getHSSFColDatas(sheet);
				}else{
					getHSSFColDatasWithResource(sheet);
				}
			}
			excel.showData();
		} catch (Exception e) {
			System.err.println("读取xls文件出错:");
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取07~.xlsx文件
	 * @param workbook
	 * @param isInlinePic 是否内嵌图片
	 */
	private void readXlsx(XSSFWorkbook workbook,boolean isInlinePic){
		try {
			// 循环每一页，并处理当前循环页
			for (int i=0;i<workbook.getNumberOfSheets();i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				if (sheet == null) continue;
				getColNames(sheet);   //获取列名
				// 处理当前页，循环读取每一行
				if(isInlinePic){
					getXSSFColDatas(sheet);
				}else{
					getXSSFColDatasWithResource(sheet);
				}
			}
		} catch (Exception e) {
			System.err.println("读取xlsx文件出错:");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取列名
	 * @param sheet
	 */
	private void getColNames(Sheet sheet){
		List<String> colName = new ArrayList<>();
		Row row = sheet.getRow(titleRowCount);
		int minColIx = row.getFirstCellNum();
		int maxColIx = row.getLastCellNum();
		for (int i = minColIx; i < maxColIx; i++) {
			Cell cell = row.getCell(i);
			colName.add(getCellValue(cell));
		}
		excel.setColName(colName);
	}
	
	/**
	 * 获取列内容
	 * @param sheet HSSFSheet
	 */
	private void getHSSFColDatasWithResource(HSSFSheet sheet){
		HSSFRow row = sheet.getRow(titleRowCount);
		int minColIx = row.getFirstCellNum();
		int maxColIx = excel.getColName().size();
		Map<String, List<Object>> colData = new HashMap<>();
		for (int colIx = minColIx; colIx < maxColIx; colIx++) {
			List<Object> rowList = new ArrayList<>();
			for (int rowNum = titleRowCount+1; rowNum <= sheet.getLastRowNum();rowNum++) {
				row = sheet.getRow(rowNum);
				HSSFCell cell = row.getCell(colIx);
				if(isPicCol(colIx)){
					savePicToServer(getCellValue(cell));
					rowList.add(excel.getNextPic());
				}else{
					rowList.add(getCellValue(cell));
				}
			}
			colData.put(excel.getColName().get(colIx), rowList);
		}
		excel.setColData(colData);
	}
	
	/**
	 * 获取列内容
	 * @param sheet HSSFSheet
	 */
	private void getHSSFColDatas(HSSFSheet sheet){
		HSSFRow row = sheet.getRow(titleRowCount);
		int minColIx = row.getFirstCellNum();
		int maxColIx = excel.getColName().size();
		Map<String, List<Object>> colData = new HashMap<>();
		for (int colIx = minColIx; colIx < maxColIx; colIx++) {
			List<Object> rowList = new ArrayList<>();
			boolean canAddPic = true;
			for (int rowNum = titleRowCount+1; rowNum <= sheet.getLastRowNum();rowNum++) {
				if(isPicCol(colIx) && canAddPic){
//					String pic = excel.getNextPic();
//					System.out.println("========================" + colIx + ":" + pic);
//					rowList.add(pic);
					rowList.add(excel.getNextPic());
					canAddPic = false;
				}else{
					row = sheet.getRow(rowNum);
					HSSFCell cell = row.getCell(colIx);
					rowList.add(getCellValue(cell));
				}
			}
			colData.put(excel.getColName().get(colIx), rowList);
		}
		excel.setColData(colData);
	}
	
	/**
	 * 获取列内容(外挂资源文件)
	 * @param sheet XSSFSheet
	 */
	private void getXSSFColDatasWithResource(XSSFSheet sheet){
		XSSFRow row = sheet.getRow(titleRowCount);
		int minColIx = row.getFirstCellNum();
		int maxColIx = excel.getColName().size();
		Map<String, List<Object>> colData = new HashMap<>();
		for (int colIx = minColIx; colIx < maxColIx; colIx++) {
			List<Object> rowList = new ArrayList<>();
//			boolean canAddPic = true;
			for (int rowNum = titleRowCount+1; rowNum <= sheet.getLastRowNum();rowNum++) {
				row = sheet.getRow(rowNum);
				XSSFCell cell = row.getCell(colIx);
				if(isPicCol(colIx)){
					savePicToServer(getCellValue(cell));
					rowList.add(excel.getNextPic());
				}else{
					rowList.add(getCellValue(cell));
				}
			}
			colData.put(excel.getColName().get(colIx), rowList);
		}
		excel.setColData(colData);
	}
	
	/**
	 * 获取列内容
	 * @param sheet XSSFSheet
	 */
	private void getXSSFColDatas(XSSFSheet sheet){
		XSSFRow row = sheet.getRow(titleRowCount);
		int minColIx = row.getFirstCellNum();
		int maxColIx = excel.getColName().size();
		Map<String, List<Object>> colData = new HashMap<>();
		for (int colIx = minColIx; colIx < maxColIx; colIx++) {
			List<Object> rowList = new ArrayList<>();
			boolean canAddPic = true;
			for (int rowNum = titleRowCount+1; rowNum <= sheet.getLastRowNum();rowNum++) {
				if(isPicCol(colIx) && canAddPic){
//					String pic = excel.getNextPic();
//					System.out.println("========================" + colIx + ":" + pic);
//					rowList.add(pic);
					rowList.add(excel.getNextPic());
					canAddPic = false;
				}else{
					row = sheet.getRow(rowNum);
					XSSFCell cell = row.getCell(colIx);
					rowList.add(getCellValue(cell));
				}
			}
			colData.put(excel.getColName().get(colIx), rowList);
		}
		excel.setColData(colData);
	}
	
	/**
	 * 是否是图片所在列
	 * @param colIndex
	 * @return
	 */
	private boolean isPicCol(int colIndex){
		for(int pci : picColIndex){
			if(colIndex == pci){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 保存资源文件到文件服务器
	 * @param picName
	 * @return
	 */
	private void savePicToServer(String picName){
		if(picName == null || picName.trim().isEmpty()) return;
		try {
			String currentPicPath = picPath + picName;
			File file = new File(currentPicPath);
			Map<String, Object> result = FileUtil.upload2FileServer(new FileInputStream(file), file.getName());
			if(result != null && !result.isEmpty()){
				if("success".equals(result.get("status"))){
					String filepath = ((JSONArray)result.get("message")).getString(0);
					excel.getPics().add(filepath);
				}else{
					System.err.println(result.get("message"));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked"})
	private void saveAllPics(Workbook workbook){
		if(workbook instanceof HSSFWorkbook){
			List<HSSFPictureData> hssfPics = (List<HSSFPictureData>) workbook.getAllPictures();
			int index = 0;
			for(HSSFPictureData picData : hssfPics){
				String nameNum = StringUtil.formatNumToLength(index++, (hssfPics.size()+"").length(), "0");
				String picPath = "Excel内部图片_" + nameNum + "." + picData.getMimeType().replace("image/", "");
				saveToFileServer(picPath,picData.getData());
			}  
		}else{
	        List<XSSFPictureData> xssfPics = (List<XSSFPictureData>)workbook.getAllPictures();
	        int index = 0;
			for(XSSFPictureData picData : xssfPics){
				String nameNum = StringUtil.formatNumToLength(index++, (xssfPics.size()+"").length(), "0");
				String picPath = "Excel内部图片_" + nameNum + "." + picData.getMimeType().replace("image/", "");
				saveToFileServer(picPath,picData.getData());
			}  
		}
	}
	
	/**
	 * 保存excel中的图片到服务器并获取服务器路径
	 * @param picPath
	 * @param picData
	 */
	private void saveToFileServer(String picPath,byte[] picData){
		try {
			File file = new File(picPath);
			FileUtil.saveFile(picPath, picData);
			Map<String, Object> result = FileUtil.upload2FileServer(new FileInputStream(file), file.getName());
			if(result != null && !result.isEmpty()){
				if("success".equals(result.get("status"))){
					String filepath = ((JSONArray)result.get("message")).getString(0);
					excel.getPics().add(filepath);
				}else{
					System.err.println(result.get("message"));
				}
			}
			FileUtil.delFile(picPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解压zip文件
	 * @param zipPath
	 */
	private File unzipFile(String zipPath){
		File zipFile = new File(zipPath);
		try {
			File tempFilePath = new File(zipFile.getParent()+"/"+zipFile.getName().substring(0, zipFile.getName().lastIndexOf(".")));
			FileUtil.delFile(tempFilePath.getPath());
			ZipUtil.unzip(zipPath, zipFile.getParent(), true);
			for(File xlsFile : tempFilePath.listFiles()){
				if(xlsFile.isFile() 
					&& (xlsFile.getName().endsWith(".xls") || xlsFile.getName().endsWith(".xlsx"))){
					return xlsFile;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
//		FileUtil.delFile(zipFile.getAbsolutePath());
//		FileUtil.delFile(zipFile.getPath());
	}
	
	
	
	/**
	 * 获取单元格的类型
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getCellType(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return "Boolean";
		case Cell.CELL_TYPE_FORMULA:
			return "Formula";
		case Cell.CELL_TYPE_NUMERIC:
			return "Numer";
		case Cell.CELL_TYPE_STRING:
			if("{图片}".equals(cell.getStringCellValue())) return "Picture";
			return "String";
		default:
			return "";
		}
	}
	
	/**
	 * 读取数字类型时转换为String读取,防止科学计数丢失精度
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell){
		if(cell == null) return "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}
	
	
	/**  
	 * 获取picColIndex  
	 * @return picColIndex picColIndex  
	 */
	public int[] getPicColIndex() {
		return picColIndex;
	}
	

	/**  
	 * 设置picColIndex  
	 * @param picColIndex picColIndex  
	 */
	public void setPicColIndex(int[] picColIndex) {
		this.picColIndex = picColIndex;
	}
	

	public static void main(String[] args) {
		ExcelImport ei = new ExcelImport();
//		File excelFile = new File("E:\\工作文档\\工作簿1.xls");
		ei.readExcelFileInZip("E:\\工作文档\\zipPath\\test.zip");
//		ei.readExcelFile(excelFile.getAbsolutePath());
	}
}
