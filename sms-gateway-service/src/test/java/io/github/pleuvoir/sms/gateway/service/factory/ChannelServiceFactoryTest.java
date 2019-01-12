package io.github.pleuvoir.sms.gateway.service.factory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.enums.ChannelEnum;
import io.github.pleuvoir.message.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.sms.gateway.service.BaseTest;

public class ChannelServiceFactoryTest extends BaseTest {

	@Autowired
	private ChannelServiceFactory channelServiceFactory;

	@Test
	public void test() {
		ChannelService channelService = channelServiceFactory.getChannelService(ChannelEnum.JIGUANG.getCode());
		channelService.sendSmsCode(new ChannelSmsMsgDTO());
	}
}