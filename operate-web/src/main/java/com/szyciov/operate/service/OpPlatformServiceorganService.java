package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.op.entity.OpPlatformServiceorgan;
import com.szyciov.op.vo.OpPlatformServiceorganVo;
import com.szyciov.operate.util.excel.OpPlatformServiceorganExcel;
import com.szyciov.op.param.OpPlatformServiceorganQueryParam;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10 0010.
 */
@Service("opPlatformServiceorganService")
public class OpPlatformServiceorganService {
    private TemplateHelper templateHelper = new TemplateHelper();

    @Autowired
    private PubInfoService pubInfoService;
    /**
     * 分页查询
     * @param queryParam
     * @param userToken
     * @return
     */
    public PageBean page(OpPlatformServiceorganQueryParam queryParam,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Page", HttpMethod.POST, userToken,
                queryParam,PageBean.class);
    }

    /**
     * 创建服务机构
     * @param opPlatformServiceorgan
     * @param userToken
     * @return
     */
    public Map<String,String> create( OpPlatformServiceorgan opPlatformServiceorgan,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Create", HttpMethod.POST, userToken,
                opPlatformServiceorgan,Map.class);
    }

    /**创建服务机构
     *
     * @param opPlatformServiceorgan
     * @param userToken
     * @return
     */
    public Map<String,String> create( Map<String,String> opPlatformServiceorgan,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Create", HttpMethod.POST, userToken,
                opPlatformServiceorgan,Map.class);
    }

    /**
     * 更新服务机构
     * @param opPlatformServiceorgan
     * @param userToken
     * @return
     */
    public Map<String,String> update( OpPlatformServiceorgan opPlatformServiceorgan,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Update", HttpMethod.POST, userToken,
                opPlatformServiceorgan,Map.class);
    }

    /**
     * 删除服务机构
     * @param id
     * @param userToken
     * @return
     */
    public Map<String,String> delete( String id,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Delete/{id}", HttpMethod.GET, userToken,
                null,Map.class,id);
    }

    /**
     * 获取服务机构
     * @param id
     * @param userToken
     * @return
     */
    public OpPlatformServiceorganVo get(String id,String userToken){
        return templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/Get/{id}", HttpMethod.GET, userToken,
                null,OpPlatformServiceorganVo.class,id);
    }

    /**
     * 获取下载 模板
     * @return
     */
    public Excel download(){
        OpPlatformServiceorganExcel opPlatformServiceorganExcel=OpPlatformServiceorganExcel.getInstance();
        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        Excel excel = new Excel();
        // excel文件
        List<String> colName = opPlatformServiceorganExcel.getTitleList();
        List<String> exampleList=opPlatformServiceorganExcel.getExampleList();
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
    public Excel  exportExcel(OpPlatformServiceorganQueryParam queryParam,String userToken ){

        //显示的表头及对应的实体类属性名
        OpPlatformServiceorganExcel opPlatformServiceorganExcel=OpPlatformServiceorganExcel.getInstance();
        List<String> colName = new ArrayList<String>();

        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        JSONArray jsonArray= templateHelper.dealRequestWithToken("/OpPlatformServiceorgan/ExportExcel", HttpMethod.POST, userToken,
                queryParam,JSONArray.class);
        //遍历要显示的列
        for (int j = 0; j < opPlatformServiceorganExcel.getCloumnList().size(); j++) {
            String key = opPlatformServiceorganExcel.getCloumnList().get(j);
            String value = opPlatformServiceorganExcel.getTitleList().get(j);
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

    public JSONObject  importExcel(MultipartFile mulfile,String userToken) throws IOException {
        JSONObject ret = new JSONObject();
        StringBuffer rets = new StringBuffer();
        String fileName = mulfile.getOriginalFilename();
        String fileNames = fileName.substring(fileName.length()-3);

        OpPlatformServiceorganService opPlatformServiceorganService=this;

        if(fileNames.equals("xls")){
            new ExcelImp().excelImp(mulfile, new ExcelRuleImport(){
                @Override
                public boolean VerifyFirstTitle(String[] columns) {
                    if(OpPlatformServiceorganExcel.getInstance().verifyTitle(columns)){
                        return true;
                    }else {
                        ret.put("ResultSign", "Error");
                        ret.put("MessageKey", "模板错误(列名)，请按照下载模板填写数据");
                        return false;
                    }
                }

                @Override
                public boolean excelRuleImport(int index, Map<String, String> map) {

                    //验证机构所在地是否存在
                    String cityName=map.get("机构所在地");
                    Map<String,String> res=pubInfoService.getCityIdByName(userToken,cityName);
                    Map<String,String> dataMap=new HashMap<>();
                    if(res.get("status").equals("success")){
                        dataMap.put("address",res.get("id"));
                    }else{
                        rets.append("第" + index + "行 机构所在地不正确！");
                        ret.put("ResultSign", "Successful");
                        ret.put("MessageKey", rets.toString());
                        return false;
                    }

                    List<String> titleList=OpPlatformServiceorganExcel.getInstance().getTitleList();
                    List<String> cloumnList=OpPlatformServiceorganExcel.getInstance().getCloumnList();

                    for (int i=0;i<titleList.size();i++){
                        dataMap.put(cloumnList.get(i),map.get(titleList.get(i)));
                    }

                    res=opPlatformServiceorganService.create(dataMap,userToken);
                    if(res.get("status").equals("success")){
                        return true;
                    }else{
                        rets.append("第" + index + "行 增加失败！");
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
