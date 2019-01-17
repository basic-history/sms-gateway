package io.github.pleuvoir.message.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolUtil;

import io.github.pleuvoir.message.channel.api.ChannelCenterService;
import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.channel.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.channel.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.message.channel.model.dto.MsgChannelDTO;
import io.github.pleuvoir.message.channel.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.channel.service.ChannelService;

/**
 * 短信服务实现
 * @author pleuvoir
 *
 */
@Service("channelCenterService")
public class ChannelCenterServiceProvider implements ChannelCenterService {

	private static Logger logger = LoggerFactory.getLogger(ChannelCenterServiceProvider.class);

	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ChannelServiceFactory channelServiceFactory;


	@Override
	public String sendSmsCode(MsgChannelDTO msgChannelDTO, SmsCodeDTO smsCode) throws ChannelServiceException {
		
		ChannelService channelService = channelServiceFactory.getChannelService(msgChannelDTO.getChannelCode());

		taskExecutor.execute(ThreadPoolUtil.safeRunnable(() -> {
			// 请求通道发送信息
			ChannelSmsMsgDTO channelSmsMsgDTO = new ChannelSmsMsgDTO();
			ChannelResultDTO result = channelService.sendSmsCode(channelSmsMsgDTO);

			if (result == null || !ChannelResultDTO.SUCCESS_CODE.equals(result.getCode())) {

			} else {

			}
		}));

		return null;
	}

}
