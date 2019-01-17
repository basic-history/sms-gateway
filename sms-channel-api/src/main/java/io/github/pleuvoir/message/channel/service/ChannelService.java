package io.github.pleuvoir.message.channel.service;

import io.github.pleuvoir.message.channel.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.channel.model.dto.ChannelSmsMsgDTO;

public interface ChannelService {

	/**
	 * 发送短信验证码
	 */
	ChannelResultDTO sendSmsCode(ChannelSmsMsgDTO channelSmsDTO);

}
