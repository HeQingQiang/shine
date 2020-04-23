package com.shine.goods.controller;

import com.shine.goods.common.*;
import com.shine.goods.vo.AccountOpenVO;
import com.shine.goods.vo.AccountVO;
import com.shine.goods.property.Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Api(tags = "账户接口文档")
public class AccountController {

    //region 账号激活
    @PostMapping("/activate")
    @ApiOperation("资金账户激活")
    public ServerResponse activate(String outUserId) {

        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestNo", "rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("merchOrderNo", "mid" + ymd);
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.账户激活.getValue());

        //业务需求
        //data.put("outUserId", "1234746039869636608");
        data.put("outUserId", outUserId);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse respones = null;

        try {

            //请求第三方接口
            respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            //System.out.println("\nhttpPost请求异常:" + e);
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        Map<String, String> map;

        map = Util.responesParseMap(respones);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    //System.out.println("验证签名失败!");
                    return ServerResponse.errorMessage("验证签名失败！");
                }
            } else {
                //System.out.println("\n没有收到sign,不予验签!");
                return ServerResponse.errorMessage("没有收到sign,不予验签！");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                //System.out.println(map.get("resultMessage"));
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                //System.out.println("资金账户激活成功！");
                return ServerResponse.successMessage("资金账户激活成功！");
                //todo...存库？
            }
        } else {
            //System.out.println("请求异常！");
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    //region 开通账号
    @PostMapping("/open")
    @ApiOperation("开通资金账户")
    public ServerResponse open(AccountOpenVO accountOpenVO) throws IOException {

        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.开通账户.getValue());

        //业务需求
        //todo...存库激活使用
        data.put("outUserId", accountOpenVO.getOutUserId());
        data.put("acctType", accountOpenVO.getAcctType());
        data.put("ifEntry", accountOpenVO.getIfEntry());
        data.put("realName", accountOpenVO.getRealName());
        data.put("idNo", accountOpenVO.getIdNo());
        data.put("taxCert", accountOpenVO.getTaxCert());
        data.put("orgCode", accountOpenVO.getOrgCode());
        data.put("corpName", accountOpenVO.getCorpName());
        data.put("legPerId", accountOpenVO.getLegPerId());
        data.put("mobile", accountOpenVO.getMobile());
        data.put("bankCardNo", accountOpenVO.getBankCardNo());
        data.put("needLogo", accountOpenVO.getNeedLogo());
        data.put("idcardfront", accountOpenVO.getIdcardfront());
        data.put("idcardreverse", accountOpenVO.getIdcardreverse());
        //data.put("certificate", accountOpenVO.getCertificate());
        //data.put("authority", accountOpenVO.getAuthority());
        //data.put("organization", accountOpenVO.getOrganization());
        //放入签名
        data.put("sign", digestUtil.sign(data));

        //HttpResponse respones = null;


        //请求第三方接口
        //System.out.println("通过请求响应拿到的location页面跳转地址:" + Https.getHttpsLocation(Property.getGatewayurl(), data));
        String httpsLocation = Https.getHttpsLocation(Property.getGatewayurl(), data);
        if (StringUtils.isEmpty(httpsLocation)) {
            return ServerResponse.errorMessage("数据请求异常");
        }
        return ServerResponse.success("请求成功", httpsLocation);
    }
    //endregion

    //region 账号信息
    @PostMapping("/queryByAccNo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outUserId", value = "会员ID", required = true, paramType = "query", dataType = "String")
    })
    @ApiOperation("通过账户号查询账户信息")
    public ServerResponse queryByAccNo(String outUserId) {

        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap();
        data.put("requestNo", "rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("merchOrderNo", "mid" + ymd);
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.账户信息.getValue());

        //业务需求
        //data.put("userAccount", "3111330022273095302");
        data.put("userAccount", outUserId);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            //请求第三方接口
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            //System.out.println("\nhttpPost请求异常:" + e);
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        Map<String, String> map;

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    //System.out.println("验证签名失败!");
                    return ServerResponse.errorMessage("验证签名失败！");
                }
            } else {
                //System.out.println("\n没有收到sign,不予验签!");
                return ServerResponse.errorMessage("没有收到sign,不予验签！");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                //System.out.println(map.get("resultMessage"));
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                //System.out.println("获取账户信息成功！");
                return ServerResponse.success("获取账户信息成功", map);
            }
        } else {
            //System.out.println("请求异常！");
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    //region 资金状态
    @PostMapping("/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outUserId", value = "会员ID", required = true, paramType = "query", dataType = "String")
    })
    @ApiOperation("查询用户资金状态")
    public ServerResponse query(String outUserId) {

        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("merchOrderNo", "mid" + ymd);
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.状态查询.getValue());

        //业务需求
        //data.put("outUserId", "1234746039869636608");
        data.put("outUserId", outUserId);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse respones = null;

        try {

            //请求第三方接口
            respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            System.out.println("\nhttpPost请求异常:" + e);
        }

        Map<String, String> map;

        map = Util.responesParseMap(respones);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    //System.out.println("验证签名失败!");
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                //System.out.println("\n没有收到sign,不予验签!");
                return ServerResponse.errorMessage("\n没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                //System.out.println(map.get("resultMessage"));
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                //System.out.println("查询状态成功！");
                return ServerResponse.success("查询状态成功！", map);
            }
        } else {
            //System.out.println("请求异常！");
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    //region 变动汇总
    @PostMapping("/summary")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "accountNo", value = "用户账户号", required = true, paramType = "query", dataType = "String"),
           @ApiImplicitParam(name = "startTime", value = "开始时间(格式yyMMddHHmmsss)", required = true, paramType = "query", dataType = "String"),
           @ApiImplicitParam(name = "endTime", value = "结束时间(格式yyMMddHHmmsss)", required = true, paramType = "query", dataType = "String")
             })
    @ApiOperation("查询账户变动汇总")
    public ServerResponse summary(String accountNo,String startTime,String endTime) {
        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("merchOrderNo", "mid" + ymd);
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.变动汇总.getValue());

        //业务需求
        //data.put("accountNo", Property.getPartnerid());
        data.put("accountNo", accountNo);
        data.put("startTime", startTime);
        data.put("endTime", endTime);
        //data.put("startTime", "20200301000000");
        //data.put("endTime", "20200331235959");

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse respones = null;

        try {

            //请求第三方接口
            respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            System.out.println("\nhttpPost请求异常:" + e);
        }

        Map<String, String> map;

        map = Util.responesParseMap(respones);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    //System.out.println("验证签名失败!");
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                //System.out.println("\n没有收到sign,不予验签!");
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                //System.out.println(map.get("resultMessage"));
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                //System.out.println("变动查詢成功！");
                return ServerResponse.success("变动查詢成功！", map);
            }
        } else {
            //System.out.println("请求异常！");
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    //region 变动明细
    @PostMapping("/details")
    @ApiOperation("账户变动明细查询")
    public ServerResponse details(AccountVO accountVO) {
        DigestUtil digestUtil = new DigestUtil();
        //todo...改变ID生成
        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装公共请求参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("requestNo", "rid" + ymd);
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("merchOrderNo", "mid" + ymd);
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("service", Const.AccountServerName.变动明细.getValue());

        //业务需求
        //AccountVO accountVO = new AccountVO();
        //accountVO.setAccountNo(idWorker.nextId());
        //accountVO.setRelationOrderNo(idWorker.nextId());
        //accountVO.setBusinessNo(idWorker.nextId());
        //accountVO.setChangeDirection(Const.ChangeDirection.支出.getValue());
        //accountVO.setTradeType(Const.TradeType.交易.getValue());
        //accountVO.setStartTime("20200301000000");
        //accountVO.setEndTime("20200303235959");
        //accountVO.setPageSize(20);
        //accountVO.setPageNum(1);

        data.put("accountNo", accountVO.getAccountNo());
        data.put("relationOrderNo", accountVO.getRelationOrderNo());
        data.put("businessNo", accountVO.getBusinessNo());
        data.put("changeDirection", accountVO.getChangeDirection());
        data.put("tradeType", accountVO.getTradeType());
        data.put("startTime", accountVO.getStartTime());
        data.put("endTime", accountVO.getEndTime());
        data.put("pageSize", accountVO.getPageSize().toString());
        data.put("pageNum", accountVO.getPageNum().toString());

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse respones = null;

        try {

            //请求第三方接口
            respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            System.out.println("\nhttpPost请求异常:" + e);
        }

        Map<String, String> map;

        map = Util.responesParseMap(respones);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    //System.out.println("验证签名失败!");
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                //System.out.println("\n没有收到sign,不予验签!");
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                //System.out.println("查看变动详情失败！");
                return ServerResponse.errorMessage("查看变动详情失败！");
            } else {
                //System.out.println(map.get("rows"));
                return ServerResponse.success("获取明细成功！", map.get("rows"));
            }
        } else {
            //System.out.println("请求异常！");
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    public static void main(String[] args) throws IOException {

        IdWorker idWorker = new IdWorker(0, 0);

        //region 账户激活
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //System.out.println("rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        //data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.账户激活.getValue());
        //
        ////业务需求
        //data.put("outUserId", "1234746039869636608");
        //
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        //HttpResponse respones = null;
        //
        //try {
        //
        //    //请求第三方接口
        //    respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //
        //} catch (Exception e) {
        //    System.out.println("\nhttpPost请求异常:" + e);
        //}
        //
        //Map<String, String> map ;
        //
        //map = Util.responesParseMap(respones);
        //
        //if (map.size() != 0) {
        //
        //    if (map.get("sign") != null) {
        //        if (!digestUtil.verify(map)) {
        //            System.out.println("验证签名失败!");
        //        }
        //    } else {
        //        System.out.println("\n没有收到sign,不予验签!");
        //    }
        //
        //    if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
        //        System.out.println(map.get("resultMessage"));
        //    }else{
        //        System.out.println("资金账户激活成功！");
        //    }
        //} else {
        //    System.out.println("请求异常！");
        //}
        //endregion

        //region 变动明细
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        //data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.变动明细.getValue());
        //
        ////业务需求
        //AccountVO accountVO = new AccountVO();
        //accountVO.setAccountNo(idWorker.nextId());
        //accountVO.setRelationOrderNo(idWorker.nextId());
        //accountVO.setBusinessNo(idWorker.nextId());
        //accountVO.setChangeDirection(Const.ChangeDirection.支出.getValue());
        //accountVO.setTradeType(Const.TradeType.交易.getValue());
        //accountVO.setStartTime("20200301000000");
        //accountVO.setEndTime("20200303235959");
        //accountVO.setPageSize(20);
        //accountVO.setPageNum(1);
        //
        //data.put("accountNo", accountVO.getAccountNo());
        //data.put("relationOrderNo", accountVO.getRelationOrderNo());
        //data.put("businessNo", accountVO.getBusinessNo());
        //data.put("changeDirection", accountVO.getChangeDirection());
        //data.put("tradeType", accountVO.getTradeType());
        //data.put("startTime", accountVO.getStartTime());
        //data.put("endTime", accountVO.getEndTime());
        //data.put("pageSize", accountVO.getPageSize().toString());
        //data.put("pageNum", accountVO.getPageNum().toString());
        //
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        //HttpResponse respones = null;
        //
        //try {
        //
        //    //请求第三方接口
        //    respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //
        //} catch (Exception e) {
        //    System.out.println("\nhttpPost请求异常:" + e);
        //}
        //
        //Map<String, String> map ;
        //
        //map = Util.responesParseMap(respones);
        //
        //if (map.size() != 0) {
        //
        //    if (map.get("sign") != null) {
        //        if (!digestUtil.verify(map)) {
        //            System.out.println("验证签名失败!");
        //        }
        //    } else {
        //        System.out.println("\n没有收到sign,不予验签!");
        //    }
        //
        //    if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
        //        System.out.println("查看变动详情失败！");
        //    }
        //} else {
        //    System.out.println("请求异常！");
        //}
        //
        //System.out.println(map.get("rows"));
        //endregion

        //region 开通账户
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //System.out.println("rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        ////data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.开通账户.getValue());
        //
        ////业务需求
        //String testUrl = "http://f.shineyu.com.cn:8088/group1/M00/00/00/rBPp1F5ZXVeAbbdJAATcMKZun0E013.jpg";
        //AccountOpenVO accountOpenVO = new AccountOpenVO();
        ////todo...存库激活使用
        //accountOpenVO.setOutUserId(idWorker.nextId());
        //accountOpenVO.setAcctType(Const.AcctType.个人.getValue());
        ////accountOpenVO.setIfEntry(Const.YesOrNo.是.getValue());
        //accountOpenVO.setRealName("张三");
        //accountOpenVO.setIdNo("500224199510133331");
        //accountOpenVO.setTaxCert(idWorker.nextId());
        //accountOpenVO.setOrgCode(idWorker.nextId());
        //accountOpenVO.setCorpName(idWorker.nextId());
        //accountOpenVO.setLegPerId(idWorker.nextId());
        //accountOpenVO.setMobile("13800138000");
        //accountOpenVO.setBankCardNo("6212263100014532844");
        //accountOpenVO.setNeedLogo(Const.YesOrNo.是.getValue());
        //accountOpenVO.setIdcardfront(testUrl);
        //accountOpenVO.setIdcardreverse(testUrl);
        ////accountOpenVO.setCertificate(testUrl);
        ////accountOpenVO.setAuthority(testUrl);
        ////accountOpenVO.setOrganization(testUrl);
        //
        //data.put("outUserId", accountOpenVO.getOutUserId());
        //data.put("acctType", accountOpenVO.getAcctType());
        //data.put("ifEntry", accountOpenVO.getIfEntry());
        //data.put("realName", accountOpenVO.getRealName());
        //data.put("idNo", accountOpenVO.getIdNo());
        //data.put("taxCert", accountOpenVO.getTaxCert());
        //data.put("orgCode", accountOpenVO.getOrgCode());
        //data.put("corpName", accountOpenVO.getCorpName());
        //data.put("legPerId", accountOpenVO.getLegPerId());
        //data.put("mobile", accountOpenVO.getMobile());
        //data.put("bankCardNo", accountOpenVO.getBankCardNo());
        //data.put("needLogo", accountOpenVO.getNeedLogo());
        //data.put("idcardfront", accountOpenVO.getIdcardfront());
        //data.put("idcardreverse", accountOpenVO.getIdcardreverse());
        ////data.put("certificate", accountOpenVO.getCertificate());
        ////data.put("authority", accountOpenVO.getAuthority());
        ////data.put("organization", accountOpenVO.getOrganization());
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        ////HttpResponse respones = null;
        //
        //
        ////请求第三方接口
        ////respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //System.out.println("通过请求响应拿到的location页面跳转地址:" + Https.getHttpsLocation(Property.getGatewayurl(), data));

        //endregion

        //region 账户信息
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //System.out.println("rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        //data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.账户信息.getValue());
        //
        ////业务需求
        //data.put("userAccount", "3111330022273095302");
        //
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        //HttpResponse respones = null;
        //
        //try {
        //
        //    //请求第三方接口
        //    respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //
        //} catch (Exception e) {
        //    System.out.println("\nhttpPost请求异常:" + e);
        //}
        //
        //Map<String, String> map ;
        //
        //map = Util.responesParseMap(respones);
        //
        //if (map.size() != 0) {
        //
        //    if (map.get("sign") != null) {
        //        if (!digestUtil.verify(map)) {
        //            System.out.println("验证签名失败!");
        //        }
        //    } else {
        //        System.out.println("\n没有收到sign,不予验签!");
        //    }
        //
        //    if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
        //        System.out.println(map.get("resultMessage"));
        //    }else{
        //        System.out.println("获取账户信息成功！");
        //    }
        //} else {
        //    System.out.println("请求异常！");
        //}
        //endregion

        //region 状态查询
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //System.out.println("rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        //data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.状态查询.getValue());
        //
        ////业务需求
        //data.put("outUserId", "1234746039869636608");
        //
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        //HttpResponse respones = null;
        //
        //try {
        //
        //    //请求第三方接口
        //    respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //
        //} catch (Exception e) {
        //    System.out.println("\nhttpPost请求异常:" + e);
        //}
        //
        //Map<String, String> map ;
        //
        //map = Util.responesParseMap(respones);
        //
        //if (map.size() != 0) {
        //
        //    if (map.get("sign") != null) {
        //        if (!digestUtil.verify(map)) {
        //            System.out.println("验证签名失败!");
        //        }
        //    } else {
        //        System.out.println("\n没有收到sign,不予验签!");
        //    }
        //
        //    if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
        //        System.out.println(map.get("resultMessage"));
        //    }else{
        //        System.out.println("查询状态成功！");
        //    }
        //} else {
        //    System.out.println("请求异常！");
        //}
        //endregion

        //region 变动汇总
        //DigestUtil digestUtil = new DigestUtil();
        ////todo...改变ID生成
        //String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());
        //
        ////组装公共请求参数
        //Map<String, String> data = new HashMap<String, String>();
        //data.put("requestNo", "rid" + ymd);
        //System.out.println("rid" + ymd);
        //data.put("partnerId", Property.getPartnerid());
        //data.put("signType", Property.getSigntype());
        //data.put("merchOrderNo", "mid" + ymd);
        //data.put("returnUrl", Property.getReturnurl());
        //data.put("notifyUrl", Property.getNotifyurl());
        //data.put("service", Const.AccountServerName.变动汇总.getValue());
        //
        ////业务需求
        //data.put("accountNo", Property.getPartnerid());
        //data.put("startTime", "20200301000000");
        //data.put("endTime", "20200303235959");
        //
        ////放入签名
        //data.put("sign", digestUtil.sign(data));
        //
        //HttpResponse respones = null;
        //
        //try {
        //
        //    //请求第三方接口
        //    respones = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        //
        //} catch (Exception e) {
        //    System.out.println("\nhttpPost请求异常:" + e);
        //}
        //
        //Map<String, String> map ;
        //
        //map = Util.responesParseMap(respones);
        //
        //if (map.size() != 0) {
        //
        //    if (map.get("sign") != null) {
        //        if (!digestUtil.verify(map)) {
        //            System.out.println("验证签名失败!");
        //        }
        //    } else {
        //        System.out.println("\n没有收到sign,不予验签!");
        //    }
        //
        //    if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
        //        System.out.println(map.get("resultMessage"));
        //    }else{
        //        System.out.println("变动查詢成功！");
        //    }
        //} else {
        //    System.out.println("请求异常！");
        //}
        //endregion

    }

}
