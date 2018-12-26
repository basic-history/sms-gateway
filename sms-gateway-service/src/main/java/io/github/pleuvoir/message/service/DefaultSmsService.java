package io.github.pleuvoir.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.api.SmsService;
import io.github.pleuvoir.message.exception.SmsException;
import io.github.pleuvoir.message.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.util.validator.HibernateValidatorUtils;
import io.github.pleuvoir.message.util.validator.ValidationResult;

/**
 * 默认的短信服务实现
 * @author pleuvoir
 *
 */
@Service("smsService")
public class DefaultSmsService implements SmsService {

	private static Logger logger = LoggerFactory.getLogger(DefaultSmsService.class);

	@Override
	public String sendSmsCode(SmsCodeDTO smsCode) throws SmsException {

		ValidationResult validResult = HibernateValidatorUtils.validateEntity(smsCode);
		if (validResult.isHasErrors()) {
			logger.error("短信发送参数校验失败，参数：" + smsCode.toJSON());
			throw new SmsException(validResult.getErrorMessageOneway());
		}
		return null;
	}

}
