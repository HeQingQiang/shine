package com.shine.goods.controller;

import com.alibaba.fastjson.JSON;
import com.shine.goods.common.*;
import com.shine.goods.property.Property;
import com.shine.goods.vo.BindAndPayVO;
import com.shine.goods.vo.ConfirmVO;
import com.shine.goods.vo.QueryVO;
import com.shine.goods.vo.RefundVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 快捷支付控制器
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 15:20
 * ----------------------------
 */
@RestController
@RequestMapping("/quick")
@Api(tags = "快捷支付文档")
public class QuickController {

    //region 绑卡并支付
    @PostMapping("/bindAndPay")
    @ApiOperation("绑卡并支付")
    public ServerResponse bindAndPay(BindAndPayVO bindAndPayVO) {

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
        data.put("service", Const.PayServerName.绑卡之后支付.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("tradeName", bindAndPayVO.getTradeName());
        data.put("amount", bindAndPayVO.getAmount());
        if (bindAndPayVO.getPayerUserId()!=null) {
            data.put("payerUserId", bindAndPayVO.getPayerUserId());
        }
        data.put("payeeUserId", bindAndPayVO.getPayeeUserId());
        if (bindAndPayVO.getPayeeUserName()!=null) {
            data.put("payeeUserName", bindAndPayVO.getPayeeUserName());
        }
        data.put("tradeModel", bindAndPayVO.getTradeModel());
        if (bindAndPayVO.getProfits()!=null) {
            data.put("profits", JSON.toJSONString(bindAndPayVO.getProfits()));
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

    //region 快捷确认打款
    @PostMapping("/confirm")
    @ApiOperation("快捷担保确认打款")
    public ServerResponse confirm(ConfirmVO confirmVO) {

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
        data.put("service", Const.PayServerName.绑卡之后支付.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", confirmVO.getOrigMerchOrderNo());
        data.put("amount", confirmVO.getAmount().toString());
        if (confirmVO.getProfits()!=null) {
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

    //region 快捷订单查询
    @PostMapping("/query")
    @ApiOperation("快捷订单查询")
    public ServerResponse query(QueryVO queryVO) {

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
        data.put("service", Const.PayServerName.快捷订单查询.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", queryVO.getOrigMerchOrderNo());
        data.put("type", queryVO.getType());

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

    //region 快捷支付退款
    @PostMapping("/refund")
    @ApiOperation("快捷支付退款")
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
        data.put("service", Const.PayServerName.快捷支付退款.getValue());
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
