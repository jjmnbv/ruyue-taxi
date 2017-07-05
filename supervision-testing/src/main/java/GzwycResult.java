/**
 * 网约车响应结果
 * Created by 林志伟 on 2017/7/5.
 */
public class GzwycResult {

    private Integer status;

    private String Content;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "GzwycResult{" +
                "    status=" + status +
                ",     Content='" + Content + '\'' +
                '}';
    }
}
