package io.github.pleuvoir.message.gateway.service.internal.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.pleuvoir.message.gateway.dao.pub.PubParamDao;
import io.github.pleuvoir.message.gateway.model.po.PubParamPO;
import io.github.pleuvoir.message.gateway.service.internal.PubParamService;

@Service
public class PubParamServiceImpl implements PubParamService {

	/** 缓存注解@Cacheable的key参数通用表达式 */
	public static final String CACHEABLE_KEY_EXPRESSION = "#root.targetClass.getSimpleName() + ':' + #root.methodName";
	/** 缓存注解@Cacheable的value参数通用表达式 */
	public static final String CACHEABLE_VALUE = "0";

	@Autowired
	private PubParamDao pubParamDao;

	@Cacheable(key = CACHEABLE_KEY_EXPRESSION, value = CACHEABLE_VALUE)
	@Override
	public Integer getSmsCacheTime() {
		return pubParamDao.getIntegerValue(PubParamPO.CODE_SMS_CACAHE_TIME);
	}

}
