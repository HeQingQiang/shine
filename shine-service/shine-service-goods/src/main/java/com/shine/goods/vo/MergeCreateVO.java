package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Desc: 收单合并支付VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/2 1:11
 * ----------------------------
 */
@Data
public class MergeCreateVO {

    @ApiModelProperty("{ 付款方用户id }")
    private String payerOutUserId;
    @ApiModelProperty("{ 付款方名称 }")
    private String payerUserName;
    @ApiModelProperty("{ 交易名称) }")
    private String tradeName;
    @ApiModelProperty("{ 交易模式 01-即时到账,02-担保交易}")
    private String tradeModel;
    @ApiModelProperty("{ 支付订单，最多20笔 }")
    private List<TradeOrderVO> tradeOrders;

}
