package io.github.pleuvoir.message.channel.factory;

import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.service.ChannelService;

public interface ChannelServiceFactory {

	/**
	 * 获取通道服务实现
	 * @param channel	通道信息，可以是通道编号或者实现类的全限定类名
	 * @return	通道服务实现类
	 * @throws ChannelServiceException	未获取到实现时抛出
	 */
	ChannelService getChannelService(String channel) throws ChannelServiceException;
}
