package io.github.pleuvoir.message.channel.tencent;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.channel.ServiceChannel;
import io.github.pleuvoir.message.enums.ChannelEnum;
import io.github.pleuvoir.message.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ServiceChannel(ChannelEnum.TENCENT)
public class TencentChannelService implements ChannelService {

	@Override
	public ChannelResultDTO sendSmsCode(ChannelSmsMsgDTO channelSmsDTO) {
		log.info("【聚通达】发送短信验证码，请求通道入参：{}", channelSmsDTO.toJSON());
		return null;
	}

}
