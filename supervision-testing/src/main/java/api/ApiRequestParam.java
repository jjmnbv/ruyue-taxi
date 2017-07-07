package api;

import api.basic.PTJBApi;

import java.util.List;

/**
 * 请求参数格式
 * Created by 林志伟 on 2017/7/6.
 */
public class ApiRequestParam<T extends  BaseApi> {
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
