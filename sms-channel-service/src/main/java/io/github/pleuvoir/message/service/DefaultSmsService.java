package io.github.pleuvoir.message.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.api.SmsService;
import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.exception.ChannelException;
import io.github.pleuvoir.message.exception.SmsException;
import io.github.pleuvoir.message.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.message.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.util.SmsExceptionTranslator;
import io.github.pleuvoir.message.util.validator.HibernateValidatorUtils;
import io.github.pleuvoir.message.util.validator.ValidationResult;

/**
 * 短信服务实现
 * @author pleuvoir
 *
 */
@Service("smsService")
public class DefaultSmsService implements SmsService {

	private static Logger logger = LoggerFactory.getLogger(DefaultSmsService.class);

	@Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private ChannelServiceFactory channelServiceFactory;

	@Override
	public String sendSmsCode(SmsCodeDTO smsCode) throws SmsException {

		ValidationResult validResult = HibernateValidatorUtils.validateEntity(smsCode);
		if (validResult.isHasErrors()) {
			logger.warn("短信发送参数校验失败，参数：" + smsCode.toJSON());
			throw new SmsException(validResult.getErrorMessageOneway());
		}

		// from db
		//String channelCode = ChannelEnum.JIGUANG.getCode();
		String channelCode = "1";

		ChannelService channelService;
		try {
			channelService = channelServiceFactory.getChannelService(channelCode);
		} catch (ChannelException e) {
			throw SmsExceptionTranslator.convertSmsException(e);
		}
		
		taskExecutor.execute(() -> {
			// 请求通道发送信息
			ChannelSmsMsgDTO channelSmsMsgDTO = new ChannelSmsMsgDTO();
			ChannelResultDTO result = channelService.sendSmsCode(channelSmsMsgDTO);

			if (result == null || !ChannelResultDTO.SUCCESS_CODE.equals(result.getCode())) {

			} else {
				
			}
		});
		return null;
	}

}
