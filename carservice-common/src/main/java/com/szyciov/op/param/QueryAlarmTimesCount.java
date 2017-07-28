package com.szyciov.op.param;

/**
 * Created by liubangwei_lc on 2017/7/24.
 * 报警次数统计
 */
public class QueryAlarmTimesCount {

    /**
     * 急加速次数
     */
    private Integer alarmAccelerateTimes;

    /**
     * 急减速次数
     */
    private Integer alarmDecelerateTimes;

    /**
     * 急转弯次数
     */
    private Integer alarmSharpTurnTimes;

    public Integer getAlarmAccelerateTimes() {
        return alarmAccelerateTimes;
    }

    public void setAlarmAccelerateTimes(Integer alarmAccelerateTimes) {
        this.alarmAccelerateTimes = alarmAccelerateTimes;
    }

    public Integer getAlarmDecelerateTimes() {
        return alarmDecelerateTimes;
    }

    public void setAlarmDecelerateTimes(Integer alarmDecelerateTimes) {
        this.alarmDecelerateTimes = alarmDecelerateTimes;
    }


    public Integer getAlarmSharpTurnTimes() {
        return alarmSharpTurnTimes;
    }

    public void setAlarmSharpTurnTimes(Integer alarmSharpTurnTimes) {
        this.alarmSharpTurnTimes = alarmSharpTurnTimes;
    }
}
