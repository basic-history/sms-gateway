package io.github.pleuvoir.rabbit.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.rabbit.model.NormalMessage;
import io.github.pleuvoir.rabbit.support.RabbitConst;


@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(RabbitConst.Normal.QUEUE),
				exchange = @Exchange(RabbitConst.Normal.EXCHANGE),
				key = RabbitConst.Normal.ROUTING_KEY
		)
)
@Component
public class NormalMessageConsumer {

	private static Logger logger = LoggerFactory.getLogger(NormalMessageConsumer.class);

	
	@RabbitHandler
	public void handler(String data) {
		
		logger.info("【普通消息消费者】已接收到消息，payload：{}", JSON.parseObject(data, NormalMessage.class).toJSON());
	}

}
