
## :smiling_imp: 方便的使用freemarker。

### 使用

添加项目依赖

```xml

<dependency>
	<groupId>io.github.pleuvoir</groupId>
	<artifactId>freemarker-plugin</artifactId>
	<version>${project.version}</version>
</dependency>
```


### spring容器中注册

```xml
<bean id="templateFactory" class="io.github.pleuvoir.TemplateFactory">
	<property name="location" value="classpath:ftl"/>
</bean>
```

value 为存放模板的位置

### 使用

注入 TemplateFactory 按照工具类的方式使用



