package com.shine.goods.common;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;


public class Https {

  // private static String contentType = "application/x-www-form-urlencoded";
  private static String contentType = "application/json;charset=UTF-8";

  /** 连接不够用的时候等待超时时间，一定要设置，而且不能太大，单位毫秒 */
  private static final int CONNECTION_REQUES_TTIMEOUT = 2 * 2000;
  /** 设置连接超时时间，单位毫秒。 */
  private static final int CONNECTION_TIMEOUT = 10 * 1000;
  /** 请求获取数据的超时时间，单位毫秒 */
  private static final int SOCKET_TIMEOUT = 20 * 1000;
  /** 连接池大小 */
  private static final int POOL_SIZE = 100;
  /** 每个路由的最大连接数 */
  private static final int MAX_PER_ROUTE = 50;

  /** 默认编码字符集 */
  private static final Charset ENCODE = Consts.UTF_8;

  private CloseableHttpClient httpClient;
  
 public String getUrl="";
 
 private String fileName="";


  // 请求重试处理
  private HttpRequestRetryHandler httpRequestRetryHandler =
      new HttpRequestRetryHandler() {
        public boolean retryRequest(
                IOException exception, int executionCount, HttpContext context) {
          if (executionCount >= 5) { // 如果已经重试了5次，就放弃
            return false;
          }
          if (exception instanceof NoHttpResponseException) { // 如果服务器丢掉了连接，那么就重试
            return true;
          }
          if (exception instanceof SSLHandshakeException) { // 不要重试SSL握手异常
            return false;
          }
          if (exception instanceof InterruptedIOException) { // 超时
            return false;
          }
          if (exception instanceof UnknownHostException) { // 目标服务器不可达
            return false;
          }
          if (exception instanceof ConnectTimeoutException) { // 连接被拒绝
            return false;
          }
          if (exception instanceof SSLException) { // ssl握手异常
            return false;
          }

          HttpClientContext clientContext = HttpClientContext.adapt(context);
          HttpRequest request = clientContext.getRequest();
          // 如果请求是幂等的，就再次尝试
          if (!(request instanceof HttpEntityEnclosingRequest)) {
            return true;
          }
          return false;
        }
      };

  public Https() throws NoSuchAlgorithmException, KeyManagementException {
    // http的链接工厂池
    ConnectionSocketFactory httpFactory = PlainConnectionSocketFactory.getSocketFactory();
    // https的链接工厂池
    SSLContext ctx = SSLContext.getInstance("SSL");  //SSL协议,已停用
//    SSLContext ctx = SSLContext.getInstance("TLSv1.2");
    ctx.init(
        null,
        new TrustManager[] {
          new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() {
              return null;
            }
          }
        },
        new SecureRandom());
    
