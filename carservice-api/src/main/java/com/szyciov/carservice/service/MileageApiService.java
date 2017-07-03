package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.MileageApiDao;
import com.szyciov.carservice.dao.OrderApiDao;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.entity.PubOrdermileagecalcLog;
import com.szyciov.enums.CalcTypeEnum;
import com.szyciov.enums.GpsSourceEnum;
import com.szyciov.enums.GpsStatusEnum;
import com.szyciov.enums.OBDInstallStateEnum;
import com.szyciov.enums.OrderTraceTableEnum;
import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.param.OrderApiParam;
import com.szyciov.util.DateUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.OBDConstans;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.latlon.LatLonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by shikang on 2017/5/18.
 */
@Service("MileageApiService")
public class MileageApiService {

    private static Logger LOGGER = Logger.getLogger(MileageApiService.class);

    private TemplateHelper templateHelper = new TemplateHelper();

    private MileageApiDao mileageApiDao;
    @Resource(name = "MileageApiDao")
    public void setMileageApiDao(MileageApiDao mileageApiDao) {
        this.mileageApiDao = mileageApiDao;
    }

    private BaiduApiService baiduApiService;
    @Resource(name = "baiduApiService")
    public void setBaiduApiService(BaiduApiService baiduApiService) {
        this.baiduApiService = baiduApiService;
    }

    private OrderApiDao orderApiDao;
    @Resource(name = "OrderApiDao")
    public void setOrderApiDao(OrderApiDao orderApiDao) {
        this.orderApiDao = orderApiDao;
    }

    /**
     * 计算里程
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    public JSONObject getOrderMileage(String orderno, String ordertype, String usetype, Boolean isLog) {
        long startLong = System.currentTimeMillis();
        PubOrdermileagecalcLog ordermileagecalcLog = ealculateMileage(orderno, ordertype, usetype, isLog);
        JSONObject json = new JSONObject();
        if(null != ordermileagecalcLog) {
            json.put("costtype", ordermileagecalcLog.getCalctype());
            if(CalcTypeEnum.OBD.code == ordermileagecalcLog.getCalctype()) {
                json.put("distance", ordermileagecalcLog.getObdmileage());
            } else if(CalcTypeEnum.OBD_GPS.code == ordermileagecalcLog.getCalctype()) {
                json.put("distance", ordermileagecalcLog.getObdgpsmileage());
            } else if(CalcTypeEnum.APP_GPS.code == ordermileagecalcLog.getCalctype()) {
                json.put("distance", ordermileagecalcLog.getAppmileage());
            } else if(CalcTypeEnum.LBSYUN.code == ordermileagecalcLog.getCalctype()) {
                json.put("distance", ordermileagecalcLog.getLbsyunmileage());
            } else {
                json.put("distance", ordermileagecalcLog.getEstimatedmileage());
            }
        } else {
            json.put("distance", 0.0);
        }
        LOGGER.info("订单(" + orderno + ")里程计算花费时间:" + (System.currentTimeMillis() - startLong));
        return json;
    }

    /**
     * 计算里程(可配置是否记录里程日志)
     * @param orderno
     * @param vehicleid
     * @param starttime
     * @param endtime
     * @param estimateMileage
     * @param isLog
     * @return
     */
    private PubOrdermileagecalcLog ealculateMileage(String orderno, String ordertype, String usetype, Boolean isLog) {
        OrderInfoDetail infoDetail = getOrderInfoDetail(orderno, ordertype, usetype);
        if(null == infoDetail) {
            return null;
        }
        String vehicleid = infoDetail.getVehicleid();
        if(StringUtils.isBlank(vehicleid)) {
            return null;
        }
        Date startDate = new Date();
        if(null != infoDetail.getStarttime()) {
            startDate = infoDetail.getStarttime();
        }
        Date endDate = new Date();
        if(null != infoDetail.getEndtime()) {
            endDate = infoDetail.getEndtime();
        }
        PubOrdermileagecalcLog mileageLog = new PubOrdermileagecalcLog();
        mileageLog.setOrderno(orderno);
        mileageLog.setEstimatedmileage(infoDetail.getEstimatedmileage() * 1000);
        if(OrderState.SERVICEDONE.state.equals(infoDetail.getStatus())) {
            if(null != infoDetail.getEndtime()) {
                //根据订单结束时间查询订单轨迹所属表
                OrderTraceTableEnum orderTraceTable = OrderTraceTableEnum.getOrderTraceTable(infoDetail.getEndtime());
                if(orderTraceTable != OrderTraceTableEnum.ORDER_GPS) {
                    mileageLog.setCalctype(infoDetail.getCosttype());
                    if(CalcTypeEnum.OBD.code == infoDetail.getCosttype()) {
                        mileageLog.setObdmileage(infoDetail.getMileage());
                    } else if(CalcTypeEnum.OBD_GPS.code == infoDetail.getCosttype()) {
                        mileageLog.setObdgpsmileage(infoDetail.getMileage());
                    } else if(CalcTypeEnum.APP_GPS.code == infoDetail.getCosttype()) {
                        mileageLog.setAppmileage(infoDetail.getMileage());
                    } else if(CalcTypeEnum.LBSYUN.code == infoDetail.getCosttype()) {
                        mileageLog.setLbsyunmileage(infoDetail.getMileage());
                    } else if(CalcTypeEnum.ESTIMATE.code == infoDetail.getCosttype()) {
                        mileageLog.setEstimatedmileage(infoDetail.getMileage());
                    }
                    return mileageLog;
                }
            }
        }

        if(null == isLog || !isLog) {
            mileageLog.setEstimatedmileage(0d);
        }


        //获取OBD安装状态
        OBDInstallStateEnum installStateEnum = getOBDInstallState(vehicleid);

        //计算OBD GPS里程
        List<PubOrdergpsdata> obdGpsList = getOrdergpsdataByOrderno(orderno, GpsSourceEnum.OBD_GPS);
        if(installStateEnum != OBDInstallStateEnum.UNINSTALL) {
            ealculateGPSMileage(obdGpsList, mileageLog, infoDetail, CalcTypeEnum.OBD_GPS, isLog);
//            mileageLog.setObdgpsmileage(ealculateGPSMileage(obdGpsList));
        }

        //计算OBD里程
        if(installStateEnum == OBDInstallStateEnum.INSTALL_OBD) {
            ealculateOBDMileage(obdGpsList, mileageLog, infoDetail, isLog);
//            mileageLog.setObdmileage(ealculateOBDMileage(obdGpsList));
        }

        //计算司机APP GPS里程
        List<PubOrdergpsdata> appGpsList = getOrdergpsdataByOrderno(orderno, GpsSourceEnum.APP_GPS);
        ealculateGPSMileage(appGpsList, mileageLog, infoDetail, CalcTypeEnum.APP_GPS, isLog);
//        mileageLog.setAppmileage(ealculateGPSMileage(appGpsList));

        //使用OBD里程
        if(installStateEnum == OBDInstallStateEnum.INSTALL_OBD && null != mileageLog.getObdmileage()
                && mileageLog.getObdmileage() > 0 && checkOBDMileage(obdGpsList, startDate, endDate, mileageLog.getObdmileage())) {
            recordOrdermileagecalcLog(mileageLog, CalcTypeEnum.OBD, isLog);
            return mileageLog;
        }

        //使用OBD GPS里程
        if(installStateEnum != OBDInstallStateEnum.UNINSTALL && null != mileageLog.getObdgpsmileage()
                && mileageLog.getObdgpsmileage() > 0 && checkEalculateGPSMileage(obdGpsList, startDate, endDate)) {
            recordOrdermileagecalcLog(mileageLog, CalcTypeEnum.OBD_GPS, isLog);
            return mileageLog;
        }

        //使用司机APP GPS里程
        if(null != mileageLog.getAppmileage() && mileageLog.getAppmileage() > 0 && checkEalculateGPSMileage(appGpsList, startDate, endDate)) {
            recordOrdermileagecalcLog(mileageLog, CalcTypeEnum.APP_GPS, isLog);
            return mileageLog;
        }

        //使用鹰眼里程
        mileageLog.setLbsyunmileage(ealculateLbsyunMileage(orderno, startDate, endDate));
        if(null != mileageLog.getLbsyunmileage() && mileageLog.getLbsyunmileage() > 0 && checkEalculateLbsMileage(mileageLog)) {
            recordOrdermileagecalcLog(mileageLog, CalcTypeEnum.LBSYUN, isLog);
            return mileageLog;
        }

        //使用预估里程
        recordOrdermileagecalcLog(mileageLog, CalcTypeEnum.ESTIMATE, isLog);
        return mileageLog;
    }

