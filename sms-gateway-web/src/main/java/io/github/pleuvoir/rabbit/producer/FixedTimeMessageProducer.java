package io.github.pleuvoir.rabbit.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.pleuvoir.rabbit.model.FixedTimeMessage;
import io.github.pleuvoir.rabbit.support.RabbitConst;
import io.github.pleuvoir.rabbit.support.creator.FixedTimeQueue;

@Component
public class FixedTimeMessageProducer {
	
	private static Logger logger = LoggerFactory.getLogger(FixedTimeMessageProducer.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	
	public void send(FixedTimeMessage msg){

		logger.info("【定时消息生产者】准备发送消息，payload：{}", msg.toJSON());

//		FixedTimeQueue fixedTimeQueue = FixedTimeQueue.create(msg.getExcutetime())
//				.deadLetterExchange(RabbitConst.FixedTime.EXCHANGE)
//				.deadLetterRoutingKey(RabbitConst.FixedTime.ROUTING_KEY)
//				.requestId(msg.getId())
//				.commit();
//		
//		if (fixedTimeQueue.isAlive()) {
//			rabbitTemplate.convertAndSend(fixedTimeQueue.getExchange(), fixedTimeQueue.getRoutingKey(), msg.toJSON());
//		}
		
		 FixedTimeQueue.create(msg.getExcutetime())
				.deadLetterExchange(RabbitConst.FixedTime.EXCHANGE)
				.deadLetterRoutingKey(RabbitConst.FixedTime.ROUTING_KEY)
				.requestId(msg.getId())
				.commit()
				.sendMessageIfAlive(msg);
	}
	

}
