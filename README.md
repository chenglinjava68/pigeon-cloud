##  pigeon-cloud



### 一、项目介绍

| 工程名      | 说明                   |
| ----------- | ---------------------- |
| pigeon-auth | 认证服务               |
| pigeon-rbac | 基于角色的权限管理模块 |
| pigeon-demo | demo服务               |



### 二、项目特性

待补充

### 三、运行项目

1. git下载并导入`pigeon-cloud`

2. git下载并导入`pigeon-common`项目，mvn clean install一下（**重要！！！**）

3. 导入数据库sql

4. 启动nacos，导入配置，按需修改各项连接参数

   > 项目里面不提供源代码方式启动nacos，自行从官网下载最新稳定版即可

5. 修改各项连接参数

6. 启动服务