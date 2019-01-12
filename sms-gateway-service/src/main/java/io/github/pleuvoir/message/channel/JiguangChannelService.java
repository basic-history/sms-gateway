package io.github.pleuvoir.message.channel;

import io.github.pleuvoir.message.enums.ChannelEnum;
import io.github.pleuvoir.message.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServiceChannel(ChannelEnum.JIGUANG)
public class JiguangChannelService implements ChannelService {

	@Override
	public ChannelResultDTO sendSmsCode(ChannelSmsMsgDTO channelSmsDTO) {
		log.info("【极光】发送短信验证码，请求通道入参：{}", channelSmsDTO.toJSON());
		return null;
	}

}
