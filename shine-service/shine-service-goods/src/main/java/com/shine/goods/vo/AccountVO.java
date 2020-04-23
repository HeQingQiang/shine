package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 账户VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/3 2:00
 * ----------------------------
 */
@Data
public class AccountVO {

    @ApiModelProperty("{ 用户账户号 }")
    private String accountNo;

    @ApiModelProperty("{ 关联订单号 }")
    private String relationOrderNo;

    @ApiModelProperty("{ 业务流水号 }")
    private String businessNo;

    @ApiModelProperty("{ 收支方向(IN:收入; OUT:支出) }")
    private String changeDirection;

    @ApiModelProperty("{ 交易类型 }")
    private String tradeType;

    @ApiModelProperty("{ 开始时间 }")
    private String startTime;

    @ApiModelProperty("{ 结束时间 }")
    private String endTime;

    @ApiModelProperty("{ 每页显示条数 }")
    private Integer pageSize;

    @ApiModelProperty("{ 页数 }")
    private Integer pageNum;

}
