package io.github.pleuvoir.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import io.github.pleuvoir.common.RspCode;
import io.github.pleuvoir.model.vo.ResultMessageVO;

public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 用于检查参数错误，发现错误会返回
	 */
	protected <T> ResultMessageVO<T> returnParamError(BindingResult br) {
		if (br.hasErrors()) {
			ResultMessageVO<T> result = new ResultMessageVO<>();
			String errorMessage = "请求参数错误";
			if (br.hasGlobalErrors()) {
				errorMessage = br.getGlobalError().getDefaultMessage();
			} else if (br.hasFieldErrors()) {
				errorMessage = br.getFieldError().getDefaultMessage();
			}
			logger.warn(errorMessage);
			return result.setResult(RspCode.INVALID_ARGUMENTS, errorMessage);
		}
		return null;
	}
}
