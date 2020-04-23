package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 综合支付VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 10:28
 * ----------------------------
 */
@Data
public class TradePayVO {

    @ApiModelProperty("{ 支付方式 }")
    private String payChannel;
    @ApiModelProperty("{ 渠道商户号 }")
    private String merchId;
    @ApiModelProperty("{ 回调地址 }")
    private String returnUrl;
    @ApiModelProperty("{ 交易模式 }")
    private String tradeModel;
    @ApiModelProperty("{ 外部买家id }")
    private String buyerOutUserID;
    @ApiModelProperty("{ 外部买家姓名 }")
    private String buyerOutUserName;
    @ApiModelProperty("{ 外部卖家id }")
    private String sellerOutUserID;
    @ApiModelProperty("{ 外部卖家名称 }")
    private String sellerOutUserName;
    @ApiModelProperty("{ 交易名称 }")
    private String tradeName;
    @ApiModelProperty("{ 交易金额 }")
    private String tradeAmount;
    @ApiModelProperty("{ 商品名称 }")
    private String goodsName;
    @ApiModelProperty("{ 商品类型 }")
    private String goodsType;
    @ApiModelProperty("{ 商品数量 }")
    private String quantity;
    @ApiModelProperty("{ 垫资金额 }")
    private String advanceAmount;
    @ApiModelProperty("{ 终端ip }")
    private String userIp;
    @ApiModelProperty("{ 备注 }")
    private String memo;
    @ApiModelProperty("{ 订单关闭时间 }")
    private String closeTime;
    @ApiModelProperty("{ 扩展参数 }")
    private ExtraVO exra;
    @ApiModelProperty("{ 分润订单(不分润不传即可，担保交易下传入无效-担保交易的分润在确认打款时传入) }")
    private ProfitsVO profits;

}