    // 忽略https的SSL认证
    ConnectionSocketFactory httpsFactory =
        new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
    // 注册到主工厂
    Registry<ConnectionSocketFactory> registry =
        RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", httpFactory)
            .register("https", httpsFactory)
            .build();
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
    cm.setMaxTotal(POOL_SIZE);
    cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);

    // 请求cookie和超时的默认配置
    RequestConfig requestConfig =
        RequestConfig.custom()
            .setCookieSpec(CookieSpecs.STANDARD_STRICT) // 设置客户端cookie，该参数非常重要，决定成败
            .setConnectionRequestTimeout(CONNECTION_REQUES_TTIMEOUT)
            .setCircularRedirectsAllowed(true) // 允许循环重定向，该参数非常重要，决定成败
            .setConnectTimeout(CONNECTION_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .build();

    httpClient =
        HttpClients.custom()
            .setConnectionManager(cm)
            .setRetryHandler(httpRequestRetryHandler)
            .setDefaultRequestConfig(requestConfig)
            .build();
  }

  /**
   * post发送text请求
   *
   * @param url 请求的url
   * @param text 请求的body内容
   * @return 请求返回的结果
   * @throws IllegalStateException
   * @throws IOException
   */
  public HttpResult textPost(String url, String text) throws IllegalStateException, IOException {
    return strPost(url, text, ContentType.TEXT_PLAIN); // "text/plain"
  }

  
  
  /**
   * post发送form请求
   *
   * @param url 请求的url
   * @param form 键值对的参数字符串
   * @return 请求返回的结果
   * @throws IllegalStateException
   * @throws IOException
   */
  public HttpResult formPost(String url, String form) throws IllegalStateException, IOException {
    return strPost(
        url, form, ContentType.APPLICATION_FORM_URLENCODED); // "application/x-www-form-urlencoded"
  }

  
  
  /**
   * post发送基于字符串的参数请求
   *
   * @param url 请求的url
   * @param str 请求字符串
   * @param contentType 请求头类型
   * @return
   * @throws IllegalStateException
   * @throws IOException
   */
  public HttpResult strPost(String url, String str, ContentType contentType)
      throws IllegalStateException, IOException {
    if (str == null) str = "";
    StringEntity entity = new StringEntity(str, ENCODE);
    entity.setContentType(contentType.getMimeType());
    return post(url, entity, false);
  }

  /**
   * http请求
   *
   * @param url 请求的url
   * @param map 参数键值对
   * @param enableRedirect 遇到重定向是否重定向
   * @return 请求返回的结果
   * @throws IllegalStateException
   * @throws IOException
   */
  public  HttpResult mapPost(String url, Map<String, String> map, boolean enableRedirect)
      throws IllegalStateException, IOException {
    if (map == null || map.isEmpty()) return this.textPost(url, null);
    HttpPost httpPost = new HttpPost(url);
    List<NameValuePair> formParams = new ArrayList();
    Iterator iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, String> elem = (Entry<String, String>) iterator.next();
      formParams.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
    }
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, ENCODE);
    entity.setContentType(
        ContentType.APPLICATION_FORM_URLENCODED.getMimeType()); // "application/x-www-form-urlencoded"
    httpPost.setEntity(entity);
    return post(url, entity, enableRedirect);
  }

  /**
   * post请求
   *
   * @param url 地址
   * @param entity 请求实体
   * @param enableRedirect 遇到重定向是否重定向
   * @return
   * @throws IllegalStateException
   * @throws IOException
   */
  private  HttpResult post(String url, StringEntity entity, boolean enableRedirect)
      throws IllegalStateException, IOException {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(entity);
    return execute(httpPost, enableRedirect);
  }



  /**
   * post请求
   *
   * @param url 地址
   * @param enableRedirect 遇到重定向是否重定向
   * @return
   * @throws IllegalStateException
   * @throws IOException
   */
  private  HttpResult post(String url , boolean enableRedirect)
      throws IllegalStateException, IOException {
    HttpPost httpPost = new HttpPost(url);
    return execute(httpPost, enableRedirect);
  }



  /**
   * get 访问url, 获取返回值
   *
   * @param url
   * @return
   * @throws IllegalStateException
   * @throws IOException
   */
  public HttpResult httpGet(String url) throws IllegalStateException, IOException {
    return this.httpGet(url, null);
  }

    /***
     *
     * @param gatewayurl
     * @param dataMap
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
  public HttpResult httpGet(String gatewayurl, Map<String, String> dataMap)throws IllegalStateException, IOException {

    String url = this.normalizeUrl(gatewayurl, dataMap, ENCODE.name(), true, false);

//    System.out.println("这是url的内容:"+url);

//    String url1 = getLink(gatewayurl,dataMap);

    	HttpGet httpGet = new HttpGet(url);
    return execute(httpGet, false); // get请求会自动重定向
  }

  /**
   * 内部执行请求
   *
   * @param request
   * @param enableRedirect 遇到重定向是否重定向
   * @return
   * @throws IOException
   */
  private  HttpResult execute(HttpUriRequest request, boolean enableRedirect) throws IOException {
    CloseableHttpResponse response = null;
    HttpEntity entity = null;

    try {
      String location;
      response = httpClient.execute(request);
      int statusCode = response.getStatusLine().getStatusCode();

      // 下载对账单需要302重定向到下载路径
      if (enableRedirect && this.isRedirect(statusCode)) {
        Header locationHeader = response.getFirstHeader("Location");
        if (locationHeader != null) {
        	location = locationHeader.getValue();
          if (location != null) {
            Map<String, String> params = getQueryMap(location);
            String code = params.get("resultCode");
            if (code != null && code.equals("UNAUTHORIZED")) {
              throw new RuntimeException(
                  code + ":" + URLDecoder.decode(params.get("resultMessage")));
            }

//            System.out.println("location地址:"+location);

            if(location.indexOf(".xls")!=-1){fileName=location.substring(location.lastIndexOf("/")+1,location.indexOf(".xls")+4);}

            return this.httpGet(location);

          }
        }
      }

      HttpResult result = new HttpResult();
      result.setStatus(statusCode);
      Map<String, String> headers = readHeaders(response);
      result.setHeaders(headers);
      entity = response.getEntity();
      if (entity != null) {
        // 下载文件的方式
        if (headers.get("Content-Type") != null
            && (headers.get("Content-Type").indexOf("application/x-download") > -1
                || headers.get("Content-Type").indexOf("application/vnd.ms-excel") > -1
                || headers.get("Content-Type").indexOf("application/zip") > -1)) {

          result.setSuccess(true);

          result.setFileName(fileName);

          result.setMessage("对账单获取成功");

          result.setBytes(EntityUtils.toByteArray(entity));

        } else {
          result.setSuccess(false);
          result.setMessage("对账单获取失败");
          result.setBody(EntityUtils.toString(entity, getCharset(headers)));
        }
      }
      return result;
    } finally {
      if (entity != null) {
        EntityUtils.consume(entity);
      } // 关闭流 释放资源
      if (response != null) {
        response.close();
      }
      if (request != null) {
        if (request instanceof HttpPost) {
          ((HttpPost) request).releaseConnection();
        }
        if (request instanceof HttpGet) {
          ((HttpGet) request).releaseConnection();
        }
      }
    }
  }

  private Map<String, String> getQueryMap(String location) {
    if (location == null) {
      return new HashMap<String, String>();
    }
    if (location.indexOf("?") == -1) {
      return new HashMap<String, String>();
    }
    String[] args = location.substring(location.indexOf("?")).split("&");
    if (args == null) {
      return new HashMap<String, String>();
    }
    Map<String, String> params = new HashMap<String, String>();
    for (String s : args) {
      String[] p = s.split("=");
      if (p != null && p.length > 1) {
        params.put(p[0], p[1]);
      }
    }
    return params;
  }

  private Charset getCharset(Map<String, String> headers) {
    String contentType = headers.get("Content-Type");
    Charset encode = ENCODE;
    try {
      if (contentType != null) {
        String[] con = contentType.split("charset=");
        if (con.length > 1) {
          encode = Charset.forName(con[1].split(";")[0]);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return encode;
  }

  /**
   * 用于get请求参数拼接
   *
   * @param url 地址
   * @param dataMap 参数
   * @param charset 参数编码字符
   * @param enableUrlEncode 是否urlEncode
   * @param includeBlank value为空的情况是否拼接
   * @return
   */
  private String normalizeUrl(
      String url,
      Map<String, String> dataMap,
      String charset,
      boolean enableUrlEncode,
      boolean includeBlank)
      throws UnsupportedEncodingException {

    if (!url.startsWith("http")) {
    	url = "http://" + url;
    }

    if(!url.contains("?")){
    	url = url+"?";
    }

    if (dataMap != null && !dataMap.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      sb.append(url);
      String value = null;
      for (Entry<String, String> entry : dataMap.entrySet()) {
        if (StringUtils.isBlank(entry.getValue()) && includeBlank) {
          sb.append(entry.getKey()).append("=").append("&");
        } else {
          value = enableUrlEncode ? URLEncoder.encode(entry.getValue(), charset) : entry.getValue();
          sb.append(entry.getKey()).append("=").append(value).append("&");
        }
      }
      sb.deleteCharAt(sb.length() - 1);
      url = sb.toString();
    }
    return url;
  }




  //判断http请求是否是被重定向
  private boolean isRedirect(int statusCode) {
    return String.valueOf(statusCode).startsWith("3");
  }



  //读取response的Headers
  private Map<String, String> readHeaders(HttpResponse httpResponse) {
    Map<String, String> result = new HashMap<String, String>();
    List<String> headerValues = null;
    for (Header header : httpResponse.getAllHeaders()) {
      if (StringUtils.isNotBlank(header.getName())) {
        if (result.containsKey(header.getName())) {
          headerValues = Lists.newArrayList(StringUtils.split(result.get(header.getName()), ","));
          headerValues.add(header.getValue());
          result.put(header.getName(), StringUtils.join(headerValues.iterator(), ","));
        } else {
          result.put(header.getName(), header.getValue());
        }
      }
    }
    return result;
  }

  //关闭httpclient连接
  public void destroy() throws Exception {
    if (this.httpClient != null) {
      httpClient.close();
    }
  }

    /***
     *
     * @param url
     * @param dataMap
     * @param contentType
     * @return
     */
  public static String doPost(String url, Map<String, String> dataMap, String contentType) {
    String result = null;

    HttpEntity entity = null;
    System.out.println("http请求开始");
    try {
      RequestConfig requestConfig =
          RequestConfig.custom()
              .setSocketTimeout(SOCKET_TIMEOUT)
              .setConnectTimeout(CONNECTION_REQUES_TTIMEOUT)
              .build();

      HttpPost httpPost = new HttpPost(url);
      httpPost.setConfig(requestConfig);
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      for (Entry<String, ?> entry : dataMap.entrySet()) {
        if (entry.getValue() == null) {
          continue;
        } else {
          nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
      }
      httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

      CloseableHttpClient httpclient = HttpClients.createDefault();
      CloseableHttpResponse response = httpclient.execute(httpPost);
      System.out.println("responseCode:" + response.getStatusLine().getStatusCode());

      entity = response.getEntity();
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        result = EntityUtils.toString(entity, "UTF-8");
      }
    } catch (Exception e) {
    	System.out.println("http请求异常" + e);
      if (e instanceof ConnectTimeoutException) {
        result = "0";
      } else if (e instanceof SocketTimeoutException) {
        result = "9";
      }
    } finally {
      try {
        EntityUtils.consume(entity);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }


    /***
     *
     * @param gatewayUrl
     * @param data
     * @return
     * @throws IOException
     */
	public static String getHttpsLocationwhthGet(String gatewayUrl, Map<String, String> data ) throws IOException {

		URL realUrl = new URL(getLink(gatewayUrl,data));

        DataOutputStream dataOutputStream = null;

		System.out.println("获取location地址时的get链接串:"+realUrl);

		TrustManager[] tm = { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}
			public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) {
			}
			public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) {
			}
		} };
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, tm, new SecureRandom());
//			 SSLContext sc = SSLContext.getInstance("TLSv1.2");

			 sc.init(
			            null,
			            new TrustManager[] {
			              new X509TrustManager() {
			                public void checkClientTrusted(X509Certificate[] chain, String authType)
			                    throws CertificateException {}
			                public void checkServerTrusted(X509Certificate[] chain, String authType)
			                    throws CertificateException {}
			                public X509Certificate[] getAcceptedIssuers() {
			                  return null;
			                }
			              }
			            },
			            new SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

		// 设置通用的请求属性
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Accept-Charset", "UTF-8;");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		conn.setInstanceFollowRedirects(false);   //这一步很重要,设置为false 不自动处理重定向


		conn.setConnectTimeout(3000);
		conn.setReadTimeout(5000);

		// 建立实际的连接
		conn.connect();

		String location = null;

		if(conn.getResponseCode()==200){
			InputStreamReader reader =null;
	        BufferedReader reader2 =null;
			 reader = new InputStreamReader(conn.getInputStream(),"utf-8");
             reader2 = new BufferedReader(reader);
            String string = reader2.readLine();
			System.out.println("这是一个同步响应的结果:" + string);
		}else if(conn.getResponseCode()==302){
			location = conn.getHeaderField("Location");
		}
		conn.disconnect();
		if (location!=null) {
			return location;
		} else {
			return getLink(gatewayUrl,data);
		}
	}


    /***
     *
     * @param gatewayUrl
     * @param data
     * @param encoding
     * @return
     * @throws Exception
     */
	@SuppressWarnings("deprecation")
	public static HttpResponse httpPost(String gatewayUrl, Map<String, String> data, String encoding) throws Exception {
		HttpResponse response = null;
		CloseableHttpClient httpclient = null;

			SSLContext sslContext = SSLContexts.custom().useTLS()
					.loadTrustMaterial(null, new TrustStrategy() {
						public boolean isTrusted(X509Certificate[] chain, String authType) {// 信任所有证书
							return true;
						}
					}).build();

//			 SSLContext sslContext = SSLContext.getInstance("TLSv1.2");


			 sslContext.init(
			            null,
			            new TrustManager[] {
			              new X509TrustManager() {
			                public void checkClientTrusted(X509Certificate[] chain, String authType)
			                    throws CertificateException {}
			                public void checkServerTrusted(X509Certificate[] chain, String authType)
			                    throws CertificateException {}
			                public X509Certificate[] getAcceptedIssuers() {
			                  return null;
			                }
			              }
			            },
			            new SecureRandom());


			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext, new AllowAllHostnameVerifier());
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			//httpClient 4.X版本设置超时方法 4.3后过�?
//			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);


			//4.3版本超时设置
			RequestConfig reConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			HttpPost post = new HttpPost(gatewayUrl);
			post.setConfig(reConfig);

			StringBuilder sb = new StringBuilder();
			if (data != null) {
				sb.append("\n url 请求参数：[");
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, ?> entry : data.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
				}
				try {
					post.setEntity(new UrlEncodedFormEntity(nvps, encoding));
				} catch (Exception e) {
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("]");
			}

			response = httpclient.execute(post);

		return response;
	}

	/***
	 * 生成请求链接
	 *
	 * @param data
	 * @return
	 */
	public static String getLink(String gatewayUrl , Map<String, String> data) {

		StringBuilder sb = new StringBuilder();

		sb.append(gatewayUrl + "?");
		if(!data.isEmpty()){
			for (Entry<String, String> entry : data.entrySet()) {
				try {
					sb.append(String.format("%s=%s&", entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8")));
					// sb.append(String.format("%s=%s&",entry.getKey(),entry.getValue(),"UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 删除掉最后拼接的&符号
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}

		return "";

	}

    /***
     * 生成请求链接
     *
     * @param data
     * @return
     */
    public static String getQueryString(Map<String, String> data) {

        StringBuilder sb = new StringBuilder();

        if(!data.isEmpty()){
            for (Entry<String, String> entry : data.entrySet()) {
                try {
                    sb.append(String.format("%s=%s&", entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8")));
                    // sb.append(String.format("%s=%s&",entry.getKey(),entry.getValue(),"UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 删除掉最后拼接的&符号
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return "";

    }


    /***
     *
     * @param gatewayUrl
     * @param data
     * @return
     * @throws IOException
     */
    public static String getHttpsLocation(String gatewayUrl, Map<String, String> data) throws IOException {

        URL realUrl = new URL(gatewayUrl);

        TrustManager[] tm = {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tm, new SecureRandom());
            //			 SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // post参数
        StringBuilder postData = new StringBuilder();
        for (Entry<String, String> param : data.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

        // 设置通用的请求属性
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.addRequestProperty("Accept-Charset", "UTF-8;");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setInstanceFollowRedirects(false); //这一步很重要,设置为false 不自动处理重定向
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(5000);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        // 建立实际的连接
        conn.connect();

        String location = null;

        if (conn.getResponseCode() == 200) {
            InputStreamReader reader = null;
            BufferedReader reader2 = null;
            reader = new InputStreamReader(conn.getInputStream(), "utf-8");
            reader2 = new BufferedReader(reader);
            String string = reader2.readLine();
            System.out.println("这是一个同步响应的结果:" + string);
        } else if (conn.getResponseCode() == 302) {
            location = conn.getHeaderField("Location");
        }

        conn.disconnect();
        if (location != null) {
            return location;
        } else {
            return getLink(gatewayUrl, data);
        }
    }

}
