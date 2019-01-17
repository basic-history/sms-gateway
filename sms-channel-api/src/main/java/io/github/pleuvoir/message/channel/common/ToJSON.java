package io.github.pleuvoir.message.channel.common;

import com.alibaba.fastjson.JSON;

public interface ToJSON {

    default String toJSON(){
        return JSON.toJSONString(this);
    }
}
