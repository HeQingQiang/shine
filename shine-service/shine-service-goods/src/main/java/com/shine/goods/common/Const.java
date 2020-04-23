package com.shine.goods.common;

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

    public static final String DESC="desc"; //降序

    public static final String ASC="asc"; //升序
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
     * 支付服务名枚举.
     */
    public enum PayServerName {
        收单创建订单( "b2c.merge.create"),
        收单支付订单("b2c.merge.pay"),
        收单确认打款( "b2c.trade.confirm"),
        交易退款服务( "b2c.trade.refund"),
        绑卡之后支付( "quickpay.merch.bindAndPay"),
        快捷担保打款( "quick.escrow.confirm"),
        快捷订单查询( "quickpay.query"),
        快捷支付退款( "quickpay.refund"),
        跳转钱包( "b2b.wallet"),
        ;

        private String value;

        /**
         * 商品状态枚举.
         *
         * @param value 状态值
         */
        PayServerName(String value) {
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
    }

    /**
     * 支付服务名枚举.
     */
    public enum TradeServerName {
        创建订单( "b2b.trade.escrowpay.cashier"),
        确认打款("b2b.trade.receipt"),
        订单关闭( "b2b.trade.close"),
        订单查询( "common.trade.query"),
        退款服务( "b2b.trade.refund"),
        代付退款( "b2b.agentpaycard.refund"),
        代付查询( "b2b.agentpay.query"),
        ;

        private String value;

        /**
         * 商品状态枚举.
         *
         * @param value 状态值
         */
        TradeServerName(String value) {
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
    }

    /**
     * 交易类型枚举.
     */
    public enum TradeType {
        交易( "TRADE"),
        充值( "DEPOSIT"),
        提现( "WITHDRAW"),
        转账( "TRANSFER"),
        退款( "REFUNDS"),
        手续费( "FEE"),
        补贴( "ADVANCE"),
        垫付手续费("FEEADVANCE"),
        收单入金("GOLDENTRY"),
        分润("SHARE");

        private String value;

        /**
         * 交易类型枚举.
         *
         * @param value 状态值
         */
        TradeType(String value) {
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
    }

    /**
     * 开户类型枚举.
     */
    public enum AcctType {
        个人( "PERSON"),
        企业("BUSINESS");

        private String value;

        /**
         * 交易类型枚举.
         *
         * @param value 状态值
         */
        AcctType(String value) {
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
    }
    /**
     * 是否枚举.
     */
    public enum YesOrNo {
        是( "YES"),
        否("NO");

        private String value;

        /**
         * 是否枚举.
         *
         * @param value 状态值
         */
        YesOrNo(String value) {
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
    }

    /**
     * 	收支方向枚举.
     */
    public enum ChangeDirection {
        //IN:收入; OUT:支出
        收入( "IN"),
        支出("OUT");

        private String value;

        /**
         * 收支方向枚举.
         *
         * @param value 状态值
         */
        ChangeDirection(String value) {
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
    }

    /**
     * 账户服务名枚举.
     */
    public enum AccountServerName {
        账户激活( "b2b.account.activate"),
        变动明细("b2b.account.details"),
        开通账户("b2b.account.open"),
        账户信息("b2b.account.queryByAccNo"),
        状态查询("b2b.account.query"),
        变动汇总("b2b.account.summary");

        private String value;

        /**
         * 商品状态枚举.
         *
         * @param value 状态值
         */
        AccountServerName(String value) {
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
    }

    /**
     * 支付方式枚举.
     */
    public enum PayType {
        微信公总号支付( "wx_pub"),
        微信小程序支付("wx_mini"),
        微信扫码支付("wx_qr"),
        微信APP支付("wx_app"),
        微信H5支付("wx_h5"),
        微信被扫支付("wx_sqr"),
        支付宝扫码("ali_qr"),
        支付宝被扫("ali_sqr"),
        支付宝生活号("ali_pub");

        private String value;

        /**
         * 商品状态枚举.
         *
         * @param value 状态值
         */
        PayType(String value) {
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
    }

    /**
     * 状态枚举.
     */
    public enum StatusEnum {
        SUCCESS(1, "正常"),
        FAIL(0, "未启用"),
        WAIT(2, "待审核"),
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
