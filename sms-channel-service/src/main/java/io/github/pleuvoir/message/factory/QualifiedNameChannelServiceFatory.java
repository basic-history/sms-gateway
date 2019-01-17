package io.github.pleuvoir.message.factory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.vip.vjtools.vjkit.collection.CollectionUtil;
import com.vip.vjtools.vjkit.collection.MapUtil;

import io.github.pleuvoir.message.channel.enums.ChannelEnum;
import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.factory.ChannelServiceFactory;
import io.github.pleuvoir.message.channel.service.ChannelService;

@Service
public class QualifiedNameChannelServiceFatory implements ChannelServiceFactory {

	private static Logger logger = LoggerFactory.getLogger(QualifiedNameChannelServiceFatory.class);
	
	//通道实现，key 为通道名称，value 为通道实现类
	private final Map<String, ChannelService> channelServiceCache = new ConcurrentHashMap<>(12);

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public ChannelService getChannelService(String channelQualifiedName) throws ChannelServiceException {
		
		// 尝试从缓存中取
		ChannelService cnl = channelServiceCache.get(channelQualifiedName);
		if (cnl != null) {
			return cnl;
		}
		// 再次尝试
		try {
			Class<?> clazz = ClassUtils.getClass(channelQualifiedName);
			Object bean = applicationContext.getBean(clazz);
			if (bean instanceof ChannelService) {
				return (ChannelService) bean;
			}
		} catch (ClassNotFoundException e) {
			logger.warn("获取短信通道失败，全限定类名：{}", channelQualifiedName, e);
		}
		throw new ChannelServiceException("暂无可用的短信通道");
	}

	@PostConstruct
	public void init()  {
		Map<String, ChannelService> channelServiceMap = applicationContext.getBeansOfType(ChannelService.class);
		if (MapUtil.isNotEmpty(channelServiceMap)) {
			channelServiceMap.forEach((qualifiedName, channelService) -> channelServiceCache.put(qualifiedName, channelService));
		}
		logger.info("短信通道工厂初始化完成，目前已实现通道：{}", Arrays.asList(this.channelServiceCache.keySet().toArray()));
	}

}
