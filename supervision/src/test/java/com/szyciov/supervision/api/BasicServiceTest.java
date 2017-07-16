package com.szyciov.supervision.api;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.supervision.api.basic.*;
import com.szyciov.supervision.util.FileUtil;
import org.junit.Test;

/**
 * 基础数据从测试
 * Created by admin on 2017/7/10.
 */
public class BasicServiceTest extends ApiServiceTest
{

	/**
	 * 3.2.1	网约车平台公司基本信息(PTJB)
	 */
	@Test
	public void testCompanyBaseInfo(){
		List<CompanyBaseInfo> list = new ArrayList<CompanyBaseInfo>();
		CompanyBaseInfo companyBaseInfo=new CompanyBaseInfo();
		companyBaseInfo.setSymbol(getSymbol());
		companyBaseInfo.setCompanyId("RY");//默认RY可以不设置
		companyBaseInfo.setCompanyName("广州讯心信息科技有限公司");
		companyBaseInfo.setIdentifier("91440101MA59EE6A9M");//测试
		companyBaseInfo.setContactPhone("02086667842");
		companyBaseInfo.setAddress("440100");
		companyBaseInfo.setBusinessScope("网约车,出租车");
		companyBaseInfo.setContactAddress("广州天河区智汇park创意园，元岗路310号C5A10");
		companyBaseInfo.setEconomicType("经营业户经济类型");
		companyBaseInfo.setRegCapital("100W");
		companyBaseInfo.setLegalName("林xx");
		companyBaseInfo.setLegalId("445202199407042411");
		companyBaseInfo.setLegalPhone("18826252100");
		companyBaseInfo.setLegalPhoto("");
		companyBaseInfo.setLegalPhotoId("");
		companyBaseInfo.setIdentifierPhoto("");
		companyBaseInfo.setState(0);
		companyBaseInfo.setFlag(1);
		companyBaseInfo.setUpdateTime(getNowTime());
		companyBaseInfo.setGzAd("广州通信地址");
		companyBaseInfo.setResponsible("林志伟");
		companyBaseInfo.setResponsibleWay("88888888");
		companyBaseInfo.setParentCompany("广州交通集团");
		companyBaseInfo.setParentAd("广州交通集团地址");
		companyBaseInfo.setPostCode("510000");
		companyBaseInfo.setServiceItem("公车");
		companyBaseInfo.setServicePromise("质量标准和承诺");
		list.add(companyBaseInfo);
		messageSender.send(list);
	}

	/**
	 *3.2.2	网约车平台公司运营规模信息
	 * @throws IOException
	 */
	@Test
	public void testCompanyOperateInfo() throws IOException
	{
		List<CompanyOperateInfo> list = new ArrayList<CompanyOperateInfo>();
		CompanyOperateInfo info = new CompanyOperateInfo();
		info.setCompanyId("RY");//默认RY可以不设置
		info.setSymbol(getSymbol());
		info.setDriverNum(105);
		info.setVehicleNum(123);
		info.setFlag(1);
		info.setUpdateTime(getNowTime());
		list.add(info);
		messageSender.send(list);
	}

	/**
	 *3.2.3	网约车平台公司支付信息(PTZF)
	 */
	@Test
	public void testCompanyPayInfo(){
		List<CompanyPayInfo> list = new ArrayList<CompanyPayInfo>();
		CompanyPayInfo companyPayInfo=new CompanyPayInfo();
		companyPayInfo.setSymbol(getSymbol());
		companyPayInfo.setPayName("微信支付");
		companyPayInfo.setPayId("454d4sdsdsds4523");
		companyPayInfo.setPayType("约车");
		companyPayInfo.setPayScope("租车");
		companyPayInfo.setPrepareBank("备付金存管银行");
		companyPayInfo.setCountDate(10);
		companyPayInfo.setState(0);
		companyPayInfo.setFlag(1);
		companyPayInfo.setUpdateTime(getNowTime());
		list.add(companyPayInfo);
		messageSender.send(list);

	}

