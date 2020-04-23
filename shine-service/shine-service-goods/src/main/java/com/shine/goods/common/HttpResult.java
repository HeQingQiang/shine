package com.shine.goods.common;
/** create by zhangpu date:2015年3月14日 */

import java.io.File;
import java.util.Map;

public class HttpResult {
	private int status;
	private Map<String, String> headers;
	private String body;
	private byte[] bytes;

	private String message;

	private File localFile = null;
	private String fileName = null;

	private boolean isSuccess = false;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public File getLocalFile() {
		return localFile;
	}

	public void setLocalFile(File localFile) {
		this.localFile = localFile;
	}

	public String getFileName() {
    try {
      String content = headers.get("Content-disposition");

      if(content!=null || content!=""){
    	  return content.split("=")[1].replaceAll("\"", "");
      }else{
    	  return this.fileName;
      }
    } catch (Exception e) {
      return this.fileName;
    }
  }

	public void SetFileName(String string) {
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return String.format("HttpResult: {status:%s, headers:%s,body:%s}",
				status, headers, body == null ? null : body.replaceAll(
						"[^\u4e00-\u9fa5]", ""));
	}

}
