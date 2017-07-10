import api.ApiResponceParam;
import api.GzwycApiUtil;
import api.GzwycResult;
import api.basic.CompanyBaseInfo;

import java.util.*;
import java.util.List;

/**
 * 标准调用示例
 * Created by 林志伟 on 2017/7/6.
 */
public class Test_GzwycApi2 {
    public static void main(String[] args) throws Exception {

//        创建api对象
        CompanyBaseInfo companyBaseInfo =new CompanyBaseInfo();
        companyBaseInfo.setSymbol("RY_0001");
        companyBaseInfo.setCompanyId("RY");
        companyBaseInfo.setCompanyName("讯心科技");
        companyBaseInfo.setIdentifier("54456454545454");
        companyBaseInfo.setContactPhone("88888888");
        companyBaseInfo.setAddress("852123");
        companyBaseInfo.setBusinessScope("约租车");
        companyBaseInfo.setContactAddress("广州天河元岗");
        companyBaseInfo.setEconomicType("不知");
        companyBaseInfo.setRegCapital("100w");
        companyBaseInfo.setLegalName("乔巴");
        companyBaseInfo.setIdentifierPhoto("http://fasfasfsf//fadsdfs");
        companyBaseInfo.setState(0);
        companyBaseInfo.setFlag(0);
        companyBaseInfo.setUpdateTime("20170704144012");
        companyBaseInfo.setGzAd("sdsds");
        companyBaseInfo.setResponsible("sdsfdsfsdf");
        companyBaseInfo.setResponsibleWay("s4d5s4d5s4d5s4d");
        companyBaseInfo.setParentCompany("广州交通集团");
        companyBaseInfo.setParentAd("455645s4d4s");
        companyBaseInfo.setPostCode("554d4sd4");
        companyBaseInfo.setServiceItem("454545454");
        companyBaseInfo.setServiceItem("1114ds4d54s5d4");

        //支持多个对象的请求
        List<CompanyBaseInfo> items= new ArrayList<CompanyBaseInfo>();
        items.add(companyBaseInfo);
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
