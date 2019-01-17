package io.github.pleuvoir.message.channel.model.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 通道返回结果
 */
@Data
public class ChannelResultDTO implements Serializable {

	private static final long serialVersionUID = -6879939688256141256L;

	private String code;
	private String msg;
	private String msgId; // 消息ID

	public static final String SUCCESS_CODE = "OK";
	public static final String ERROR_CODE = "ERROR";

	public ChannelResultDTO() {
	}

	public ChannelResultDTO(String code) {
		this.code = code;
	}

	public ChannelResultDTO(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}