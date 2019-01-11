package io.github.pleuvoir.message.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public enum ChannelEnum {

	/** 阿里云 **/
	ALIYUN("01", "aliyun"),
	/** 极光 **/
	JIGUANG("02", "jiguang"),
	/** 聚通达 **/
	TENCENT("03", "tencent");

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private String name;

	private ChannelEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	
	public static String getCodeByName(String name) {
		for (ChannelEnum channel : ChannelEnum.values()) {
			if (channel.getName().equals(name)) {
				return channel.getCode();
			}
		}
		return StringUtils.EMPTY;
	}
}