	/**
	 * 3.2.4	网约车平台公司服务机构(PTFWJG)
	 */
	@Test
	public void testCompanyServiceOrgan(){
		List<CompanyServiceOrgan> list = new ArrayList<CompanyServiceOrgan>();
		CompanyServiceOrgan companyServiceOrgan=new CompanyServiceOrgan();
		companyServiceOrgan.setSymbol(getSymbol());
		companyServiceOrgan.setAddress("440100");
		companyServiceOrgan.setServiceName("如约的士");
		companyServiceOrgan.setServiceNo("sfjshsjhdfjsd456");
		companyServiceOrgan.setDetailAddress("服务机构地址");
		companyServiceOrgan.setResponsibleName("林志伟");
		companyServiceOrgan.setResponsiblePhone("88888888");
		companyServiceOrgan.setManagerName("林志伟");
		companyServiceOrgan.setManagerPhone("87845442");
		companyServiceOrgan.setContactPhone("88888888");
		companyServiceOrgan.setMailAddress("478280531@qq.com");
		companyServiceOrgan.setCreateDate("20170701");
		companyServiceOrgan.setState(0);
		companyServiceOrgan.setFlag(1);
		companyServiceOrgan.setUpdateTime(getNowTime());
		list.add(companyServiceOrgan);
		messageSender.send(list);
	}

	/**
	 * 3.2.5	网约车平台公司经营许可(CPTJYXK)
	 */
	@Test
	public void testCompanyOperatingPermit(){
		List<CompanyOperatingPermit> list = new ArrayList<CompanyOperatingPermit>();
		CompanyOperatingPermit companyOperatingPermit=new CompanyOperatingPermit();
		companyOperatingPermit.setSymbol(getSymbol());
		companyOperatingPermit.setAddress("440110");
		companyOperatingPermit.setCertificate("djsahdja5dsddd45q");
		companyOperatingPermit.setOperationArea("广州");
		companyOperatingPermit.setOwnerName("广州讯心信息科技有限公司");
		companyOperatingPermit.setOrganization("广州");
		companyOperatingPermit.setStartDate("20160106");
		companyOperatingPermit.setStopDate("20250101");
		companyOperatingPermit.setCertifyDate("20170707");
		companyOperatingPermit.setState(0);
		companyOperatingPermit.setFlag(1);
		companyOperatingPermit.setUpdateTime(getNowTime());

		list.add(companyOperatingPermit);
		messageSender.send(list);
	}

	/**
	 * 3.2.6	网约车平台公司计程计价方式(
	 */
	@Test
	public void testCompanyValuationWay(){
		List<CompanyValuationWay> list = new ArrayList<CompanyValuationWay>();
		CompanyValuationWay companyValuationWay=new CompanyValuationWay();
		companyValuationWay.setSymbol(getSymbol());
		companyValuationWay.setCompanyId("RY");
		companyValuationWay.setAddress("440101");
		companyValuationWay.setFareType("运价类型编码");
		companyValuationWay.setFareTypeNote("运价类型编码shuoming");
		companyValuationWay.setFareValidOn("20160101010101");
		companyValuationWay.setFareValidOff("20190101010101");
		companyValuationWay.setStartFare("100");
		companyValuationWay.setStartMile("10");
		companyValuationWay.setUnitPrice("5");
		companyValuationWay.setUpPrice("6");
		companyValuationWay.setUpPriceStartMile("8");
		companyValuationWay.setMorningPeakTimeOn("0700");
		companyValuationWay.setMorningPeakTimeOff("0930");
		companyValuationWay.setEveningPeakTimeOn("1830");
		companyValuationWay.setEveningPeakTimeOff("2030");
		companyValuationWay.setOtherPeakTimeOn("1200");
		companyValuationWay.setOtherPeakTimeOff("1430");
		companyValuationWay.setPeakUnitPrice("12");
		companyValuationWay.setPeakPriceStartMile("11");
		companyValuationWay.setUnitPricePerMile("12");
		companyValuationWay.setUnitPricePerMinute("52");
		companyValuationWay.setLongDistancePrice("10");
		companyValuationWay.setLongDistanceStartMile("125");
		companyValuationWay.setLowSpeedPricePerMinute("14");
		companyValuationWay.setNightPricePerMile("25");
		companyValuationWay.setNightPricePerMinute("14");
		companyValuationWay.setOtherPrice("25");
		companyValuationWay.setState(0);
		companyValuationWay.setFlag(1);
		companyValuationWay.setUpdateTime(getNowTime());
		companyValuationWay.setTaxiTypeCode(0);

		companyValuationWay.setServiceTypeCode("dafbsadfsd");
		companyValuationWay.setFareRuleUrl("http://www.xxx.com/sds/dsdf");


		list.add(companyValuationWay);
		messageSender.send(list);
	}


