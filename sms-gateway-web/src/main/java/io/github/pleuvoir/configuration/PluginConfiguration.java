package io.github.pleuvoir.configuration;

import org.springframework.context.annotation.Configuration;

import io.github.pleuvoir.rabbit.autoconfigure.EnableRabbitMQPlugin;

@EnableRabbitMQPlugin(location = "config/[profile]/rabbitmq-[profile].properties")
@Configuration
public class PluginConfiguration {

}
