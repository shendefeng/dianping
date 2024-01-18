# dianping

校园点评项目

## **接口文档：**

https://apifox.com/apidoc/shared-ab512b88-2547-48fd-bfc2-e817d5b30a24

## 技术栈

### 后端技术栈：

- 😄SpringBoot + MyBatis-Plus
- 😁MySQL：用户信息的持久化存储
- ❤Redis：商店缓存、高频访问数据缓存
- 🤔阿里云OSS：图片信息
- 🛒Redission分布式锁、Lua脚本：解决集群服务下一人一单的问题

### 前端技术栈：

- ⛷️Vue

### 部署：

Nginx

### TODO：

- [ ] 使用消息队列实现秒杀的异步处理，优化秒杀性能
- [ ] 支付功能的完善
- [ ] 使用ES提高搜索的功能

## 运行

后端项目在main分支下，可以直接clone。

修改`application.properties`里的数据库配置信息和Redis的配置信息

数据库结构的SQL语句在`main/resource/db/hmdp.sql`下


