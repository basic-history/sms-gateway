package io.github.pleuvoir.message.gateway.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pleuvoir.message.exception.ChannelException;
import io.github.pleuvoir.message.exception.SmsException;


public final class SmsExceptionTranslator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsExceptionTranslator.class);

	public static SmsException convertSmsException(Throwable ex) {

		if (ex instanceof SmsException) {
			return (SmsException) ex;
		}
		if (ex instanceof ChannelException) {
			return new SmsException(ex.getMessage());
		}

		LOGGER.error("【短信网关内部抛出的异常或错误】", ex);

		// 从异常链尝试获取最初的异常信息
		String message;
		try {
			message = getRootCause(ex).getMessage();
		} catch (Exception e) {
			return new SmsException("系统繁忙，请稍后再试");
		}
		return new SmsException(message);
	}

	public static Throwable getRootCause(final Throwable throwable) {
		final List<Throwable> list = getThrowableList(throwable);
		return list.size() < 2 ? null : list.get(list.size() - 1);
	}

	public static List<Throwable> getThrowableList(Throwable throwable) {
		final List<Throwable> list = new ArrayList<>();
		while (throwable != null && !list.contains(throwable)) {
			list.add(throwable);
			throwable = throwable.getCause();
		}
		return list;
	}
}
