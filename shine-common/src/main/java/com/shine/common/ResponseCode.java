package com.shine.common;

/**
 * 返回信息码.
 *
 * @Created shineYu
 * @Date 2019/5/25 15:30
 */
public enum ResponseCode {

    PARAM_ERROR(-1, "参数错误"),
    CART_EMPTY(-10, "购物车信息为空"),
    PRODUCT_NOT_EXIST(-20, "商品不存在"),
    PRODUCT_STOCK_ERROR(-22, "库存有误"),
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;


    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
