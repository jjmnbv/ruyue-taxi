package com.szyciov.util;

import org.apache.commons.lang.StringUtils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * 获取随机编码
 * @author zhu
 */
public class UNID {
	
	private static long counter = 1;

	/**
	 * 随机的字符串
	 */
	private final static byte[] RANDOMCHARS = { 'Y', 'A', 'U', '8', 'K', 'V', 'C', 'U', '6', 'D', 'N', 'E', 'I', 'F',
			'L', 'J', 'U', 'K', 'M', 'W', 'L', 'T', '4', 'I', 'N', '2', 'O', 'P', 'X', 'S', 'T', '7', 'U', '1', 'Q',
			'Y', '0', 'C', 'S', 'Z', '3', 'M', 'R', '5', 'B', '9' };

	/**
	 * 字母符号
	 */
	private final static byte[] RANDOMCHARS_ZM = { 'Y', 'A', 'U', 'K', 'V', 'C', 'U', 'D', 'N', 'E', 'F',
			'L', 'J', 'U', 'K', 'M', 'W', 'L', 'T', 'N','P', 'X', 'S', 'T', 'U', 'Q',
			'Y', 'C', 'S', 'Z', 'M', 'R', 'B'};

	/**
	 * 数字符号
	 */
	private final static byte[] RANDOMCHARS_SZ = {'8', '6','4', '2','7', '1','0', '3', '5', '9' };

	/**
	 * 特殊符号
	 */
	private final static byte[] RANDOMCHARS_FH = {'!', '@','#', '$','%', '&','*', '(', ')', '+' };

	private static synchronized long getNextSerial() {
		return counter++;
	}
	
	private static int getRandomAbsInt(Random rd) {
		int r = rd.nextInt() + 1;
		r = Math.abs(r);
		return r;
	}
	
	public static String getUNID(){
		return getUNID(null);
	}
	
	public static String getUNID(String id){
		byte[] buf = new byte[5];
		long ns = getNextSerial();
	    long l = System.currentTimeMillis();
	    int rcl = RANDOMCHARS.length;
	    Random rd = new Random(l + ns + ( (id != null) ? id.hashCode() : 3423));
	    for(int i=0;i<buf.length;i++){
	    	buf[i] = RANDOMCHARS[getRandomAbsInt(rd)%rcl];
	    }
	    return new String(buf);
	}
	
	public static String getUNID(String id,int length){
		byte[] buf = new byte[length];
		long ns = getNextSerial();
	    long l = System.currentTimeMillis();
	    int rcl = RANDOMCHARS.length;
	    Random rd = new Random(l + ns + ( (id != null) ? id.hashCode() : 3423));
	    for(int i=0;i<buf.length;i++){
	    	buf[i] = RANDOMCHARS[getRandomAbsInt(rd)%rcl];
	    }
	    return new String(buf);
	}

	/**
	 * 获取数字随机串
	 * @param id
	 * @param length
	 * @return
	 */
	public static String getUNID4SZ(String id,int length){
		byte[] buf = new byte[length];
		long ns = getNextSerial();
		long l = System.currentTimeMillis();
		int rcl = RANDOMCHARS_SZ.length;
		Random rd = new Random(l + ns + ( (id != null) ? id.hashCode() : 3423));
		for(int i=0;i<buf.length;i++){
			buf[i] = RANDOMCHARS_SZ[getRandomAbsInt(rd)%rcl];
		}
		return new String(buf);
	}

	/**
	 * 获取字母随机串
	 * @param id
	 * @param length
	 * @return
	 */
	public static String getUNID4ZM(String id,int length){
		byte[] buf = new byte[length];
		long ns = getNextSerial();
		long l = System.currentTimeMillis();
		int rcl = RANDOMCHARS_ZM.length;
		Random rd = new Random(l + ns + ( (id != null) ? id.hashCode() : 3423));
		for(int i=0;i<buf.length;i++){
			buf[i] = RANDOMCHARS_ZM[getRandomAbsInt(rd)%rcl];
		}
		return new String(buf);
	}

	/**
	 * 获取数字随机串
	 * @param id
	 * @param length
	 * @return
	 */
	public static String getUNID4FH(String id,int length){
		byte[] buf = new byte[length];
		long ns = getNextSerial();
		long l = System.currentTimeMillis();
		int rcl = RANDOMCHARS_FH.length;
		Random rd = new Random(l + ns + ( (id != null) ? id.hashCode() : 3423));
		for(int i=0;i<buf.length;i++){
			buf[i] = RANDOMCHARS_FH[getRandomAbsInt(rd)%rcl];
		}
		return new String(buf);
	}

	/**
	 * 获取数字和字母和一位符号的组合，唯一用于获取密码
	 * 获取的字符一定是组合的
	 * @param id
	 * @param length
	 * @return
	 */
	public static String getSZZMUNID(String id,int length){
		Random rd = new Random();
		length = length-1;//预留一位符号位
		int zmlength = rd.nextInt(length-1)+1;
		int szlength = length-zmlength;
		if(zmlength<=0||szlength<=0){
			return null;
		}
		return getUNID4ZM(id,zmlength)+getUNID4FH(id,1)+getUNID4SZ(id,szlength);
	}

	/**
	 * 获取默认的密码组合（8位数字字母符号组合）
	 * @return
	 */
	public static String getPwdDefStr(){
		return getSZZMUNID(null,8);
	}

	/**
	 * 检验字符串是不是有数字字母和指定的特殊字符构成
	 * @param str
	 * @return
	 */
	public static boolean isZMSZFHStr(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}
		if(str.matches(".*[\\d]+.*")&&str.matches(".*[a-zA-z]+.*")&&str.matches(".*[!@#$%&*()+]+.*")){
			return true;
		}
		return false;
	}
}
