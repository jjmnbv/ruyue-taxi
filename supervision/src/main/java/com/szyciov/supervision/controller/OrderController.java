package com.szyciov.supervision.controller;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szyciov.supervision.api.order.OrderSupplementsRequest;
import com.szyciov.supervision.api.service.BaseService;
import com.szyciov.supervision.dao.PubSupervisionLogDao;
import com.szyciov.supervision.entity.PubSupervisionLog;
import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;
import com.szyciov.supervision.token.service.TokenService;
import com.szyciov.supervision.util.EntityInfoList;
import com.szyciov.supervision.util.FileUtil;
import com.szyciov.supervision.util.GzwycApi;
import com.szyciov.supervision.util.HttpContent;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * 订单补传协议
 * Created by 林志伟 on 2017/7/25.
 */
@RestController
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);

    private @Autowired
    BaseService baseService;

    private @Autowired
    PubSupervisionLogDao pubSupervisionLogDao;
    /**
     * 订单补传协议
     */
    @RequestMapping("/api/order/supplements")
    public String Supplements(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("binFile") MultipartFile binFile) throws Exception {
        //权限检验

        String paramString = FileUtil.convertStreamToString(binFile.getInputStream());

        HttpContent httpContent = GzwycApi.checkReceiveData(httpServletRequest, paramString);
        if (httpContent.getStatus() == 200) {//处理结果
            EntityInfoList<OrderSupplementsRequest> infoList = baseService.receiveOrderSupplements(paramString);
            httpContent.setContent(JSONUtil.toJackson(infoList));
        }


        /**
         * 记录请求信息
         */
        PubSupervisionLog pubSupervisionLog=new PubSupervisionLog();
        pubSupervisionLog.setDirect(2);
        pubSupervisionLog.setRequestHeader(JSONUtil.toJackson(getHeader(httpServletRequest)));
        pubSupervisionLog.setRequestParam(paramString);
        pubSupervisionLog.setResponceCode(httpContent.getStatus());
        pubSupervisionLog.setResponceContent(httpContent.getContent());
        pubSupervisionLog.setInterfaceType(InterfaceType.ORDER.value()+":"+InterfaceType.ORDER.description());
        pubSupervisionLog.setInterfaceCommand(CommandEnum.OrderSupplementsRequest.value()+":"+CommandEnum.OrderSupplementsRequest.description());
        pubSupervisionLog.setCreatetime(new Date());
        pubSupervisionLogDao.insert(pubSupervisionLog);

        //设置响应状态码
        httpServletResponse.setStatus(httpContent.getStatus());

        return  httpContent.getContent();
    }

    @GetMapping("/test")
    public String test(){
        return  "test";
    }

    /**
     * 获取请求头信息
     * @param httpServletRequest
     * @return
     */
    private HashMap getHeader(HttpServletRequest httpServletRequest){
        HashMap<String,String> map=new HashMap<>();
        map.put(GzwycApi.BINFILE_AUTH,httpServletRequest.getHeader(GzwycApi.BINFILE_AUTH));
        map.put(GzwycApi.BINFILE_GZIP,httpServletRequest.getHeader(GzwycApi.BINFILE_GZIP));
        map.put(GzwycApi.BINFILE_MD5,httpServletRequest.getHeader(GzwycApi.BINFILE_MD5));
        map.put(GzwycApi.BINFILE_REQLEN,httpServletRequest.getHeader(GzwycApi.BINFILE_REQLEN));
        return  map;
    }
}
