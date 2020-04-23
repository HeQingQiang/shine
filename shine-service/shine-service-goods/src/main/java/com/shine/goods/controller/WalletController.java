package com.shine.goods.controller;

import com.shine.goods.common.*;
import com.shine.goods.property.Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 钱包控制器
 * ----------------------------
 * @Author: shine
 * @Date: 2020/3/6 15:23
 * ----------------------------
 */
@RestController
@RequestMapping("/wallet")
@Api(tags = "钱包接口文档")
public class WalletController {

    //region 跳转钱包
    @PostMapping
    @ApiOperation("跳转钱包")
    public ServerResponse refund(String outUserId) {

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
        data.put("service", Const.PayServerName.跳转钱包.getValue());
        data.put("merchOrderNo", "mid" + ymd);

        //组装业务请求参数
        data.put("outUserId", outUserId);

        //放入签名
        data.put("sign", digestUtil.sign(data));

        String result = null;

        try {
            result = Https.getHttpsLocation(Property.getGatewayurl(), data);
        } catch (IOException e) {
            return ServerResponse.errorMessage("参数错误！" + e.getMessage());
        }

        if (result != null) {
            return ServerResponse.success("获取钱包地址成功！", result);
        } else {
            return ServerResponse.errorMessage("获取钱包地址失败！");
        }
    }
    //endregion

}
