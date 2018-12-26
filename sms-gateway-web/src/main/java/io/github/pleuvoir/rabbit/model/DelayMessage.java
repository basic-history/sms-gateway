package io.github.pleuvoir.rabbit.model;

import java.time.LocalDateTime;

import io.github.pleuvoir.message.common.ToJSON;

public class DelayMessage implements ToJSON {

	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 开始时间
	 */
	private LocalDateTime beginTime;

	// getter and setter
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(LocalDateTime beginTime) {
		this.beginTime = beginTime;
	}

}
