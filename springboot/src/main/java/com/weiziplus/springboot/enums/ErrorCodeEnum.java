package com.weiziplus.springboot.enums;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/29 15:29
 */
public enum ErrorCodeEnum {

    ERROR_ZERO_SCORE(901,"没有测验数据"),
    ERROR_UNFINISH_SCORE(902,"测验数据未填写完整"),
    ERROR_SUBIMT_TESTING(911,"提交测验结果失败");
    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValueByCode(Integer code){
        ErrorCodeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ErrorCodeEnum resultCodeEnum = var1[var3];
            if (code.equals(resultCodeEnum.getCode())) {
                return resultCodeEnum.getMsg();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

