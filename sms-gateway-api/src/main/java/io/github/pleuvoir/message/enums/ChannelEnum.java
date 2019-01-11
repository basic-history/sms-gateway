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

	/**
	 * 通过名称获取编码，忽略大小写，剔除空格
	 * @param name	通道名称
	 * @return	通道编码
	 */
	public static String getCodeByName(String name) {
		for (ChannelEnum channel : ChannelEnum.values()) {
			if (channel.getName().equals(StringUtils.trim(name).toLowerCase())) {
				return channel.getCode();
			}
		}
		return StringUtils.EMPTY;
	}
}
