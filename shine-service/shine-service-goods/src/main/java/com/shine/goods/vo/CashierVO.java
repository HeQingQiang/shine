package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Desc: 合并支付担保交易创建订单VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/9 0:54
 * ----------------------------
 */
@Data
public class CashierVO {

    @ApiModelProperty("{ 外部买家id }")
    private String buyerOutUserID;
    @ApiModelProperty("{ 外部买家姓名 }")
    private String buyerOutUserName;
    @ApiModelProperty("{ 交易详情 }")
    private List<EscrowPaymentOrdersVO> escrowPaymentOrders;
    @ApiModelProperty("{ 是否展示结果页 }")
    private String resultPage;

}
