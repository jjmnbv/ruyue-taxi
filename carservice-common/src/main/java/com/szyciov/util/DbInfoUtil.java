package com.szyciov.util;  
  
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

  
/** 
 *  
 * <p>Description: 获取数据库基本信息的工具类</p> 
 *  
 * @author Efy 
 * @date 2016年8月22日 下午1:00:34 
 */  
public class DbInfoUtil {
	/**
	 * 数据库名
	 */
	public static String database = "carservicedev";
	/**
	 * 数据库IP
	 */
	public static String ip = "10.10.10.100";
	/**
	 * 数据库端口
	 */
	public static int port = 3306;
	/**
	 * 数据库驱动
	 */
	public static String driver = "com.mysql.jdbc.Driver";
	/**
	 * 数据库用户名
	 */
	public static String user = "developer";
	/**
	 * 数据库密码
	 */
	public static String pwd = "yc@123456";
	/**
	 * 数据库表名
	 */
	public static String table = "pub_drivernews";
	/**
	 * 生成的类名(如不指定则使用表名自动大小写转换);
	 */
	public static String className = "";
	/**
	 * 实体类包名
	 */
	public static String packageName = "com.szyciov.entity";
	/**
	 * 作者名字
	 */
	public static String authorName = "Efy";
	/**
	 * 类描述
	 */
	public static String description = "司机第三方交易记录实体类";
	/**
	 * 是否覆盖已存在文件(默认false)
	 */
	public static boolean overwrite = false;
	
	/**
	 * 文件类型枚举
	 * @ClassName FileType 
	 * @author Efy Shu
	 * @Description 标记要生成的文件类型
	 * @date 2017年3月2日 下午2:04:28
	 */
    public enum FileType{
    	Controller("Controller"),Service("Service"),Dao("Repository"),Mapper(""),Mapper_XML("");
    	public String annotation;
    	FileType(String annotation){
    		this.annotation = annotation;
    	} 
    }
    
    /** 
     * 根据数据库的连接参数，获取指定表的基本信息：字段名、字段类型、字段注释 
     * @param driver 数据库连接驱动 
     * @param url 数据库连接url 
     * @param user  数据库登陆用户名 
     * @param pwd 数据库登陆密码 
     * @param table 表名 
     * @return Map集合 
     */  
	public List<Map<String, String>> getTableInfo(String driver,String ip,int port,String user,String pwd,String table){
		String url = "jdbc:mysql://"+ip+":"+port+"/"+database+"?useUnicode=true&characterEncoding=UTF-8";
        List<Map<String, String>> result = new ArrayList<>();  
          
        Connection conn = null;       
        DatabaseMetaData dbmd = null;  
        try {  
            conn = getConnections(driver,url,user,pwd);  
              
            dbmd = conn.getMetaData();  

            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[] { "TABLE" });  
              
            while (resultSet.next()) {  
                String tableName=resultSet.getString("TABLE_NAME");  
                System.out.println("数据库表名:" + tableName);
                String tableRemarks=resultSet.getString("REMARKS");
                System.out.println("数据库注释:" + tableRemarks);
                ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn),tableName.toUpperCase(), "%");  
                while(rs.next()){  
//                        System.out.println("字段名："+rs.getString("COLUMN_NAME")+"--字段注释："+rs.getString("REMARKS")+"--字段数据类型："+rs.getString("TYPE_NAME"));  
                    Map<String,String> map = new HashMap<>();  
                    String colName = rs.getString("COLUMN_NAME");  
                    map.put("name", colName);  
                      
                    String remarks = rs.getString("REMARKS");  
                    if(remarks == null || remarks.equals("")){  
                        remarks = colName;  
                    }  
                    map.put("comment",remarks);  
                      
                    String dbType = rs.getString("TYPE_NAME");  
                    map.put("dbType",dbType.toLowerCase());  
                      
                    map.put("javaType", changeDbType(dbType));  
                    result.add(map);  
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
          
        return result;  
    }  
    
    private String changeDbType(String dbType) {  
        dbType = dbType.toUpperCase();  
        switch(dbType){  
            case "VARCHAR":  
            case "VARCHAR2":  
            case "CHAR":  
                return "String";  
            case "NUMBER":  
            case "DECIMAL":  
            case "DOUBLE":
                return "double";  
            case "TINYINT":
            	return "boolean";
            case "INT":  
            case "SMALLINT":  
            case "INTEGER":  
                return "int";  
            case "BIGINT":  
                return "long";  
            case "DATETIME":  
            case "TIMESTAMP":  
            case "DATE":  
                return "Date";  
            default:  
                return "null";  
        }  
    }  
    
