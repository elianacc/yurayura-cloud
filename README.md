## 前言

此项目为个人兴趣编写，实现了spring cloud alibaba的基本搭建及使用，并融合其他所学实现了一些系统的基本功能和两个增删改查模块样例（番剧和用户），后续还会继续增加常用业务功能及C端服务

此项目对应B端Vue项目 https://github.com/elianacc/yurayura-business-vue/tree/yurayura-cloud-business-vue 

## 项目目录介绍

```apl
yurayura-cloud
├── yurayura-cloud-client-business-base ----- 服务消费者包整合（b端）
├── yurayura-cloud-client-business-comic ----- 番剧服务消费者（b端）
├── yurayura-cloud-client-business-sys ----- 系统服务消费者（b端）
├── yurayura-cloud-client-business-user ----- 用户服务消费者（b端）
├── yurayura-cloud-commons ----- 通用entity、vo、dto、enumerate、exception等整合
├── yurayura-cloud-service-comic ----- 番剧服务生产者
├── yurayura-cloud-service-sys ----- 系统服务生产者
└── yurayura-cloud-service-user ----- 用户服务生产者
yurayura-codegenerator  ----- 服务生产者代码生成器
```

## 项目技术构成

| 描述                | 框架                                | 版本                        |
| ------------------- | ----------------------------------- | --------------------------- |
| 基础                | Spring Boot                         | 2.3.12.RELEASE              |
| 微服务基础          | Spring Cloud + Spring Cloud Alibaba | Hoxton.SR12 + 2.2.6.RELEASE |
| 服务注册与配置中心  | Nacos                               | 2.0.3                       |
| 服务流控及熔断降级  | Sentinel                            | 1.8.1                       |
| 服务调用            | OpenFeign                           | 2.2.9.RELEASE               |
| 分布式事务          | Seata                               | 1.3.0                       |
| 数据库持久层（ORM） | MyBatis-Plus                        | 3.5.1                       |
| 自动生成代码工具    | MyBatis-Plus-Generator              | 3.5.1                       |
| 分页工具            | Mybatis-PageHelper                  | 5.2.0                       |
| 认证和授权          | Shiro                               | 1.7.1                       |
| 分布式锁            | Lock4j                              | 2.2.2                       |
| 日志                | Logback                             | 1.2.3                       |
| 项目构建            | Maven                               | 3.6.3                       |
| 代码简化工具        | Lombok                              | 1.18.20                     |
| 数据库连接池        | HikariCP                            | 3.4.5                       |
| 数据库              | MySQL80                             | 8.0.28                      |
| NoSQL数据库         | Redis                               | 3.2.100                     |
| 在线接口文档生成    | Knife4j（swagger2增强）             | 3.0.3                       |
| JSON处理工具        | FastJson                            | 1.2.83                      |
| 工具类库            | Hutool                              | 5.8.9                       |

## 常用功能实现

-  sentinel可视化配置同步到nacos持久化
-  全局捕获异常处理
-  AOP统一处理Web请求日志
-  防止重复提交表单数据
-  分布式session共享
-  外部静态资源访问处理
-  通用接口返回信息处理
-  发送Email工具
-  redis常用操作工具
-  跨域处理
-  系统数据字典
-  系统菜单设置
-  系统管理员角色分配
-  系统角色分配权限
-  系统权限管理

## 开发工具

-  开发环境： JDK 1.8
-  编码： IntelliJ IDEA（idea）
-  数据库可视化： Navicat Premium
-  Redis可视化： RedisDesktopManager
-  maven私服工具： Nexus
-  页面访问及调试： Google Chrome
-  markdown： Typora
-  截图： Snipaste































