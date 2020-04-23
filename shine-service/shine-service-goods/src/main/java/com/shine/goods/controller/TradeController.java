package com.shine.goods.controller;

import com.alibaba.fastjson.JSON;
import com.shine.goods.common.*;
import com.shine.goods.property.Property;
import com.shine.goods.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 支付控制器
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 19:32
 * ----------------------------
 */
@RestController
@RequestMapping("/trade")
@Api(tags = "支付接口文档")
public class TradeController {

    //region 创建订单
    @PostMapping("/cashier")
    @ApiOperation("合并支付担保交易创建订单跳转收银台")
    public ServerResponse bindAndPay(CashierVO cashierVO) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.创建订单.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("buyerOutUserID", cashierVO.getBuyerOutUserID());
        if (cashierVO.getBuyerOutUserName() != null) {
            data.put("buyerOutUserName", cashierVO.getBuyerOutUserName());
        }
        data.put("escrowPaymentOrders", JSON.toJSONString(cashierVO.getEscrowPaymentOrders()));
        if (cashierVO.getResultPage() != null) {
            data.put("resultPage", cashierVO.getResultPage());
        }

        //放入签名
        data.put("sign", digestUtil.sign(data));

        try {
            return ServerResponse.success("请求成功！", Https.getHttpsLocation(Property.getGatewayurl(), data));
        } catch (IOException e) {
            return ServerResponse.errorMessage("请求失败！" + e.getMessage());
        }

    }
    //endregion

    //region 确认打款
    @PostMapping("/receipt")
    @ApiOperation("确认打款")
    public ServerResponse receipt(ConfirmVO confirmVO) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.确认打款.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", confirmVO.getOrigMerchOrderNo());
        data.put("amount", confirmVO.getAmount().toString());
        if (confirmVO.getProfits() != null) {
            data.put("profits", JSON.toJSONString(confirmVO.getProfits()));
        }

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                return ServerResponse.success("支付成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 订单关闭
    @PostMapping("/close")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "origMerchOrderNo", value = "原商户交易订单号",
                    required = true, paramType = "query", dataType = "String")
    })
    @ApiOperation("订单关闭")
    public ServerResponse close(String origMerchOrderNo) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.订单关闭.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", origMerchOrderNo);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            }

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            return ServerResponse.success("订单查询成功！", map);
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 订单查询
    @PostMapping("/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "origMerchOrderNo", value = "原商户订单号",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "原订单类型 PAY:支付订单，REFUND：退款订单",
                    required = true, paramType = "query", dataType = "String")
    })
    @ApiOperation("订单查询")
    public ServerResponse query(String origMerchOrderNo, String type) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.订单查询.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", origMerchOrderNo);
        data.put("type", type);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                return ServerResponse.success("订单查询成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 退款服务
    @PostMapping("/refund")
    @ApiOperation("退款服务")
    public ServerResponse refund(RefundVO refundVO) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.退款服务.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("refundMerchOrdeNo", refundVO.getOrigMerchOrderNo());
        if (refundVO.getRefundAdvanceAmount() != null) {
            data.put("refundAdvanceAmount", refundVO.getRefundAdvanceAmount().toString());
        }
        data.put("refundAmount", refundVO.getAmount().toString());
        data.put("reason", refundVO.getReason());
        data.put("memo", refundVO.getMemo());

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            }

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }
            return ServerResponse.success("退款成功！", map);

        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 代付退款
    @PostMapping("/after")
    @ApiOperation("代付退款")
    public ServerResponse after(B2BRefundVO refundVO) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.代付退款.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("payerOutUserId", refundVO.getPayerOutUserId());
        if (refundVO.getAccountType() != null) {
            data.put("accountType", refundVO.getAccountType());
        }
        if (refundVO.getTradeName() != null) {
            data.put("tradeName", refundVO.getTradeName());
        }
        data.put("realName", refundVO.getRealName());
        data.put("bankCardNo", refundVO.getBankCardNo());
        data.put("phone", refundVO.getPhone());
        data.put("IdCard", refundVO.getIdCard());
        data.put("amount", refundVO.getAmount().toString());
        if (refundVO.getMemo() != null) {
            data.put("memo", refundVO.getMemo());
        }

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            }
            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }
            return ServerResponse.success("退款成功！", map);

        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 代付查询
    @PostMapping("/queryByAgent")
    @ApiOperation("代付查询")
    public ServerResponse queryByAgent(RefundVO refundVO) {

        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid" + ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.TradeServerName.代付查询.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", refundVO.getOrigMerchOrderNo());
        data.put("amount", refundVO.getAmount().toString());
        data.put("reason", refundVO.getReason());

        //放入签名
        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {

            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                return ServerResponse.success("退款成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

}
