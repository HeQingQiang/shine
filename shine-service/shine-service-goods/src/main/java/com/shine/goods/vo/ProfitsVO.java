package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 分润订单VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 10:32
 * ----------------------------
 */
@Data
public class ProfitsVO {

    @ApiModelProperty("{ 分润订单号 }")
    private String profitOrderNo;
    @ApiModelProperty("{ 分润收款方外部用户id }")
    private String userID;
    @ApiModelProperty("{ 分润收款方名称 }")
    private String userName;
    @ApiModelProperty("{ 分润金额 }")
    private String shareAmount;
    @ApiModelProperty("{ 备注 }")
    private String memo;

}
