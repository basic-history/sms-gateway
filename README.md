
# sms-gateway


> doing..

## 介绍
 互联网应用中经常要使用发送短信的功能，短信网关做为基础设施，应当是高可用、灵活可配置且不与业务系统耦合的。基于此，剥离出该系统。

系统由三部分构成：
1. 脚本迁移
2. 后台管理
3. 服务端

## 特性
- 支持 RPC 和 HTTP 调用
- 短信通道接入分离
- 数据库迁移工具
- 后台管理项目（基于 VUE）
- 短信通道健康率检测，支持智能切换

## 技术
spring、dubbo、zookeeprt、redis、mybatis-plus、akka

## 开源协议
[Apache License](LICENSE)

