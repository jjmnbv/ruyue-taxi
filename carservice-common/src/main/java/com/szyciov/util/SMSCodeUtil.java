package com.szyciov.util;

import java.util.Random;

public class SMSCodeUtil {

	/**
	 * 获得随机六位短信验证码
	 * 
	 * @return
	 */
	public static String getRandCode() {
		return getRandCode(4);
	}

	private static String getRandCode(int charcount) {
		StringBuffer charvalue = new StringBuffer();
		for (int i = 0; i < charcount; i++) {
			charvalue.append(randomInt(0, 10));
		}
		return charvalue.toString();
	}

	private static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
}