    /**
     * 获取OBD安装状态
     * @param vehicleid
     * @return
     */
    private OBDInstallStateEnum getOBDInstallState(String vehicleid) {
        PubVehicle pubVehicle = mileageApiDao.getVehicleById(vehicleid);
        if(null == pubVehicle) {
            return null;
        }

        //查询车辆安装设备的状态
        String url = SystemConfig.getSystemProperty("vmsApi") + "?apikey="
                + SystemConfig.getSystemProperty("vmsApikey")
                + "&plate=" + pubVehicle.getPlateNo();
        JSONObject ret = templateHelper.dealRequestWithFullUrlToken(url, HttpMethod.GET, null, null, JSONObject.class);
        if(null != ret && !ret.isEmpty() && ret.containsKey("status") && ret.getInt("status") == OBDConstans.VMS_API_OK) {
            if(ret.containsKey("vhecEqpList") && null != ret.get("vhecEqpList")) {
                JSONArray vhecEqpList = ret.getJSONArray("vhecEqpList");
                if(vhecEqpList.isEmpty()) {
                    return OBDInstallStateEnum.UNINSTALL;
                } else {
                    boolean flag = false;
                    for (int m = 0;m < vhecEqpList.size();m++) {
                        JSONObject vhecEqp = vhecEqpList.getJSONObject(m);
                        if(vhecEqp.isEmpty()) {
                            continue;
                        }
                        Object categoryName = vhecEqp.get("categoryName");
                        if(OBDConstans.OBD_CATEGORY_NAME.equals(categoryName)) {
                            return OBDInstallStateEnum.INSTALL_OBD;
                        }
                        if(OBDConstans.GPS_CATEGORY_NAME.equals(categoryName)) {
                            flag = true;
                        }
                    }
                    if(flag) {
                        return OBDInstallStateEnum.INSTALL_GPS;
                    }
                }
            }
        }
        return OBDInstallStateEnum.UNINSTALL;
    }

