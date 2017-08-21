package com.szyciov.supervision.api.responce;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */

public class EntityInfoList<T> {
    private List<T> items;
    
    public boolean isAllSuccess(){
    	return items == null || items.size() <= 0;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public EntityInfoList(List<T> items) {
        this.items = items;
    }

    public EntityInfoList() {
    }

}
