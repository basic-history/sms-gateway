
## :smiling_imp: dubbo 请求日志记录插件。

### 使用

添加项目依赖

```xml

<dependency>
	<groupId>io.github.pleuvoir</groupId>
	<artifactId>dubbo-tracefilter-plugin</artifactId>
	<version>${project.version}</version>
</dependency>
```

### 配置

dubbo 服务提供配置文件需要增加 `filter="dubboTraceFilter"`

```xml
<dubbo:provider delay="-1" timeout="${dubbo.provider.timeout}" retries="0" filter="dubboTraceFilter"/>
```

logback 配置

```xml
<logger name="io.github.pleuvoir.DubboTraceFilter" level="DEBUG" />
```


