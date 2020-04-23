package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Desc: 交易订单集合
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/2 1:04
 * ----------------------------
 */
@Data
public class TradeOrderVO {

    @ApiModelProperty("{ 单笔交易订单号 }")
    private String subMerchOrderNo;
    @ApiModelProperty("{ 交易名称 }")
    private String tradeName;
    @ApiModelProperty("{ 外部卖家id }")
    private String payeeUserId;
    @ApiModelProperty("{ 交易金额 }")
    private BigDecimal amount;
    @ApiModelProperty("{ 订单关闭时间 }")
    private String closeTime;
    @ApiModelProperty("{ 分润订单 }")
    private List<ProfitVO> profits;

}
