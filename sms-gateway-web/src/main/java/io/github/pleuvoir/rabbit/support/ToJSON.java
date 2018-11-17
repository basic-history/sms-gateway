package io.github.pleuvoir.rabbit.support;

import com.alibaba.fastjson.JSON;

public interface ToJSON {

	default String toJSON(){
		return JSON.toJSONString(this);
	}
}