	/**
	 *3.2.7	网约车车辆基本信息(CLJB)
	 */
	@Test
	public void testVehiclelnfo(){
		List<Vehiclelnfo> list = new ArrayList<Vehiclelnfo>();
		Vehiclelnfo vehiclelnfo=new Vehiclelnfo();
		vehiclelnfo.setSymbol(getSymbol());
		vehiclelnfo.setAddress("440100");
		vehiclelnfo.setVehicleNo("粤A12345");
		vehiclelnfo.setPlateColor("黑色");
		vehiclelnfo.setSeats("120");
		vehiclelnfo.setBrand("车辆厂牌");
		vehiclelnfo.setModel("车辆型号");
		vehiclelnfo.setVehicleType("小车");
		vehiclelnfo.setOwnerName("林志伟");
		vehiclelnfo.setVehicleColor("白色");
		vehiclelnfo.setEngineId("dhshvdad4");
		vehiclelnfo.setVin("12345678901234567");
		vehiclelnfo.setCertifyDateA("20170203");
		vehiclelnfo.setFuelType("车辆燃料类型");
		vehiclelnfo.setEngineDisplace("dhdhsdg");
		vehiclelnfo.setEnginePower("10000W");
		vehiclelnfo.setWheelBase("轴距");
		vehiclelnfo.setPhoto("");
		vehiclelnfo.setPhotoId("");
		vehiclelnfo.setCertificate("");
		vehiclelnfo.setTransAgency("广州xxx");
		vehiclelnfo.setTransArea("经营区域");
		vehiclelnfo.setTransDateStart("20150902");
		vehiclelnfo.setTransDateStop("20180809");
		vehiclelnfo.setCertifyDateB("20170502");
		vehiclelnfo.setFixState("车辆检修状态");
		vehiclelnfo.setNextFixDate("20190203");
		vehiclelnfo.setCheckState("1");
		vehiclelnfo.setCheckDate("20170805");
		vehiclelnfo.setFeePrintId("dsjdsdhsk");
		vehiclelnfo.setGpsBrand("卫星定位装置品牌");
		vehiclelnfo.setGpsModel("卫星定位装置型号");
		vehiclelnfo.setGpsImei("shgdsgjdsd1ddd");
		vehiclelnfo.setGpsInstallDate("20150906");
		vehiclelnfo.setRegisterDate("20111111");
		vehiclelnfo.setCommercialType("1");
		vehiclelnfo.setFareType("运价类型编码");
		vehiclelnfo.setState(0);
		vehiclelnfo.setFlag(1);
		vehiclelnfo.setUpdateTime(getNowTime());
		vehiclelnfo.setVehicleTec("车辆技术状况");
		vehiclelnfo.setVehicleSafe("安全");
		vehiclelnfo.setLesseeName("广州如约数据科技有限公司");
		vehiclelnfo.setLesseeCode("91440101MA59EE6A9M");

		list.add(vehiclelnfo);
		messageSender.send(list);
	}

	/**
	 * 3.2.8	网约车车辆保险信息(CLBX)
	 */
	@Test
	public void testVehicleInsuranceInfo(){
		List<VehicleInsuranceInfo> list = new ArrayList<VehicleInsuranceInfo>();
		VehicleInsuranceInfo vehicleInsuranceInfo=new VehicleInsuranceInfo();
		vehicleInsuranceInfo.setSymbol(getSymbol());
		vehicleInsuranceInfo.setVehicleNo("粤A12345");
		vehicleInsuranceInfo.setPlateColor("黑色");
		vehicleInsuranceInfo.setInsurCom("保险公司");
		vehicleInsuranceInfo.setInsurNum("dshdjsdjsjdhsjdsw");
		vehicleInsuranceInfo.setInsurType("保险类型");
		vehicleInsuranceInfo.setInsurCount("10000");
		vehicleInsuranceInfo.setInsurEff("20170205");
		vehicleInsuranceInfo.setInsurExp("20190603");
		vehicleInsuranceInfo.setFlag(1);
		vehicleInsuranceInfo.setUpdateTime(getNowTime());
		list.add(vehicleInsuranceInfo);
		messageSender.send(list);
	}

