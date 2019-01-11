package io.github.pleuvoir.message.factory;

import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.channel.ServiceChannel;
import io.github.pleuvoir.message.exception.SmsException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimpleChannelServiceFatory implements ChannelServiceFactory {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public ChannelService getChannelService(String channelCode) {
		log.info("获取短信通道，编号：{}", channelCode);

		Map<String, ChannelService> channelServices = applicationContext.getBeansOfType(ChannelService.class, false,
				false);

		Iterator<String> iterator = channelServices.keySet().iterator();
		while (iterator.hasNext()) {
			ChannelService channelService = channelServices.get(iterator.next());
			ServiceChannel serviceChannel = channelService.getClass().getAnnotation(ServiceChannel.class);
			if (channelCode.equals(serviceChannel.value().getCode())) {
				log.info("获取到短信通道：{}", serviceChannel.value().getName());
				return channelService;
			}
		}
		
		return null;
	}

}
