package com.szyciov.util.sequence;

/**
 * 机构/租赁公司注册顺序序列
 * @author Fisher
 *
 */
public class CompanyRegSequence {
	 
	private static final int MIN = 1;
	
    private static final int MAX = 99;
    
    private static final String DEFAULT = "A01";
	
	private CompanyRegSequence() {}
	
	public synchronized static String getNextSeq(String no) {
		if(no == null || no.length() < 3) return DEFAULT;
		StringBuffer sb = new StringBuffer();
		
		int prefix = no.substring(0, 1).charAt(0);
		int suffix = Integer.parseInt(no.substring(1, 3));
		
		if(suffix == MAX) {
			suffix = MIN;
			prefix ++;
		} else {
			suffix ++;
		}
		
		sb.append((char) prefix);
		sb.append(suffix < 10 ? "0" + suffix : suffix);
		
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		String result = null;
		
		for(int i = 0; i < 100; i++) {
			result = CompanyRegSequence.getNextSeq(result);
			System.out.println(result);
		}
	}

}
