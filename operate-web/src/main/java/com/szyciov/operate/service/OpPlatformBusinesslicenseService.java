package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.op.dto.OpPlatformBusinesslicenseDto;
import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.op.param.OpPlatformBusinesslicenseQueryParam;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.op.vo.OpPlatformBusinesslicenseVo;
import com.szyciov.operate.util.excel.OpPlatformBusinesslicenseExcel;
import com.szyciov.operate.util.excel.OpPlatformStagnationExcel;
import com.szyciov.util.ExcelImp;
import com.szyciov.util.ExcelRuleImport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created by lzw on 2017/8/16.
 */
@Service("opPlatformBusinesslicenseService")
public class OpPlatformBusinesslicenseService {
    private TemplateHelper templateHelper = new TemplateHelper();
    @Autowired
    private PubInfoService pubInfoService;
    /**
     * 分页查询
     * @param queryParam
     * @param userToken
     * @return
     */
    public PageBean page(OpPlatformBusinesslicenseQueryParam queryParam, String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Page", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }

    /**
     * 增加
     * @param opPlatformBusinesslicenseDto
     * @param userToken
     * @return
     */
    public Map<String,String> create(OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto, String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Create", HttpMethod.POST, userToken,
                opPlatformBusinesslicenseDto,Map.class);
    }

