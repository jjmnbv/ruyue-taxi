package com.szyciov.supervision.api.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.supervision.api.BaseApi;
import com.supervision.api.basic.CompanyOperateInfo;
import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.util.HttpContent;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import com.szyciov.supervision.enums.RequestType;
import com.szyciov.supervision.token.service.TokenService;
import com.szyciov.supervision.util.BasicRequest;
import com.szyciov.supervision.util.EntityInfoList;
import com.szyciov.supervision.util.GzwycApi;

/**
 * Created by admin on 2017/7/10.
 */
@Component
public class BaseService {

    private static Logger logger = LoggerFactory.getLogger(TokenService.class);


    /**
     *  发送接口对象
     * @return
     * @throws IOException
     */
    public EntityInfoList<?extends BaseApi> sendApi(List<? extends BaseApi> list) throws IOException {

        if(list==null||list.size()<=0){
            logger.error("API_INFO:发送请求数据-接收数据为空");
            return  null;
        }

        String token = TokenService.getToken();
        EntityInfoList infoList = new EntityInfoList(list);
        String result = JSONUtil.toJackson(infoList);
        CommandEnum commandEnum= list.get(0).getCommand();
        InterfaceType interfaceType=list.get(0).getApiType();
        BasicRequest req = new BasicRequest(result, interfaceType,commandEnum , RequestType.REQ,token);
        HttpContent httpContent = GzwycApi.send(req);

        if(httpContent.getStatus()==200){
            TypeReference<EntityInfoList<BaseApi>> t=new TypeReference<EntityInfoList<BaseApi>>(){};
            // 编码处理
            String content=new String(httpContent.getContent().getBytes("ISO-8859-1"),"UTF-8");
            EntityInfoList<BaseApi> infoList2 = JSONUtil.objectMapper.readValue(content, t);
            //过滤发送成功的消息
            List<BaseApi> collect = infoList2.getItems().stream().filter(i -> i.getSuccess() == 0).collect(Collectors.toList());
            infoList2.setItems(collect);

            if(infoList2.getItems()!=null&&infoList2.getItems().size()>0) {
                logger.error("API_INFO:发送请求数据失败|接口类型:{}|接口描述:{}|命令类型:{}|命令描述:{}|失败记录:{}", interfaceType.value(), interfaceType.description(), commandEnum.value(), commandEnum.description(), JSONUtil.toJackson(collect));
            }else {
                logger.info("API_INFO:发送请求数据成功|接口类型:{}|接口描述:{}|命令类型:{}|命令描述:{}|发送成功数目:{}", interfaceType.value(), interfaceType.description(), commandEnum.value(), commandEnum.description(),list.size());
            }
            return infoList2;
        }else if(httpContent.getStatus()==950) {//状态处理,token失效，重新获取
            TokenService.clear();
            return  null;
        }else{
            logger.error("API_INFO:发送请求数据|接口类型:{}|接口描述:{}|命令类型:{}|命令描述:{}|状态码:{}|返回内容:{}",interfaceType.value(),interfaceType.description(),commandEnum.value(),commandEnum.description(),httpContent.getStatus(),httpContent.getContent());
            return  null;
        }

    }
}
