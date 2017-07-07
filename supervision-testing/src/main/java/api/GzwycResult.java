package api;

import api.ApiResponceParam;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 网约车响应结果
 * Created by 林志伟 on 2017/7/5.
 */
public class GzwycResult {

    public GzwycResult() {
    }

    public GzwycResult(Integer status, String content) {
        this.status = status;
        this.content = content;
    }

    /**
     * 返回状态
     */
    private Integer status;

    /**
     * 内容
     */
    private String content;

    /**
     * 封装的响应参数，当status等于200时，会自动解析content,否则为null
     */
    private ApiResponceParam apiResponceParam;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public void parseContent(){
        if(this.status==200){//开始解析内容
            ObjectMapper objectMapper=new ObjectMapper();
            try {
                apiResponceParam=objectMapper.readValue(this.getContent(), ApiResponceParam.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ApiResponceParam getApiResponceParam() {
        return apiResponceParam;
    }

    public void setApiResponceParam(ApiResponceParam apiResponceParam) {
        this.apiResponceParam = apiResponceParam;
    }

    @Override
    public String toString() {
        return "api.GzwycResult{" +
                "    status=" + status +
                ",     content='" + content + '\'' +
                ",     apiResponceParam=" + apiResponceParam +
                '}';
    }
}
