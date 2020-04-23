package com.shine.goods.controller;

import com.alibaba.fastjson.JSON;
import com.shine.goods.common.*;
import com.shine.goods.vo.*;
import com.shine.goods.property.Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc: 合并支付控制器
 * ----------------------------
 * @Author: shine
 * @Date: 2020/2/27 1:02
 * ----------------------------
 */
@RestController
@RequestMapping("/merge")
@Api(tags = "合并支付文档")
public class MergeController {

    //region 收单合并支付
    /**
     * 综合支付
     * 支付方式：余额支付。
     * 交易模式：01-即时到账（A-->B）、
     * 03-担保交易冻结收款方(A-->B[冻结],确认打款后解冻)
     *
     * @param mergePayVO
     * @return
     */
    @PostMapping("/pay")
    @ApiOperation("综合支付(资金账户体系创建订单并付款)")
    public ServerResponse pay(MergePayVO mergePayVO) {

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
        data.put("service", Const.PayServerName.收单支付订单.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", mergePayVO.getOrigMerchOrderNo());
        if (mergePayVO.getMerchId()!=null) {
            data.put("merchId", mergePayVO.getMerchId());
        }
        data.put("payChannel", mergePayVO.getPayChannel());
        data.put("userIp", mergePayVO.getUserIp());
        data.put("extra", JSON.toJSONString(mergePayVO.getExtra()));
        data.put("returnUrl", mergePayVO.getReturnUrl());

        //region 测试
//        data.put("tradeModel", "01");                              //交易模式   01-即时到账,  02担保交易
//		data.put("buyerOutUserID", "19050714362388130001");          //外部买家id
//		data.put("buyerOutUserName", "shine");                        //外部买家姓名
//        data.put("sellerOutUserID", data.get("partnerId"));      //外部卖家id
//        data.put("sellerOutUserName", "重庆涂邦之家科技有限责任公司");        //外部卖家名称
//        data.put("tradeName", "测试订单22134234");                 //交易名称
//        data.put("tradeAmount", "0.02");                           //交易金额
//        data.put("goodsName", "测试商品");                          //商品名称
//        data.put("goodsType", "测试饮料");                              //商品类型
//        data.put("quantity", "5");                                  //商品数量
////		data.put("advanceAmount", "");                                       //垫资金额    如无需垫资 不用传这个字段
//        data.put("userIp", "192.168.0.1");                         //终端ip
//        data.put("memo", "这是备注的内容");                         //备注
//        data.put("closeTime", "2020-02-28 02:45:14");            //订单关闭时间
//        //todo...由前端传过来支付方式
//        data.put("payChannel", "wx_sqr");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//        data.put("extra", "{\"authCode\":\"134743591831752652\"}");

//		data.put("payChannel", "wx_sqr");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//		data.put("extra", "{\"authCode\":\"136059717191862668\"}");

//		data.put("payChannel", "wx_pub");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//		data.put("extra", "{\"openID\": \"oInjP1Pm1hQ73B0sIYJ9F41nF8qo\", \"appID\": \"wx89a773f3a7b5563a\"}");//扩展参数

//		data.put("profits", "");//分润订单(不分润不传即可，担保交易下传入无效-担保交易的分润在确认打款时传入)                                                deviceType

        //放入签名
        //endregion

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
                return ServerResponse.errorMessage("\n没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                return ServerResponse.success("查询状态成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

}
    //endregion

    //region 收单创建订单
    /**
     *收单合并支付创建订单
     * @param mergeCreateVO
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("综合支付(资金账户体系创建订单并付款)")
    public ServerResponse create(MergeCreateVO mergeCreateVO) {

        IdWorker idWorker = new IdWorker(0,0);
        DigestUtil digestUtil = new DigestUtil();

        String ymd = new SimpleDateFormat("yyMMddHHmmsss").format(new Date());

        //组装参数
        Map<String, String> data = new HashMap<String, String>();

        //组装公共请求参数
        data.put("partnerId", Property.getPartnerid());
        data.put("signType", Property.getSigntype());
        data.put("returnUrl", Property.getReturnurl());
        data.put("notifyUrl", Property.getNotifyurl());
        data.put("requestNo", "rid" + ymd);
        System.out.println("rid"+ymd);
        data.put("context", "涂邦之家联调测试");
        data.put("service", Const.PayServerName.收单创建订单.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("payerOutUserId", mergeCreateVO.getPayerOutUserId());
        data.put("payerUserName", mergeCreateVO.getPayerUserName());
        data.put("tradeName", mergeCreateVO.getTradeName());
        data.put("tradeModel", mergeCreateVO.getTradeModel());
        List<TradeOrderVO> tradeOrders = mergeCreateVO.getTradeOrders();
        for (TradeOrderVO tradeOrder : tradeOrders) {

        }
        data.put("tradeOrders", JSON.toJSONString(mergeCreateVO.getTradeOrders()));

        //放入签名
        data.put("sign", digestUtil.sign(data));

        //发起请求
        HttpResponse response = null;

        try {
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());
        } catch (Exception e) {
            return  ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        //处理响应内容
        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<String, String>();

        map = Util.responesParseMap(response);

        if (map.size() != 0) {
            if (map.get("sign") != null) {
                if (!digestUtil.verify(map)) {
                    return ServerResponse.errorMessage("验证签名失败!");
                }
            } else {
                return ServerResponse.errorMessage("\n没有收到sign,不予验签!");
            }

            if (!map.get("resultCode").equals("EXECUTE_SUCCESS")) {
                return ServerResponse.errorMessage(map.get("resultMessage"));
            } else {
                return ServerResponse.success("合并订单创建成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }
    }
    //endregion

    //region 收单确认打款
    @PostMapping("/confirm")
    @ApiOperation("收单确认打款")
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
        data.put("service", Const.PayServerName.收单确认打款.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", confirmVO.getOrigMerchOrderNo());
        data.put("amount", confirmVO.getAmount().toString());
        data.put("profits", JSON.toJSONString(confirmVO.getProfits()));

        //region 测试
//        data.put("tradeModel", "01");                              //交易模式   01-即时到账,  02担保交易
//		data.put("buyerOutUserID", "19050714362388130001");          //外部买家id
//		data.put("buyerOutUserName", "shine");                        //外部买家姓名
//        data.put("sellerOutUserID", data.get("partnerId"));      //外部卖家id
//        data.put("sellerOutUserName", "重庆涂邦之家科技有限责任公司");        //外部卖家名称
//        data.put("tradeName", "测试订单22134234");                 //交易名称
//        data.put("tradeAmount", "0.02");                           //交易金额
//        data.put("goodsName", "测试商品");                          //商品名称
//        data.put("goodsType", "测试饮料");                              //商品类型
//        data.put("quantity", "5");                                  //商品数量
////		data.put("advanceAmount", "");                                       //垫资金额    如无需垫资 不用传这个字段
//        data.put("userIp", "192.168.0.1");                         //终端ip
//        data.put("memo", "这是备注的内容");                         //备注
//        data.put("closeTime", "2020-02-28 02:45:14");            //订单关闭时间
//        //todo...由前端传过来支付方式
//        data.put("payChannel", "wx_sqr");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//        data.put("extra", "{\"authCode\":\"134743591831752652\"}");

//		data.put("payChannel", "wx_sqr");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//		data.put("extra", "{\"authCode\":\"136059717191862668\"}");

//		data.put("payChannel", "wx_pub");//支付方式   wx_pub-微信公众号支付,wx_mini-微信小程序支付,wx_qr-微信扫码支付，wx_app-微信APP支付,wx_h5-微信H5支付wx_sqr-微信被扫支付,ali_qr-支付宝扫码,ali_sqr-支付宝被扫,ali_pub-支付宝生活号(JSAPI)
//		data.put("extra", "{\"openID\": \"oInjP1Pm1hQ73B0sIYJ9F41nF8qo\", \"appID\": \"wx89a773f3a7b5563a\"}");//扩展参数

//		data.put("profits", "");//分润订单(不分润不传即可，担保交易下传入无效-担保交易的分润在确认打款时传入)                                                deviceType

        //放入签名
        //endregion

        data.put("sign", digestUtil.sign(data));

        HttpResponse response = null;

        try {

            /**使用httpClient 发送post请求*/
            response = Https.httpPost(Property.getGatewayurl(), data, Property.getEncoding());

        } catch (Exception e) {
            return ServerResponse.errorMessage("httpPost请求异常:" + e);
        }

        /**jsonObjStr 转换为map*/
        Map<String, String> map = new HashMap<>();

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

            return ServerResponse.success("打款成功！", map);


        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

    //region 交易退款服务
    @PostMapping("/refund")
    @ApiOperation("交易退款服务")
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
        data.put("service", Const.PayServerName.交易退款服务.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("origMerchOrderNo", refundVO.getOrigMerchOrderNo());
        data.put("amount", refundVO.getAmount().toString());
        data.put("refundAdvanceAmount", refundVO.getRefundAdvanceAmount().toString());
        data.put("reason", refundVO.getReason());

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
                return ServerResponse.success("打款成功！", map);
            }
        } else {
            return ServerResponse.errorMessage("请求异常！");
        }

    }
    //endregion

}
