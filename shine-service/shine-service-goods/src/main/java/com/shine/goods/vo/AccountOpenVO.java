package com.shine.goods.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Desc: 账户开通VO
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/3 2:24
 * ----------------------------
 */
@Data
public class AccountOpenVO {


    @ApiModelProperty("{ 外部会员Id }")
    private String outUserId;

    @ApiModelProperty("{ 开户类型 }")
    private String acctType	;

    @ApiModelProperty("{ 是否入网(默认为1) }")
    private String ifEntry;

    @ApiModelProperty("{ 真实名称(企业填写企业名) }")
    private String realName;

    @ApiModelProperty("{ 证件号码(如果是企业传入企业营业执照号或社会统一编码) }")
    private String idNo;

    @ApiModelProperty("{ 税务登记号(个人不需要填写) }")
    private String taxCert;

    @ApiModelProperty("{ 组织机构代码(个人不需要填写) }")
    private String orgCode	;

    @ApiModelProperty("{ 法人姓名(个人不需要填写) }")
    private String corpName;

    @ApiModelProperty("{ 法人证件号(个人不需要填写) }")
    private String legPerId;

    @ApiModelProperty("{ 联系手机号 }")
    private String mobile;

    @ApiModelProperty("{ 银行账户号 }")
    private String bankCardNo;

    @ApiModelProperty("{ 是否需要logo }")
    private String needLogo	;

    @ApiModelProperty("{ 身份证正面url }")
    private String idcardfront;

    @ApiModelProperty("{ 身份证反面url }")
    private String idcardreverse;

    @ApiModelProperty("{ 营业执照url(个人不需要填写) }")
    private String certificate;

    @ApiModelProperty("{ 税务登记证url(个人不需要填写) }")
    private String authority;

    @ApiModelProperty("{ 组织机构代码证url(个人不需要填写) }")
    private String organization;

}
