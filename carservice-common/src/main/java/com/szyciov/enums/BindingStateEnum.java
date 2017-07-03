package com.szyciov.enums;

/**
 * 绑定状态枚举
 * Created by LC on 2017/3/9.
 */
public enum BindingStateEnum {

    BINDING("1","已绑定"),
    UN_BINDING("0","未绑定");


    public String code;
    public String msg;

    BindingStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 返回绑定状态汉字
     * @param code  绑定状态code
     * @return
     */
    public static String getBindingText(String code){
        switch (code) {
            case "0":
                return BindingStateEnum.UN_BINDING.msg;
            case "1":
                return BindingStateEnum.BINDING.msg;
            default:
                return BindingStateEnum.UN_BINDING.msg;
        }
    }
}
