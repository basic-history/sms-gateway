package io.github.pleuvoir.message.exception;

/**
 * 短信异常
 */
public class SmsException extends Exception {

	private static final long serialVersionUID = -163867460119033563L;

	private String msg;

	public SmsException() {
		super();
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