    /**创建服务机构
     *
     * @param opPlatformBusinesslicenseDto
     * @param userToken
     * @return
     */
    public Map<String,String> create( Map<String,Object> opPlatformBusinesslicenseDto,String userToken){
//        System.out.println("导入数据:"+opPlatformBusinesslicenseDto);
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Create", HttpMethod.POST, userToken,
                opPlatformBusinesslicenseDto,Map.class);
    }

    /**
     * 更新服务机构
     * @param opPlatformBusinesslicenseDto
     * @param userToken
     * @return
     */
    public Map<String,String> update( OpPlatformBusinesslicenseDto opPlatformBusinesslicenseDto,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Update", HttpMethod.POST, userToken,
                opPlatformBusinesslicenseDto,Map.class);
    }

    /**
     * 删除
     * @param id
     * @param userToken
     * @return
     */
    public Map<String,String> delete( String id,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Delete/{id}", HttpMethod.GET, userToken,
                null,Map.class,id);
    }

    /**
     * 获取
     * @param id
     * @param userToken
     * @return
     */
    public OpPlatformBusinesslicenseVo get(String id, String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/Get/{id}", HttpMethod.GET, userToken,
                null,OpPlatformBusinesslicenseVo.class,id);
    }

    /**
     * 获取下载 模板
     * @return
     */
    public Excel download(){
        OpPlatformBusinesslicenseExcel opPlatformBusinesslicenseExcel=OpPlatformBusinesslicenseExcel.getInstance();
        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        Excel excel = new Excel();

        List<String> colName = opPlatformBusinesslicenseExcel.getTitleList();
        List<String> exampleList=opPlatformBusinesslicenseExcel.getExampleList();
        excel.setColName(colName);
        List<Object> exampleData = null;
        for (int i=0;i<colName.size();i++){
            exampleData=new ArrayList<>();
            exampleData.add(exampleList.get(i));
            colData.put(colName.get(i),exampleData);
        }
        excel.setColData(colData);
        return  excel;
    }



    /**
     * 导出excel
     * @param queryParam
     * @param userToken
     * @return
     */
    public Excel  exportExcel(OpPlatformBusinesslicenseQueryParam queryParam,String userToken ){

        //显示的表头及对应的实体类属性名
        OpPlatformBusinesslicenseExcel opPlatformBusinesslicenseExcel=OpPlatformBusinesslicenseExcel.getInstance();
        List<String> colName = new ArrayList<String>();

        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        JSONArray jsonArray= templateHelper.dealRequestWithToken("/OpPlatformBusinesslicense/ExportExcel", HttpMethod.POST, userToken,
                queryParam,JSONArray.class);

        //遍历要显示的列
        for (int j = 0; j < opPlatformBusinesslicenseExcel.getCloumnList().size(); j++) {
            String key = opPlatformBusinesslicenseExcel.getCloumnList().get(j);
            String value = opPlatformBusinesslicenseExcel.getTitleList().get(j);
            //获取值对象list
            List<Object> valueList = colData.get(value);
            //如果list为空
            if (valueList == null) {
                //创建结果list 设置值为当前查询结果集
                valueList = new ArrayList<>(jsonArray.size());
            }
            //循环结果对象
            String str="";
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object o =  jsonObject.get(key);

                //处理要显示的值
                if(key.equals("startdate")||key.equals("stopdate")||key.equals("certifydate")){
                    str= (String) jsonObject.get(key);
                    o=str.substring(0,10);
                }else if(key.equals("scopes")){//处理经营区域
                    JSONArray jsonArray2= (JSONArray) o;
                    System.out.println("经营区域："+jsonArray2);
                    str="";
                    for (int k=0;k<jsonArray2.size();k++){
                        JSONObject jsonObject2= (JSONObject) jsonArray2.get(k);
                        if(k>0){
                            str+=",";
                        }
                        str+=jsonObject2.get("operationarea");
                    }
                    o=str;
                }else if(key.equals("state")){
                    str= String.valueOf(jsonObject.get(key));
                    if(str.equals("1")){
                        str="有效";
                    }else {
                        str="无效";
                    }
                    o=str;
                }


                if (o != null) {
                    valueList.add(o);
                } else {
                    valueList.add("");
                }
            }
            //设置列值
            colData.put(value, valueList);
            //新增列标题列
            colName.add(value);
        }

        Excel excel = new Excel();
        excel.setColName(colName);
        excel.setColData(colData);
        return excel;
    }

    /**
     * 导入excel
     * @param mulfile
     * @param userToken
     * @return
     * @throws IOException
     */
    public JSONObject  importExcel(MultipartFile mulfile, String userToken) throws IOException {
        JSONObject ret = new JSONObject();
        StringBuffer rets = new StringBuffer();
        String fileName = mulfile.getOriginalFilename();
        String fileNames = fileName.substring(fileName.length()-3);

        OpPlatformBusinesslicenseService opPlatformBusinesslicenseService=this;
//        excel模板实例
        OpPlatformBusinesslicenseExcel opPlatformBusinesslicenseExcel= OpPlatformBusinesslicenseExcel.getInstance();

        if(fileNames.equals("xls")){
            new ExcelImp().excelImp(mulfile, new ExcelRuleImport(){
                @Override
                public boolean VerifyFirstTitle(String[] columns) {
                    if(opPlatformBusinesslicenseExcel.verifyTitle(columns)){
                        return true;
                    }else {
                        ret.put("ResultSign", "Error");
                        ret.put("MessageKey", "模板错误(列名)，请按照下载模板填写数据");
                        return false;
                    }
                }

                @Override
                public boolean excelRuleImport(int index, Map<String, String> map) {
                    String cityName=map.get("经营许可地");
                    Map<String,String> res=pubInfoService.getCityIdByName(userToken,cityName);
                    Map<String,Object> dataMap=new HashMap<>();
                    if(res.get("status").equals("success")){
                        dataMap.put("address",res.get("id"));
                    }else{
                        rets.append("第" + index + "行 机构所在地不正确！");
                        ret.put("ResultSign", "Successful");
                        ret.put("MessageKey", rets.toString());
                        return false;
                    }
                    if(!map.get("证照状态").equals("有效")&&!map.get("证照状态").equals("无效")){
                        rets.append("第" + index + "行 证照状态必须是有效或者无效！");
                        ret.put("ResultSign", "Successful");
                        ret.put("MessageKey", rets.toString());
                        return false;
                    }

                    List<String> titleList=opPlatformBusinesslicenseExcel.getTitleList();
                    List<String> cloumnList=opPlatformBusinesslicenseExcel.getCloumnList();

                    for (int i=0;i<titleList.size();i++){
                        if(cloumnList.get(i).equals("scopes")){
                            String scopesStr=map.get(titleList.get(i));
                            String[] scopes=  scopesStr.split(",");
                            dataMap.put("operationareas", Arrays.asList(scopes));
                        }else if(cloumnList.get(i).equals("state")){
                            if(map.get(titleList.get(i)).equals("有效")){
                                map.put(titleList.get(i),"1");
                            }else{
                                map.put(titleList.get(i),"0");
                            }
                        }
                        dataMap.put(cloumnList.get(i),map.get(titleList.get(i)));
                    }


//                    System.out.println("数据map:"+dataMap);
                    res=opPlatformBusinesslicenseService.create(dataMap,userToken);
                    if(res.get("status").equals("success")){
                        return true;
                    }else{
                        rets.append("第" + index + "行 增加失败:"+res.get("message"));
                        ret.put("ResultSign", "Successful");
                        ret.put("MessageKey", rets.toString());
                        return false;
                    }


                }

            });
        }else{
            ret.put("ResultSign", "Error");
            ret.put("MessageKey", "请导入***.xls类型文件");
        }
        return  ret;
    }

}
