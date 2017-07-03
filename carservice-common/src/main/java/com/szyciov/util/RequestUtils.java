package com.szyciov.util;

import javax.servlet.http.HttpServletRequest;

/**
 * request请求工具类
 * Created by LC on 2017/4/10.
 */
public class RequestUtils {

    /**
     * 获取客户端IP，支持反向代理，如nginx，但不支持正向代理，比如客户端浏览器自己使用代理工具
     * @param request
     * @return 客户端IP
     */
    public static String getClientIP(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        return ip;
    }
}
 