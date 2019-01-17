package io.github.pleuvoir.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pleuvoir.message.api.SmsService;
import io.github.pleuvoir.message.api.TimeService;
import io.github.pleuvoir.message.channel.model.dto.SendVerifyCodeDTO;
import io.github.pleuvoir.message.channel.model.dto.SmsCodeDTO;
import io.github.pleuvoir.message.exception.SmsException;
import io.github.pleuvoir.message.model.vo.ResultMessageVO;
import io.github.pleuvoir.rabbit.model.NormalMessage;
import io.github.pleuvoir.rabbit.producer.NormalMessageProducer;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private TimeService timeService;
	@Autowired
	private SmsService smsService;

	@Autowired
	private NormalMessageProducer mormalMessageProducer;

	@GetMapping("/now")
	public ResultMessageVO<String> time(@Validated SendVerifyCodeDTO dto, BindingResult result) {
		if (result.hasErrors()) {
			return returnParamError(result);
		}
		ResultMessageVO<String> vo = ResultMessageVO.success();
		String now = timeService.now();
		logger.info("当前时间：{}", now);
		vo.setData(now);
		return vo;
	}

	@GetMapping("/mq")
	public ResultMessageVO<String> mq(String id) {
		ResultMessageVO<String> vo = ResultMessageVO.success();

		NormalMessage mqMessage = new NormalMessage();
		mqMessage.setId(id);
		mormalMessageProducer.send(mqMessage);

		vo.setData(mqMessage.toJSON());
		return vo;
	}
	
	@GetMapping("/sms")
	public ResultMessageVO<String> sms(SendVerifyCodeDTO dto) {
		ResultMessageVO<String> vo = ResultMessageVO.success();

		
		SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
		smsCodeDTO.setPhone("1860927xxxx");
		smsCodeDTO.setCode("2580");
		smsCodeDTO.setVerCode("123");
		try {
			smsService.sendSmsCode(smsCodeDTO);
		} catch (SmsException e) {
			e.printStackTrace();
			vo.setFail(e.getMsg());
		}
		return vo;
	}

}
