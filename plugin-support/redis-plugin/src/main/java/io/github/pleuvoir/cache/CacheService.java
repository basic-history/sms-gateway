package io.github.pleuvoir.cache;

import java.util.Map;

/**
 * 缓存服务
 */
public interface CacheService {
	
	/**
	 * 生成redis key
	 * @param prefix
	 * @param content
	 * @return
	 */
	public String generateKey(String prefix, String content);
	
	/**
	 * 生成redis key
	 * @param prefix
	 * @param content
	 * @return
	 */
	public String generateKey(String prefix, String...content);
	
	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 * @param second
	 */
	public void set(String key, Object value, int second);
	
	public void increment(String key, double value, int second);
	
	/**
	 * 添加缓存，使用默认失效时间
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);
	
	/**
	 * 取缓存
	 * @param key
	 * @return Object
	 */
	public Object get(String key);
	
	/**
	 * 取缓存
	 * @param key
	 * @return String
	 */
	public String getString(String key);
	
	/**
	 * 取缓存
	 * @param key
	 * @return Integer
	 */
	public Integer getInteger(String key);
	
	/**
	 * 更新缓存有效期
	 * @param key
	 * @param second
	 */
	public void updateExpire(String key, int second);
	
	/**
	 * 得到缓存有效期
	 * @param key
	 * @return
	 */
	public Long getExpire(String key);
	
	/**
	 * 当缓存中没有时存入，缓存中存在时不存入
	 * @param key
	 * @param value
	 * @return 缓存中没有时返回true，缓存中有时返回false
	 */
	boolean putIfExist(String key, Object value);
	
	/**
	 * 当缓存中没有时存入，缓存中存在时不存入
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @return 缓存中没有时返回true，缓存中有时返回false
	 */
	boolean putIfExist(String key, Object value, int expireSeconds);
	
	/** 
     * 缓存中key对应的值增加数值double 
     * @param key  
     * @param incrValue 
     * @return 
     */  
	double incrBy(String key, double incrValue);  
	
	/** 
     * 缓存中key对应的值增加数值double 
     * @param key  
     * @param incrValue 
     * @param seconds 
     * @return 
     */  
	double incrBy(String key, double incrValue, int seconds);
	
	double getIncrValue(String key);
	
	/**
	 * 设置hash值，使用默认的有效时长<br>
	 * 注意，默认的有效时长设置的是key的有效时长
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 */
	public void putHashValue(String key, String hashKey, Object hashValue);
	
	/**
	 * 设置hash值，指定有效时长<br>
	 * 注意，有效时长设置的是key的有效时长
	 * @param key
	 * @param hashKey
	 * @param hashValue
	 * @param expireSeconds 有效时长（秒）
	 */
	public void putHashValue(String key, String hashKey, Object hashValue, long expireSeconds);
	
	/**
	 * 获取hash的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object getHashValue(String key, String hashKey);
	
	/**
	 * 获取全部的hash值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getAllHashValues(String key);
	
	/**
	 * 删除指定的key
	 * @param key
	 */
	public void del(String key);
	
	/**
	 * 删除指定的hash中的key
	 * @param key 缓存的key
	 * @param hashKey hash的key
	 */
	public void delHash(String key, String hashKey);
}