	/**
	 * 3.2.9	网约车车辆里程信息(CLLC)
	 */
	@Test
	public void testVehicleMileageLnfo(){
		List<VehicleMileageLnfo> list = new ArrayList<VehicleMileageLnfo>();
		VehicleMileageLnfo vehicleMileageLnfo=new VehicleMileageLnfo();
		vehicleMileageLnfo.setSymbol(getSymbol());
		vehicleMileageLnfo.setAddress("440110");
		vehicleMileageLnfo.setVehicleNo("粤A12314");
		vehicleMileageLnfo.setPlateColor("黑色");
		vehicleMileageLnfo.setTotalMile("12");
		vehicleMileageLnfo.setFlag(1);
		vehicleMileageLnfo.setUpdateTime(getNowTime());
		list.add(vehicleMileageLnfo);
		messageSender.send(list);
	}

	/**
	 * 3.2.10	网约车驾驶员基本信息(JSYJB)
	 */
	@Test
	public void testDriverInfo(){
		List<DriverInfo> list = new ArrayList<DriverInfo>();
		DriverInfo driverInfo=new DriverInfo();
		driverInfo.setSymbol(getSymbol());
		driverInfo.setAddress("440100");
		driverInfo.setDriverName("驾驶员姓名");
		driverInfo.setDriverIDCard("445202199603022514");
		driverInfo.setDriverPhone("18826252222");
		driverInfo.setDriverGender("1");//0:未知，1：男，2：女，9：未说明性别
		driverInfo.setDriverBirthday("19920802");
		driverInfo.setDriverNationality("中国");
		driverInfo.setDriverNation("汉族");
		driverInfo.setDriverMaritalStatus("未婚");
		driverInfo.setDriverLanguageLevel("12");
		driverInfo.setDriverEducation("本科");
		driverInfo.setDriverCensus("");
		driverInfo.setDriverAddress("");
		driverInfo.setDriverContactAddress("广东广州");
		driverInfo.setPhoto("");
		driverInfo.setPhotoId("");
		driverInfo.setLicenseId("sjfjsfkshshw7224");
		driverInfo.setLicensePhoto("");
		driverInfo.setLicensePhotoId("dfhhfjhfd");
		driverInfo.setDriverType("123456");
		driverInfo.setGetDriverLicenseDate("20150204");
		driverInfo.setDriverLicenseOn("20140206");
		driverInfo.setDriverLicenseOff("20281214");
		driverInfo.setTaxiDriver("1");
		driverInfo.setCertificateA("dasjdjsdjdw2842");
		driverInfo.setCertificateB("dshhey424dbsjh");
		driverInfo.setNetworkCarIssueOrganization("如约第十");
		driverInfo.setNetworkCarIssueDate("20150401");
		driverInfo.setGetNetworkCarProofDate("20131213");
		driverInfo.setNetworkCarProofOn("20150402");
		driverInfo.setNetworkCarProofOff("20170225");
		driverInfo.setRegisterDate("20150306");
		driverInfo.setFullTimeDriver("1");
		driverInfo.setInDriverBlacklist("1");
		driverInfo.setCommercialType("1");
		driverInfo.setContractCompany("公司");
		driverInfo.setContractOn("20160203");
		driverInfo.setContractOff("20250203");
		driverInfo.setEmergencyContact("林志伟");
		driverInfo.setEmergencyContactPhone("18826252133");
		driverInfo.setEmergencyContactAddress("");
		driverInfo.setState(0);
		driverInfo.setFlag(1);
		driverInfo.setUpdateTime(getNowTime());
		driverInfo.setRewardPunishment("奖惩计算机上的还是");

		list.add(driverInfo);
		messageSender.send(list);

	}


