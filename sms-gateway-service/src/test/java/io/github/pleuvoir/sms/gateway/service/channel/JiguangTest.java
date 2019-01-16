package io.github.pleuvoir.sms.gateway.service.channel;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.message.channel.jiguang.JiguangChannelService;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.sms.gateway.service.BaseTest;

public class JiguangTest extends BaseTest {

	@Autowired
	JiguangChannelService jiguangChannelService;

	@Test
	public void test() {
		ChannelSmsMsgDTO channelSmsMsg = new ChannelSmsMsgDTO();
		channelSmsMsg.setPhone("18609276666");
		channelSmsMsg.setAccessKeyId("e60dfc9a92aaeca4363b4065");
		channelSmsMsg.setAccessKeySecret(null);
		channelSmsMsg.setTemplateCode("149794");
		channelSmsMsg.setContent(RandomStringUtils.randomNumeric(6));
		channelSmsMsg.setTemplateParam("code");
		channelSmsMsg.setSendMsgUrl("https://api.sms.jpush.cn/v1/messages");
		jiguangChannelService.sendSmsCode(channelSmsMsg);
	}

}
