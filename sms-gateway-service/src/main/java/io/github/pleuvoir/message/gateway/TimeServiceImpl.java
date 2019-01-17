package io.github.pleuvoir.message.gateway;

import io.github.pleuvoir.message.api.TimeService;
import io.github.pleuvoir.message.util.DateFormat;
import io.github.pleuvoir.redis.cache.CacheService;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("timeService")
public class TimeServiceImpl implements TimeService {

	@Autowired
	private CacheService cacheService;

	@Override
	public String now() {
		String key = cacheService.generateKey("redis：", "now");
		String oldValue = cacheService.getString(key);
		if (StringUtils.isBlank(oldValue)) {
			String newVal = DateFormat.DATETIME_DEFAULT.format(LocalDateTime.now());
			cacheService.set(key, newVal, 5);
			return newVal;
		} else {
			return oldValue;
		}
	}

}
