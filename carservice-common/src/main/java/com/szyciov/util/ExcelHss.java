package com.szyciov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 
 * 解析xls格式的excel
 * 
 * **/
public class ExcelHss {
	private HSSFWorkbook hssfworkbook; //xls返回的是HSSFWorkbook
	private HSSFSheet hssfSheet;
	private HSSFRow hssfRow;
	private int columnNumbers;
	private int totalRows;
	private List<String> nullable;
	/**
	 * 解析每一列
	 * */
	private String getCellFormatValueHss(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case XSSFCell.CELL_TYPE_NUMERIC: {
				BigDecimal big = new BigDecimal(cell.getNumericCellValue());
				cellvalue = big.toString();
				break;
			}
			case XSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				/*
				 * if (XSSFDateUtil.isCellDateFormatted(cell)) { //
				 * 如果是Date类型则，转化为Data格式
				 * 
				 * //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00 //cellvalue =
				 * cell.getDateCellValue().toLocaleString();
				 * 
				 * //方法2：这样子的data格式是不带带时分秒的：2011-10-12 Date date =
				 * cell.getDateCellValue(); SimpleDateFormat sdf = new
				 * SimpleDateFormat("yyyy-MM-dd"); cellvalue = sdf.format(date);
				 * 
				 * } // 如果是纯数字 else { // 取得当前Cell的数值 cellvalue =
				 * String.valueOf(cell.getNumericCellValue()); }
				 */
				BigDecimal bigula = new BigDecimal(cell.getCachedFormulaResultType());
				cellvalue = bigula.toString();
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}
	/**
	 * 解析Excel第一行
	 * */
	public String[] getTitleHss(InputStream is) {
		try {
			hssfworkbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		hssfSheet = hssfworkbook.getSheetAt(0);
		System.out.println("hssfSheet.getSheetName() = " + hssfSheet.getSheetName());

		// 获得行数
		totalRows = hssfSheet.getLastRowNum();
		System.out.println("totalRows = " + totalRows);
		hssfRow = hssfSheet.getRow(0);// first line:title
		// 获得列数
		if(hssfRow != null){
			columnNumbers = hssfRow.getPhysicalNumberOfCells();
		}
		System.out.println("columnNumbers = " + columnNumbers);

		// 列名的集合
		String[] title = new String[columnNumbers];
		for (int i = 0; i < columnNumbers; i++) {
			title[i] = getCellFormatValueHss(hssfRow.getCell(i));
		}

		// 返回的是列名的数组
		return title;
	}
	/**
	 * 得到整个Excel的解析 有成功失败的
	 * */
	public Map<String, Map> excelImport(InputStream is){
		String[] title = getTitleHss(is);
		Map<Integer, Map> meiyihang = new HashMap<Integer, Map>();
		Map<String, Map> callback = new HashMap<String, Map>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= totalRows; i++) {
			Map<String, String> map = new HashMap<String, String>();
			hssfRow = hssfSheet.getRow(i);
			if(hssfRow == null){
				continue;
			}
			int j = 0;
			while (j < columnNumbers) {
				String cell = getCellFormatValueHss(hssfRow.getCell(j)).trim();
				if (!cell.equals("")) {
					if(hssfRow.getCell(j).getCellType()==0 && DateUtil.isCellDateFormatted(hssfRow.getCell(j))){// 判断单元格是否属于日期格式  
						Date date1 = hssfRow.getCell(j).getDateCellValue();
					    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd"); 
					    String date = dff.format(date1);   //日期转化
					    map.put(title[j], date);
					}else{
					    if(hssfRow.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC && cell.indexOf(".")!=-1) {  
					        DecimalFormat df = new DecimalFormat("0.0");
					        cell = df.format(hssfRow.getCell(j).getNumericCellValue());
					    } 
						map.put(title[j], cell);
					}
					meiyihang.put(i, map);
				}else if(cell.equals("")){
					map.put(title[j], cell);
					meiyihang.put(i, map);
				}
				j++;
			}
			meiyihang.put(i, map);
		}
		Map<Integer, Map> failure = new HashMap<Integer, Map>();
		Map<Integer, Map> successful = new HashMap<Integer, Map>();
		for (int i = 1; i <= totalRows; i++) {
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> map1 = new HashMap<String, String>();
			for(int j=0;j<columnNumbers;j++){
				if(meiyihang.get(i) == null){
					continue;
				}
				if(meiyihang.get(i).get(title[j]).equals("")&&(nullable==null||nullable!=null&&!nullable.contains(title[j]))){
					map.put(title[j], meiyihang.get(i).get(title[j])+"");
				}else{
					map1.put(title[j], meiyihang.get(i).get(title[j])+"");
				}
				}
			if(map1.size()==0){continue;}
			if(map.size() == 0){
				successful.put(i, map1);
			}else{
				int temcount = nullable==null?columnNumbers:columnNumbers-nullable.size();
				if(map.size()>0 && map.size()<temcount){
					failure.put(i, map);
				}
//				meiyihang1.put(i, map);
			}
		}
		callback.put("successful" , successful);
		callback.put("failure", failure);
		return callback;
	}
	
