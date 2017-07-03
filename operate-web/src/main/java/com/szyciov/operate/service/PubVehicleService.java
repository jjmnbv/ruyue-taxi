package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LC on 2017/3/28.
 */
@Service
public class PubVehicleService {


    private TemplateHelper templateHelper = new TemplateHelper();

    /**
     * 导出Excel
     *
     * @param param
     * @param token
     * @return
     * @throws IllegalAccessException
     */
    public Excel exportExcel(VehicleIndexQueryParam param, String token) throws IllegalAccessException {

        //显示的表头及对应的实体类属性名
        List<String> cloumnList = new ArrayList<>(11);
        cloumnList.add("vehicletype");
        cloumnList.add("serviceCars");
        cloumnList.add("boundstate");
        cloumnList.add("vehiclestatus");
        cloumnList.add("platenoStr");
        cloumnList.add("brandCars");
        cloumnList.add("vin");
        cloumnList.add("color");
        cloumnList.add("loads");
        cloumnList.add("cityStr");
        cloumnList.add("scopes");


        List<String> titleList = new ArrayList<>(11);
        titleList.add("车辆类型");
        titleList.add("服务车型");
        titleList.add("绑定状态");
        titleList.add("营运状态");
        titleList.add("车牌号");
        titleList.add("品牌车系");
        titleList.add("车架号");
        titleList.add("颜色");
        titleList.add("荷载人数");
        titleList.add("登记城市");
        titleList.add("经营区域");

        List<String> colName = new ArrayList<String>();

        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

        JSONArray jsonArray = templateHelper.dealRequestWithToken("/PubVehicle/ExportExcel", HttpMethod.POST,
                token, param, JSONArray.class);
        //遍历要显示的列
        for (int j = 0; j < cloumnList.size(); j++) {
            String key = cloumnList.get(j);
            String value = titleList.get(j);
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
}
 