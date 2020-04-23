package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 收单支付扩展参数
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/2 1:50
 * ----------------------------
 */
@Data
public class MergePayParamVO {

    @ApiModelProperty("{ 微信提供appId }")
    private String appID;
    @ApiModelProperty("{ 微信公众号，小程序/支付宝用户标识 }")
    private String openID;
    @ApiModelProperty("{ 微信/支付宝 条码支付授权码 }")
    private String authCode;
    @ApiModelProperty("{ 设备类型 }")
    private String deviceType;
    @ApiModelProperty("{ H5支付参数 }")
    private String sceneInfo;

}