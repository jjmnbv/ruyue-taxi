package com.xxkj.passenger.wechat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityInfoList<T> {
    private List<T> items;
    
    public boolean isAllSuccess(){
    	return items != null && items.size() > 0;
    }
}
