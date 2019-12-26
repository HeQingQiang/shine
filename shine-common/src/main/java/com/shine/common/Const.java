package com.shine.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 公共常量.
 *
 * @author shineYu
 * @Date 2019/5/25 15:26
 */
public class Const {

    /**当前登录用户.*/
    public static final String CURRENT_USER = "currentUser";
    /**Email登录.*/
    public static final String EMAIL = "email";
    /**用户名登录.*/
    public static final String USERNAME = "username";
    /**文件上传目录.*/
    public static final String PATH_FILE = "upload";
    /**文件服务器地址.*/
    public static final String FILE_SERVER_PATH = "http://image.shineyu.com.cn/";
    /**文件地址.*/
    public static final String FILE_PATH = "/opt/files/images/";

    /**
     * 商品列表排序.
     */
    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    /**
     * 购物车接口.
     */
    public interface Cart {
        int CHECKED = 1;//即购物车选中状态
        int UN_CHECKED = 0;//购物车中未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 角色接口.
     */
    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }



    /**
     * 商品上下架枚举.
     */
    public enum ProductStatusEnum {
        ON_SALE(0, "上架"),
        NO_SALE(1, "下架");
        private String value;
        private int code;

        /**
         * 商品状态枚举.
         *
         * @param code 状态码
         * @param value 状态值
         */
        ProductStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return int 结果
         */
        public int getCode() {
            return code;
        }
    }

    /**
     * 状态枚举.
     */
    public enum StatusEnum {
        SUCCESS(1, "正常"),
        FAIL(0, "未启用"),
        DELETE(-1, "已删除");

        /**
         * 订单状态枚举.
         *
         * @param code 状态码
         * @param value 状态值
         */
        StatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        /**值.*/
        private String value;
        /**键.*/
        private int code;

        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return String 结果
         */
        public int getCode() {
            return code;
        }

        /**
         * 获取订单状态值.
         *
         * @param code 值
         * @return OrderStatusEnum 结果
         */
        public static StatusEnum codeOf(int code) {
            for (StatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    /**
     * 订单状态枚举.
     */
    public enum OrderStatusEnum {
        CANCELED(0, "已取消"),
        NEW(1, "新订单"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        /**
         * 订单状态枚举.
         *
         * @param code 状态码
         * @param value 状态值
         */
        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        /**值.*/
        private String value;
        /**键.*/
        private int code;

        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return String 结果
         */
        public int getCode() {
            return code;
        }

        /**
         * 获取订单状态值.
         *
         * @param code 值
         * @return OrderStatusEnum 结果
         */
        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    /**
     * 订单支付枚举.
     */
    public enum PayStatusEnum {
        CANCELED(0, "已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已付款"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");

        /**
         * 订单支付枚举.
         *
         * @param code 状态码
         * @param value 状态值
         */
        PayStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        /**值.*/
        private String value;
        /**键.*/
        private int code;

        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return String 结果
         */
        public int getCode() {
            return code;
        }

        /**
         * 获取支付状态值.
         *
         * @param code 值
         * @return OrderStatusEnum 结果
         */
        public static PayStatusEnum codeOf(int code) {
            for (PayStatusEnum payStatusEnum : values()) {
                if (payStatusEnum.getCode() == code) {
                    return payStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }


    /**
     *阿里回调接口.
     */
    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     *支付方式枚举.
     */
    public enum PayPlatformEnum {
        /**支付宝.*/
        ALIPAY(1, "支付宝");

        PayPlatformEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;


        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return int 结果
         */
        public int getCode() {
            return code;
        }
    }

    /**
     * 支付方式枚举.
     */
    public enum PaymentTypeEnum {
        ONLINE_PAY(1, "在线支付");

        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private String value;
        private int code;


        /**
         * get.
         *
         * @return String 结果
         */
        public String getValue() {
            return value;
        }

        /**
         * get.
         *
         * @return int 结果
         */
        public int getCode() {
            return code;
        }

        /**
         * 支付类型枚举.
         *
         * @param code 值
         * @return PaymentTypeEnum 结果
         */
        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }

    }

}
