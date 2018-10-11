package io.github.pleuvoir.model.vo;

import java.io.Serializable;

import io.github.pleuvoir.common.RspCode;
import io.github.pleuvoir.common.ToJSON;

public class ResultMessageVO<T> implements Serializable, ToJSON {

	private static final long serialVersionUID = 5144774580823377954L;

	private final String code;		// 返回码

	private final String msg;		// 返回信息

	private T data; 		// 数据


	private ResultMessageVO(RspCode rspCode, String msg) {
		this.code = rspCode.getCode();
		this.msg = msg;
	}

	public static <T> ResultMessageVO<T> success() {
		return new ResultMessageVO<>(RspCode.SUCCESS, RspCode.SUCCESS.getMsg());
	}

	public static <T> ResultMessageVO<T> fail(String message) {
		return new ResultMessageVO<>(RspCode.FAIL, message);
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public ResultMessageVO<T> setData(T data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "ResultMessageVO: " + toJSON();
	}
}
