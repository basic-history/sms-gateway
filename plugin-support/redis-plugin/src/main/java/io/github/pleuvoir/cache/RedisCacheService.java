package io.github.pleuvoir.cache;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import io.github.pleuvoir.kit.PropertiesWrap;

/**
 * redis缓存实现
 */
public class RedisCacheService implements CacheService {
	
	private static final String KEY_REDIS_EXPIRE = "redis.expire";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name = "redisPropertiesWrap")
	private PropertiesWrap redisConfig;
	
	@Override
	public String generateKey(String prefix, String content){
		return prefix + content;
	}
	
	@Override
	public String generateKey(String prefix, String...content){
		return prefix + StringUtils.join(content, ":");
	}
	
	@Override
	public void set(String key, Object value, int second){
		if(value == null){
			return;
		}
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		redisTemplate.expire(key, second, TimeUnit.SECONDS);
	}
	
	@Override
	public void increment(String key, double value, int second){
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		ops.increment(key, value);
		redisTemplate.expire(key, second, TimeUnit.SECONDS);
	}
	
	@Override
	public void set(String key, Object value){
		set(key, value, redisConfig.getInteger(KEY_REDIS_EXPIRE, 60));
	}
	
	@Override
	public Object get(String key){
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		return ops.get(key);
	}
	
	@Override
	public String getString(String key) {
		Object obj = get(key);
		if(obj == null){
			return "";
		}
		return obj.toString();
	}

	@Override
	public Integer getInteger(String key) {
		Object obj = get(key);
		if(obj == null){
			return null;
		}
		return Integer.parseInt(obj.toString());
	}

	@Override
	public void updateExpire(String key, int second) {
		if(StringUtils.isEmpty(key)){
			return;
		}
		Object obj = get(key);
		if(obj != null){
			redisTemplate.expire(key, second, TimeUnit.SECONDS);
		}
	}
	
	@Override
	public Long getExpire(String key) {
		Long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
		return time;
	}
	
	@Override
	public boolean putIfExist(String key, Object value) {
		boolean isExist = putIfExist(key, value, redisConfig.getInteger(KEY_REDIS_EXPIRE, 60)); 
		return isExist;
	}

	@Override
	public boolean putIfExist(String key, Object value, int expireSeconds) {
		StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
		JdkSerializationRedisSerializer valueSerializer = (JdkSerializationRedisSerializer)redisTemplate.getValueSerializer();
		byte[] k = keySerializer.serialize(key);
		byte[] v = valueSerializer.serialize(value);
			
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Boolean rs = connection.setNX(k, v);
				connection.expire(k, expireSeconds);
				return rs;
			}
		});
	}
	
	/** 
     * 缓存中key对应的值增加数值long 
     * @param key  
     * @param incrValue 
     * @return 
     */  
	@Override
    public double incrBy(String key, double incrValue){
        //将key对应的数字加decrement。如果key不存在，操作之前，key就会被置为0。  
        //如果key的value类型错误或者是个不能表示成数字的字符串，  
        //就返回错误。这个操作最多支持64位有符号的正型数字。  
        Double result = (Double) redisTemplate.execute(new RedisCallback<Object>() {  
            @Override  
            public Double doInRedis(RedisConnection connection) throws DataAccessException {  
            	StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
            	byte[] k = keySerializer.serialize(key);
                return connection.incrBy(k, incrValue);
            }  
        }); 
        if(result != null){
        	return result;
        }
        return 0.00;
    }
	
	/** 
     * 缓存中key对应的值增加数值long 
     * @param key  
     * @param incrValue 
     * @param seconds 
     * @return 
     */  
	@Override
    public double incrBy(String key, double incrValue, int seconds){
        //将key对应的数字加decrement。如果key不存在，操作之前，key就会被置为0。  
        //如果key的value类型错误或者是个不能表示成数字的字符串，  
        //就返回错误。这个操作最多支持64位有符号的正型数字。  
        Double result = (Double) redisTemplate.execute(new RedisCallback<Object>() {  
            @Override  
            public Double doInRedis(RedisConnection connection) throws DataAccessException {  
            	StringRedisSerializer keySerializer = (StringRedisSerializer)redisTemplate.getKeySerializer();
            	byte[] k = keySerializer.serialize(key);
            	Double num = connection.incrBy(k, incrValue);
            	connection.expire(k, seconds);
                return num;
            }  
        }); 
        if(result != null){
        	return result;
        }
        return 0.00;
    }
	
	/**
	 * 获取increment操作后的值
	 */
	@Override
	public double getIncrValue(String key) {  
	      
	    return redisTemplate.execute(new RedisCallback<Double>() {  
	        @Override  
	        public Double doInRedis(RedisConnection connection) throws DataAccessException {  
	            RedisSerializer<String> serializer=redisTemplate.getStringSerializer();  
	            byte[] rowkey=serializer.serialize(key);  
	            byte[] rowval=connection.get(rowkey);  
	            try {  
	                String val=serializer.deserialize(rowval);  
	                return Double.parseDouble(val);  
	            } catch (Exception e) {  
	                return 0.00;  
	            }  
	        }  
	    });  
	}

	@Override
	public void putHashValue(String key, String hashKey, Object hashValue) {
		putHashValue(key, hashKey, hashValue, redisConfig.getInteger(KEY_REDIS_EXPIRE, 60));
	}

	@Override
	public void putHashValue(String key, String hashKey, Object hashValue, long expireSeconds) {
		redisTemplate.opsForHash().put(key, hashKey, hashValue);
		redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
	}

	@Override
	public Object getHashValue(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	@Override
	public Map<Object, Object> getAllHashValues(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void delHash(String key, String hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}

}
