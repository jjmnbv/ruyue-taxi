/**
 * 测试接口类
 * Created by 林志伟 on 2017/7/5.
 */
public class Test_GzwycApi {
    public static void main(String[] args){
        GzwycApi gzwycApi=new GzwycApi();
        //设置参数
        String companyId="RY";
        String result = "{\"items\":[{\"companyId\":\""+companyId+"\",\"vehicleNum\":1,\"driverNum\":0.00,\"symbol\":\"123123123\","
                + "\"flag\":1,\"updateTime\":\"2018-12-23 17:30:00\"},"
                + "{\"companyId\":\""+companyId+"\",\"vehicleNum\":1.12,\"driverNum\":1.00,"
                + "\"flag\":1,\"updateTime\":\"2018-12-23 17:30:00\"},"
                + "{\"companyId\":\""+companyId+"\",\"vehicleNum\":100,\"driverNum\":100,"
                + "\"flag\":1,\"updateTime\":\"2018-12-23 17:30:00\"},"
                + "{\"companyId\":\""+companyId+"\",\"vehicleNum\":12.12,\"driverNum\":12.23,"
                + "\"flag\":\"1\",\"updateTime\":\"2018-12-23 17:30:00\"},"
                + "{\"companyId\":\""+companyId+"\",\"vehicleNum\":9.1,\"driverNum\":1.0,"
                + "\"flag\":\"1\",\"updateTime\":\"2018-12-23 17:30:00\"}"
                + "]}";
//        设置接口类型
        gzwycApi.setApiType("BASIC");
//        设置命令
        gzwycApi.setCommand("PTYYGM");
        try {
//            发送请求
            GzwycResult gzwycResult=gzwycApi.send(result);
            System.out.println("result:"+gzwycResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String token=gzwycApi.getToken();
//        System.out.println("token:"+token);

    }
}
