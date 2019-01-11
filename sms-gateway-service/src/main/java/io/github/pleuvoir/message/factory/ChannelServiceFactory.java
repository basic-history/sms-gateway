package io.github.pleuvoir.message.factory;

import io.github.pleuvoir.message.channel.ChannelService;

public interface ChannelServiceFactory {

	ChannelService getChannelService(String channelCode);
}
