package io.github.pleuvoir.message.model.vo;


import io.github.pleuvoir.message.common.RspCode;


public class ErrorResultMessageVO extends ResultMessageVO<Object> {


	private static final long serialVersionUID = -2555372231794911241L;

	public ErrorResultMessageVO() {
	}

	public ErrorResultMessageVO(RspCode rspCode, String msg) {
		super(rspCode, msg);
	}

	public ErrorResultMessageVO(String code, String msg) {
		super(code, msg);
	}

}
