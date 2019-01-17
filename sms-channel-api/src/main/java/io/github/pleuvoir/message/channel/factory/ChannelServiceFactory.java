package io.github.pleuvoir.message.channel.factory;

import io.github.pleuvoir.message.channel.exception.ChannelServiceException;
import io.github.pleuvoir.message.channel.service.ChannelService;

public interface ChannelServiceFactory {

	ChannelService getChannelService(String channelCode) throws ChannelServiceException;
}
