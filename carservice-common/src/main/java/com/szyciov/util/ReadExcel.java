package com.szyciov.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.szyciov.entity.PubAirPortAddr;

/**
 * @author Hongten
 * @created 2014-5-20
 */
public class ReadExcel {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static final String LIB_PATH = "lib";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";
    
    
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public List<PubAirPortAddr> readExcel(String path) throws IOException {
        if (path == null || EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = path.substring(path.lastIndexOf(".")+1);
            if (!EMPTY.equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<PubAirPortAddr> readXlsx(String path) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        PubAirPortAddr PubAirPortaddr = null;
        List<PubAirPortAddr> list = new ArrayList<PubAirPortAddr>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    PubAirPortaddr = new PubAirPortAddr();
                    XSSFCell city = xssfRow.getCell(1);
                    XSSFCell name = xssfRow.getCell(2);
                    PubAirPortaddr.setCity(getValue(city));
                    PubAirPortaddr.setName(getValue(name));
                    list.add(PubAirPortaddr);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<PubAirPortAddr> readXls(String path) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        PubAirPortAddr pubAirportaddr = null;
        List<PubAirPortAddr> list = new ArrayList<PubAirPortAddr>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	pubAirportaddr = new PubAirPortAddr();
                    HSSFCell city = hssfRow.getCell(1);
                    HSSFCell name = hssfRow.getCell(2);
                    pubAirportaddr.setId("");
                    pubAirportaddr.setLng(0.0);
                    pubAirportaddr.setLat(0.0);
                    pubAirportaddr.setCreatetime(new Date());
                    pubAirportaddr.setUpdatetime(new Date());
                    pubAirportaddr.setStatus(1);
                    pubAirportaddr.setCity(getValue(city));
                    pubAirportaddr.setName(getValue(name));
                    list.add(pubAirportaddr);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    public static void main(String[] args) throws IOException {
		ReadExcel re = new ReadExcel();
		String path  = "E:\\工作文档\\网约车项目文档\\网约车项目\\020.需求分析\\数据字典\\城市和机场字典.xlsx";
		List<PubAirPortAddr> list = re.readExcel(path);
		for(PubAirPortAddr p : list){
			System.out.println(p.getCity());
		}
	}
}