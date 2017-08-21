package com.szyciov.supervision.controller;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.serivice.PubSupervisionLogService;
import com.szyciov.supervision.serivice.impl.PubSupervisionLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szyciov.supervision.api.dto.order.OrderSupplementsRequest;
import com.szyciov.supervision.api.service.BaseApiService;
import com.szyciov.supervision.entity.PubSupervisionLog;
import com.szyciov.supervision.api.service.TokenService;
import com.szyciov.supervision.api.responce.EntityInfoList;
import com.szyciov.supervision.util.FileUtil;
import com.szyciov.supervision.util.GzwycApiUtil;
import com.szyciov.supervision.api.responce.HttpContent;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;

/**
 * 订单补传协议
 * Created by 林志伟 on 2017/7/25.
 */
@RestController
public class OrderController {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);

    private @Autowired
    BaseApiService baseService;

    private @Autowired
    PubSupervisionLogService PubSupervisionLogService;
    /**
     * 订单补传协议
     */
    @RequestMapping("/api/order/supplements")
    public String Supplements(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("binFile") MultipartFile binFile) throws Exception {
        //权限检验

        String paramString = FileUtil.convertStreamToString(binFile.getInputStream());

        HttpContent httpContent = GzwycApiUtil.checkReceiveData(httpServletRequest, paramString);
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
        PubSupervisionLogService.insert(pubSupervisionLog);

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
        map.put(GzwycApiUtil.BINFILE_AUTH,httpServletRequest.getHeader(GzwycApiUtil.BINFILE_AUTH));
        map.put(GzwycApiUtil.BINFILE_GZIP,httpServletRequest.getHeader(GzwycApiUtil.BINFILE_GZIP));
        map.put(GzwycApiUtil.BINFILE_MD5,httpServletRequest.getHeader(GzwycApiUtil.BINFILE_MD5));
        map.put(GzwycApiUtil.BINFILE_REQLEN,httpServletRequest.getHeader(GzwycApiUtil.BINFILE_REQLEN));
        return  map;
    }
}
