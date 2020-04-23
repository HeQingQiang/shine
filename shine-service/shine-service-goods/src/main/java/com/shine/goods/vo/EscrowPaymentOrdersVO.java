package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 交易详情VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/9 0:55
 * ----------------------------
 */
@Data
public class EscrowPaymentOrdersVO {

    @ApiModelProperty("{ 单笔交易订单号 }")
    private String subMerchOrderNo;
    @ApiModelProperty("{ 外部卖家id }")
    private String sellerOutUserID;
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
    @ApiModelProperty("{ 备注 }")
    private String memo;
    @ApiModelProperty("{ 补贴金额 }")
    private String advanceAmount;
    @ApiModelProperty("{ 订单关闭时间 }")
    private String closeTime;

}
