package com.szyciov.supervision.util;

import com.szyciov.supervision.TaxiExecption;
import com.szyciov.supervision.config.CacheHelper;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 网约车接口类
 * Created by 林志伟 on 2017/7/4.
 */
public class GzwycApi {

    public static final String SEPARATOR_UNDERLINE = "_";
    private static char[] commonDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String DATA_P = "yyyyMMddHHmmssSSS";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_P);

    public static GzwycResult send(BasicRequest request) throws IOException {
        String responseString = sendMsg(request, false);
        return JSONUtil.objectMapper.readValue(responseString, GzwycResult.class);
    }
    public static String token(BasicRequest request) throws IOException {
        return sendMsg(request, true);
    }

    private static String sendMsg(BasicRequest request, Boolean isToken) throws IOException {
        HttpPost post = new HttpPost(CacheHelper.getServiceUrl());
        InputStream in = new ByteArrayInputStream(request.getResult().getBytes("UTF-8"));
        String md5 = hash(in);
        in.reset();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        System.out.println("md5:" + md5);
        post.addHeader("binfile-md5", md5);
        if (isToken) {
            post.addHeader("binfile-auth", CacheHelper.getCompanyId());//平台标识
        } else {
            post.addHeader("binfile-auth", request.getToken());//token
        }

        post.addHeader("binfile-gzip", "false");
        post.addHeader("binfile-reqlen", String.valueOf(in.available()));//请求长度
        String fileName = new StringBuffer(CacheHelper.getCompanyId()).append(SEPARATOR_UNDERLINE)
                .append(request.getInterfaceType().getValue()).append(SEPARATOR_UNDERLINE)
                .append(request.getCommand().getValue()).append(request.getRequestType().getValue())
                .append(LocalDateTime.now().format(formatter)).append(".json").toString();

        builder.addBinaryBody("binFile", in, ContentType.DEFAULT_BINARY, fileName);// 文件
        builder.addTextBody("filename", fileName, ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), "UTF-8"));
        post.setEntity(builder.build());

        return HttpClientUtil.sendHttpPost(post, ContentType.APPLICATION_FORM_URLENCODED);
    }

    public static String hash(InputStream in) {
        try {
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
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
