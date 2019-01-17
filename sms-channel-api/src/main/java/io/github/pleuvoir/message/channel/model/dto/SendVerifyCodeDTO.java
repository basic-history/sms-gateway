package io.github.pleuvoir.message.channel.model.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import io.github.pleuvoir.message.channel.common.ToJSON;

/**
 * 发送验证码
 *
 */
public class SendVerifyCodeDTO implements Serializable, ToJSON {

	private static final long serialVersionUID = -2332614302265228637L;

	@NotBlank(message = "手机号不能为空")
	private String phone;

	@NotBlank(message = "业务编号不能为空")
	private String bizCode;

	private String ip;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return toJSON();
	}
}
