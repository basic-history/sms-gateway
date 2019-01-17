package io.github.pleuvoir.message.channel.api;

import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.model.dto.MsgChannelDTO;
import io.github.pleuvoir.message.channel.model.dto.SmsCodeDTO;

/**
 * 渠道服务提供者
 * @author pleuvoir
 *
 */
public interface ChannelCenterService {

	/**
	 * 发送短信验证码
	 * @param msgChannelDTO	渠道信息
	 * @param smsCode	短信验证码参数
	 * @return
	 * @throws ChannelServiceException	通道异常，未获取到通道时抛出
	 */
	String sendSmsCode(MsgChannelDTO msgChannelDTO, SmsCodeDTO smsCode) throws ChannelServiceException;
}
