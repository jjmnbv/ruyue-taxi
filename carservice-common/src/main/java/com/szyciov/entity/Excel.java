package com.szyciov.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName Excel 
 * @author Efy Shu
 * @Description excel数据类
 * @date 2016年9月8日 下午4:57:29
 * <pre>
 * 例子:
 *         colName = {"列1","列2""列3","列4""列5","列6""列7","列8"};
 *         colData = [{{"列1":[{"列1数据1","列1数据2","列1数据3","列1数据4","列1数据5"}]},{"列2":...}},...];
 *     生成结果:
 *     列1        |        列2        |        列3        |...
 *     列1数据1    |     列2数据1                ...
 *     列1数据2    |     列2数据1
 *     列1数据3    |        ...
 * </pre>
 */
public class Excel {
	private String title;
	private List<String> colName;
	private Map<String,List<Object>> colData;
	private List<String> rowData;
	private int sheetMaxRow = -1;
	private int picIndex = 0;
	private List<String> pics = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getColName() {
		return colName;
	}
	public void setColName(List<String> colName) {
		this.colName = colName;
	}
	public Map<String, List<Object>> getColData() {
		return colData;
	}
	public void setColData(Map<String, List<Object>> colData) {
		this.colData = colData;
	}
	public List<String> getRowData() {
		return rowData;
	}
	public void setRowData(List<String> rowData) {
		this.rowData = rowData;
	}
	public int getSheetMaxRow() {
		return sheetMaxRow;
	}
	public void setSheetMaxRow(int sheetMaxRow) {
		this.sheetMaxRow = sheetMaxRow;
	}
	public List<String> getPics() {
		return pics;
	}
	public void setPics(List<String> pics) {
		this.pics = pics;
	}
	public String getNextPic(){
		if(picIndex >= pics.size()){
			return "";
		}
		return pics.get(picIndex++);
	}
	public void showData(){
		for(String name : colName){
			System.out.print(name + " | ");
		}
		System.out.println();
		int maxColIx = colName.size();
		for(int rowIx = 0;rowIx < colData.get(colName.get(0)).size();rowIx++){
			for(int colIx = 0;colIx < maxColIx;colIx++){
				System.out.print(colData.get(colName.get(colIx)).get(rowIx) + " | ");
			}
			System.out.println();
		}
	}
}
