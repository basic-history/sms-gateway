package io.github.pleuvoir.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pleuvoir.api.TimeService;
import io.github.pleuvoir.model.dto.SendVerifyCodeDTO;
import io.github.pleuvoir.model.vo.ResultMessageVO;

@RestController
@RequestMapping("/datetime")
public class TestController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private TimeService timeService;

	@GetMapping("/now")
	public ResultMessageVO<String> time(@Validated SendVerifyCodeDTO dto, BindingResult br) {
		if (br.hasErrors()) {
			return returnParamError(br);
		}
		ResultMessageVO<String> vo = ResultMessageVO.success();
		String now = timeService.now();
		logger.info("当前时间：{}", now);
		vo.setData(now);
		return vo;
	}

}
