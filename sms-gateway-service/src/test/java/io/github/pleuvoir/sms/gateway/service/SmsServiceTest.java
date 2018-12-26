package io.github.pleuvoir.sms.gateway.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.message.api.SmsService;
import io.github.pleuvoir.message.exception.SmsException;
import io.github.pleuvoir.message.model.dto.SmsCodeDTO;

public class SmsServiceTest extends BaseTest {

	@Autowired
	private SmsService smsService;

	@Test
	public void test() {
		SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
		smsCodeDTO.setPhone("1860927xxxx");
		smsCodeDTO.setCode("2580");
		smsCodeDTO.setVerCode("123");
		try {
			smsService.sendSmsCode(smsCodeDTO);
		} catch (SmsException e) {
			e.printStackTrace();
		}
	}
}
