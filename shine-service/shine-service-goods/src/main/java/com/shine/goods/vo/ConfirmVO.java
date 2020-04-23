package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Desc: 收单确认打款VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 16:32
 * ----------------------------
 */
@Data
public class ConfirmVO {

    @ApiModelProperty("{ 原订单号(子订单号) }")
    private String origMerchOrderNo;
    @ApiModelProperty("{ 打款金额 }")
    private BigDecimal amount;
    @ApiModelProperty("{ 分润订单（不分润不传即可）}")
    private List<ProfitsVO> profits;

}
