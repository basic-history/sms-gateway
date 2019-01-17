package io.github.pleuvoir.sms.gateway.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.message.gateway.service.internal.PubParamService;
import io.github.pleuvoir.sms.gateway.BaseTest;

public class PubParamServiceTest extends BaseTest {

	@Autowired
	private PubParamService pubParamService;

	@Test
	public void test() {
		pubParamService.getSmsCacheTime();
		pubParamService.getSmsCacheTime();
	}
}
