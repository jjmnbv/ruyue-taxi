package com.szyciov.supervision.util;

import com.szyciov.supervision.api.responce.HttpContent;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * http工具类，返回响应内容和响应状态
 * Created by 林志伟 on 2017/7/13.
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static int connTimeout = 10000;
    private static int soTimeout = -1;


    /**
     *
     * @param httpPost
     */
    public static HttpContent sendHttpPost(HttpPost httpPost) {
        CloseableHttpResponse response = null;
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        if (connTimeout > 0) configBuilder.build();
        if (soTimeout > 0) configBuilder.setSocketTimeout(soTimeout);
        clientBuilder.setDefaultRequestConfig(configBuilder.build());
        // 4.3以后会自动在interceptor中实现启用压缩和自动解压，所以不需要gzip的时候需要指定一下---binfile-gzip true/false
        if (!false) clientBuilder.disableContentCompression();
        HttpContent httpContent=new HttpContent();

        try {
            CloseableHttpClient client = clientBuilder.build();
            response = client.execute(httpPost);
            httpContent.setStatus(response.getStatusLine().getStatusCode());
            //utf8編碼
            String content=EntityUtils.toString(response.getEntity(),"UTF-8");
            httpContent.setContent(content);
        } catch (Exception e) {
            logger.error(e.toString());
            httpContent.setStatus(-1);
            httpContent.setContent(e.toString());
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
                httpContent.setStatus(-1);
                httpContent.setContent(e.toString());
            }
        }
        return  httpContent;
    }

}
