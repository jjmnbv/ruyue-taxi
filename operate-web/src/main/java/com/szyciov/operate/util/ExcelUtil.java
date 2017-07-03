package com.szyciov.operate.util;

import com.szyciov.entity.Excel;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
/**
 * excel 导出工具类封装
 * Created by liubangwei_lc on 2017/5/31.
 */
public class ExcelUtil {

    public static  Excel deal(List<String> cloumnList, List<String> titleList, JSONArray jsonArray ){
        List<String> colName = new ArrayList<String>();
        Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
        for (int j=0;j<cloumnList.size();j++) {
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
