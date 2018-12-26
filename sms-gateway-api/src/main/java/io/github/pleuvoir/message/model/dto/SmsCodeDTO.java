package io.github.pleuvoir.message.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.github.pleuvoir.message.common.ToJSON;


/**
 * 短信验证码 DTO
 *
 */
public class SmsCodeDTO implements Serializable, ToJSON {

	private static final long serialVersionUID = 240690879220466336L;

	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$", message = "手机号格式有误")
	private String phone; // 接收短信的手机号

	@NotBlank(message = "验证码不能为空")
	private String code; // 验证码

	@NotBlank(message = "验证业务编号不能为空")
	private String verCode; // 验证业务编号


	/**登录验证码**/
	public static String VER_CODE_LOGIN = "001";


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
