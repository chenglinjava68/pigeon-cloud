##  pigeon-cloud



### 一、📄 项目介绍

```
pigeon-cloud-parent
    ├───pigeon-auth				-- 认证服务器           
    ├───pigeon-demo				-- 测试demo模块    
    ├───pigeon-file				-- 文件服务器  
    ├───pigeon-gateway			-- 网关      
    ├───pigeon-governance		-- 测试demo模块    
    ├───pigeon-openapi			
    └───pigeon-rbac				-- 用户管理模块
```



### 二、🎨 项目特性

- 主体框架：采用最新的Spring Cloud 2020.0.2, Spring Boot 2.4.5, Spring Cloud Alibaba 2021.1版本进行系统设计；
- 统一注册：支持Nacos作为注册中心，实现多配置、分群组、分命名空间、多业务模块的注册和发现功能；
- 统一认证：统一Oauth2认证协议，采用jwt的方式，实现统一认证，支持自定义grant_type实现
- 业务监控：利用Spring Boot Admin 来监控各个独立Service的运行状态；
- 内部调用：集成了Feign两种模式支持内部调用
- 在线文档：通过接入Knife4j，实现在线API文档的查看与调试;
- 消息中心：集成消息中间件RabbitMq、Kafka，对业务进行异步处理;
- 业务分离：采用前后端分离的框架设计，前端采用vue-element-admin
- 链路追踪：自定义traceId的方式，实现简单的链路追踪功能



### 三、🍻 运行项目

1. git下载并导入`pigeon-cloud`

2. git下载并导入`pigeon-common`项目，mvn clean install一下（**重要！！！**）

3. 导入数据库sql

4. 启动nacos，导入配置，按需修改各项连接参数

   > 项目里面不提供源代码方式启动nacos，自行从官网下载最新稳定版即可

5. 修改各项连接参数

6. 启动服务



### 四、✨ 特别鸣谢

