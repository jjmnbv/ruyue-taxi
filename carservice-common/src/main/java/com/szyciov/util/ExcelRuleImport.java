package com.szyciov.util;

import java.util.Map;

/**
 * Excel 规则导出
 * */
public interface ExcelRuleImport {
	/**
	 * index  第几行
	 * map 第几行的map
	 * */
	public boolean excelRuleImport(int index,Map<String, String> map);

	/**
	 * 验证第一行标题
	 * @param columns
	 * @return
	 */
	boolean VerifyFirstTitle(String[] columns);
}
