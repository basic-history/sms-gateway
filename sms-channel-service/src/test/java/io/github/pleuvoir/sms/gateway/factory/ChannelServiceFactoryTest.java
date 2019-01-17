package io.github.pleuvoir.sms.gateway.factory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.channel.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.message.channel.service.ChannelService;
import io.github.pleuvoir.sms.gateway.BaseTest;

public class ChannelServiceFactoryTest extends BaseTest {

	@Autowired
	private ChannelServiceFactory channelServiceFactory;

	@Test
	public void test() throws ChannelServiceException {
		ChannelService channelService = channelServiceFactory.getChannelService("02");
		channelService.sendSmsCode(new ChannelSmsMsgDTO());
	}
}