	/**
	 * 3.2.11	网约车驾驶员培训信息(JSYPX)
	 */
	@Test
	public void testDriverTrainingInfo(){
		List<DriverTrainingInfo> list = new ArrayList<DriverTrainingInfo>();
		DriverTrainingInfo driverTrainingInfo=new DriverTrainingInfo();
		driverTrainingInfo.setSymbol(getSymbol());
		driverTrainingInfo.setAddress("440100");
		driverTrainingInfo.setLicenseId("dgbsjadjahd244");
		driverTrainingInfo.setCourseName("培训课名称");
		driverTrainingInfo.setCourseDate("20160503");
		driverTrainingInfo.setStartTime("20160203");
		driverTrainingInfo.setStopTime("20180101");
		driverTrainingInfo.setDuration("105241");
		driverTrainingInfo.setFlag(1);
		driverTrainingInfo.setUpdateTime(getNowTime());
		driverTrainingInfo.setType("GQ");
		list.add(driverTrainingInfo);
		messageSender.send(list);
	}

	/**
	 * 3.2.12	网约车驾驶员移动终端信息
	 */
	@Test
	public void testDriverMobileTerminalInfo(){
		List<DriverMobileTerminalInfo> list = new ArrayList<DriverMobileTerminalInfo>();
		DriverMobileTerminalInfo driverMobileTerminalInfo=new DriverMobileTerminalInfo();
		driverMobileTerminalInfo.setSymbol(getSymbol());
		driverMobileTerminalInfo.setAddress("440100");
		driverMobileTerminalInfo.setLicenseId("dghsgdhsghdsh3s");
		driverMobileTerminalInfo.setDriverPhone("18826252137");
		driverMobileTerminalInfo.setNetType("1");
		driverMobileTerminalInfo.setAppVersion("v1.0.0.0");
		driverMobileTerminalInfo.setMapType("2");
		driverMobileTerminalInfo.setState(0);
		driverMobileTerminalInfo.setFlag(1);
		driverMobileTerminalInfo.setUpdateTime(getNowTime());
		driverMobileTerminalInfo.setMobileModel("18826252137");
		list.add(driverMobileTerminalInfo);
		messageSender.send(list);
	}


	/**
	 * 3.2.13	网约车驾驶员统计信息(JSYTJ)
	 */
	@Test
	public void testDriverStatistics(){
		List<DriverStatistics> list = new ArrayList<DriverStatistics>();
		DriverStatistics driverStatistics=new DriverStatistics();
		driverStatistics.setSymbol(getSymbol());
		driverStatistics.setAddress("440100");
		driverStatistics.setLicenseId("12s4dsdsjkdsd");
		driverStatistics.setCycle("20150201");
		driverStatistics.setOrderCount(10);
		driverStatistics.setTrafficViolationCount(20);
		driverStatistics.setTrafficAccidentCount(1);
		driverStatistics.setComplainedCount("20");
		driverStatistics.setViolateRecord("12");
		driverStatistics.setFlag(1);
		driverStatistics.setUpdateTime(getNowTime());

		list.add(driverStatistics);
		messageSender.send(list);
	}

	/**
	 * 3.2.14	网约车乘客基本信息(CKJB)
	 */
	@Test
	public void  testPassengerInfo(){
		List<PassengerInfo> list = new ArrayList<PassengerInfo>();
		PassengerInfo passengerInfo=new PassengerInfo();
		passengerInfo.setSymbol(getSymbol());
		passengerInfo.setRegisterDate("20170306");
		passengerInfo.setPassengerPhone("18826222137");
		passengerInfo.setPassengerName("林志伟");
		passengerInfo.setPassengerSex("1");
		passengerInfo.setState(0);
		passengerInfo.setFlag(1);
		passengerInfo.setUpdateTime(getNowTime());
		list.add(passengerInfo);
		messageSender.send(list);

	}

	/**
	 * 3.2.15	网约车乘客状态信息(CKZT)
	 */
	@Test
	public void testPassengerStatus(){
		List<PassengerStatus> list = new ArrayList<PassengerStatus>();
		PassengerStatus passengerStatus=new PassengerStatus();
		passengerStatus.setSymbol(getSymbol());
		passengerStatus.setInDate("20170105");
		passengerStatus.setOutDate("20170502");
		passengerStatus.setPassengerPhone("18826252137");
		passengerStatus.setCause("原因");
		passengerStatus.setState(0);
		passengerStatus.setFlag(1);
		passengerStatus.setUpdateTime(getNowTime());
		list.add(passengerStatus);
		messageSender.send(list);
	}


