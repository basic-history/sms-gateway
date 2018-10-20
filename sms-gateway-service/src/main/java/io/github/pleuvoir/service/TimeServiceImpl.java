package io.github.pleuvoir.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.api.TimeService;
import io.github.pleuvoir.cache.CacheService;
import io.github.pleuvoir.util.DateFormat;

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
