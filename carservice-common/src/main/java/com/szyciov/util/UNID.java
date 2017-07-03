package com.szyciov.util;

import java.util.Random;

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
}