	/**
	 * 得到整个Excel的解析
	 * */
	public Map<Integer, Map> excelAllImport(InputStream is){
		String[] title = getTitleHss(is);
		Map<Integer, Map> callback = new HashMap<Integer, Map>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= totalRows; i++) {
			Map<String, String> map = new HashMap<String, String>();
			boolean flag = false;
			hssfRow = hssfSheet.getRow(i);
			if(hssfRow == null){
				continue;
			}
			int j = 0;
			while (j < columnNumbers) {
				String cell = getCellFormatValueHss(hssfRow.getCell(j)).trim();
				if (!cell.equals("")) {
					if(hssfRow.getCell(j).getCellType()==0 && DateUtil.isCellDateFormatted(hssfRow.getCell(j))){// 判断单元格是否属于日期格式  
						Date date1 = hssfRow.getCell(j).getDateCellValue();
					    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd"); 
					    String date = dff.format(date1);   //日期转化
					    map.put(title[j], date);
					}else{
					    if(hssfRow.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC && cell.indexOf(".")!=-1) {  
					        DecimalFormat df = new DecimalFormat("0.0");
					        cell = df.format(hssfRow.getCell(j).getNumericCellValue());
					    } 
						map.put(title[j], cell);
					}
				}else if(cell.equals("")){
					map.put(title[j], cell);
				}
				j++;
			}
			for(int k=0;k<columnNumbers;k++){
				if(map.get(title[k]) != null && !map.get(title[k]).equals("")){
					flag = true;
				}
			}
			if(flag){
				callback.put(i, map);
			}
		}
		return callback;
	}
	/**
	 * 得到整个Excel的解析  规则接口
	 * */
	public Map<Integer, Map> excelRuleImport(InputStream is,ExcelRuleImport excelRuleImport){
		String[] title = getTitleHss(is);
		Map<Integer, Map> callback = new HashMap<Integer, Map>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= totalRows; i++) {
			Map<String, String> map = new HashMap<String, String>();
			boolean flag = false;
			hssfRow = hssfSheet.getRow(i);
			if(hssfRow == null){
				continue;
			}
			int j = 0;
			while (j < columnNumbers) {
				String cell = getCellFormatValueHss(hssfRow.getCell(j)).trim();
				if (!cell.equals("")) {
					if(hssfRow.getCell(j).getCellType()==0 && DateUtil.isCellDateFormatted(hssfRow.getCell(j))){// 判断单元格是否属于日期格式  
						Date date1 = hssfRow.getCell(j).getDateCellValue();
					    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd"); 
					    String date = dff.format(date1);   //日期转化
					    map.put(title[j], date);
					}else{
					    if(hssfRow.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC && cell.indexOf(".")!=-1) {  
					        DecimalFormat df = new DecimalFormat("0.0");
					        cell = df.format(hssfRow.getCell(j).getNumericCellValue());
					    } 
						map.put(title[j], cell);
					}
				}else if(cell.equals("")){
					map.put(title[j], cell);
				}
				j++;
			}
			for(int k=0;k<columnNumbers;k++){
				if(map.get(title[k]) != null && !map.get(title[k]).equals("")){
					flag = true;
				}
			}
			if(flag && excelRuleImport.excelRuleImport(i,map)){
				callback.put(i, map);
			}
		}
		return callback;
	}
	
	public Workbook create(InputStream inp) throws IOException,InvalidFormatException {
	    if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
	        return new HSSFWorkbook(inp);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	        return new XSSFWorkbook(OPCPackage.open(inp));
	    }
	    throw new IllegalArgumentException("你的excel版本目前poi解析不了");
	}

	
	
	public HSSFWorkbook getHssfworkbook() {
		return hssfworkbook;
	}

	public void setHssfworkbook(HSSFWorkbook hssfworkbook) {
		this.hssfworkbook = hssfworkbook;
	}

	public HSSFSheet getHssfSheet() {
		return hssfSheet;
	}

	public void setHssfSheet(HSSFSheet hssfSheet) {
		this.hssfSheet = hssfSheet;
	}

	public HSSFRow getHssfRow() {
		return hssfRow;
	}

	public void setHssfRow(HSSFRow hssfRow) {
		this.hssfRow = hssfRow;
	}

	public int getColumnNumbers() {
		return columnNumbers;
	}

	public void setColumnNumbers(int columnNumbers) {
		this.columnNumbers = columnNumbers;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public static void main(String[] args) throws Exception {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		ExcelHss ep = new ExcelHss();
		String path = "D://111.xls";
		InputStream is = new FileInputStream(new File(path));
		if(path.endsWith("xlsx")){
			System.out.println(111);
		}else if(path.endsWith("xls")){
			System.out.println(222);
		}else{
			System.out.println("不可解析文件");
		}
		ep.excelAllImport(is);
	}

	public void setExtraParam(List<String> nullable) {
		this.nullable = nullable;
	}
}