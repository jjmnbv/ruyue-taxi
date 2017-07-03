package com.szyciov.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 * @author zhu
 */
public class PYTools {
	/**
	 * 全拼音的类型
	 * @author zhu
	 */
	public static enum Type {
		UPPERCASE, // 全部大写
		LOWERCASE, // 全部小写
		FIRSTUPPER // 首字母大写
	}
	
	/**
	 * 获取拼音类型
	 * @author zhu
	 */
	private static enum RType {
		FULL, //全拼音
		FIRST //首字母
	}

	/**
	 * 获取拼音全称（默认没有分割符并且是大写）
	 * @param str
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFullPinYin(String str) throws BadHanyuPinyinOutputFormatCombination{
		return getFullPinYin(str,"",Type.UPPERCASE);
	}
	
	/**
	 * 获取拼音全称（默认大写）
	 * @param str
	 * @param spera
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFullPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination{
		return getFullPinYin(str,spera,Type.UPPERCASE);
	}
	
	/**
	 * 获取拼音全称
	 * @param str
	 * @param spera
	 * @param type
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFullPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
		return getPinYin(str,spera,type,RType.FULL);
	}
	
	/**
	 * 获取拼音首字母（没有分割符并且大写）
	 * @param str
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFirstPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
		return getPinYin(str,"",Type.UPPERCASE,RType.FIRST);
	}
	
	/**
	 * 获取拼音首字母（大写方式）
	 * @param str
	 * @param spera
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFirstPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
		return getPinYin(str,spera,Type.UPPERCASE,RType.FIRST);
	}
	
	/**
	 * 获取拼音首字母
	 * @param str
	 * @param spera
	 * @param type
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String getFirstPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
		return getPinYin(str,spera,type,RType.FIRST);
	}
	
	private static String getPinYin(String str, String spera, Type type,RType rtype) throws BadHanyuPinyinOutputFormatCombination {
		if (str == null || str.trim().length() == 0){
			return "";
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
		if (type == Type.UPPERCASE){
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		}else{
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		}

		String py = "";
		String temp = "";
		String[] t;
		for (int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if ((int) c<=128){
				py += c;
			}else{
				t = PinyinHelper.toHanyuPinyinStringArray(c, format);
				if (t == null){
					py += c;
				}else{
					if(rtype==RType.FULL){
						temp = t[0];
						if(type == Type.FIRSTUPPER){
							temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
						}
						py += temp + (i == str.length() - 1 ? "" : spera);
					}else{
						py += t[0].toUpperCase().charAt(0)+ (i == str.length() - 1 ? "" : spera);;
					}
				}
			}
		}
		return py.trim();
	}
}
