package com.wx;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class DocFunc {
	
	/**
	 * 将document对象转换为字符串
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static String doc2String(Document doc) throws Exception{
//		TransformerFactory  tf  =  TransformerFactory.newInstance();
//        Transformer t = tf.newTransformer();    
//        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        t.setOutputProperty(OutputKeys.INDENT, "yes");  
//        t.setOutputProperty(OutputKeys.METHOD, "xml");
//        ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();   
//        t.transform(new DOMSource(doc), new StreamResult(bos));    
//        return bos.toString();
		return toStringFromDoc(doc);
	}
	
	
	  /*  
     * 把dom文件转换为xml字符串  
     */  
    public static String toStringFromDoc(Document document){  
        String result = null;  
        if (document != null){
            StringWriter strWtr = new StringWriter();  
            StreamResult strResult = new StreamResult(strWtr);  
            TransformerFactory tfac = TransformerFactory.newInstance();  
            try {  
                javax.xml.transform.Transformer t = tfac.newTransformer();  
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
                t.setOutputProperty(OutputKeys.INDENT, "no");  
                t.setOutputProperty(OutputKeys.METHOD, "xml");
                t.transform(new DOMSource(document.getDocumentElement()),strResult);  
            }catch (Exception e){  
                System.err.println("XML.toString(Document): " + e);  
            }
            result = strResult.getWriter().toString();  
            try{  
                strWtr.close();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }
        return result;  
    }

}
