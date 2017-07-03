package com.szyciov.driver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class TempTest {
	public static void main(String[] args) {
		JSONObject json = JSONObject.fromObject(
				"{\"arrivaltime\":null,\"companyid\":\"b2d72d37-e945-4465-98bb-dbf221be43d8\",\"completetime\":null,\"createtime\":{\"date\":6,\"day\":2,\"hours\":18,\"minutes\":27,\"month\":8,\"seconds\":17,\"time\":1473157637214,\"timezoneOffset\":-480,\"year\":116},\"departuretime\":null,\"driverid\":\"\",\"endtime\":null,\"estimatedcost\":45,\"estimatedmileage\":26,\"estimatedtime\":20,\"falltime\":null,\"fltno\":\"\",\"mileage\":0,\"offCity\":\"武汉市\",\"offaddress\":\"武汉市洪山区软件园中路-公交车站\",\"offaddrlat\":0,\"offaddrlng\":0,\"onCity\":\"武汉市\",\"onaddress\":\"武汉市青山区青宜居\",\"onaddrlat\":0,\"onaddrlng\":0,\"orderamount\":0,\"orderno\":\"FF57E0E0-F7CA-48FE-89F1-483218E4A31E\",\"orderreason\":\"\",\"ordersource\":\"\",\"orderstatus\":\"1\",\"ordertime\":null,\"ordertype\":\"\",\"passengerphone\":\"18817156054\",\"passengers\":\"王五5\",\"paymethod\":\"0\",\"paytype\":\"\",\"pricecopy\":\"\",\"selectedmodel\":\"0\",\"starttime\":null,\"status\":0,\"tripremark\":\"\",\"undertime\":null,\"updatetime\":{\"date\":6,\"day\":2,\"hours\":18,\"minutes\":27,\"month\":8,\"seconds\":17,\"time\":1473157637214,\"timezoneOffset\":-480,\"year\":116},\"usercomment\":\"\",\"userid\":\"A4970DB8-2647-4FAE-A4E9-A66EB8128DB0\",\"userrate\":\"\",\"usetime\":{\"date\":6,\"day\":2,\"hours\":8,\"minutes\":0,\"month\":8,\"seconds\":0,\"time\":1473120000000,\"timezoneOffset\":-480,\"year\":116},\"usetype\":\"0\",\"vehicleid\":\"\",\"vehiclessubject\":\"  123423投入4他\",\"vehiclessubjecttype\":\"cQsbBgVs-LQzYpqzI-PSGxcxzr-MNazRfml\"}");
		System.out.println(json.names().size());
		String insert = "INSERT INTO org_order VALUES("+
			"#{orderno},"+
			"#{usetype},"+
			"#{companyid},"+
			"#{ordertype},"+
			"#{userid},"+
			"#{passengers},"+
			"#{passengerphone},"+
			"#{driverid},"+
			"#{vehicleid},"+
			"#{selectedmodel},"+
			"#{onaddress},"+
			"#{offaddress},"+
			"#{onaddrlng},"+
			"#{onaddrlat},"+
			"#{offaddrlng},"+
			"#{offaddrlat},"+
			"#{usetime},"+
			"#{vehiclessubjecttype},"+
			"#{vehiclessubject},"+
			"#{tripremark},"+
			"#{orderstatus},"+
			"#{paymethod},"+
			"#{estimatedtime},"+
			"#{estimatedmileage},"+
			"#{estimatedcost},"+
			"#{paytype},"+
			"#{mileage},"+
			"#{orderamount},"+
			"#{undertime},"+
			"#{ordertime},"+
			"#{departuretime},"+
			"#{arrivaltime},"+
			"#{starttime},"+
			"#{endtime},"+
			"#{completetime},"+
			"#{pricecopy},"+
			"#{fltno},"+
			"#{falltime},"+
			"#{orderreason},"+
			"#{ordersource},"+
			"#{userrate},"+
			"#{usercomment},"+
			"#{createtime},"+
			"#{updatetime},"+
			"#{status}"+
		")";
		Pattern pa = Pattern.compile("#\\{(\\w+)\\}");
		Matcher ma = pa.matcher(insert);
		while(ma.find()){
			for(int i=0;i<ma.groupCount();i++){
				Object value = json.get(ma.group(i).replaceAll("#|\\{|\\}", ""));
				if(value.getClass().getName().contains("Integer")){
					insert = insert.replace(ma.group(i),value+"");
				}else{
					if(value.equals("null")){
						insert = insert.replace(ma.group(i),null);
					}else {
						insert = insert.replace(ma.group(i),"'"+value+"'");
					}
				}
			}
		}
		System.out.println(insert);
	}
}
