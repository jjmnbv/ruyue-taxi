package com.szyciov.supervision.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.szyciov.supervision.api.responce.HttpContent;
import com.szyciov.supervision.api.request.BasicRequest;
import com.szyciov.supervision.service.PubSupervisionLogService;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szyciov.supervision.TaxiExecption;
import com.szyciov.supervision.config.CacheHelper;
import com.szyciov.supervision.entity.PubSupervisionLog;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;



/**
 * 网约车接口类
 * Created by 林志伟 on 2017/7/4.
 */
@Component
public class GzwycApiUtil {

    private static Logger logger = LoggerFactory.getLogger(GzwycApiUtil.class);

    private static final String SIGN_TYPE = "MD5";
    private static final String FILE_SUFFIX = ".json";
    public static final String BINFILE_AUTH = "binfile-auth";
    public static final String BINFILE_MD5 = "binfile-md5";
    public static final String BINFILE_GZIP = "binfile-gzip";
    public static final String BINFILE_REQLEN = "binfile-reqlen";
    private static final String BIN_FILE = "binFile";
    private static final String FILENAME = "filename";
    private static final String GZIP_FALSE = "false";
    private static final String SEPARATOR_UNDERLINE = "_";
    private static char[] commonDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String DATA_P = "yyyyMMddHHmmssSSS";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_P);

    private @Autowired
    PubSupervisionLogService pubSupervisionLogService;

    public HttpContent send(BasicRequest request) throws IOException {
        HttpContent httpContent = sendMsg(CacheHelper.getServiceUrl(),request, false);
        return httpContent;
    }

    public   HttpContent token(BasicRequest request) throws IOException {
        return sendMsg(CacheHelper.getServiceUrl(),request, true);
    }

    public   HttpContent sendMsg(String serviceUrl,BasicRequest request, Boolean isToken) throws IOException {
        HttpPost post = new HttpPost(serviceUrl);
        InputStream in = new ByteArrayInputStream(request.getResult().getBytes(Consts.UTF_8.name()));
        String md5 = hash(in);
        in.reset();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        post.addHeader(BINFILE_MD5, md5);
        if (isToken) {
            post.addHeader(BINFILE_AUTH, CacheHelper.getCompanyId());//平台标识
        }else{
            post.addHeader(BINFILE_AUTH, request.getToken());//token
        }

        post.addHeader(BINFILE_GZIP, GZIP_FALSE);
        post.addHeader(BINFILE_REQLEN, String.valueOf(in.available()));//请求长度
        String fileName = new StringBuffer(CacheHelper.getCompanyId()).append(SEPARATOR_UNDERLINE)
                .append(request.getInterfaceType().value()).append(SEPARATOR_UNDERLINE)
                .append(request.getCommand().value()).append(SEPARATOR_UNDERLINE).append(request.getRequestType().value()).append(SEPARATOR_UNDERLINE)
                .append(LocalDateTime.now().format(formatter)).append(FILE_SUFFIX).toString();

        builder.addBinaryBody(BIN_FILE, in, ContentType.DEFAULT_BINARY, fileName);// 文件
        builder.addTextBody(FILENAME, fileName, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
        post.setEntity(builder.build());

        Map<String, String> headerMap = Arrays.asList(post.getAllHeaders()).stream().collect(Collectors.toMap(Header::getName, Header::getValue));
        HttpContent httpContent=HttpUtil.sendHttpPost(post);


        PubSupervisionLog pubSupervisionLog=new PubSupervisionLog();
        pubSupervisionLog.setDirect(1);
        pubSupervisionLog.setRequestHeader(JSONUtil.toJackson(headerMap));
        pubSupervisionLog.setRequestParam(request.getResult());
        pubSupervisionLog.setResponceCode(httpContent.getStatus());
        pubSupervisionLog.setResponceContent(httpContent.getContent());
        pubSupervisionLog.setInterfaceType(request.getInterfaceType().value()+":"+request.getInterfaceType().description());
        pubSupervisionLog.setInterfaceCommand(request.getCommand().value()+":"+request.getCommand().description());
        pubSupervisionLog.setCreatetime(new Date());
        pubSupervisionLogService.insert(pubSupervisionLog);

        return httpContent;
    }

    public static HttpContent  checkReceiveData(HttpServletRequest httpServletRequest,String paramStr) throws Exception {
        InputStream in = null;
        in = new ByteArrayInputStream(paramStr.getBytes(Consts.UTF_8.name()));
        String md5 = hash(in);
        if(!md5.equals(httpServletRequest.getHeader(BINFILE_MD5))){
            return new HttpContent(998,"md5签名错误:"+md5+"|"+httpServletRequest.getHeader(BINFILE_MD5));
        }
//        if(!CacheHelper.getOpenToken().equals(httpServletRequest.getHeader(BINFILE_AUTH))){
//            return new HttpContent(999,"平台的标识错误！");
//        }

        return  new HttpContent(200,paramStr);

    }




    public static String hash(InputStream in) {
        try {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance(SIGN_TYPE);
            int numRead = 0;
            while ((numRead = in.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            in.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            throw new TaxiExecption();
        }
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(commonDigit[(b[i] & 0xf0) >>> 4]);
            sb.append(commonDigit[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
