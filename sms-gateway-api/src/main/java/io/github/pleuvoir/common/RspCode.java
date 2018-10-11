package io.github.pleuvoir.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 返回码<br/>
 * 【此类不可随意修改】
 */
public enum RspCode {

	/** 成功*/
	SUCCESS("SUCCESS", "成功"),
	/** 错误 */
	ERROR("ERROR", "错误"), 								// 系统出现异常，该错误码不允许手动返回
	/** 操作失败 */
	FAIL("FAIL", "操作失败"), 							// 出现此错误表示告知前端弹出错误消息，故此错误码的错误消息会被前端使用
	/** 参数错误 */
	INVALID_ARGUMENTS("INVALID_ARGUMENTS", "参数错误"), 	// 请求的参数未通过校验时返回
	/** 接口已废除 */
	DEPRECATED_INTERFACE("DEPRECATED_INTERFACE", "过时的接口");

	private RspCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private final String code;

	private final String msg;

	public boolean isEquals(String code) {
		return StringUtils.equals(this.code, code);
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
