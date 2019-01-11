package io.github.pleuvoir.message.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.channel.ServiceChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimpleChannelServiceFatory implements ChannelServiceFactory {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public ChannelService getChannelService(String channelCode) {
		log.info("获取短信通道，编号：{}", channelCode);

		Map<String, Object> channels = applicationContext.getBeansWithAnnotation(ServiceChannel.class);
		
		
		log.info(JSON.toJSONString(channels));
		
		
		return null;
	}

}
