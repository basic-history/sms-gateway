package io.github.pleuvoir.message.dao.pub;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.github.pleuvoir.message.model.po.PubParamPO;

public interface PubParamDao extends BaseMapper<PubParamPO> {

	List<PubParamPO> findByCodes(String[] codes);

	PubParamPO getParamByCode(@Param("code") String code);

	String getStringValue(@Param("code") String code);

	BigDecimal getDecimalValue(@Param("code") String code);

	Integer getIntegerValue(@Param("code") String code);

	Boolean getBooleanValue(@Param("code") String code);

}

