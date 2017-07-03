/**
 * 
 */
package com.szyciov.enums;

/**
 * @ClassName OperateTypeEnum 
 * @author Efy Shu
 * @Description 规则状态枚举
 * @date 2017年3月14日 下午12:18:10 
 */
public enum RuleStateEnum {
	ENABLE("0","启用"),DISABLE("1","禁用");
	
    public String code;
    public String msg;

    RuleStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
