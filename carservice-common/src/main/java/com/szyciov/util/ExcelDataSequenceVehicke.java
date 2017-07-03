package com.szyciov.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.szyciov.entity.Excel;

/**
 * @ClassName ExcelExport
 * @author Efy
 * @Description excel操作类,使用时先调用{@link setOs},不调用则默认使用FileOutputStream
 * @date 2016年7月19日 下午4:03:23
 * 带序列的excel导出
 */
public class ExcelDataSequenceVehicke {
	private Workbook workbook = null;
	private int sheetMaxRow = 60000;
	private String sheetName = "数据页";
	private int titleRowCount = 1;                         //标题所占行数
	private int defaultColWidth = 20;
	
	private Excel excel;
	
	
	private OutputStream os = null;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ExcelDataSequenceVehicke(Excel excel) {
		this.excel = excel;
		
	}
	
	public ExcelDataSequenceVehicke(HttpServletRequest request,HttpServletResponse response,Excel excel){
		this.request = request;
		this.response = response;
		this.excel = excel;
	}
	
	/**
	 * 创建excel文件
	 * 
	 * @param targetFile 目标文件
	 */
	public void createExcel(File targetFile,String[] s,String[] s1,String[] s2) {
		List<String> colName = excel.getColName();
		Map<String, List<Object>> colData = excel.getColData();

		int maxRow = getMostCount(colData);
		try {
			// XSSFWorkbook used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
			workbook = new HSSFWorkbook();// XSSFWorkbook();//WorkbookFactory.create(inputStream);
		} catch (Exception e) {
			System.err.println("创建Excel失败: ");
			e.printStackTrace();
		}
		if (workbook != null) {
			// 如果超过Sheet最大行数,则建立多个Sheet
			if (maxRow > sheetMaxRow-titleRowCount) {
				for (int i = 1; i - 1 < (maxRow % (sheetMaxRow - titleRowCount)> 0 ? maxRow / (sheetMaxRow - titleRowCount) + 1 : maxRow / (sheetMaxRow - titleRowCount)); i++) {
					workbook.createSheet(sheetName + i);
					Sheet sheet = workbook.getSheetAt(i - 1);
					sheet.setDefaultColumnWidth(defaultColWidth);    //设置默认列宽
					writeName(sheet, colName);
				}
			} else {
				workbook.createSheet(sheetName);
				Sheet sheet = workbook.getSheetAt(0);
				sheet.setDefaultColumnWidth(defaultColWidth);        //设置默认列宽
				writeName(sheet, colName);
			}
			// 填入列数据
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < colName.size(); i++) {
				writeData(sheet, colData.get(colName.get(i)), i,s,s1,s2);
			}
			try {
				if(request == null || response == null){
					os = new FileOutputStream(targetFile);
				}else{
					String decodeName = decodeFileName(targetFile.getName());
					response.reset();
					response.addHeader("Content-Disposition", "attachment;filename=" + decodeName);
//					response.addHeader("Content-Length", "" + targetFile.length());
					response.addHeader("Transfer-Encoding", "Chunked"); //改为Chunked则不需要声明文件长度,由http协议自动判断
					response.setContentType("application/octet-stream");
					os = response.getOutputStream();
				}
				workbook.write(os);
				os.flush();
				os.close();
			} catch (Exception e) {
				System.out.println("写入Excel失败: ");
				e.printStackTrace();
			}
		}
	}

	private int getMostCount(Map<String, List<Object>> colData) {
		int most = 0;
		for (String key : colData.keySet()) {
			if (most < colData.get(key).size())
				most = colData.get(key).size();
		}
		return most;
	}

	private void writeName(Sheet sheet, List<String> colName) {
		int colNum = 0;
		//合并单元格
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colName.size()-1));
//		Row row0 = sheet.createRow(0);                             //创建标题单元格
////		row0.setHeight((short) 500);
//		Cell title = row0.createCell(colNum, Cell.CELL_TYPE_STRING);
//		Font font = workbook.createFont();
//		font.setFontName("方正小标宋简体");                         //设置标题字体
//		font.setFontHeightInPoints((short) 18);                    //设置标题大小
//		CellStyle style = workbook.createCellStyle();
//		style.setFont(font);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //设置居中
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);        //左边框
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);       //上边框
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);     //右边框
//        title.setCellStyle(style);
//		title.setCellValue(excel.getTitle());
		
		Row row = sheet.createRow(0);
		Font nameFont = workbook.createFont();
		nameFont.setFontName("黑体");                         //设置标题字体
		nameFont.setFontHeightInPoints((short) 12);   //设置标题大小
		CellStyle nameStyle = workbook.createCellStyle();
		nameStyle.setFont(nameFont);
		nameStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //设置居中
		nameStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		nameStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);        //左边框
		nameStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);       //上边框
		nameStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);     //右边框
		nameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		nameStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		for (String col : colName) {
			Cell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING);
			cell.setCellStyle(nameStyle);
			cell.setCellValue(col);
			colNum++;
		}
	}

	private void writeData(Sheet sheet, List<Object> colData, int colNum,String[] s,String[] s1,String[] s2) {
		int rowNum = titleRowCount;
//		sheet.autoSizeColumn(colNum);//自动调整宽度
		Font dataFont = workbook.createFont();
		dataFont.setFontName("仿宋");                         //设置标题字体
		dataFont.setFontHeightInPoints((short) 12);   //设置标题大小
		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setWrapText(true);
		dataStyle.setFont(dataFont);
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);      //设置居中
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);        //左边框
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);       //上边框
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);     //右边框
		try {
			for (Object col : colData) {
				// 当数据行大于Sheet最大行数时,换下一个Sheet
				if (rowNum + 1 > sheetMaxRow) {
					int currSheetIndex = workbook.getSheetIndex(sheet);
					sheet = workbook.getSheetAt(currSheetIndex + 1);
					rowNum = titleRowCount;
				}
				Row row = sheet.getRow(rowNum);
				row = row == null ? sheet.createRow(rowNum) : row;
				Cell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING);
				//起始行序号，终止行序号，起始列序号，终止列序号
				CellRangeAddressList regions = new CellRangeAddressList(1, 20,0, 0);
				DVConstraint constraint =DVConstraint.createExplicitListConstraint(s);
				HSSFDataValidation dataValidate = new HSSFDataValidation(regions,constraint);
				
				CellRangeAddressList regions1 = new CellRangeAddressList(1, 20,2, 2);
				DVConstraint constraint1 =DVConstraint.createExplicitListConstraint(s1);
				HSSFDataValidation dataValidate1 = new HSSFDataValidation(regions1,constraint1);
				
				sheet.addValidationData(dataValidate);
				sheet.addValidationData(dataValidate1);
				if(s2.length > 0){
					CellRangeAddressList regions2 = new CellRangeAddressList(1, 20,7, 7);
					DVConstraint constraint2 =DVConstraint.createExplicitListConstraint(s2);
					HSSFDataValidation dataValidate2 = new HSSFDataValidation(regions2,constraint2);
					sheet.addValidationData(dataValidate2);
				}
				cell.setCellStyle(dataStyle);
				cell.setCellValue(objectToString(col));
					
				rowNum++;
			}
		} catch (Exception e) {
			System.err.println("写入列数据失败.当前列:" + colNum + " 当前行:" + rowNum + " 写入数据:" + colData.get(rowNum - 1));
			e.printStackTrace();
		}
	}

	public int getSheetMaxRow() {
		return sheetMaxRow;
	}

	private String objectToString(Object src){
		String result = null;
		switch (src.getClass().getSimpleName().toUpperCase()) {
		case "DATE":
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(src);
			break;
		case "INT":
		case "DOUBLE":
		case "FLOAT":
			result = src+"";
			break;
		default:
			result = src.toString();
			break;
		}
		return result;
	}
	
	/**
	 * 设置sheet最大行数(默认60000),超过时会新建sheet
	 * @param sheetMaxRow
	 */
	public void setSheetMaxRow(int sheetMaxRow) {
		this.sheetMaxRow = sheetMaxRow+titleRowCount;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public int getTitleRowCount() {
		return titleRowCount;
	}

	/**
	 * 设置标题所占用行数
	 * @param titleRowCount
	 */
	public void setTitleRowCount(int titleRowCount) {
		this.titleRowCount = titleRowCount;
	}

	public int getDefaultColWidth() {
		return defaultColWidth;
	}

	/**
	 * 设置默认列宽(默认20)
	 * @param defaultColWidth
	 */
	public void setDefaultColWidth(int defaultColWidth) {
		this.defaultColWidth = defaultColWidth;
	}

	/**
	 * 解决中文乱码问题
	 * @param filename
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String decodeFileName(String filename) throws UnsupportedEncodingException{
		String finalFileName = "";
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (-1 != agent.indexOf("MSIE")) {
				// IE浏览器访问
				finalFileName = (new StringBuilder(String.valueOf(URLEncoder.encode(filename, "utf-8")))).toString();
			} else if (-1 != agent.indexOf("Firefox")) {
				finalFileName = (new StringBuilder(String.valueOf(new String(filename.getBytes("utf-8"), "ISO8859_1"))))
						.toString();
			} else if (-1 != agent.indexOf("Chrome")) {
				finalFileName = (new StringBuilder(String.valueOf(new String(filename.getBytes("utf-8"), "ISO8859_1"))))
						.toString();
			} else if (-1 != agent.indexOf("Safari")) {
				finalFileName = (new StringBuilder(String.valueOf(new String(filename.getBytes("utf-8"), "ISO8859_1"))))
						.toString();
			} else {
				// 其他浏览器,判断内核
				if (-1 != agent.indexOf("Trident")) {
					finalFileName = (new StringBuilder(String.valueOf(URLEncoder.encode(filename, "utf-8"))))
							.toString();
				} else {
					finalFileName = (new StringBuilder(
							String.valueOf(new String(filename.getBytes("utf-8"), "ISO8859_1")))).toString();
				}
			}
		}
		return finalFileName;
	}
	
//	public static void main(String[] args) {
//		String name = "test123124234";
//		List<String> colName = new ArrayList<>();
//		colName.add("姓名");
//		colName.add("年龄");
//		colName.add("性别");
//		List<Object> colData1 = new ArrayList<>();
//		colData1.add("张三张三张三张三张三张三张三张三张三张三");
//		colData1.add("李四");
//		colData1.add("王五");
//		colData1.add("张三");
//		colData1.add("李四");
//		colData1.add("王五");
//		colData1.add("张三");
//		colData1.add("李四");
//		colData1.add("王五");
//		colData1.add("张三");
//		colData1.add("李四");
//		List<Object> colData2 = new ArrayList<>();
//		colData2.add("18");
//		colData2.add("19");
//		colData2.add("20");
//		colData2.add("11");
//		colData2.add("12");
//		colData2.add("23");
//		colData2.add("14");
//		colData2.add("15");
//		colData2.add("26");
//		colData2.add("17");
//		colData2.add("28");
//		List<Object> colData3 = new ArrayList<>();
//		colData3.add("男");
//		colData3.add("女");
//		colData3.add("男");
//		colData3.add("男");
//		colData3.add("女");
//		colData3.add("男");
//		colData3.add("男");
//		colData3.add("女");
//		colData3.add("男");
//		colData3.add("男");
//		colData3.add("女");
//		Map<String, List<Object>> colData = new HashMap<>();
//		colData.put("姓名", colData1);
//		colData.put("年龄", colData2);
//		colData.put("性别", colData3);
//		Excel excel = new Excel();
//		excel.setTitle(name);
//		excel.setColName(colName);
//		excel.setColData(colData);
//		ExcelDataSequence ee = new ExcelDataSequence(excel);
//		ee.setSheetMaxRow(6);
//		ee.createExcel(new File("e:/test11.xls"));
//	}
}
