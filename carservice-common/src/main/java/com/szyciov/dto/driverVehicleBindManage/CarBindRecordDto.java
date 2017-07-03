package com.szyciov.dto.driverVehicleBindManage;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 网约车绑定操作记录显示
 */
public class CarBindRecordDto {

    //工号
    private String jobnum;
    //司机姓名
    private String name;
    //司机电话
    private String phone;
    //绑定类型
    private String bindstate;
    //服务车型名称
    private String modelName;
    //车牌号
    private String platenoStr;
    //操作时间
    private Date createtime;
    //操作原因
    private String unbindreason;
    //操作人姓名
    private String nickname;
    //车架号
    private String vinStr;
    //车辆信息
    private String vechicleInfo;

    //创建时间格式化
    private String createtimeStr;
    //归属车企
    private String belongLeasecompanyName;

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBindstate() {
        if(this.bindstate!=null){
            if(BindingStateEnum.BINDING.code.equals(this.bindstate)){
                return "绑定";
            }else if(BindingStateEnum.UN_BINDING.code.equals(this.bindstate)){
                return "解绑";
            }
        }
        return bindstate;
    }

    public void setBindstate(String bindstate) {
        this.bindstate = bindstate;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUnbindreason() {
        if(StringUtils.isEmpty(this.unbindreason)){
            return "/";
        }
        return unbindreason;
    }

    public void setUnbindreason(String unbindreason) {
        this.unbindreason = unbindreason;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getVinStr() {
        return vinStr;
    }

    public void setVinStr(String vinStr) {
        this.vinStr = vinStr;
    }

    public String getVechicleInfo() {
        return  this.getModelName()+" "+this.getPlatenoStr();
    }

    public void setVechicleInfo(String vechicleInfo) {
        this.vechicleInfo = vechicleInfo;
    }

    public String getCreatetimeStr() {
        if(this.createtime!=null){
            return DateUtil.format(this.createtime);
        }
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

	public String getBelongLeasecompanyName() {
		return belongLeasecompanyName;
	}

	public void setBelongLeasecompanyName(String belongLeasecompanyName) {
		this.belongLeasecompanyName = belongLeasecompanyName;
	}
}
 