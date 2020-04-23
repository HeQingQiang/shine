package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Desc: 交易退款服务VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 16:32
 * ----------------------------
 */
@Data
public class RefundVO {

    @ApiModelProperty("{ 原订单号 }")
    private String origMerchOrderNo;
    @ApiModelProperty("{ 退款金额 }")
    private BigDecimal amount;
    @ApiModelProperty("{ 原交易垫资退还金额 }")
    private BigDecimal refundAdvanceAmount;
    @ApiModelProperty("{ 退款原因 }")
    private String reason;
    @ApiModelProperty("{ 备注 }")
    private String memo;

}
