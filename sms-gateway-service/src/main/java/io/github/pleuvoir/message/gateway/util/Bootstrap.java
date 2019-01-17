package io.github.pleuvoir.message.gateway.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Bootstrap {

	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	private static GenericXmlApplicationContext context = null;

	/**
	 * 设置
	 * @param xml
	 * @param profile
	 */
	public static void startup(String xml, Profile profile) {
		if (context != null) {
			logger.warn("服务已启动，请勿多次执行");
			return;
		}
		context = new GenericXmlApplicationContext();
		context.getEnvironment().setActiveProfiles(profile.value());
		context.registerShutdownHook();
		context.load(xml);
		context.refresh();
		context.start();
		logger.info("服务已启动。");

		synchronized (Bootstrap.class) {
			for (;;) {
				try {
					Bootstrap.class.wait();
				} catch (InterruptedException ignored) {

				}
			}
		}
	}

	/**
	 * 环境
	 */
	public static enum Profile {
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
			for (Profile p : Profile.values()) {
				if (StringUtils.equals(code, p.value())) {
					return p;
				}
			}
			return null;
		}
	}
}
