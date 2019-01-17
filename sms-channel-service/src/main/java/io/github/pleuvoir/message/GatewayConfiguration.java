package io.github.pleuvoir.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.github.pleuvoir.rabbit.autoconfigure.EnableRabbitMQPlugin;

@EnableRabbitMQPlugin(location = "config/profiles/[profile]/rabbitmq-[profile].properties")
@Configuration
public class GatewayConfiguration {

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(Math.min(Runtime.getRuntime().availableProcessors() + 1, 32));
		threadPoolTaskExecutor.setMaxPoolSize(150);
		threadPoolTaskExecutor.setQueueCapacity(1000);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		threadPoolTaskExecutor.setThreadNamePrefix("async-");
		return threadPoolTaskExecutor;
	}
}
