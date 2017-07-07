package api.basic;

import lombok.Data;

/**
 * 3.2.10	网约车驾驶员基本信息(JSYJB)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class JSYJBApi  extends BasicApi {
    public JSYJBApi() {
        super();
        setCommand("JSYJ");
    }

//    注册地行政区划编号
    private String address;
//    机动车驾驶员姓名
    private String driverName;
//    驾驶员身份证号
    private String driverIDCard;
//  驾驶员联系电话
    private String driverPhone;
//    驾驶员性别
    private String driverGender;
//    出生日期
    private String driverBirthday;
//    国籍
    private String driverNationality;
//    驾驶员民族
    private String driverNation;
//    驾驶员婚姻状况
    private String driverMaritalStatus;
//    驾驶员外语能力
    private String driverLanguageLevel;
//    驾驶员学历
    private String driverEducation;
//    户口登记机关名称
    private String driverCensus;
//    户口地址或常住地址
    private String driverAddress;
//    驾驶员通信地址
    private String driverContactAddress;
//    驾驶员照片文件
    private String photo;
//    驾驶员照片文件编号
    private String photoId;
//    机动车驾驶证号
    private String licenseId;
//    机动车驾驶证扫描件文件
    private String licensePhoto;
//    机动车驾驶证扫描件文件编号
    private String licensePhotoId;
//    准驾车型
    private String driverType;
//    初次领取驾驶证日期
    private String getDriverLicenseDate;
//    驾驶证有效期限起
    private String driverLicenseOn;
//    驾驶证有效期限止
    private String driverLicenseOff;
//    是否巡游出租汽车驾驶员
    private String taxiDriver;
//    网络预约出租汽车驾驶员资格证号
    private String certificateA;
//    巡游出租汽车驾驶员资格证号
    private String certificateB;
//  网络预约出租汽车驾驶员证发证机构
    private String networkCarIssueOrganization;
//    资格证发证日期
    private String networkCarIssueDate;
//    初次领取资格证日期
    private String getNetworkCarProofDate;
//    网络预约出租汽车驾驶员证有效期起
    private String networkCarProofOn;
//    网络预约出租汽车驾驶员证有效期止
    private String networkCarProofOff;
//    注册日期
    private String registerDate;
//    是否专职驾驶员
    private String fullTimeDriver;
//    是否在驾驶员黑名单内
    private String inDriverBlacklist;
//    出租资格类别
    private String commercialType;
//    驾驶员合同（或协议）签署公司标识
    private String contractCompany;
//    合同（或协议）有效期起
    private String contractOn;
//    合同（或协议）有效期止
    private String contractOff;
//    紧急情况联系人
    private String emergencyContact;
//    紧急情况联系人电话
    private String emergencyContactPhone;
//    紧急情况联系人通讯地址
    private String emergencyContactAddress;
//    奖惩情况
    private String rewardPunishment;








}
