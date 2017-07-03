package com.szyciov.enums;

/**
 * 弹窗显示枚举
 * 
 */
public enum WindowVisualEnum {
	
	FUCHUANG("0","浮窗"),
    TANCHUANG("1","弹窗");


    public String code;
    public String msg;

    WindowVisualEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 返回弹窗状态汉字
     * @param code  弹窗状态code
     * @return
     */
    public static String getBindingText(String code){
        switch (code) {
            case "0":
                return WindowVisualEnum.FUCHUANG.msg;
            case "1":
                return WindowVisualEnum.TANCHUANG.msg;
            default:
                return WindowVisualEnum.TANCHUANG.msg;
        }
    }
}
