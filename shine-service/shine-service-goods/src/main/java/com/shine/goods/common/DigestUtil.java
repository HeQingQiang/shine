package com.shine.goods.common;

import com.shine.goods.property.Property;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class DigestUtil {
	

	private static String KEY = "";
	private static String PFXUrl = "";
	private static String PFXPass = "";
	private static String CERUrl = "";

	public DigestUtil()  {
		
		try{
			DigestUtil.KEY = Property.getKey();
		}catch(RuntimeException e){
			throw new RuntimeException("从配置文件读取商户安全码配置失败:" + e);
		}
		
		if(KEY == "" && PFXUrl == "" || KEY == null && PFXUrl == null ){
			throw new RuntimeException("签名工具类初始化失败, 配置文件 KEY 与  PFXUrl 不同同时为空" );
		}
		
	}
	


	/**
	 * 签名方法 : 判断签名类型自动签名
	 * @param data
	 * @return
	 */
	public String sign(Map<String, String> data){
				
		if(data.get("signType")==null||data.get("signType")==""){
			throw new RuntimeException("计算签名失败    signType 签名类型不能为空!  " );
		}else if("MD5".equals(data.get("signType"))){
			if(KEY.length()!=32){
				throw new RuntimeException("计算签名失败   KEY长度不正确,请检查!  " );
			}
			return signMD5(data,KEY);
		}else if("RSA".equals(data.get("signType"))){
			try {
				return signRSA(buildWaitingForSign(data),loadPrivateKeyByPFX(PFXUrl, PFXPass));
				
			} catch (RuntimeException e) {
				throw new RuntimeException("RSA签名异常 :\n " + e);
			}
		}else{
			throw new RuntimeException("计算签名失败   不支持的签名类型!  " );
		}

	}

	/**
	 * 验证签名
	 * @param data
	 * @return
	 */
	public  boolean  verify(Map<String, String> data){
		
		String sign = data.get("sign");
		
		if(data.get("signType")==null||data.get("signType")==""){
			throw new RuntimeException("验证签名失败    signType 签名类型不能为空!  " );
			
		}else if("MD5".equals(data.get("signType"))){
			if(KEY.length()!=32){
				throw new RuntimeException("验证签名失败    KEY长度异常,请检查!  " );
			}
			return verifyWithMD5(data,KEY);
		}else if("RSA".equals(data.get("signType"))){
			try {
				return verifyWithRSA(data,loadPublicKeyByCER(CERUrl));
			} catch (RuntimeException e) {
				System.out.println("验证签名失败:\n " + e);
			}
			
		}else{
			throw new RuntimeException("验证签名失败:  不支持的签名类型!  " );
		}
		
		return false;
	}

	/**
	 * 从CER文件中加载公钥
	 * 
	 * @param CERUrl
	 * @return
	 */
	public static PublicKey loadPublicKeyByCER(String CERUrl) {

		try {
			File file = new File(CERUrl);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf
					.generateCertificate(new FileInputStream(file));
			PublicKey publicKey = cert.getPublicKey();
			String publicKeyString = Base64.encode(publicKey.getEncoded());

//			 System.out.println("-----------------CER publicKey star--------------------");
//			 System.out.println(publicKeyString);
//			 System.out.println("-----------------CER publicKey end-------------------");

			return publicKey;

		} catch (Exception e) {
			System.out.println("解析证书出错！");
			e.printStackTrace();
		}
		return null;

	}

    /***
     * 从PFX文件里加载私钥
     * @param PFXUrl
     * @param strPassword
     * @return
     */
	public static PrivateKey loadPrivateKeyByPFX(String PFXUrl,
                                                 String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(PFXUrl);
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
			}
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();

//			 System.out.println("--------PFX  PrivateKey star-------");
//			 System.out.println(Base64.encodeBase64String(prikey.getEncoded()));
//			 System.out.println("--------PFX  PrivateKey end-------");

			return prikey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /***
     * 从PFX文件里加载公钥
     * @param PFXUrl
     * @param strPassword
     * @return
     */
	public static PublicKey loadPublicKeyByPFX(String PFXUrl, String strPassword) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(PFXUrl);
			char[] nPassword = null;
			if ((strPassword == null) || strPassword.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = strPassword.toCharArray();
			}
			ks.load(fis, nPassword);
			fis.close();
			Enumeration enumas = ks.aliases();
			String keyAlias = null;
			if (enumas.hasMoreElements())// we are readin just one certificate.
			{
				keyAlias = (String) enumas.nextElement();
			}
			PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
			Certificate cert = ks.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();

//			 System.out.println("--------PFX  PublicKey star-------");
//			 System.out.println(Base64.encodeBase64String(pubkey.getEncoded()));
//			 System.out.println("--------PFX  PublicKey end-------");

			return pubkey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有参数, 不允许用户传入多个同名参数.如果传入，则获取首个参数值
	 * 
	 * @param request     ServletRequest
	 * @return 所有参数Map
	 */
	public static Map<String, String> getParameters(ServletRequest request) {
		Map<String, String> params = new HashMap();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String[] values = request.getParameterValues(name);
			if (values == null || values.length == 0) {
				continue;
			}
			String value = values[0];
			// 注意：这里是判断不为null,没有包括空字符串的判断。
			if (value != null) {
				params.put(name, value);
			}
		}
		return params;
	}

    /***
     * 构造签名字符串
     * @param dataMap
     * @return
     */
	public static String buildWaitingForSign(Map<String, String> dataMap) {
		
		if (dataMap == null || dataMap.size() == 0) {
			throw new IllegalArgumentException("请求数据不能为空");
		}

		Map<String, String> treeMap = new TreeMap<String, String>(dataMap);

		StringBuilder stringToSign = new StringBuilder();

		for (Entry<String, String> entry : treeMap.entrySet()) {
			if (entry.getKey().equals("sign")) {
				continue;
			}
			stringToSign.append(entry.getKey()).append("=").append(entry.getValue()).append("&");	
		}
		
		stringToSign.deleteCharAt(stringToSign.length() - 1);

		return StringUtils.trimToEmpty(stringToSign.toString());
	}

	/**
	 * RSA私钥签名(使用商户证书私钥)
	 * 
	 * @param waitToSignStr
	 *            待签字符串
	 * @param privateKey
	 *            商户证书私钥
	 * @return 签名base64编码结果
	 */
	public static String signRSA(String waitToSignStr, PrivateKey privateKey) {
		try {
			byte[] dataBytes = waitToSignStr.getBytes("UTF-8");
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(privateKey);
			signature.update(dataBytes);
			byte[] result = signature.sign();
			return Base64.encode(result);
		} catch (Exception e) {
			throw new RuntimeException("RAS私钥签名失败:" + e.getMessage());
		}
	}

    /***
     *
     * @param waitToSignStr
     * @param key
     * @param function
     * @return
     */
	public static String signMD5(String waitToSignStr , String key , String function ) {
		
		if(function!="SHA-256"){
			function="MD5";
		}
		try {
			waitToSignStr = waitToSignStr.concat(key);
			
			System.out.println("waitToSignStr:"+waitToSignStr);   //在控制台里打印出签名字符串, 方便调试签名  可以删掉
			
			byte[] toDigest = waitToSignStr.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance(function);
			md.update(toDigest);
			return new String(Hex.encodeHex(md.digest()));
		} catch (Exception e) {
			throw new RuntimeException("签名计算失败!");
		}
	}
	
	/**
	 * md5签名得到sign字符串
	 * @param data
	 * @param key
	 * @return
	 */
	public static String signMD5(Map<String, String> data, String key) {
		String signType = data.get("signType");
		return signMD5(buildWaitingForSign(data), key , signType);
	}

	/**
	 * 验证签名
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static boolean verifyWithMD5(Map<String, String> data, String key) {

		String signType = data.get("signType");
		String sign = data.get("sign");
	
		if(sign.equals(signMD5(buildWaitingForSign(data),key,signType))){
			return true;
		}else{
			return false;
		}	
	}

	/**
	 * RSA私钥签名(使用商户证书私钥)
	 * 
	 * @param request   从request对象中获取签名内容
	 * @param privateKey
	 * @return 签名base64编码结果
	 */
	public static String signWithRSA(ServletRequest request, PrivateKey privateKey) {
		return signRSA(buildWaitingForSign(getParameters(request)), privateKey);
	}
	
	/**
	 * 获取请求参数链接字符串
	 * 
	 * @param gateWayUrl 网关请求地址
	 * @param  map  存放参数的HashMap
	 * @param privateKey  从PFX文件中获取到的私钥
	 * @return  响应请求易极付的请求url链接
	 */
	public static String getRequestUrl(String gateWayUrl , Map<String, String> map, PrivateKey privateKey) {
		
		if(gateWayUrl.contains("http://")||gateWayUrl.contains("https://")){
			gateWayUrl="http://"+gateWayUrl;
		}
		return  gateWayUrl+"?"+buildWaitingForSign(map)+"&sign="+signRSA(buildWaitingForSign(map),privateKey);
	}
	
	/**
	 * 公钥验签(使用网关证书公钥)
	 * 
	 * @param waitToSignStr
	 *            待签字符串
	 * @param sign
	 *            网关的签名
	 * @param publicKey
	 *            网关的公钥
	 * @return 验证结果 true:通过,false:未通过
	 */
	public static boolean verifyWithRSA(String waitToSignStr, String sign , PublicKey publicKey) {
		try {
			byte[] dataBytes = waitToSignStr.getBytes("UTF-8");
			byte[] signBytes = Base64.decode(sign);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(publicKey);
			signature.update(dataBytes);
			return signature.verify(signBytes);
		} catch (Exception e) {
			throw new RuntimeException("RSA公钥验签失败: " + e.getMessage());
		}
	}

    /***
     *
     * @param waitToSignStr
     * @param YJFSign
     * @param publicKeyStr
     * @return
     */
	public static boolean verifyWithRSA(String waitToSignStr, String YJFSign, String publicKeyStr) {

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKeyStr);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));
			return verifyWithRSA(waitToSignStr, YJFSign, pubKey);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

    /***
     *
     * @param request
     * @param YJFSign
     * @param publicKey
     * @return
     */
	public static boolean verifyWithRSA(ServletRequest request, String YJFSign, PublicKey publicKey) {
		return verifyWithRSA(buildWaitingForSign(getParameters(request)), YJFSign,publicKey);
	}

	/***
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public static boolean verifyWithRSA(Map<String, String> data, PublicKey publicKey) {
		return verifyWithRSA(buildWaitingForSign(data), data.get("sign"),publicKey);
	}

    /***
     *
     * @param request
     * @param YJFSign
     * @param publicKey
     * @return
     */
	public static boolean verifyWithRSA(ServletRequest request, String YJFSign, String publicKey) {
		return verifyWithRSA(buildWaitingForSign(getParameters(request)), YJFSign,publicKey);
	}
}