package com.shine.goods.property;

/**
 * @Desc:  第三方公共参数
 * ----------------------------
 * @Author: shine
 * @Date: 2020/2/22 16:30
 * ----------------------------
 */
public class Property {

	private static final String gatewayUrl = "http://test.hlhbar.com.cn:8380/gateway.html";
	private static final String partnerId = "19082909355626570691";
	private static final String key = "1dac6e087e97c260a4fbf1d3134cc287";//
	private static final String signType = "MD5";
	
	// 链接超时时间
	private static final int connectionTimeout = 60000;
	// 读取等待超时时间
	private static final int readTimeout = 60000;

	private static final String encoding = "UTF-8";

	private static final String localUrl = "http://vinebear.gicp.net:11515/";   //本地服务url
	private static final String returnUrl = localUrl + "returnServlet";
	private static final String notifyUrl = localUrl + "notifyServlet";

	public static String getPartnerid() {
		return partnerId;
	}

	public static String getKey() {
		return key;
	}

	public static String getLocalUrl() {
		return localUrl;
	}

	public static String getSigntype() {
		return signType;
	}

	public static String getEncoding() {
		return encoding;
	}

	public static String getReturnurl() {
		return returnUrl;
	}

	public static String getNotifyurl() {
		return notifyUrl;
	}

	public static int getReadtimeout() {
		return readTimeout;
	}

	public static int getConnectiontimeout() {
		return connectionTimeout;
	}
	
	public static String getGatewayurl() {
		return gatewayUrl;
	}

}
