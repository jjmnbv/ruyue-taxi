package api;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 * 网约车接口发送工具类
 * Created by 林志伟 on 2017/7/4.
 */
public class GzwycApiUtil {

//    private String serverUrl="http://wycapi.gzjt.gov.cn/api/app/common/binapi";
    private String serverUrl="http://120.76.199.119:8099/api/transfer";
//    公司标识
    private String companyId="RY";
    private String key="SLj16Qq7FK9nbyi4";

//   接口类型标识
    private String apiType="";
//    命令
    private String command;
//    发送接收标识
    private String requestResponce="REQ";


    private boolean isGetToken=false;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRequestResponce() {
        return requestResponce;
    }

    public void setRequestResponce(String requestResponce) {
        this.requestResponce = requestResponce;
    }

    public boolean isGetToken() {
        return isGetToken;
    }

    public void setGetToken(boolean getToken) {
        isGetToken = getToken;
    }

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        String result = "{\"platform\":\""+companyId+"\",\"key\":\""+key+"\"}";
        try {
            GzwycApiUtil gzwycApiUtil =new GzwycApiUtil();
            gzwycApiUtil.setApiType("TOKEN");
            gzwycApiUtil.setCommand("ACCESS");
            gzwycApiUtil.setGetToken(true);
            GzwycResult gzwycResult= gzwycApiUtil.send(result);
            if(gzwycResult.getStatus()==200){
                ObjectMapper objectMapper=new ObjectMapper();
                Map map= objectMapper.readValue(gzwycResult.getContent(), Map.class);
                System.out.println("token:"+(String)map.get("token"));
                return (String) map.get("token");
            }
            else{
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;

    }

    /**
     * 发送请求
     * @param apiRequestParam
     * @return
     * @throws Exception
     */
    public GzwycResult send(ApiRequestParam<? extends BaseApi> apiRequestParam) throws Exception {
        if(apiRequestParam.getItems()==null||apiRequestParam.getItems().size()<=0){
            GzwycResult gzwycResult=new GzwycResult();
            gzwycResult.setStatus(-1);
            gzwycResult.setContent("传递参数不能为空！");
            return  gzwycResult;
        }
        ObjectMapper mapper = new ObjectMapper();
        String result=mapper.writeValueAsString(apiRequestParam);
        System.out.println("解析："+result);
        this.setApiType(apiRequestParam.getItems().get(0).getApiType());
        this.setCommand(apiRequestParam.getItems().get(0).getCommand());
        return this.send(result);

    }
    /**
     * 发送请求
     * @param items
     * @return
     * @throws Exception
     */
    public GzwycResult send(List<? extends  BaseApi> items) throws Exception {
        ApiRequestParam apiRequestParam=new ApiRequestParam();
        apiRequestParam.setItems(items);
        return  this.send(apiRequestParam);
    }



    /**
     * 发送请求
     * @param result
     * @return
     * @throws Exception
     */
    public GzwycResult send(String result) throws Exception {
        HttpPost post = new HttpPost(this.serverUrl);
        InputStream in;
        in = new ByteArrayInputStream(result.getBytes("UTF-8"));
        String md5 = hash(in);
        in.reset();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        System.out.println("md5:"+md5);
        post.addHeader("binfile-md5", md5);
        if(isGetToken){
            post.addHeader("binfile-auth", companyId);//平台标识
        }else{
            String token=this.getToken();
            if(token==null){
                return  new GzwycResult(-2,"获取token失败！");
            }
            post.addHeader("binfile-auth", token);//token
        }

        post.addHeader("binfile-gzip", "false");
        post.addHeader("binfile-reqlen", String.valueOf(in.available()));//请求长度
        String filename = this.getCompanyId()+"_"+this.getApiType()+"_"+this.getCommand()+"_"+this.getRequestResponce()+"_"+this.getNowDate()+".json";
        System.out.println("filename:"+filename);
        builder.addBinaryBody("binFile", in, ContentType.DEFAULT_BINARY, filename);// 文件
        builder.addTextBody("filename", filename, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
        post.setEntity(builder.build());
        System.out.println(post.toString());

        int connTimeout = 10000;
        int soTimeout = -1;

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        if (connTimeout > 0) configBuilder.build();
        if (soTimeout > 0) configBuilder.setSocketTimeout(soTimeout);
        clientBuilder.setDefaultRequestConfig(configBuilder.build());
        // 4.3以后会自动在interceptor中实现启用压缩和自动解压，所以不需要gzip的时候需要指定一下---binfile-gzip true/false
        if (!false) clientBuilder.disableContentCompression();
//		clientBuilder.setProxy(proxy);----放开抓包
        CloseableHttpClient client = clientBuilder.build();
        HttpResponse response = client.execute(post);
        GzwycResult gzwycResult=new GzwycResult();
        gzwycResult.setStatus(response.getStatusLine().getStatusCode());
        String content=EntityUtils.toString(response.getEntity());
        content=new String(content.getBytes("ISO-8859-1"),"UTF-8");
        gzwycResult.setContent(content);
        System.out.println(gzwycResult);
        if(!isGetToken)
            gzwycResult.parseContent();
//        System.out.println(gzwycResult);
        return  gzwycResult;
    }

    private static char[] commonDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String hash(InputStream in)
    {

        try
        {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while ((numRead = in.read(buffer)) > 0)
            {

                md5.update(buffer, 0, numRead);
            }
            in.close();
            return toHexString(md5.digest());
        }
        catch (Exception e)
        {
            return "";
        }
    }

    private static String toHexString(byte[] b)
    {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++)
        {
            sb.append(commonDigit[(b[i] & 0xf0) >>> 4]);
            sb.append(commonDigit[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public  String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
