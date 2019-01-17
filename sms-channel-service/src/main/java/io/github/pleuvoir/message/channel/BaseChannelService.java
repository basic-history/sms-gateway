package io.github.pleuvoir.message.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pleuvoir.message.channel.model.dto.ChannelSmsMsgDTO;

public class BaseChannelService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected String generateSendErrorMessage(ChannelSmsMsgDTO channelSmsMsgDTO) {
		return "短信发送失败请求，参数：".concat(channelSmsMsgDTO.toJSON());
	}

}
