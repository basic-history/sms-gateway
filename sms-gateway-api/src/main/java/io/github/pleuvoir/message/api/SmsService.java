package io.github.pleuvoir.message.api;

import io.github.pleuvoir.message.channel.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.exception.SmsException;

/**
 * 短信服务
 *
 */
public interface SmsService {
	
	/**
	 * 发送短信验证码
	 * @param smsCode
	 */
	String sendSmsCode(SmsCodeDTO smsCode) throws SmsException;

}
	