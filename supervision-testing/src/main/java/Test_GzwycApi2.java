import api.ApiRequestParam;
import api.ApiResponceParam;
import api.GzwycApiUtil;
import api.GzwycResult;
import api.basic.PTJBApi;

import java.util.*;
import java.util.List;

/**
 * 标准调用示例
 * Created by 林志伟 on 2017/7/6.
 */
public class Test_GzwycApi2 {
    public static void main(String[] args) throws Exception {

//        创建api对象
        PTJBApi ptjbApi=new PTJBApi();
        ptjbApi.setSymbol("RY_0001");
        ptjbApi.setCompanyId("RY");
        ptjbApi.setCompanyName("讯心科技");
        ptjbApi.setIdentifier("54456454545454");
        ptjbApi.setContactPhone("88888888");
        ptjbApi.setAddress("852123");
        ptjbApi.setBusinessScope("约租车");
        ptjbApi.setContactAddress("广州天河元岗");
        ptjbApi.setEconomicType("不知");
        ptjbApi.setRegCapital("100w");
        ptjbApi.setLegalName("乔巴");
        ptjbApi.setIdentifierPhoto("http://fasfasfsf//fadsdfs");
        ptjbApi.setState(0);
        ptjbApi.setFlag(0);
        ptjbApi.setUpdateTime("20170704144012");
        ptjbApi.setGzAd("sdsds");
        ptjbApi.setResponsible("sdsfdsfsdf");
        ptjbApi.setResponsibleWay("s4d5s4d5s4d5s4d");
        ptjbApi.setParentCompany("广州交通集团");
        ptjbApi.setParentAd("455645s4d4s");
        ptjbApi.setPostCode("554d4sd4");
        ptjbApi.setServiceItem("454545454");
        ptjbApi.setServiceItem("1114ds4d54s5d4");

        //支持多个对象的请求
        List<PTJBApi> items= new ArrayList<PTJBApi>();
        items.add(ptjbApi);
        GzwycApiUtil gzwycApiUtil =new GzwycApiUtil();
        GzwycResult gzwycResult= gzwycApiUtil.send(items);
        System.out.println("响应状态码:"+gzwycResult.getStatus());
        System.out.println("响应内容:"+gzwycResult.getContent());
        if(gzwycResult.getStatus()==200){
            //获取解析结果
            ApiResponceParam apiResponceParam=gzwycResult.getApiResponceParam();
            System.out.println("响应结果列表:"+apiResponceParam.getItems());
            System.out.println("响应成功数目:"+apiResponceParam.getSuccessNum());
            System.out.println("响应失败数目:"+apiResponceParam.getFailNum());
        }

    }
}
