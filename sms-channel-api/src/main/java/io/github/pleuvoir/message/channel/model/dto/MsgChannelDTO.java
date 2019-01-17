package io.github.pleuvoir.message.channel.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 短信渠道信息
 * @author pleuvoir
 *
 */
@Data
public class MsgChannelDTO implements Serializable {

	private static final long serialVersionUID = 1307165110480329286L;

	private String channelCode;
}
