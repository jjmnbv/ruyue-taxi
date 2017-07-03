/**
 * 
 */
package com.szyciov.enums;

/**
 * @ClassName OperateTypeEnum 
 * @author Efy Shu
 * @Description OpTaxiAccountRuleModiLog的操作枚举
 * @date 2017年3月14日 下午12:18:10 
 */
public enum OperateTypeEnum {
	ENABLE("0","启用"),DISABLE("1","禁用"),ADD("2","新增"),MODIFY("3","修改");
	
    public String code;
    public String msg;

    OperateTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
