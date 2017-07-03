package com.szyciov.passenger.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	private static VelocityEngine ve;
	static{
		ve = new VelocityEngine();
	    Properties prop = new Properties();
        //设置velocity的编码
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");       
		ve.init(prop);
	}
	
	public static void createTemplate(String vmpath,Writer out,VelocityContext context) throws Exception{
		FileInputStream fin = new FileInputStream(vmpath);
		try{
			InputStreamReader ird = new InputStreamReader(fin, "UTF-8");
			try{
				ve.evaluate(context, out, "", ird);
			}finally{
				ird.close();
			}
		}finally{
			fin.close();
		}
	}

}
