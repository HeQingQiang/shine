package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Desc: 支付实体
 * ----------------------------
 * @Author: shine
 * @Date: 2020/2/27 1:14
 * ----------------------------
 */
@Data
public class PayVO {

    @ApiModelProperty("{ 付款方id }")
    private String payerOutUserId;
    @ApiModelProperty("{ 收款方id }")
    private String payeeOutUserID;
    @ApiModelProperty("{ 交易金额 }")
    private BigDecimal amount;
    @ApiModelProperty("{ 交易说明 }")
    private String tradeName;
    @ApiModelProperty("{ 交易备注 }")
    private String tradeMemo;

}
