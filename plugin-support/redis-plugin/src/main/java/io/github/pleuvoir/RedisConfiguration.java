package io.github.pleuvoir;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.github.pleuvoir.cache.CacheService;
import io.github.pleuvoir.cache.RedisCacheService;
import io.github.pleuvoir.kit.PropertiesWrap;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

	private String location;
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Bean
	public PropertiesWrap redisPropertiesWrap() throws IOException {
		if (StringUtils.isBlank(location)) {
			throw new IllegalArgumentException(" :-) redis 配置文件获取失败，未设置 location");
		}
		Properties pro = PropertiesLoaderUtils.loadProperties(new ClassPathResource(location));
		return new PropertiesWrap(pro);
	}

	@Bean(name="jedisConnectionFactory")
	public JedisConnectionFactory getJedisConnectionFactory(@Qualifier("redisPropertiesWrap") PropertiesWrap config) throws IOException{
		
		JedisPoolConfig pool = new JedisPoolConfig();
		pool.setMaxTotal(config.getInteger("redis.pool.maxTotal", JedisPoolConfig.DEFAULT_MAX_TOTAL));
		pool.setMaxIdle(config.getInteger("redis.pool.maxIdle", JedisPoolConfig.DEFAULT_MAX_IDLE));
		pool.setMaxWaitMillis(config.getLong("redis.pool.maxWaitMillis", JedisPoolConfig.DEFAULT_MAX_WAIT_MILLIS));
		pool.setTestOnBorrow(config.getBoolean("redis.pool.testOnBorrow", JedisPoolConfig.DEFAULT_TEST_ON_BORROW));
		
		String hostAndPortStr = config.getString("redis.hostAndPort");
		if (StringUtils.isBlank(hostAndPortStr)) {
			logger.error(" :-) redis 配置获取 [hostAndPortStr] 失败，请检查");
			throw new IllegalArgumentException(" :-) redis 配置获取 [hostAndPortStr] 失败，请检查");
		}
		JedisConnectionFactory factory = null;
		String[] nodes = hostAndPortStr.split(",");
		//单机模式
		if(nodes.length == 1){
			String[] node = nodes[0].split(":");
			if(node.length == 2){
				factory = new JedisConnectionFactory();
				factory.setHostName(node[0]);
				factory.setPort(Integer.parseInt(node[1]));
			}
		}
		else{
			//集群模式
			RedisClusterConfiguration redisCluster = new RedisClusterConfiguration();
			RedisClusterNode clusterNode = null;
			for (int i = 0; i < nodes.length; i++) {
				String[] node = nodes[i].split(":");
				if(node.length != 2){
					continue;
				}
				clusterNode = new RedisClusterNode(node[0], Integer.parseInt(node[1]));
				redisCluster.addClusterNode(clusterNode);
			}
			factory = new JedisConnectionFactory(redisCluster);
		}
		factory.setPoolConfig(pool);
		factory.setDatabase(config.getInteger("redis.database", 0));
		factory.setPassword(config.getString("redis.password", ""));
		return factory;
	}
	
	@Bean(name="redisTemplate")
	public RedisTemplate<String,Object> getRedisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory){
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer(Charset.forName("UTF-8")));
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		return redisTemplate;
	}
	
	@Bean(name="stringRedisTemplate")
	public StringRedisTemplate getStringRedisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory){
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
		return stringRedisTemplate;
	}
	
	@Bean(name="redisPluginCacheService")
	public CacheService getCacheService() {
		return new RedisCacheService();
	}
	
}
