package com.shine.goods.common;

import org.apache.http.HttpResponse;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;


public class Util {

	/** 
	 * 将request取出的map转换成可读map
	 *  
	 * @param request 
	 * @return 
	 */  
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
	    //  从request里获取到包装过的map
	    Map properties = request.getParameterMap();
	    //   
	    Map<String, String> returnMap = new TreeMap<String, String>();
	    Iterator entries = properties.entrySet().iterator();
	    Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}


	/**
	 * map转换签名字符串
	 *
	 * @param data
	 * @return
	 */
	public static String signString(Map<String, String> data) {

		Map<String, String> treeMap = new TreeMap<String, String>(data);

		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : treeMap.entrySet()) {
			if (entry.getKey().equals("sign")) {
				continue;
			}
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}


	/***
	 * 生成请求链接
	 *
	 * @param data
	 * @return
	 */
	public static String getLink(String gatewayUrl , Map<String, String> data , boolean isUrlencode) {

		StringBuilder sb = new StringBuilder();

	    if (!gatewayUrl.startsWith("http")) {
	    	gatewayUrl = "http://" + gatewayUrl;
	    }

	    if(!gatewayUrl.contains("?")){
	    	gatewayUrl = gatewayUrl+"?";
	    }

		for (Entry<String, String> entry : data.entrySet()) {
			try {
				if(isUrlencode){
					sb.append(String.format("%s=%s&", entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8")));
				}else{
					sb.append(String.format("%s=%s&",entry.getKey(),entry.getValue(),"UTF-8"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 删除掉最后拼接的&符号
		sb.deleteCharAt(sb.length() - 1);
		return gatewayUrl+sb.toString();
	}



	/**
	 * response转换Json字符串
	 *
	 * @param response
	 */
	public static String responesParseJsionStr(HttpResponse response) {

		String result = null;

		if (null != response) {

			try {

				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				StringBuffer sb = new StringBuffer();

				String str = br.readLine();
				while (str != null) {
					sb.append(str + (char) 13 + (char) 10);
					str = br.readLine();
				}
				result = sb.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}


	/**
	 * JSON字符串转换成map
	 *
	 * @param jsonObjStr
	 * @return Map
	 *
	 */
	public static Map<String, String> JsonStrParseMap(String jsonObjStr){

		JsonMapper jsmper = new JsonMapper();

		@SuppressWarnings("unchecked")
        HashMap<String, Object> map = jsmper.fromJson(jsonObjStr, HashMap.class);  //从json字符串里转换出HashMap

		String impString = jsonObjStr;  //吧json字符串保存到临时变量�?

		Map<String, String> resultMap = new TreeMap<String, String>();


		if(map.isEmpty()){

			return null ;

		}else{
			for (Entry<String, Object> m : map.entrySet()) {

				try{
					if(m.getValue()==null){
						continue;
					}
					if(m.getValue() instanceof String){
						resultMap.put(m.getKey(), m.getValue().toString());

					}else if(m.getValue() instanceof Integer){
						resultMap.put(m.getKey(), String.valueOf(m.getValue()));

					}else if(m.getValue() instanceof Double){
						DecimalFormat df   = new DecimalFormat("######.00");  //强制double对象保留两位小数
						resultMap.put(m.getKey(), df.format(m.getValue()));

					}else  if(m.getValue() instanceof Boolean){
						resultMap.put(m.getKey(), String.valueOf(m.getValue()).equals("true")?"true":"false");

					} else if(m.getValue() instanceof ArrayList) {
						//把json内部的数组对象直接作为字符串
						String str = impString.substring(impString.indexOf(m.getKey()+"\":")+m.getKey().length()+2,impString.indexOf("]")+1);
						resultMap.put(m.getKey(),str);

					}else if(m.getValue() instanceof LinkedHashMap){
						String str = impString.substring(impString.indexOf(m.getKey()+"\":")+m.getKey().length()+2,impString.indexOf("}")+1);
						resultMap.put(m.getKey(),str);

					}else{
						System.out.println("意外的处理json字符串转换类型:"+ m.getValue().getClass().getName());
					}
				}catch(Exception e){

				}
			}

		}

		return resultMap;
	}


	/**
	 * response转换成map
	 *
	 * @param response
	 */
	public static Map<String, String> responesParseMap(HttpResponse response) {
	return JsonStrParseMap(responesParseJsionStr(response));
}



	/**
	 * 把易极付响应的参数转成的map中的某个字段的值写成文件
	 * @param fileName  文件写入的目录和文件名
	 * @param content   base64字符串内容
	 */
	public static boolean base64toFile(String fileName, String content ){

		try{
			File file = new File(fileName);
			File fileParent = file.getParentFile();

			if(!fileParent.exists()){        //如果目录不存在先创建目录
				if(fileParent.mkdirs()){
			    	System.out.println("创建新文件夹"+fileParent+"成功!");
			    }
			}
			if (!file.exists() || !file.isFile()) {  //如果文件不存在 创建新文件
				if(file.createNewFile()){
					System.out.println("创建新文件"+fileName+"成功!");
				}
			}

			BASE64Decoder decoder = new BASE64Decoder();
			
			if(appendFileForByte(fileName,decoder.decodeBuffer(content))){
				return true;
			}else{
				return false;
			}

		}catch (Exception e){
			System.out.println("写入文件发生异常:"+e);
			return false;
		}
	}
	
	
	  /***
	   * 写入文件
	   * @param data
	   * @param file
	   * @throws IOException
	   */
	  public static void writeToFile(byte[] data, File file) throws IOException {
	    OutputStream out = null;
	    try {
	      out = new FileOutputStream(file);
	      for (int i = 0; i < data.length; i++) {
	        out.write(data[i]);
	      }
	    } finally {
	      if (out != null) {
	        try {
	          out.close();
	        } catch (Exception e) {
	          throw new IOException("数据流关闭失败", e);
	        }
	      }
	    }
	  }
	
	/***
	 * 将byte[] 类型对象写入成文件 适合生成图片等二进制文件
	 * @param fileName
	 * @param content
	 */
	public static boolean appendFileForByte(String fileName, byte[] content) {
		RandomAccessFile randomFile = null;
		try {

			File file = new File(fileName);
			File fileParent = file.getParentFile();
			
			if(!fileParent.exists()){        //如果目录不存在先创建目录 
				if(fileParent.mkdirs()){
			    	System.out.println("创建新文件夹"+fileParent+"成功!");
			    }
			}
			if (!file.exists() || !file.isFile()) {  //如果文件不存在 创建新文件
				if(file.createNewFile()){
					System.out.println("创建新文件"+fileName+"成功!");
				}
			}
			
			// content=new String(content.getBytes("UTF-8"), "UTF-8");//编码转换

			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write(content);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	 * 将String类型对象写入成文件   适合写入txt等文本文件
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void appendFileForString(String fileName, String content) {
		RandomAccessFile randomFile = null;
		try {

			File file = new File(fileName);
			File fileParent = file.getParentFile();
			
			if(!fileParent.exists()){        //如果目录不存在先创建目录 
				if(fileParent.mkdirs()){
			    	System.out.println("创建新文件夹"+fileParent+"成功!");
			    }
			}
			if (!file.exists() || !file.isFile()) {  //如果文件不存在 创建新文件
				if(file.createNewFile()){
					System.out.println("创建新文件"+fileName+"成功!");
				}
			}
			// content=new String(content.getBytes("UTF-8"), "UTF-8");//编码转换

			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes("utf-8"));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/***
	 * 获取用户ip地址
	 * @param request
	 * @return
	 */
    public static String getIpAddress(HttpServletRequest request) {
	    	
	        String ip = request.getHeader("x-forwarded-for");
	        
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	        return ip;  
	    }  
	
    
    

    
    
	/**
	 * 追加文件：使用RandomAccessFile
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void appendTextFile(String fileName, String content) {
		RandomAccessFile randomFile = null;
		try {

			// content=new String(content.getBytes("UTF-8"), "UTF-8");//编码转换

			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes("utf-8"));

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    
    
    
    
}










	
	

