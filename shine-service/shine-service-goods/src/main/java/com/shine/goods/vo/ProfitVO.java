package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Desc: 分润订单VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/2 1:06
 * ----------------------------
 */
@Data
public class ProfitVO {

    @ApiModelProperty("{ 分润订单号 }")
    private String profitOrderNo;
    @ApiModelProperty("{ 分润收款方外部用户id }")
    private String userID;
    @ApiModelProperty("{ 分润收款方名称 }")
    private String userName;
    @ApiModelProperty("{ 分润金额 }")
    private BigDecimal shareAmount;
    @ApiModelProperty("{ 备注 }")
    private String memo;

}
