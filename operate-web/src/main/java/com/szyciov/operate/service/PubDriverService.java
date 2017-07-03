package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.entity.Retcode;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.param.PubDriverQueryParam;
import com.szyciov.op.param.PubDriverSelectParam;
import com.szyciov.param.PhoneAuthenticationParam;
import com.szyciov.util.RequestUtils;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PubDriverService")
public class PubDriverService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public JSONObject listPubDriverBySelect(PubDriverSelectParam param, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + "/PubDriver/listPubDriverBySelect",
				HttpMethod.POST, userToken, param, JSONObject.class);
	}


	/**
	 * 导出Excel
	 * @param param
	 * @param token
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Excel exportExcel(PubDriverQueryParam param,String token) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

		//显示的表头及对应的实体类属性名
		List<String> cloumnList = new ArrayList<>(11);
		cloumnList.add("jobnum");
		cloumnList.add("name");
		cloumnList.add("phone");
		cloumnList.add("sex");
		cloumnList.add("vehicletype");
		cloumnList.add("idcardnum");
		cloumnList.add("driversTypeName");
		cloumnList.add("driveryears");
		cloumnList.add("cityName");
		cloumnList.add("boundstate");
		cloumnList.add("jobstatus");
		List<String> titleList = new ArrayList<>(11);
		titleList.add("资格证号");
		titleList.add("姓名");
		titleList.add("手机号");
		titleList.add("性别");
		titleList.add("司机类型");
		titleList.add("身份证号");
		titleList.add("驾驶类型");
		titleList.add("驾驶工龄");
		titleList.add("登记城市");
		titleList.add("绑定状态");
		titleList.add("在职状态");
		List<String> colName = new ArrayList<String>();
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();

		JSONArray jsonArray =templateHelper.dealRequestWithToken("/PubDriver/ExportData",
				HttpMethod.POST, token, param, JSONArray.class);
		//遍历要显示的列
		for (int j=0;j<cloumnList.size();j++) {
			String key = cloumnList.get(j);
			String value = titleList.get(j);
			//获取值对象list
			List<Object> valueList = colData.get(value);
			//如果list为空
			if (valueList == null) {
				//创建结果list 设置值为当前查询结果集
				valueList = new ArrayList<>(jsonArray.size());
			}
			//循环结果对象
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Object o = jsonObject.get(key);
				if (o != null) {
					valueList.add(o);
				} else {
					valueList.add("");
				}
			}
			//设置列值
			colData.put(value, valueList);
			//新增列标题列
			colName.add(value);
		}

		Excel excel = new Excel();
		excel.setColName(colName);
		excel.setColData(colData);
		return excel;
	}

	/**
	 * 手机实名制认证
	 * @param request
	 * @param pubDriver
	 * @return
	 */
	public Map<String,String> phoneAuthentication(HttpServletRequest request, PubDriver pubDriver){

		PhoneAuthenticationParam param = new PhoneAuthenticationParam();

		param.setCardNo(pubDriver.getIdCardNum());

		param.setIpAddr(RequestUtils.getClientIP(request));

		param.setMobile(pubDriver.getPhone());

		param.setRealName(pubDriver.getName());

		JSONObject jsonObject =  templateHelper.dealRequestWithTokenCarserviceApiUrl("/XunChengApi/realNameAuthentication",
				HttpMethod.POST, null, param,
				JSONObject.class);

		Map<String,String> ret = new HashMap<>();
		if(Retcode.OK.code==Integer.parseInt(jsonObject.get("status").toString())){
			boolean phoneAuthenticationed =  jsonObject.getBoolean("data");
			if(!phoneAuthenticationed){
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "该号码实名验证未通过，请更换手机号码");
			}else{
				return null;
			}
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", jsonObject.getString("message"));
		}
		return ret;
	}


}
