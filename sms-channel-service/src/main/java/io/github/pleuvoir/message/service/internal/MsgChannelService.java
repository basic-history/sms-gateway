package io.github.pleuvoir.message.service.internal;

import io.github.pleuvoir.message.model.po.PubMsgChannelPO;

public interface MsgChannelService {

    /**
     * 按通道编号获取可用的通道信息
     * @param code  通道编号
     * @return  通道信息
     */
    PubMsgChannelPO getUsabeChannel(String code);
}
