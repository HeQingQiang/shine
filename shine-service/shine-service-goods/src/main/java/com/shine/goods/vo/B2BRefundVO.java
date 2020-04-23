package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Desc: 代付退款服务VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 16:32
 * ----------------------------
 */
@Data
public class B2BRefundVO {

    @ApiModelProperty("{ 付款方 }")
    private String payerOutUserId;
    @ApiModelProperty("{ 账户类型 付款方为平台时，1：普通账户，2:待结算账户 }")
    private String accountType;
    @ApiModelProperty("{ 交易说明 }")
    private String tradeName;
    @ApiModelProperty("{ 收款方姓名 }")
    private String realName;
    @ApiModelProperty("{ 收款方银行卡号 }")
    private String bankCardNo;
    @ApiModelProperty("{ 收款方手机号 }")
    private String phone;
    @ApiModelProperty("{ 收款方身份证号码 }")
    private String IdCard;
    @ApiModelProperty("{ 交易金额 }")
    private BigDecimal amount;
    @ApiModelProperty("{ 备注 }")
    private String memo;

}