   /**
    * 获取数据库连接
    * @param driver
    * @param url
    * @param user
    * @param pwd
    * @return
    * @throws Exception
    */
    private Connection getConnections(String driver,String url,String user,String pwd) throws Exception {  
        Connection conn = null;  
        try {  
            Properties props = new Properties();  
            props.put("remarksReporting", "true");  
            props.put("user", user);  
            props.put("password", pwd);  
            Class.forName(driver);  
            conn = DriverManager.getConnection(url, props);  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;  
        }  
        return conn;  
    }  
      
    /**
     * 其他数据库不需要这个方法 oracle和db2需要  
     * @param conn
     * @return
     * @throws Exception
     */
    private String getSchema(Connection conn) throws Exception {  
        String schema;  
        schema = conn.getMetaData().getUserName();  
        if ((schema == null) || (schema.length() == 0)) {  
            throw new Exception("ORACLE数据库模式不允许为空");  
        }  
        return schema.toUpperCase().toString();  
  
    }  
  
	/**
	 * 将字符串首字母大写
	 * @param str
	 * @return
	 */
    public String upperHeader(String str){
    	str = str.substring(0,1).toUpperCase()+str.substring(1);
    	StringBuffer sb = new StringBuffer();
    	Pattern p = Pattern.compile("_(\\w{1})");
    	Matcher m = p.matcher(str);
    	while (m.find()){ 
	    	m.appendReplacement(sb, m.group(1).toUpperCase());
    	}
    	m.appendTail(sb);
    	return sb.toString();
    }
    