    /**
     * 计算GPS里程
     * @param gpsList
     * @return
     */
    private void ealculateGPSMileage(List<PubOrdergpsdata> gpsList, PubOrdermileagecalcLog ordermileagecalcLog, OrderInfoDetail infoDetail, CalcTypeEnum calcType, Boolean isLog) {
        if(null == gpsList || gpsList.isEmpty()) {
            return;
        }

        double retMileage = 0;
        PubOrdergpsdata startOrdergpsdata = null; //第一个坐标点
        PubOrdergpsdata pubOrdergpsdata = null; //用于与下一个坐标点计算里程
        for (int m = 0;m < gpsList.size();m++) {
            PubOrdergpsdata ordergpsdata = gpsList.get(m); //当前计算里程的坐标点
            if(null == ordergpsdata.getGpsstatus() || GpsStatusEnum.VALID.code != ordergpsdata.getGpsstatus()) {
                continue;
            }
            if(null == pubOrdergpsdata) { //如果为空，表示当前为第一个点，默认第一个点为有效点
                pubOrdergpsdata = ordergpsdata;
                startOrdergpsdata = ordergpsdata;
                continue;
            }

            //计算两点之间的距离(米)和所用时间(毫秒)
            double pointMileage = LatLonUtil.getDistance(pubOrdergpsdata.getLng(), pubOrdergpsdata.getLat(), ordergpsdata.getLng(), ordergpsdata.getLat());
            long pointTime = ordergpsdata.getGpstime().getTime() - pubOrdergpsdata.getGpstime().getTime();
            if(pointTime == 0) {
                LOGGER.info("订单(" + ordergpsdata.getOrderno() + ")的坐标点(" + ordergpsdata.getId() + ")无效");
                continue;
            }
            //校验两点间的平均速度是否符合:(pointMileage/1000)/(pointTime/1000/60/60)
            double averageSpeed = pointMileage / pointTime * 60 * 60;
            if(averageSpeed < OBDConstans.POINT_AVERAGE_SPEED_CRITICAL) {
                pubOrdergpsdata = ordergpsdata;
                retMileage += pointMileage;
            } else {
                LOGGER.info("订单(" + ordergpsdata.getOrderno() + ")的坐标点(" + ordergpsdata.getId() + ")无效");
            }
        }

        //计算优化后的里程(只有订单结束才需要做里程优化)
        double optimizeMileage = 0;
        if(null != isLog && isLog) {
            if(null != infoDetail.getStartlng() && null != infoDetail.getStartllat()) {
                double startMileage = LatLonUtil.getDistance(infoDetail.getStartlng(), infoDetail.getStartllat(), startOrdergpsdata.getLng(), startOrdergpsdata.getLat());
                optimizeMileage += startMileage;
            }
            if(null != infoDetail.getEndlng() && null != infoDetail.getEndllat()) {
                double endMileage = LatLonUtil.getDistance(infoDetail.getEndlng(), infoDetail.getEndllat(), pubOrdergpsdata.getLng(), pubOrdergpsdata.getLat());
                optimizeMileage += endMileage;
            }
        }

        if(CalcTypeEnum.OBD_GPS == calcType) {
            ordermileagecalcLog.setObdgpsmileage(retMileage);
            ordermileagecalcLog.setOptimizeobdgpsmileage(retMileage + optimizeMileage);
        } else if(CalcTypeEnum.APP_GPS == calcType) {
            ordermileagecalcLog.setAppmileage(retMileage);
            ordermileagecalcLog.setOptimizeappgpsmileage(retMileage + optimizeMileage);
        }
    }

