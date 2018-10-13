package io.github.pleuvoir.model.vo;

import java.io.Serializable;

import io.github.pleuvoir.common.RspCode;
import io.github.pleuvoir.common.ToJSON;

public class ResultMessageVO<T> implements Serializable, ToJSON {
	
	private static final long serialVersionUID = 7781316982699830573L;

	private String code;// 返回码

	private String msg;// 返回信息

	private T data; // 数据

	public ResultMessageVO() {
	}

	public ResultMessageVO(RspCode rspCode) {
		this.code = rspCode.getCode();
		this.msg = rspCode.getMsg();
	}

	public ResultMessageVO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultMessageVO(RspCode rspCode, String msg) {
		this.code = rspCode.getCode();
		this.msg = msg;
	}

	public ResultMessageVO<T> setResult(RspCode rspCode, String msg) {
		this.code = rspCode.getCode();
		this.msg = msg;
		return this;
	}


	public static <T> ResultMessageVO<T> success() {
		return new ResultMessageVO<>(RspCode.SUCCESS, RspCode.SUCCESS.getMsg());
	}

	public static <T> ResultMessageVO<T> fail(String message) {
		return new ResultMessageVO<>(RspCode.FAIL, message);
	}

	public ResultMessageVO<T> setSuccess() {
		return this.setResult(RspCode.SUCCESS, RspCode.SUCCESS.getMsg());
	}

	public ResultMessageVO<T> setFail(String message) {
		return this.setResult(RspCode.FAIL, message);
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public ResultMessageVO<T> setMsg(String msg) {
		this.msg = msg;
		return this;
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
