package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

/**
 * 上传坐标参数类
 * @ClassName UploadOrdinatesParam 
 * @author Efy Shu
 * @Description 上传坐标参数类
 * @date 2017年4月13日 下午3:42:01
 */
public class UploadOrdinatesParam extends BaseParam {
	/**
	 * 司机ID
	 */
	private String id;
	/**
	 * 经度
	 */
	@SzycValid(rules={"checkLatLng"})
	private double lng;
	/**
	 * 纬度
	 */
	@SzycValid(rules={"checkLatLng"})
	private double lat;
	/**
	 * 朝向
	 */
	private double facing;
	/**
	 * 速度
	 */
	private double speed;
	
	/**
	 * 里程[可选,服务中上传]
	 */
	private double mileage;
	
	/**
	 * 订单号,侧边栏一键报警用
	 */
	@SzycValid(rules="checkOrderNO")
	private String orderno;
	
	/**
	 * 距离终点还有多少秒
	 */
	private int lefttime;
	
	/**
	 * 距离终点还有多少米
	 */
	private int leftkm;
	
	/**
	 * 报警类型,侧边栏一件报警用
	 */
	@SzycValid(rules="checkAlarmType")
	private int type;
	
	/**  
	 * 获取司机ID  
	 * @return id 司机ID  
	 */
	public String getId() {
		return id;
	}
	
	/**  
	 * 设置司机ID  
	 * @param id 司机ID  
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**  
	 * 获取经度  
	 * @return lng 经度  
	 */
	public double getLng() {
		return lng;
	}
	
	/**  
	 * 设置经度  
	 * @param lng 经度  
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	/**  
	 * 获取纬度  
	 * @return lat 纬度  
	 */
	public double getLat() {
		return lat;
	}
	
	/**  
	 * 设置纬度  
	 * @param lat 纬度  
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	/**  
	 * 获取朝向  
	 * @return facing 朝向  
	 */
	public double getFacing() {
		return facing;
	}
	
	/**  
	 * 设置朝向  
	 * @param facing 朝向  
	 */
	public void setFacing(double facing) {
		this.facing = facing;
	}
	
	/**  
	 * 获取里程[可选服务中上传]  
	 * @return mileage 里程[可选服务中上传]  
	 */
	public double getMileage() {
		return mileage;
	}
	
	/**  
	 * 设置里程[可选服务中上传]  
	 * @param mileage 里程[可选服务中上传]  
	 */
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	/**  
	 * 获取订单号侧边栏一键报警用  
	 * @return orderno 订单号侧边栏一键报警用  
	 */
	public String getOrderno() {
		return orderno;
	}
	
	/**  
	 * 设置订单号侧边栏一键报警用  
	 * @param orderno 订单号侧边栏一键报警用  
	 */
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	/**  
	 * 获取报警类型侧边栏一件报警用  
	 * @return type 报警类型侧边栏一件报警用  
	 */
	public int getType() {
		return type;
	}

	/**  
	 * 设置报警类型侧边栏一件报警用  
	 * @param type 报警类型侧边栏一件报警用  
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**  
	 * 获取速度  
	 * @return speed 速度  
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**  
	 * 设置速度  
	 * @param speed 速度  
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getLefttime() {
		return lefttime;
	}

	public void setLefttime(int lefttime) {
		this.lefttime = lefttime;
	}

	public int getLeftkm() {
		return leftkm;
	}

	public void setLeftkm(int leftkm) {
		this.leftkm = leftkm;
	}
	
}
