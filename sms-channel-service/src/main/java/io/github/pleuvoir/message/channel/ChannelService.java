package io.github.pleuvoir.message.channel;

import io.github.pleuvoir.message.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;

public interface ChannelService {

	/**
	 * 发送短信验证码
	 */
	ChannelResultDTO sendSmsCode(ChannelSmsMsgDTO channelSmsDTO);

}
