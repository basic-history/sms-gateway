package io.github.pleuvoir.message.factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.channel.ServiceChannel;

@Service
public class SimpleChannelServiceFatory implements ChannelServiceFactory, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(SimpleChannelServiceFatory.class);
	
	private final Map<String, ChannelService> channelServiceCache = new ConcurrentHashMap<>(12);

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public ChannelService getChannelService(String channelCode) {
		logger.info("获取短信通道，通道编号：{}", channelCode);
		return this.channelServiceCache.get(channelCode);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		Collection<ChannelService> channelServices = applicationContext.getBeansOfType(ChannelService.class, false, false).values();
		
		channelServices.forEach(channelService -> {

			Class<? extends ChannelService> clazz = channelService.getClass();
			if (clazz.isAnnotationPresent(ServiceChannel.class)) {
				ServiceChannel serviceChannel = clazz.getAnnotation(ServiceChannel.class);
				this.channelServiceCache.put(serviceChannel.value().getCode(), channelService);

				if (logger.isDebugEnabled()) {
					logger.debug("初始化短信通道，{} -> {}", serviceChannel.value().getCode(),
							serviceChannel.value().getName());
				}
			}
		});
		
		logger.info("短信通道工厂初始化完成，目前可用通道编号：{}", Arrays.asList(this.channelServiceCache.keySet().toArray()));
	}

}
