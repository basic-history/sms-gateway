
## :smiling_imp: 方便的使用redis。

### 使用

添加项目依赖

```xml

<dependency>
	<groupId>io.github.pleuvoir</groupId>
	<artifactId>redis-plugin</artifactId>
	<version>${project.version}</version>
</dependency>
```

### 举个栗子  ###

`redis.properties`文件内容如下

```xml
##### redis
redis.hostAndPort=127.0.0.1:6379
redis.database=1
redis.password=
redis.pool.maxIdle=4
redis.pool.maxTotal=6
redis.pool.maxWait=5000
redis.pool.testOnBorrow=true
```

#### spring容器中注册

```xml
<bean class="io.github.pleuvoir.RedisConfiguration">
    <property name="location" value="redis.properties"/>
</bean>
```

或者

```java
@Bean
public RedisConfiguration redisConfiguration() {
	RedisConfiguration redisConfiguration = new RedisConfiguration();
	redisConfiguration.setLocation("redis.properties");
	return redisConfiguration;
}
```

### API

配置完成后，会提供缓存服务`CacheService` 提供了一些操作数据的方法：

```java
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
 * 当缓存中没有时存入，缓存中存在时不存入
 * @param key
 * @param value
 * @return 缓存中没有时返回true，缓存中有时返回false
 */
boolean putIfExist(String key, Object value);

...
```


