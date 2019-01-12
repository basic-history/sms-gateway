package io.github.pleuvoir.message.factory;

import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.exception.ChannelException;

public interface ChannelServiceFactory {

	ChannelService getChannelService(String channelCode) throws ChannelException;
}
