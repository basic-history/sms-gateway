package io.github.pleuvoir.message.gateway;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.api.SmsService;
import io.github.pleuvoir.message.channel.api.ChannelCenterService;
import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.model.dto.MsgChannelDTO;
import io.github.pleuvoir.message.channel.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.common.RspCode;
import io.github.pleuvoir.message.exception.SmsException;
import io.github.pleuvoir.message.util.validator.HibernateValidatorUtils;
import io.github.pleuvoir.message.util.validator.ValidationResult;

/**
 * 短信服务实现
 * @author pleuvoir
 *
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ChannelCenterService channelCenterService;

	@Override
	public String sendSmsCode(SmsCodeDTO smsCodeDTO) throws SmsException {

		ValidationResult validResult = HibernateValidatorUtils.validateEntity(smsCodeDTO);
		if (validResult.isHasErrors()) {
			LOGGER.warn("短信发送参数校验失败，参数：" + smsCodeDTO.toJSON());
			throw new SmsException(validResult.getErrorMessageOneway());
		}

		// from db
		// String channelCode = ChannelEnum.JIGUANG.getCode();
		String channelCode = "1";
		
		MsgChannelDTO msgChannelDTO = new MsgChannelDTO();
		msgChannelDTO.setChannelCode(channelCode);
		
		try {
			channelCenterService.sendSmsCode(msgChannelDTO, smsCodeDTO);
		} catch (ChannelServiceException e) {
			LOGGER.error("渠道异常，请求参数信息：{}， 失败原因:{}", smsCodeDTO.toJSON(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error("渠道异常，请求参数信息：{}， 失败原因:{}", smsCodeDTO.toJSON(), e.getMessage());
			throw new SmsException(RspCode.ERROR);
		}

		return null;
	}

}
