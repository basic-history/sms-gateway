package io.github.pleuvoir.migration;

import org.apache.commons.lang3.StringUtils;

public enum Profile{
	/** 开发环境 */
	DEV("dev"),
	/** 测试环境 */
	TEST("test"),
	/** 生产环境 */
	PROD("prod");
	
	private String code;
	
	private Profile(String code) {
		this.code = code;
	}
	
	public String value() {
		return this.code;
	}
	
	@Override
	public String toString() {
		return value();
	}
	
	public static Profile toEnum(String code) {
		for(Profile p : Profile.values()) {
			if(StringUtils.equals(code, p.value())) {
				return p;
			}
		}
		return null;
	}
}