    /**
     * 计算OBD里程
     * @param start
     * @param end
     * @return
     */
    private void ealculateOBDMileage(List<PubOrdergpsdata> gpsList, PubOrdermileagecalcLog ordermileagecalcLog, OrderInfoDetail infoDetail, Boolean isLog) {
        if(null == gpsList || gpsList.isEmpty() || gpsList.size() < 2) {
            return;
        }
        PubOrdergpsdata startGps = null; //起点
        int startIndex = 0;
        for (;startIndex < gpsList.size();startIndex++) {
            PubOrdergpsdata ordergpsdata = gpsList.get(startIndex);
            if(null == ordergpsdata.getMileage() || ordergpsdata.getMileage() == 0) {
                continue;
            }
            startGps = ordergpsdata;
            break;
        }
        PubOrdergpsdata endGps = null; //终点
        int endIndex = gpsList.size() - 1;
        for (;endIndex >= 0;endIndex--) {
            PubOrdergpsdata ordergpsdata = gpsList.get(endIndex);
            if(null == ordergpsdata.getMileage() || ordergpsdata.getMileage() == 0) {
                continue;
            }
            endGps = ordergpsdata;
            break;
        }
        //如果起终点的总里程数为空，或者起点总里程大于终点总里程，表示该坐标点集合为无效数据
        if(null == startGps || null == endGps || null == startGps.getMileage()
                || null == endGps.getMileage() || (startGps.getMileage() > endGps.getMileage())) {
            return;
        }
        ordermileagecalcLog.setObdmileage(endGps.getMileage() - startGps.getMileage());

        //计算优化后的里程(只有订单结束才需要对里程计算做优化)
        double startMileage = 0;
        double endMileage = 0;
        if(null != isLog && isLog) {
            //计算订单开始时间到第一个坐标点的距离
            PubOrdergpsdata secGps = null;
            startIndex++;
            for (;startIndex < gpsList.size();startIndex++) {
                PubOrdergpsdata ordergpsdata = gpsList.get(startIndex);
                if(null == ordergpsdata.getMileage() || ordergpsdata.getMileage() == 0) {
                    continue;
                }
                secGps = ordergpsdata;
                break;
            }
            if(null != secGps) {
                double startSecMileage = secGps.getMileage() - startGps.getMileage(); //第一个点和第二个点的距离(米)
                long startCountTimes = secGps.getGpstime().getTime() - startGps.getGpstime().getTime(); //第一个点和第二个点的间隔时间
                long startGapTimes = startGps.getGpstime().getTime() - infoDetail.getStarttime().getTime(); //第一个点和订单开始时间间隔
                if(startCountTimes != startGapTimes) {
                    startMileage = startSecMileage / startCountTimes * startGapTimes;
                }
            }

            //计算订单结束时间到最后一个坐标点的距离
            PubOrdergpsdata lastSecGps = null;
            endIndex--;
            for (;endIndex >= 0;endIndex--) {
                PubOrdergpsdata ordergpsdata = gpsList.get(endIndex);
                if(null == ordergpsdata.getMileage() || ordergpsdata.getMileage() == 0) {
                    continue;
                }
                lastSecGps = ordergpsdata;
                break;
            }
            if(null != lastSecGps) {
                Date endDate = infoDetail.getEndtime();
                if(null == endDate) {
                    endDate = new Date();
                }
                double lastSecMileage = endGps.getMileage() - lastSecGps.getMileage(); //最后一个点和倒数第二个点的距离(米)
                long endCountTimes = endGps.getGpstime().getTime() - lastSecGps.getGpstime().getTime(); //最后一个点和倒数第二个点的时间间隔
                long endGapTimes = endDate.getTime() - endGps.getGpstime().getTime(); //最后一个点和订单结束时间间隔
                if(endCountTimes != endGapTimes) {
                    endMileage = lastSecMileage / endCountTimes * endGapTimes;
                }
            }
        }
        ordermileagecalcLog.setOptimizeobdmileage(ordermileagecalcLog.getObdmileage() + startMileage + endMileage);
    }

    /**
     * 计算鹰眼里程
     * @param orderno
     * @param starttime
     * @param endtime
     * @return
     */
    private Double ealculateLbsyunMileage(String orderno, Date startDate, Date endDate) {
        JSONObject mileageJson = baiduApiService.getOrderMileage(orderno, DateUtil.format(startDate), DateUtil.format(endDate), null);
        if(null == mileageJson || mileageJson.isEmpty() || !mileageJson.containsKey("distance")) {
            return null;
        }
        return Double.valueOf(mileageJson.get("distance").toString());
    }

