package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 收单支付VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/2 1:46
 * ----------------------------
 */
@Data
public class MergePayVO {

    @ApiModelProperty("{ 原商户交易订单号 }")
    private String origMerchOrderNo;
    @ApiModelProperty("{ 支付方式 }")
    private String payChannel;
    @ApiModelProperty("{ 终端ip }")
    private String userIp;
    @ApiModelProperty("{ 扩展参数 }")
    private ExtraVO extra;
    @ApiModelProperty("{ 页面跳转地址 }")
    private String returnUrl;
    @ApiModelProperty("{ 渠道商户号 }")
    private String merchId;

}
