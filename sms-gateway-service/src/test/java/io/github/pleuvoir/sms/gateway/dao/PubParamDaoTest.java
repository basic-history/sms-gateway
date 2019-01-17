package io.github.pleuvoir.sms.gateway.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.message.gateway.dao.pub.PubParamDao;
import io.github.pleuvoir.message.gateway.model.po.PubParamPO;
import io.github.pleuvoir.sms.gateway.BaseTest;

public class PubParamDaoTest extends BaseTest{

	@Autowired
	private PubParamDao pubParamDao;
	
	@Test
	public void test() {
		PubParamPO paramByCode = pubParamDao.getParamByCode(PubParamPO.CODE_SMS_CACAHE_TIME);
		System.out.println(JSON.toJSON(paramByCode));
	}
}
