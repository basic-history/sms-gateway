package io.github.pleuvoir.message.util.net;

import java.io.Serializable;
import java.util.Map;

import io.github.pleuvoir.message.common.ToJSON;

/**
 * 请求返回响应对象
 */
public class ResponseObj implements Serializable, ToJSON {

	private static final long serialVersionUID = -8615005349716770154L;

	private Map<String, String> respHeaders;
	private String respMsg;

	public Map<String, String> getRespHeaders() {
		return respHeaders;
	}

	public void setRespHeaders(Map<String, String> respHeaders) {
		this.respHeaders = respHeaders;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
}
