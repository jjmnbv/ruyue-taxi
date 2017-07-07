package api;

import java.util.List;

/**
 * 响应参数，封装多个结果
 * Created by 林志伟 on 2017/7/6.
 */
public class ApiResponceParam {
    private  List<ApiResult> items;


    /**
     * 获取成功数
     * @return
     */
    public int getSuccessNum() {
        int success=0;
        if(items==null){
            return  success;
        }

        for (int i=0;i<items.size();i++){
            if(items.get(i).getSuccess()==1){
                success++;
            }
        }
        return  success;
    }


    /**
     * 获取失败数
     * @return
     */
    public int getFailNum() {
        int fail=0;
       if(items==null){
           return  fail;
       }

       for (int i=0;i<items.size();i++){
           if(items.get(i).getSuccess()!=1){
               fail++;
           }
       }
       return  fail;
    }



    public List<ApiResult> getItems() {
        return items;
    }

    public void setItems(List<ApiResult> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ApiResponceParam{" +
                "    items=" + items +
                '}';
    }
}

