package io.github.pleuvoir.message;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pleuvoir.message.util.Bootstrap;
import io.github.pleuvoir.message.util.Bootstrap.Profile;


/**
 * 启动器，第一个参数为启动环境，参考：{@link Profile}，若不设置参数按照默认的环境启动：{@link #DEFAULT_PROFILE}
 *
 */
public class ServiceBootstrap {

	private static Logger logger = LoggerFactory.getLogger(ServiceBootstrap.class);

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
