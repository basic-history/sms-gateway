版本号规则：

版本号分为3部分：Va_b_c
V是版本号前缀，无需关心
a是1级版本号，不发生变化
b是2级版本号，当库中表结构需要发生变化时，该版本号提升。如，添加新表、删除旧表、增加表字段、删除表字段、修改表字段。
c是3级版本号，当表中的数据需要发生变化时，该版本号变化；变化规则 p（主键），t（建表），d（基础数据）。
