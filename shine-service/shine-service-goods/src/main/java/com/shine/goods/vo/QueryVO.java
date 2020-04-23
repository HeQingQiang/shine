package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 快捷订单查询VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 19:06
 * ----------------------------
 */
@Data
public class QueryVO {

    @ApiModelProperty("{ 原商户订单号 }")
    private String origMerchOrderNo;
    @ApiModelProperty("{ 原订单类型PAY:支付订单，REFUND：退款订单 }")
    private String type;

}
