package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.op.entity.OpPlatformStagnation;
import com.szyciov.operate.util.excel.OpPlatformStagnationExcel;
import com.szyciov.op.param.OpPlatformStagnationQueryParam;
import com.szyciov.util.ExcelImp;
import com.szyciov.util.ExcelRuleImport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
@Service("opPlatformStagnationService")
public class OpPlatformStagnationService {
    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 分页查询
     * @param queryParam
     * @param userToken
     * @return
     */
    public PageBean page(OpPlatformStagnationQueryParam queryParam, String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Page", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }

    /**
     * 创建服务机构
     * @param opPlatformStagnation
     * @param userToken
     * @return
     */
    public Map<String,String> create(OpPlatformStagnation opPlatformStagnation, String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Create", HttpMethod.POST, userToken,
                opPlatformStagnation,Map.class);
    }

    /**创建服务机构
     *
     * @param opPlatformStagnation
     * @param userToken
     * @return
     */
    public Map<String,String> create( Map<String,String> opPlatformStagnation,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Create", HttpMethod.POST, userToken,
                opPlatformStagnation,Map.class);
    }

    /**
     * 更新服务机构
     * @param opPlatformStagnation
     * @param userToken
     * @return
     */
    public Map<String,String> update( OpPlatformStagnation opPlatformStagnation,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Update", HttpMethod.POST, userToken,
                opPlatformStagnation,Map.class);
    }

    /**
     * 删除服务机构
     * @param id
     * @param userToken
     * @return
     */
    public Map<String,String> delete( String id,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Delete/{id}", HttpMethod.GET, userToken,
                null,Map.class,id);
    }

    /**
     * 获取服务机构
     * @param id
     * @param userToken
     * @return
     */
    public OpPlatformStagnation get(String id,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformStagnation/Get/{id}", HttpMethod.GET, userToken,
                null,OpPlatformStagnation.class,id);
    }

    /**
     * 获取下载 模板
     * @return
     */
    public Excel download(){
        OpPlatformStagnationExcel opPlatformStagnationExcel=OpPlatformStagnationExcel.getInstance();
        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        Excel excel = new Excel();

        List<String> colName = opPlatformStagnationExcel.getTitleList();
        List<String> exampleList=opPlatformStagnationExcel.getExampleList();
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
    public Excel  exportExcel(OpPlatformStagnationQueryParam queryParam,String userToken ){

        //显示的表头及对应的实体类属性名
        OpPlatformStagnationExcel opPlatformStagnationExcel=OpPlatformStagnationExcel.getInstance();
        List<String> colName = new ArrayList<String>();

        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        JSONArray jsonArray= templateHelper.dealRequestWithToken("/OpPlatformStagnation/ExportExcel", HttpMethod.POST, userToken,
                queryParam,JSONArray.class);
        //遍历要显示的列
        for (int j = 0; j < opPlatformStagnationExcel.getCloumnList().size(); j++) {
            String key = opPlatformStagnationExcel.getCloumnList().get(j);
            String value = opPlatformStagnationExcel.getTitleList().get(j);
            //获取值对象list
            List<Object> valueList = colData.get(value);
            //如果list为空
            if (valueList == null) {
                //创建结果list 设置值为当前查询结果集
                valueList = new ArrayList<>(jsonArray.size());
            }
            //循环结果对象
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object o = jsonObject.get(key);
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

    public JSONObject  importExcel(MultipartFile mulfile, String userToken) throws IOException {
        JSONObject ret = new JSONObject();
        StringBuffer rets = new StringBuffer();
        String fileName = mulfile.getOriginalFilename();
        String fileNames = fileName.substring(fileName.length()-3);

        OpPlatformStagnationService opPlatformStagnationService=this;
//        excel模板实例
        OpPlatformStagnationExcel opPlatformStagnationExcel=OpPlatformStagnationExcel.getInstance();

        if(fileNames.equals("xls")){
            new ExcelImp().excelImp(mulfile, new ExcelRuleImport(){
                @Override
                public boolean VerifyFirstTitle(String[] columns) {
                    if(opPlatformStagnationExcel.verifyTitle(columns)){
                        return true;
                    }else {
                        ret.put("ResultSign", "Error");
                        ret.put("MessageKey", "模板错误(列名)，请按照下载模板填写数据");
                        return false;
                    }
                }

                @Override
                public boolean excelRuleImport(int index, Map<String, String> map) {
                    Map<String,String> dataMap=new HashMap<>();
                    List<String> titleList=opPlatformStagnationExcel.getTitleList();
                    List<String> cloumnList=opPlatformStagnationExcel.getCloumnList();

                    for (int i=0;i<titleList.size();i++){
                        dataMap.put(cloumnList.get(i),map.get(titleList.get(i)));
                    }


                    Map<String,String> res=opPlatformStagnationService.create(dataMap,userToken);
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
