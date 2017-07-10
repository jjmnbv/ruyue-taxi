package api.evaluate;

import lombok.Data;

/**
 * 3.6.3	驾驶员处罚信息（JSYCF）
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class DriverPenaltyInfo extends EvaluateApi {
    public DriverPenaltyInfo() {
        super();
        setCommand("JSYCF");
    }

    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 处罚时间	YYYYMMDDHHMMSS
     */
    private String punishTime;
    /**
     * 处罚原因
     */
    private String punishReason;
    /**
     *处罚结果
     */
    private String punishResult;
}
