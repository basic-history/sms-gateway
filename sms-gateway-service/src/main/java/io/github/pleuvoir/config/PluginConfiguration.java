package io.github.pleuvoir.config;

import org.springframework.context.annotation.Configuration;

import io.github.pleuvoir.rabbit.autoconfigure.EnableRabbitMQPlugin;

@EnableRabbitMQPlugin(location = "config/profiles/[profile]/rabbitmq-[profile].properties")
@Configuration
public class PluginConfiguration {

}
