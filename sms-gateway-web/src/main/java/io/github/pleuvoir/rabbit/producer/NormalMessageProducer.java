package io.github.pleuvoir.rabbit.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.pleuvoir.rabbit.model.NormalMessage;
import io.github.pleuvoir.rabbit.support.RabbitConst;

@Component
public class NormalMessageProducer {
	
	private static Logger logger = LoggerFactory.getLogger(NormalMessageProducer.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	
	public void send(NormalMessage msg){

		logger.info("【普通消息生产者】准备发送消息，payload：{}", msg.toJSON());
	
		rabbitTemplate.convertAndSend(RabbitConst.Normal.EXCHANGE, RabbitConst.Normal.ROUTING_KEY, msg.toJSON());
	}

}
