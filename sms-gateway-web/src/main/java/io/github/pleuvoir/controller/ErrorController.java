package io.github.pleuvoir.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.pleuvoir.common.RspCode;
import io.github.pleuvoir.model.vo.ErrorResultMessageVO;


@RestController
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping("/400")
	public ErrorResultMessageVO error400() {
		return new ErrorResultMessageVO(RspCode.ERROR, "参数格式错误");
	}

	@RequestMapping("/404")
	public ErrorResultMessageVO error404() {
		return new ErrorResultMessageVO(RspCode.ERROR, "未定义的接口");
	}

	@RequestMapping("/405")
	public ErrorResultMessageVO error405() {
		return new ErrorResultMessageVO(RspCode.ERROR, "不支持的请求方式");
	}
}
