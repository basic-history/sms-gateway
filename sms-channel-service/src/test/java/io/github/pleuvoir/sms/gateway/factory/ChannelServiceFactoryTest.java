package io.github.pleuvoir.sms.gateway.factory;


import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.channel.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.message.channel.service.ChannelService;
import io.github.pleuvoir.sms.gateway.BaseTest;

public class ChannelServiceFactoryTest extends BaseTest {

	@Resource(name = "namedChannelServiceFatory")
	private ChannelServiceFactory namedChannelServiceFatory;

	@Resource(name = "simpleChannelServiceFatory")
	private ChannelServiceFactory simpleChannelServiceFatory;

	@Test
	public void simpleChannelServiceFatoryTest() throws ChannelServiceException {
		ChannelService channelService = simpleChannelServiceFatory.getChannelService("03");
		channelService.sendSmsCode(new ChannelSmsMsgDTO());
	}

	@Test
	public void qualifiedNameChannelServiceFatoryTest() throws ChannelServiceException {
		ChannelService beanNameChannelService = namedChannelServiceFatory
				.getChannelService("io.github.pleuvoir.message.channel.tencent.TencentChannelService");
		Assert.assertNotNull(beanNameChannelService);
		
		ChannelService qualifiedChannelService = namedChannelServiceFatory
				.getChannelService("tencentChannelService");
		Assert.assertNotNull(qualifiedChannelService);
	}
}
