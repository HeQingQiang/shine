package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Desc: 绑卡支付VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 17:02
 * ----------------------------
 */
@Data
public class BindAndPayVO {

    @ApiModelProperty("{ \t交易名称 }")
    private String tradeName;
    @ApiModelProperty("{ 交易金额 }")
    private String amount;
    @ApiModelProperty("{ 付款人id }")
    private String payerUserId;
    @ApiModelProperty("{ 收款人id }")
    private String payeeUserId;
    @ApiModelProperty("{ 收款人姓名 }")
    private String payeeUserName;
    @ApiModelProperty("{ 交易模式01-即时到账,02-担保交易 }")
    private String tradeModel;
    @ApiModelProperty("{ 分润订单(不分润不传即可，担保交易下传入无效-担保交易的分润在确认打款时传入) }")
    private List<ProfitsVO> profits;

}