	/**
	 * 3.2.16	网约车平台投资人信息*(PTTZR)
	 */
	@Test
	public void testCompanyInvestorInfo(){
		List<CompanyInvestorInfo> list = new ArrayList<CompanyInvestorInfo>();
		CompanyInvestorInfo companyInvestorInfo=new CompanyInvestorInfo();
		companyInvestorInfo.setSymbol(getSymbol());
		companyInvestorInfo.setEpCode("91440101MA59EE6A9M");
		companyInvestorInfo.setInvesterName("林志伟");
		companyInvestorInfo.setInvIdType("JMSFZ");
		companyInvestorInfo.setInvIdCode("91440101MA59EE6A9M");
		companyInvestorInfo.setMoney("253");
		companyInvestorInfo.setFlag(1);
		companyInvestorInfo.setUpdateTime(getNowTime());

		list.add(companyInvestorInfo);
		messageSender.send(list);
	}

	/**
	 * 3.2.17	网约车平台联系人信息*(PTLXR)
	 */
	@Test
	public void testCompanyConcactInfo(){
		List<CompanyConcactInfo> list = new ArrayList<CompanyConcactInfo>();
		CompanyConcactInfo companyConcactInfo=new CompanyConcactInfo();
		companyConcactInfo.setSymbol(getSymbol());
		companyConcactInfo.setEpCode("91440101MA59EE6A9M");
		companyConcactInfo.setContact("林志伟");
		companyConcactInfo.setContactWay("18826252137");
		companyConcactInfo.setFlag(1);
		companyConcactInfo.setUpdateTime(getNowTime());
		list.add(companyConcactInfo);
		messageSender.send(list);
	}

	/**
	 * 未打通 ContractPhoto 需要base64编码
	 * 3.2.18	个体驾驶员合同信息*(GTJSYHT)
	 */
	@Test
	public void testDriverCntractInfo(){
		List<DriverCntractInfo> list = new ArrayList<DriverCntractInfo>();
		DriverCntractInfo driverCntractInfo=new DriverCntractInfo();
		driverCntractInfo.setSymbol(getSymbol());
		driverCntractInfo.setAddress("440100");
		driverCntractInfo.setDriverName("林志伟");
		driverCntractInfo.setDriTel("88888888");
		driverCntractInfo.setGender("1");
		driverCntractInfo.setBirthday("19880501");
		driverCntractInfo.setDriverNation("汉");
		driverCntractInfo.setIdType("JMSFZ");
		driverCntractInfo.setIdNo("445202199506022515");
		driverCntractInfo.setDriCertNo("1234567844454");
		driverCntractInfo.setVehicleNo("粤A12345");
		driverCntractInfo.setVin("12345678901234567");
		driverCntractInfo.setVehCertNo("dsdghshdsujd");
		driverCntractInfo.setType("ZC");
		driverCntractInfo.setContractType("HT");

		driverCntractInfo.setContractPhoto(FileUtil.PDFToBase64(new File("E:\\讯心\\如约的士\\code\\ry-taxi\\supervision\\src\\test\\java\\com\\szyciov\\supervision\\api\\pdf\\test.pdf")));
		driverCntractInfo.setContractStatus("YX");
		driverCntractInfo.setSignTime("20160905120202");
		driverCntractInfo.setValidTime("20161005");
		driverCntractInfo.setInvalidTime("20171005");
		driverCntractInfo.setFlag(1);
		driverCntractInfo.setUpdateTime(getNowTime());
		list.add(driverCntractInfo);
		messageSender.send(list);
	}

	/**
	 *3.2.19	普通企业接入信息*(PTQYJR)
	 */
	@Test
	public void testEnterpriseAccessInfo(){
		List<EnterpriseAccessInfo> list = new ArrayList<EnterpriseAccessInfo>();
		EnterpriseAccessInfo enterpriseAccessInfo=new EnterpriseAccessInfo();
		enterpriseAccessInfo.setSymbol(getSymbol());
		enterpriseAccessInfo.setAddress("440100");
		enterpriseAccessInfo.setEpName("如约数据");
		enterpriseAccessInfo.setEpCode("91440101MA59EE6A9M");
		enterpriseAccessInfo.setEpStatus("YY");
		enterpriseAccessInfo.setAccessTime("20160205");
		enterpriseAccessInfo.setFlag(1);
		enterpriseAccessInfo.setUpdateTime(getNowTime());
		list.add(enterpriseAccessInfo);
		messageSender.send(list);
	}
}
