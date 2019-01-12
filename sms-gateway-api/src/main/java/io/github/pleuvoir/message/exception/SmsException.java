package io.github.pleuvoir.message.exception;

import io.github.pleuvoir.message.common.RspCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 短信异常
 */
@Getter
@Setter
public class SmsException extends Exception {

	private static final long serialVersionUID = -163867460119033563L;

	private RspCode rspCode;

	private String msg;

	public SmsException() {
		super();
	}

	public SmsException(RspCode rspCode, String msg) {
		super();
		this.rspCode = rspCode;
		this.msg = msg;
	}

	public SmsException(String message, Throwable cause) {
		super(message, cause);
		this.msg = message;
	}

	public SmsException(String message) {
		super(message);
		this.msg = message;
	}

	public SmsException(Throwable cause) {
		super(cause);
	}

	public SmsException(RspCode rspCode) {
		super();
		this.rspCode = rspCode;
	}

}
