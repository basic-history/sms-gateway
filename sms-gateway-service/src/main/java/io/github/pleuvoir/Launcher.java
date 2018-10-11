package io.github.pleuvoir;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pleuvoir.common.Bootstrap;
import io.github.pleuvoir.common.Bootstrap.Profile;


/**
 * 启动器，第一个参数为启动环境，参考：{@link Profile}，若不设置参数按照默认的环境启动：{@link #DEFAULT_PROFILE}
 * @author pleuvoir
 *
 */
public class Launcher {

	private static Logger logger = LoggerFactory.getLogger(Launcher.class);

	/** 默认的启动环境：开发 */
	public static final Profile DEFAULT_PROFILE = Profile.DEV;

	public static void main(String[] args) {
		Profile profile = null;
		if (ArrayUtils.isNotEmpty(args) && StringUtils.isNotBlank(args[0])) {
			logger.info("设置启动环境：{}", args[0]);
			profile = Profile.toEnum(args[0]);
		}
		if (profile == null) {
			logger.info("未设置启动环境，使用默认环境启动：{}", DEFAULT_PROFILE);
			profile = DEFAULT_PROFILE;
		}
		Bootstrap.startup("classpath:config/application-context.xml", profile);
	}
}
