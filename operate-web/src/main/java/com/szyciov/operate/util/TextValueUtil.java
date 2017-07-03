package com.szyciov.operate.util;


import com.szyciov.entity.TextAndValue;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 将字典值转换工具类
 * Created by liubangwei_lc on 2017/6/8.
 */
public class TextValueUtil {

    /**
     * 将字典值转换为text and value
     * @param list
     * @return
     */
    public static List<TextAndValue> convert(List list){
        List<TextAndValue> textAndValueList = new ArrayList<>();

        for (Object dictionary : list) {
            LinkedHashMap<String, Object> dictionaryList = (LinkedHashMap<String, Object>) dictionary;
            TextAndValue textAndValue = new TextAndValue();
            textAndValue.setText(String.valueOf(dictionaryList.get("text")));
            textAndValue.setValue(String.valueOf(dictionaryList.get("value")));
            textAndValueList.add(textAndValue);
        }

        return textAndValueList;
    }
}
