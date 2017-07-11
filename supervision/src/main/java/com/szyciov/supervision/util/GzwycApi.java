package com.szyciov.supervision.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.supervision.api.BaseApi;
import lombok.Cleanup;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.szyciov.supervision.TaxiExecption;
import com.szyciov.supervision.config.CacheHelper;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

/**
 * 网约车接口类
 * Created by 林志伟 on 2017/7/4.
 */
public class GzwycApi {

    private static Logger logger = LoggerFactory.getLogger(GzwycApi.class);

    private static final String SIGN_TYPE = "MD5";
    private static final String FILE_SUFFIX = ".json";
    private static final String BINFILE_AUTH = "binfile-auth";
    private static final String BINFILE_MD5 = "binfile-md5";
    private static final String BINFILE_GZIP = "binfile-gzip";
    private static final String BINFILE_REQLEN = "binfile-reqlen";
    private static final String BIN_FILE = "binFile";
    private static final String FILENAME = "filename";
    private static final String GZIP_FALSE = "false";
    private static final String SEPARATOR_UNDERLINE = "_";
    private static char[] commonDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String DATA_P = "yyyyMMddHHmmssSSS";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_P);

    public static <T extends BaseApi> EntityInfoList<T> send(BasicRequest request, @SuppressWarnings("rawtypes") TypeReference t) throws IOException {
        String responseString = sendMsg(request, false);
        EntityInfoList<T> infoList = JSONUtil.objectMapper.readValue(responseString, t);
        //过滤发送成功的消息
        List<T> collect = infoList.getItems().stream().filter(i -> i.getSuccess() == 0).collect(Collectors.toList());
        infoList.setItems(collect);
        logger.info("FAILINFO -> result:{} ",JSONUtil.toJackson(infoList));
        return infoList;
    }

    public static String token(BasicRequest request) throws IOException {
        return sendMsg(request, true);
    }

    public static String sendMsg(BasicRequest request, Boolean isToken) throws IOException {
        HttpPost post = new HttpPost(CacheHelper.getServiceUrl());
        @Cleanup InputStream in = new ByteArrayInputStream(request.getResult().getBytes(Consts.UTF_8.name()));
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
        logger.debug("request params -> headers:{}, fileName:{}", JSONUtil.toJackson(headerMap), fileName);
        String content=HttpClientUtil.sendHttpPost(post, ContentType.APPLICATION_FORM_URLENCODED);
        content=new String(content.getBytes("ISO-8859-1"),"UTF-8");
        logger.debug("responce content -> content:{}", content);
        return content;
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
