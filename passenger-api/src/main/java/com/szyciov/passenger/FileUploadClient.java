package com.szyciov.passenger;
import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.OutputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;  
/* 
 * @author xk 
 * 上传文件到文件服务器的客户端 
 */  
public class FileUploadClient{  
     public static void main(String[] args) {  
       try {  
           URL url = new URL("http://localhost:8080/passenger-api/Passenger/UploadPassengerImg?usertoken=123"); 
           HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
           // 发送POST请求必须设置如下两行  
           conn.setDoOutput(true);  
           conn.setUseCaches(false);  
           conn.setRequestMethod("POST");  
           conn.setRequestProperty("Content-Type","text/html");  
           conn.setRequestProperty("Cache-Control","no-cache");  
           conn.setRequestProperty("Charsert", "UTF-8");   
           conn.connect();  
           conn.setConnectTimeout(10000);  
           OutputStream out =conn.getOutputStream();  
  
           File file = new File("C:\\Users\\admin\\Desktop\\vm.html");  
   
           DataInputStream in = new DataInputStream(new FileInputStream(file));  
  
           int bytes = 0;  
           byte[] buffer = new byte[1024];  
           while ((bytes = in.read(buffer)) != -1) {  
               out.write(buffer, 0, bytes);  
           }  
           in.close();  
           out.flush();  
           out.close();   
      
           conn.getInputStream();  
           conn.disconnect();  
       } catch (Exception e) {  
//            System.out.println("发送文件出现异常！" + e);  
            e.printStackTrace();  
       }  
    }  
}  