    /**
     * 评估OBD里程是否满足计算里程条件
     * @param start
     * @param end
     * @param starttime
     * @param endtime
     * @return
     */
    private boolean checkOBDMileage(List<PubOrdergpsdata> gpsList, Date startDate, Date endDate, Double obdMileage) {
        if(null == gpsList || gpsList.isEmpty() || gpsList.size() < 2) {
            return false;
        }

        PubOrdergpsdata startGps = gpsList.get(0); //起点
        PubOrdergpsdata endGps = gpsList.get(gpsList.size() - 1); //终点
        //GPS起点与订单开始时间的时间差必须小于OBDConstans.ORDER_GPS_TIME_INTERVAL_CRITICAL
        if(Math.abs(startGps.getGpstime().getTime() - startDate.getTime()) >= (OBDConstans.ORDER_GPS_TIME_INTERVAL_CRITICAL * 1000)) {
            return false;
        }
        //GPS终点与订单结束时间的时间差必须小于OBDConstans.ORDER_GPS_TIME_INTERVAL_CRITICAL
        if(Math.abs(endDate.getTime() - endGps.getGpstime().getTime()) >= (OBDConstans.ORDER_GPS_TIME_INTERVAL_CRITICAL * 1000)) {
            return false;
        }

        long time = endGps.getGpstime().getTime() - startGps.getGpstime().getTime(); //耗时
        //如果两点处于同一时间，里程数为0表示有效数据，否则表示无效数据
        if(time == 0) {
            return false;
        }
        double averageSpeed = obdMileage / time * 60 * 60; //平均速度:(mileage/1000)/(time/1000/60/60)
        //OBD里程平均速度必须小于OBDConstans.START_END_AVERAGE_SPEED_CRITICAL的值
        if(averageSpeed < OBDConstans.START_END_AVERAGE_SPEED_CRITICAL) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 评估GPS是否满足计算里程条件
     * @param gpsList
     * @param starttime
     * @param endtime
     * @return
     */
    private boolean checkEalculateGPSMileage(List<PubOrdergpsdata> gpsList, Date startDate, Date endDate) {
        if(null == gpsList || gpsList.isEmpty()) {
            return false;
        }

        //预计的GPS点数量
        double estimateGpsCount = (endDate.getTime() - startDate.getTime())/1000.0/OBDConstans.GPS_UPLOAD_TIME_INTERVAL;
        if(estimateGpsCount == 0) {
            return false;
        }
        //GPS的实际数量/预计数量>OBD里程平均速度必须大于OBDConstans.GPS_ACTUAL_ESTIMATE_RATIO_CRITICAL
        if(gpsList.size() / estimateGpsCount <= OBDConstans.GPS_ACTUAL_ESTIMATE_RATIO_CRITICAL) {
            return false;
        }

        //所有GPS点之间的时间最大间隔小于OBDConstans.GPS_POINT_TIME_INTERVAL_CRITICAL的值
        long starttime = startDate.getTime();
        for (PubOrdergpsdata ordergpsdata : gpsList) {
            if((ordergpsdata.getGpstime().getTime() - starttime) < OBDConstans.GPS_POINT_TIME_INTERVAL_CRITICAL * 1000) {
                starttime = ordergpsdata.getGpstime().getTime();
            } else {
                return false;
            }
        }
        if((endDate.getTime() - starttime) < OBDConstans.GPS_POINT_TIME_INTERVAL_CRITICAL * 1000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 评估鹰眼是否满足计算里程条件
     * @param ordermileagecalcLog
     * @return
     */
    private boolean checkEalculateLbsMileage(PubOrdermileagecalcLog ordermileagecalcLog) {
        //判断APP里程是否正确
        if(null == ordermileagecalcLog.getAppmileage() || ordermileagecalcLog.getAppmileage() == 0) {
            return true;
        }

        //计算鹰眼里程和APP里程的比例(必须在OBDConstans.MIN_APP_LBS_MULTIPLE和OBDConstans.MAX_APP_LBS_MULTIPLE之间)
        double proportion = ordermileagecalcLog.getLbsyunmileage() / ordermileagecalcLog.getAppmileage();
        if(proportion <= OBDConstans.MIN_APP_LBS_MULTIPLE || proportion >= OBDConstans.MAX_APP_LBS_MULTIPLE) {
            return false;
        }
        return true;
    }

    /**
     * 记录里程计算日志
     * @param pubOrdermileagecalcLog
     * @param ealculateTypeEnum
     * @param isLog
     */
    private void recordOrdermileagecalcLog(PubOrdermileagecalcLog ordermileagecalcLog, CalcTypeEnum calcType, Boolean isLog) {
        LOGGER.info("订单(" + ordermileagecalcLog.getOrderno() + ")保存里程计算日志(" + JSONObject.fromObject(ordermileagecalcLog) + ")，是否保存:" + isLog);
        ordermileagecalcLog.setCalctype(calcType.code);
        if(null != isLog && isLog) {
            ordermileagecalcLog.setId(GUIDGenerator.newGUID());
            mileageApiDao.insertPubOrdermileagecalcLog(ordermileagecalcLog);
        }
    }

    /**
     * 获取订单GPS现行数据
     * @param orderno
     * @param gpsSourceEnum
     * @return
     */
    private List<PubOrdergpsdata> getOrdergpsdataByOrderno(String orderno, GpsSourceEnum gpsSource) {
        PubOrdergpsdata ordergpsdata = new PubOrdergpsdata();
        ordergpsdata.setOrderno(orderno);
        ordergpsdata.setGpssource(gpsSource.code);
        return mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
    }

    /**
     * 上报GPS
     * @param object
     */
    public void uploadGps(PubOrdergpsdata object) {
        long startLong = System.currentTimeMillis();

        //校验GPS有效性
        if(!checkGpsValid(object)) {
            LOGGER.info("坐标校验失败....");
            LOGGER.info("校验数据:" + JSONObject.fromObject(object));
            return;
        }

        //查询司机当前正在服务的订单号
        PubDriver pubDriver = new PubDriver();
        String orderno = getServiceOrdernoByDriver(object, pubDriver);
        if(StringUtils.isNotBlank(orderno)) {
            object.setId(GUIDGenerator.newGUID());
            object.setOrderno(orderno);
            mileageApiDao.insertPubOrdergpsdata(object);
        } else {
            LOGGER.info("司机(" + object.getDriverid() + ")没有正在服务的订单");
        }

        //更新司机gps信息
        updateDriverGps(object, pubDriver);

        LOGGER.info("订单(" + orderno + ")坐标点(" + (object.getGpssource() == 1?"OBDGPS":"APPGPS") + ")上传花费时间:" + (System.currentTimeMillis() - startLong));
    }

    /**
     * 校验GPS有效性
     * @param object
     * @return
     */
    private boolean checkGpsValid(PubOrdergpsdata object) {
        //入参不能为空
        if(null == object) {
            return false;
        }
        object.setGpsstatus(GpsStatusEnum.VALID.code);
        //校验GPS时间有效性(时间与服务器时间间隔前后小于OBDConstans.GPS_valid_TIME_INTERVAL)
        if(null == object.getGpstime()) {
            return false;
        }
        long timeInterval = Math.abs(System.currentTimeMillis() - object.getGpstime().getTime());
        if(timeInterval >= OBDConstans.GPS_valid_TIME_INTERVAL * 1000) {
            return false;
        }
        //GPS位置在中国范围内
        boolean flag = true;
        if(null == object.getLng() || object.getLng() == 0) {
            flag = false;
        }
        if(null == object.getLat() || object.getLat() == 0) {
            flag = false;
        }

        //OBDGPS做纠偏处理
        if(flag && GpsSourceEnum.OBD_GPS.code == object.getGpssource()) {
            PubOrdergpsdata ordergpsdata = mileageApiDao.getBaiduOffset(object);
            if(null != ordergpsdata) {
                object.setLng(ordergpsdata.getLng()/1000000d + object.getLng());
                object.setLat(ordergpsdata.getLat()/1000000d + object.getLat());
            }
        }
        if(!flag) {
            if(GpsSourceEnum.APP_GPS.code == object.getGpssource()) {
                return false;
            } else if(GpsSourceEnum.OBD_GPS.code == object.getGpssource()) {
                object.setGpsstatus(GpsStatusEnum.INVALID.code);
            }
        } else {
            if(object.getLng() < OBDConstans.CHINA_START_LNG || object.getLng() > OBDConstans.CHINA_END_LNG) {
                return false;
            }
            if(object.getLat() < OBDConstans.CHINA_START_LAT || object.getLat() > OBDConstans.CHINA_END_LAT) {
                return false;
            }
        }

        //获取司机信息
        if(StringUtils.isNotBlank(object.getDriverid())) {
            return true;
        } else {
            PubVehicle pubVehicle = mileageApiDao.getVehicleById(object.getVehicleid());
            if(null == pubVehicle || StringUtils.isBlank(pubVehicle.getDriverId())) {
                return false;
            } else {
                object.setDriverid(pubVehicle.getDriverId());
            }
        }
        return true;
    }

    /**
     * 更新司机gps
     * @param object
     */
    private void updateDriverGps(PubOrdergpsdata object, PubDriver pubDriver) {
        if(GpsStatusEnum.VALID.code != object.getGpsstatus()) {
            return;
        }
        //更新司机gps信息
        if(null == pubDriver.getGpstime() || pubDriver.getGpstime().getTime() < object.getGpstime().getTime()) {
            pubDriver.setId(object.getDriverid());
            pubDriver.setGpstime(object.getGpstime());
            pubDriver.setGpsspeed(object.getGpsspeed());
            pubDriver.setGpsdirection(object.getGpsdirection());
            pubDriver.setLng(object.getLng());
            pubDriver.setLat(object.getLat());
            pubDriver.setGpssource(object.getGpssource());
            mileageApiDao.updateDriverGps(pubDriver);
        }

        //添加司机gps信息
        if(GpsSourceEnum.APP_GPS.code == object.getGpssource()) {
            PubDrivertrack pubDrivertrack = new PubDrivertrack();
            pubDrivertrack.setId(GUIDGenerator.newGUID());
            pubDrivertrack.setDriverid(object.getDriverid());
            pubDrivertrack.setGpsspeed(object.getGpsspeed());
            pubDrivertrack.setGpsdirection(object.getGpsdirection());
            pubDrivertrack.setLng(object.getLng());
            pubDrivertrack.setLat(object.getLat());
            mileageApiDao.insertPubDrivertrack(pubDrivertrack);
        }
    }

    /**
     * 查询司机当前正在服务的订单号
     * @param driverid
     * @return
     */
    private String getServiceOrdernoByDriver(PubOrdergpsdata object, PubDriver pubDriver) {
        //查询司机详情
        PubDriver driver = mileageApiDao.getPubDriverById(object.getDriverid());
        if(null == driver) {
            return null;
        }
        pubDriver.setGpstime(driver.getGpstime());

        long gpsDate = object.getGpstime().getTime();

        //从redis中获取订单信息
        String key = "OBD_MILGES_CURRENT_ORDER_" + object.getDriverid() + "_*";
        Set<String> keys = JedisUtil.getKeys(key);
        for(String k : keys) {
            String value = JedisUtil.getString(k);
            JSONObject json = JSONObject.fromObject(value);
            long startDate = StringUtil.parseDate(json.getString("starttime"), StringUtil.DATE_TIME_FORMAT).getTime();
            long endDate = new Date().getTime();
            String orderstatus = json.getString("orderstatus");
            if(OrderState.SERVICEDONE.state.equals(orderstatus) || OrderState.WAITMONEY.equals(orderstatus)) {
                endDate = StringUtil.parseDate(json.getString("endtime"), StringUtil.DATE_TIME_FORMAT).getTime();
            }
            //判断定位时间是否在订单服务范围内
            if(gpsDate <= endDate && gpsDate >= startDate) {
                return json.getString("orderno");
            }
        }
        return null;
    }

    /**
     * 查询订单轨迹
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    public JSONObject getTraceData(String orderno, String ordertype, String usetype, boolean fullReturn) {
        //查询订单详情
        OrderInfoDetail infoDetail = getOrderInfoDetail(orderno, ordertype, usetype);
        //判断订单状态
        if(!OrderState.INSERVICE.state.equals(infoDetail.getStatus()) && !OrderState.SERVICEDONE.state.equals(infoDetail.getStatus())
                && !OrderState.WAITMONEY.state.equals(infoDetail.getStatus())) {
            return null;
        }
        //根据订单结束时间查询订单轨迹所属表
        OrderTraceTableEnum orderTraceTable = OrderTraceTableEnum.getOrderTraceTable(infoDetail.getEndtime());
        //查询订单轨迹点来源
        GpsSourceEnum gpsSource = GpsSourceEnum.getGpsSource(infoDetail.getCosttype());
        double mileage = infoDetail.getMileage(); //订单里程
        //订单未完成时，查询订单实时里程数
        if(null == gpsSource) {
            PubOrdermileagecalcLog ordermileagecalcLog = ealculateMileage(infoDetail.getOrderno(), infoDetail.getType(), infoDetail.getUsetype(), false);
            if(null == ordermileagecalcLog) {
                return null;
            }
            if(CalcTypeEnum.OBD.code == ordermileagecalcLog.getCalctype()) {
                gpsSource = GpsSourceEnum.OBD_GPS;
                mileage = ordermileagecalcLog.getObdmileage();
            } else if(CalcTypeEnum.OBD_GPS.code == ordermileagecalcLog.getCalctype()) {
                gpsSource = GpsSourceEnum.OBD_GPS;
                mileage = ordermileagecalcLog.getObdgpsmileage();
            } else if(CalcTypeEnum.APP_GPS.code == ordermileagecalcLog.getCalctype()) {
                gpsSource = GpsSourceEnum.APP_GPS;
                mileage = ordermileagecalcLog.getAppmileage();
            } else if(CalcTypeEnum.LBSYUN.code == ordermileagecalcLog.getCalctype()) {
                gpsSource = GpsSourceEnum.APP_GPS;
                mileage = ordermileagecalcLog.getLbsyunmileage();
            } else {
                gpsSource = GpsSourceEnum.APP_GPS;
                mileage = ordermileagecalcLog.getEstimatedmileage();
            }
            infoDetail.setCosttype(ordermileagecalcLog.getCalctype());
        }
        infoDetail.setMileage(mileage);
        List<PubOrdergpsdata> ordergpsdataList = null;
        PubOrdergpsdata ordergpsdata = new PubOrdergpsdata();
        ordergpsdata.setOrderno(infoDetail.getOrderno());
        ordergpsdata.setGpsstatus(GpsStatusEnum.VALID.code);
        ordergpsdata.setGpssource(gpsSource.code);
        if(orderTraceTable == OrderTraceTableEnum.ORDER_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
        } else if(orderTraceTable == OrderTraceTableEnum.HISTORY_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata);
        } else {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
            ordergpsdataList.addAll(mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata));
        }
        //拼装轨迹数据
        return assembleTrace(ordergpsdataList, infoDetail, fullReturn);
    }

    /**
     * 拼装轨迹数据
     * @param ordergpsdataList
     * @param infoDetail
     * @return
     */
    private JSONObject assembleTrace(List<PubOrdergpsdata> ordergpsdataList, OrderInfoDetail infoDetail, boolean fullReturn) {
        if(null == ordergpsdataList || ordergpsdataList.isEmpty() || ordergpsdataList.size() == 1) {
            return assembleVirtualTrace(infoDetail, fullReturn);
        }

        JSONObject json = new JSONObject();

        //开始坐标
        JSONObject startPoint = new JSONObject();
        PubOrdergpsdata startData = ordergpsdataList.get(0);
        startPoint.put("longitude", startData.getLng());
        startPoint.put("latitude", startData.getLat());
        startPoint.put("coord_type", 3);
        startPoint.put("loc_time", startData.getGpstime().getTime()/1000);

        //结束坐标
        JSONObject endPoint = new JSONObject();
        PubOrdergpsdata endData = ordergpsdataList.get(ordergpsdataList.size() - 1);
        endPoint.put("longitude", endData.getLng());
        endPoint.put("latitude", endData.getLat());
        endPoint.put("coord_type", 3);
        endPoint.put("loc_time", endData.getGpstime().getTime()/1000);

        if(fullReturn) {
            JSONArray points = new JSONArray();
            for (PubOrdergpsdata ordergpsdata : ordergpsdataList) {
                //位置点
                JSONArray location = new JSONArray();
                location.add(ordergpsdata.getLng());
                location.add(ordergpsdata.getLat());

                //轨迹数据点
                JSONObject point = new JSONObject();
                point.put("loc_time", ordergpsdata.getGpstime().getTime()/1000);
                point.put("location", location);
                point.put("create_time", StringUtil.formatDate(ordergpsdata.getGpstime(), StringUtil.DATE_TIME_FORMAT));
                point.put("direction", ordergpsdata.getGpsdirection());
                point.put("height", 0);
                point.put("radius", 0);
                point.put("speed", ordergpsdata.getGpsspeed());

                points.add(point);
            }
            json.put("points", points);
        }

        //数据拼装
        json.put("status", 0);
        json.put("size", ordergpsdataList.size());
        json.put("entity_name", infoDetail.getOrderno());
        json.put("distance", infoDetail.getMileage());
        json.put("toll_distance", 0);
        json.put("start_point", startPoint);
        json.put("end_point", endPoint);
        json.put("lowspeedcost", 0);
        json.put("costtype", infoDetail.getCosttype());
        return json;
    }

    /**
     * 根据下单上车地点拼装坐标轨迹
     * @param infoDetail
     * @return
     */
    private JSONObject assembleVirtualTrace(OrderInfoDetail infoDetail, boolean fullReturn) {
        JSONObject json = new JSONObject();

        Date starttime = infoDetail.getStarttime();
        long loc_time = starttime.getTime()/1000;
        String create_time = StringUtil.formatDate(starttime, "yyyy-MM-dd HH:mm:ss");

        //开始坐标
        JSONObject startPoint = new JSONObject();
        startPoint.put("longitude", infoDetail.getOnlng());
        startPoint.put("latitude", infoDetail.getOnlat());
        startPoint.put("coord_type", 3);
        startPoint.put("loc_time", loc_time);

        //位置点
        JSONArray location = new JSONArray();
        location.add(infoDetail.getOnlng());
        location.add(infoDetail.getOnlat());

        if(fullReturn) {
            //轨迹数据点
            JSONObject point = new JSONObject();
            point.put("loc_time", loc_time);
            point.put("location", location);
            point.put("create_time", create_time);
            point.put("direction", 0);
            point.put("height", 0);
            point.put("radius", 0);
            point.put("speed", 0);

            JSONArray points = new JSONArray();
            points.add(point);
            json.put("points", points);
        }

        //轨迹数据
        json.put("status", 0);
        json.put("size", 1);
        json.put("entity_name", infoDetail.getOrderno());
        json.put("distance", 0);
        json.put("toll_distance", 0);
        json.put("start_point", startPoint);
        json.put("isInterface", 1);
        json.put("lowspeedcost", 0);
        json.put("costtype", infoDetail.getCosttype());
        return json;
    }

    /**
     * 查询订单详情
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    private OrderInfoDetail getOrderInfoDetail(String orderno, String ordertype, String usetype) {
        OrderVarietyEnum orderVariety = OrderVarietyEnum.getOrderVariety(usetype, ordertype);
        OrderApiParam orderApiParam = new OrderApiParam();
        orderApiParam.setOrderno(orderno);
        orderApiParam.setOrderprop(orderVariety.icode);
        return orderApiDao.getOrderInfoById(orderApiParam);
    }

    /**
     * 迁移订单GPS数据到历史表
     */
    public void migrationPubOrdergpsdata() {
        //查询需要迁移的数据
        List<PubOrdergpsdata> dataList = mileageApiDao.getPubOrdergpsdataByMigration();
        //删除原数据
        if(null != dataList && !dataList.isEmpty()) {
            for (PubOrdergpsdata obj : dataList) {
                String id = obj.getId();
                //将数据迁移到历史表
                obj.setId(GUIDGenerator.newGUID());
                mileageApiDao.insertPubOrdergpsdataHistory(obj);
                //删除原表数据
                mileageApiDao.deletePubOrdergpsdataById(id);
            }
        }
    }

    /**
     * 查询OBDGPS和APPGPS坐标点
     * @param orderno
     * @param ordertype
     * @param usetype
     * @return
     */
    public JSONObject getGpsTraceData(String orderno, String ordertype, String usetype) {
        long startLong = System.currentTimeMillis();
        JSONObject json = new JSONObject();

        OrderInfoDetail infoDetail = getOrderInfoDetail(orderno, ordertype, usetype);
        OrderTraceTableEnum orderTraceTable = OrderTraceTableEnum.getOrderTraceTable(infoDetail.getEndtime());
        List<PubOrdergpsdata> ordergpsdataList = null;

        PubOrdergpsdata ordergpsdata = new PubOrdergpsdata();
        ordergpsdata.setOrderno(infoDetail.getOrderno());
        ordergpsdata.setGpssource(GpsSourceEnum.OBD_GPS.code);
        ordergpsdata.setGpsstatus(GpsStatusEnum.VALID.code);
        //OBD轨迹
        if(orderTraceTable == OrderTraceTableEnum.ORDER_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
        } else if(orderTraceTable == OrderTraceTableEnum.HISTORY_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata);
        } else {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
            ordergpsdataList.addAll(mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata));
        }
        json.put("obd", assembleTrace(ordergpsdataList, infoDetail, true));

        //APP轨迹
        ordergpsdata.setGpssource(GpsSourceEnum.APP_GPS.code);
        if(orderTraceTable == OrderTraceTableEnum.ORDER_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
        } else if(orderTraceTable == OrderTraceTableEnum.HISTORY_GPS) {
            ordergpsdataList = mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata);
        } else {
            ordergpsdataList = mileageApiDao.getOrdergpsdataByOrderno(ordergpsdata);
            ordergpsdataList.addAll(mileageApiDao.getOrdergpsdataHistoryByOrderno(ordergpsdata));
        }
        json.put("app", assembleTrace(ordergpsdataList, infoDetail, true));

        LOGGER.info("订单(" + orderno + ")轨迹查询花费时间:" + (System.currentTimeMillis() - startLong));
        return json;
    }

}
