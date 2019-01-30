package io.github.pleuvoir.message.service.internal.impl;

import io.github.pleuvoir.message.dao.biz.MsgChannelDao;
import io.github.pleuvoir.message.model.po.PubMsgChannelPO;
import io.github.pleuvoir.message.service.internal.MsgChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgChannelServiceImpl implements MsgChannelService {

    @Autowired
    private MsgChannelDao msgChannelDao;

    @Override
    public PubMsgChannelPO getUsabeChannel(String code) {
        PubMsgChannelPO msgChannelPO = new PubMsgChannelPO();
        msgChannelPO.setCode(code);
        msgChannelPO.setStatus(PubMsgChannelPO.STATUS_YES);
        return msgChannelDao.selectOne(msgChannelPO);
    }
}