    /**
     * 生成文件
     * @param data
     */
    public void processFile(List<Map<String, String>> data){
    	System.out.println("字段结果集:");
    	System.out.println(data);
    	String p = packageName.replace(".", "/");
		String path = this.getClass().getResource("/"+p).getPath().replace("/target/classes/", "/src/main/java/").substring(1)+"/";
		System.out.println("生成路径:"+path);
		if(className == null || className.isEmpty()){
			className = upperHeader(table);
		}
		System.out.println("文件名:"+className +".java");
		File output = new File(path+className +".java");
		if(output.exists() && overwrite){
			System.out.println("文件已存在,覆盖已有文件");
		}else if (output.exists() && !overwrite) {
			System.out.println("文件已存在,跳过已有文件");
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(output);
			fos.write(processContent(data).getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 生成实体内容
     * @param data
     * @return
     */
    public String processContent(List<Map<String, String>> data){
    	StringBuffer sb = new StringBuffer();
    	String nowStr = StringUtil.formatDate(new Date(), "yyyy年M月d日 HH:mm:dd");
    	sb.append("package ").append(packageName).append(";\r\n\r\n\r\n");
    	sb.append("import java.util.Date;\r\n");
    	sb.append("import com.szyciov.driver.param.BaseParam;\r\n");
    	sb.append("\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("  * @ClassName ").append(upperHeader(className)).append("\r\n");
		sb.append("  * @author ").append(authorName).append("\r\n");
		sb.append("  * @Description ").append(description).append("\r\n");
		sb.append("  * @date ").append(nowStr).append("\r\n");
		sb.append("  */ \r\n");
		// 实体部分
		sb.append("public class ").append(upperHeader(className)).append(" extends BaseParam").append("{\r\n");
		processAllAttrs(sb,data);// 属性
		processAllMethod(sb,data);// get set方法
		sb.append("}\r\n");
		return sb.toString();
    }
    
    /**
     * 生成所有属性
     * @param sb
     */
    public void processAllAttrs(StringBuffer sb,List<Map<String, String>> data){
    	for(Map<String, String> tempMap : data){
    		sb.append("\t/**\r\n");
    		sb.append("\t  *").append(tempMap.get("comment")).append("\r\n");
    		sb.append("\t  */\r\n");
    		sb.append("\tprivate ").append(tempMap.get("javaType")).append(" ").append(tempMap.get("name")).append(";\r\n");
    		sb.append("\r\n");
    	}
    }
    
    /**
     * 生成所有getter&setter
     * @param sb
     */
    public void processAllMethod(StringBuffer sb,List<Map<String, String>> data){
    	for(Map<String, String> tempMap : data){
    		sb.append("\t/**\r\n");
    		sb.append("\t  *设置").append(tempMap.get("comment")).append("\r\n");
    		sb.append("\t  */\r\n");
			sb.append("\tpublic void set").append(upperHeader(tempMap.get("name"))).append("(").append(tempMap.get("javaType")).append(" ").append(tempMap.get("name")).append("){\r\n");
			sb.append("\t\tthis.").append(tempMap.get("name")).append("=").append(tempMap.get("name")).append(";\r\n");
			sb.append("\t}\r\n");
			sb.append("\r\n");
    		sb.append("\t/**\r\n");
    		sb.append("\t  *获取").append(tempMap.get("comment")).append("\r\n");
    		sb.append("\t  */\r\n");
			sb.append("\tpublic ").append(tempMap.get("javaType")).append(" get").append(upperHeader(tempMap.get("name"))).append("(){\r\n");
			sb.append("\t\treturn ").append(tempMap.get("name")).append(";\r\n");
			sb.append("\t}\r\n");
			sb.append("\r\n");
    	}
    }
    
    /**
     * 生成SQL语句
     * @param data
     */
    public void processSQL(List<Map<String, String>> data){
    	StringBuffer sb = new StringBuffer();
    	//INSERT语句
    	sb.append("INSERT INTO ").append(table).append("(").append("\t");
    	int index = 1;
    	for(Map<String, String> tempMap : data){
    		if(index > 6) {
    			sb.append("\t");
    			index = 1;
    		}
    		sb.append(tempMap.get("name")).append(",");
    		index++;
    	}
    	sb.append(") VALUES(").append("\t");
    	index = 1;
    	for(Map<String, String> tempMap : data){
    		if(index > 6) {
    			sb.append("\t");
    			index = 1;
    		}
    		sb.append("#{").append(tempMap.get("name")).append("},");
//    		sb.append("\t").append("NULL,");
    		index++;
    	}
    	sb.append(")");
    	System.out.println(sb.toString().replaceAll(",(\\))", "$1").replaceAll("(\t|\\))", "\r\n$1"));
    	System.out.println();
    	//UPDATE语句
    	sb = new StringBuffer();
    	sb.append("UPDATE ").append(table).append(" SET ").append("\t");
    	index = 1;
    	for(Map<String, String> tempMap : data){
    		if(index > 3) {
    			sb.append("\t");
    			index = 1;
    		}
    		sb.append(tempMap.get("name")).append("=#{").append(tempMap.get("name")).append("}").append(",");
    		index++;
    	}
    	sb.append(")WHERE ").append(data.get(0).get("name")).append("=#{").append(data.get(0).get("name")).append("}");
    	System.out.println(sb.toString().replaceAll(",(\\))", "$1").replaceAll("(\t|\\))", "\r\n$1").replace(")W", "W"));
    	System.out.println();
    	//DELETE语句
    	sb = new StringBuffer();
    	sb.append("DELETE FROM ").append(table).append(" WHERE ").append(data.get(0).get("name")).append("=#{").append(data.get(0).get("name")).append("}");
    	System.out.println(sb.toString());
    }
    
    /**
     * 生成Controller文件
     * @param project
     * @param isApi
     */
    public void processController(String author,String desc,String project,String clazzName,boolean isApi){
    	String pack = "com.szyciov."+project+".controller";
    	String p = pack.replace(".", "/");
		String path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
		System.out.println("生成路径:"+path);
		Map<String, String> data = new HashMap<>();
    	data.put("pack",pack);
    	data.put("clazzName",clazzName+"Controller");
    	data.put("author",author);
    	data.put("desc",desc+"Controller");
    	data.put("importPack",pack.replace("controller", "service"));
    	data.put("importClazzName",clazzName+"Service");
    	data.put("path", path);
		processBaseFile(data,FileType.Controller,isApi);
    }
    
    /**
     * 生成Service文件
     * @param project
     * @param isApi
     */
    public void processService(String author,String desc,String project,String clazzName,boolean isApi){
    	String pack = "com.szyciov."+project+".service";
    	String p = pack.replace(".", "/");
		String path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
		System.out.println("生成路径:"+path);
		Map<String, String> data = new HashMap<>();
    	data.put("pack",pack);
    	data.put("clazzName",clazzName+"Service");
    	data.put("author",author);
    	data.put("desc",desc+"Service");
    	data.put("importPack",pack.replace("service", "dao"));
    	data.put("importClazzName",clazzName+"Dao");
    	data.put("path", path);
		processBaseFile(data,FileType.Service,isApi);
    }
   
    
    /**
     * 生成Dao文件
     * @param project
     * @param isApi
     */
    public void processDao(String author,String desc,String project,String clazzName,boolean isApi){
    	if(!isApi) return;
    	String pack = "com.szyciov."+project+".dao";
    	String p = pack.replace(".", "/");
		String path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
		System.out.println("生成路径:"+path);
		Map<String, String> data = new HashMap<>();
    	data.put("pack",pack);
    	data.put("clazzName",clazzName+"Dao");
    	data.put("author",author);
    	data.put("desc",desc+"Dao");
    	data.put("importPack",pack.replace("dao", "mapper"));
    	data.put("importClazzName",clazzName+"Mapper");
    	data.put("path", path);
		processBaseFile(data,FileType.Dao,isApi);
    }
    
    /**
     * 生成Mapper文件
     * @param project
     * @param isApi
     */
    public void processMapper(String author,String desc,String project,String clazzName,boolean isApi){
    	if(!isApi) return;
    	String pack = "com.szyciov."+project+".mapper";
    	String p = pack.replace(".", "/");
		String path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
		System.out.println("生成路径:"+path);
		Map<String, String> data = new HashMap<>();
    	data.put("pack",pack);
    	data.put("clazzName",clazzName+"Mapper");
    	data.put("author",author);
    	data.put("desc",desc+"Mapper");
    	data.put("importPack",pack.replace("mapper", "mapper"));
    	data.put("importClazzName",clazzName+"Mapper");
    	data.put("path", path);
		processBaseFile(data,FileType.Mapper,isApi);
		
		path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/resources/mapper").substring(1)+"/";
		System.out.println("生成路径:"+path);
    	data.put("pack",pack);
    	data.put("clazzName",clazzName+"Mapper");
    	data.put("author",author);
    	data.put("desc",desc+"Mapper");
    	data.put("importPack",pack.replace("mapper", "mapper"));
    	data.put("importClazzName",clazzName+"Mapper");
    	data.put("path", path);
		processBaseFile(data,FileType.Mapper_XML,isApi);
    }

    /**
     * 删除Controller文件
     * @param project
     * @param isApi
     */
    public void removeController(String author,String desc,String project,String clazzName,boolean isApi){
    	String pack = "com.szyciov."+project+".controller";
    	String p = pack.replace(".", "/");
		String path = this.getClass().getResource("/").getPath();
		path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
		path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
		System.out.println("文件路径:"+path);
		Map<String, String> data = new HashMap<>();
		data.put("clazzName",clazzName+"Controller");
    	data.put("path", path);
		removeFile(data,FileType.Controller,isApi);
    }
    
    /**
     * 删除Service文件
     * @param project
     * @param isApi
     */
    public void removeService(String author,String desc,String project,String clazzName,boolean isApi){
    	String pack = "com.szyciov."+project+".service";
    	String p = pack.replace(".", "/");
    	String path = this.getClass().getResource("/").getPath();
    	path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
    	path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
    	System.out.println("文件路径:"+path);
		Map<String, String> data = new HashMap<>();
		data.put("clazzName",clazzName+"Service");
    	data.put("path", path);
		removeFile(data,FileType.Service,isApi);
    }
    
    /**
     * 生成Dao文件
     * @param project
     * @param isApi
     */
    public void removeDao(String author,String desc,String project,String clazzName,boolean isApi){
    	if(!isApi) return;
    	String pack = "com.szyciov."+project+".dao";
    	String p = pack.replace(".", "/");
    	String path = this.getClass().getResource("/").getPath();
    	path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
    	path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
    	System.out.println("文件路径:"+path);
		Map<String, String> data = new HashMap<>();
		data.put("clazzName",clazzName+"Dao");
    	data.put("path", path);
		removeFile(data,FileType.Dao,isApi);
    }
    
    /**
     * 删除Mapper文件
     * @param project
     * @param isApi
     */
    public void removeMapper(String author,String desc,String project,String clazzName,boolean isApi){
    	if(!isApi) return;
    	String pack = "com.szyciov."+project+".mapper";
    	String p = pack.replace(".", "/");
    	String path = this.getClass().getResource("/").getPath();
    	path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
    	path = path.replace("/target/classes/", "/src/main/java/"+p).substring(1)+"/";
    	System.out.println("文件路径:"+path);
		Map<String, String> data = new HashMap<>();
		data.put("clazzName",clazzName+"Mapper");
    	data.put("path", path);
		removeFile(data,FileType.Mapper,isApi);
    	
    	path = this.getClass().getResource("/").getPath();
    	path = path.replace("carservice-common", project + "-" + (isApi?"api":"web"));
    	path = path.replace("/target/classes/", "/src/main/resources/mapper").substring(1)+"/";
    	System.out.println("文件路径:"+path);
		data = new HashMap<>();
		data.put("clazzName",clazzName+"Mapper");
    	data.put("path", path);
		removeFile(data,FileType.Mapper_XML,isApi);
    }
    
    /**
     * 生成所有基础文件
     * @param author
     * @param desc
     * @param project
     * @param clazzName
     * @param isApi
     */
    public void processAllBaseFile(String author,String desc,String project,String clazzName,boolean isApi){
    	processController(author,desc,project, clazzName,isApi);
    	processService(author,desc,project, clazzName,isApi);
    	processDao(author,desc,project, clazzName,isApi);
    	processMapper(author,desc,project, clazzName,isApi);
    }
    
    /**
     * 删除所有基础文件
     * @param author
     * @param desc
     * @param project
     * @param clazzName
     * @param isApi
     */
    public void removeAllBaseFile(String author,String desc,String project,String clazzName,boolean isApi){
    	removeController(author,desc,project, clazzName,isApi);
    	removeService(author,desc,project, clazzName,isApi);
    	removeDao(author,desc,project, clazzName,isApi);
    	removeMapper(author,desc,project, clazzName,isApi);
    }
    
    /**
     * 生成基础文件
     * @param data
     * @param fileType
     */
    public void processBaseFile(Map<String, String> data,FileType fileType,boolean isApi){
    	String suffix = ".java";
    	if(FileType.Mapper_XML.equals(fileType)){
    		suffix = ".xml";
    	}
    	System.out.println("文件名:"+data.get("clazzName") +suffix);
		File output = new File(data.get("path")+data.get("clazzName") +suffix);
		if(output.exists() && overwrite){
			System.out.println("文件已存在,覆盖已有文件");
		}else if (output.exists() && !overwrite) {
			System.out.println("文件已存在,跳过已有文件");
			return;
		}
		try {
			FileOutputStream fos = new FileOutputStream(output);
			String content = processBaseContent(data,fileType,isApi);
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * 删除文件
     * @param path
     */
    public void removeFile(Map<String, String> data,FileType fileType,boolean isApi){
    	String suffix = ".java";
    	if(FileType.Mapper_XML.equals(fileType)){
    		suffix = ".xml";
    	}
    	System.out.println("文件名:"+data.get("clazzName") +suffix);
    	File f = new File(data.get("path")+data.get("clazzName") +suffix);
    	if(f.exists() && f.isFile()){
    		f.delete();
    		System.out.println("文件已删除");
    	}else{
    		System.out.println("文件不存在,或类型为文件夹");
    	}
    }
    
    /**
     * 生成基础内容
     * @param data
     * @param fileType
     * @return
     */
    public String processBaseContent(Map<String, String> data,FileType fileType,boolean isApi){
    	String pack = data.get("pack");
    	String clazzName = data.get("clazzName");
    	String author = data.get("author");
    	String desc = data.get("desc");
    	String importPack = data.get("importPack");
    	String importClazzName = data.get("importClazzName");
    	
    	StringBuffer sb = new StringBuffer();
    	String nowStr = StringUtil.formatDate(new Date(), "yyyy年M月d日 HH:mm:dd");
    	sb.append("package ").append(pack).append(";\r\n\r\n\r\n");
    	if(!FileType.Mapper.equals(fileType) && !FileType.Mapper_XML.equals(fileType)){
    		if(isApi || (!isApi && !importClazzName.contains("Dao"))){
    			sb.append("import javax.annotation.Resource;\r\n");
    			sb.append("import "+importPack+"."+importClazzName+";\r\n");
    		}
    		sb.append("import org.springframework.stereotype."+fileType.annotation+";\r\n");
    	}
    	if(FileType.Controller.equals(fileType)){
    		if(isApi){
    			sb.append("import com.szyciov.util.ApiExceptionHandle;\r\n");
    		}else{
    			sb.append("import com.szyciov.util.WebExceptionHandle;\r\n");
    		}
    	}
    	sb.append("\r\n\r\n\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("  * @ClassName ").append(clazzName).append("\r\n");
		sb.append("  * @author ").append(author).append("\r\n");
		sb.append("  * @Description ").append(desc).append("\r\n");
		sb.append("  * @date ").append(nowStr).append("\r\n");
		sb.append("  */ \r\n");
		// 实体部分
		if (FileType.Mapper.equals(fileType)) {
			sb.append("public interface ").append(clazzName).append("{\r\n\r\n");
			sb.append("}\r\n");
		}else if(FileType.Mapper_XML.equals(fileType)){
			sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append("\r\n");
			sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\r\n");
			sb.append("<!-- 引入接口，只要实现接口的方法就会调用对应接口方法的 select id -->").append("\r\n");
			sb.append("<mapper namespace=\""+pack+"."+clazzName+"\">").append("\r\n");
			sb.append("\r\n");
			sb.append("</mapper>");
		}else{
			sb.append("@"+fileType.annotation).append("(\""+clazzName+"\")").append("\r\n");
			sb.append("public class ").append(clazzName);
			if(FileType.Controller.equals(fileType)){
	    		if(isApi){
	    			sb.append(" extends ApiExceptionHandle");
	    		}else{
	    			sb.append(" extends WebExceptionHandle");
	    		}
			}
			sb.append("{\r\n\r\n");
	    	List<Map<String, String>> list = new ArrayList<>();
	    	Map<String, String> map = new HashMap<>();
	    	map.put("name", importClazzName);
	    	map.put("comment", "依赖");
	    	list.add(map);
			processAllBaseAttrs(sb,list,isApi);// 属性
			processAllBaseMethod(sb,list,isApi);// set方法
			sb.append("}\r\n");
		}
//		System.out.println(sb.toString());
		return sb.toString();
    }
    
    /**
     * 生成基础属性
     * @param sb
     * @param data
     */
    public void processAllBaseAttrs(StringBuffer sb,List<Map<String, String>> data,boolean isApi){
    	for(Map<String, String> tempMap : data){
    		if(!isApi && tempMap.get("name").contains("Dao")){
    			continue;
    		}
    		sb.append("\t/**\r\n");
    		sb.append("\t  *").append(tempMap.get("comment")).append("\r\n");
    		sb.append("\t  */\r\n");
    		sb.append("\tprivate ").append(tempMap.get("name")).append(" ").append(tempMap.get("name").toLowerCase()).append(";\r\n");
    		sb.append("\r\n");
    	}
    }
    
    /**
     * 生成基础注入资源
     * @param sb
     * @param data
     */
    public void processAllBaseMethod(StringBuffer sb,List<Map<String, String>> data,boolean isApi){
    	for(Map<String, String> tempMap : data){
    		if(!isApi && tempMap.get("name").contains("Dao")){
    			continue;
    		}
    		sb.append("\t/**\r\n");
    		sb.append("\t  *").append(tempMap.get("comment")).append("注入\r\n");
    		sb.append("\t  */\r\n");
    		sb.append("\t@Resource");
			if(!tempMap.get("name").contains("Mapper")){
				sb.append("(name=\""+tempMap.get("name")+"\")");
			}
			sb.append("\r\n");
			sb.append("\tpublic void set").append(tempMap.get("name")).append("(").append(tempMap.get("name")).append(" ").append(tempMap.get("name").toLowerCase()).append("){\r\n");
			sb.append("\t\tthis.").append(tempMap.get("name").toLowerCase()).append("=").append(tempMap.get("name").toLowerCase()).append(";\r\n");
			sb.append("\t}\r\n");
			sb.append("\r\n");
    	}
    }
    
    public static void main(String[] args) {
        DbInfoUtil diu = new DbInfoUtil();
//        overwrite = true;
        List<Map<String, String>> list = diu.getTableInfo(driver,ip,port,user,pwd,table);
        diu.processSQL(list);
//        diu.processFile(list);
//        diu.removeAllBaseFile("Efy Shu","出租车消息功能","carservice","DriverMessage", true);
//        diu.processAllBaseFile("Efy Shu","出租车消息功能","carservice","DriverMessage", true);
    }
}  