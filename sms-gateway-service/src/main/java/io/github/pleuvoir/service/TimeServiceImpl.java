package io.github.pleuvoir.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import io.github.pleuvoir.api.TimeService;
import io.github.pleuvoir.util.DateFormat;

@Service("timeService")
public class TimeServiceImpl implements TimeService {

	@Override
	public String now() {
		return DateFormat.DATETIME_DEFAULT.format(LocalDateTime.now());
	}

}
