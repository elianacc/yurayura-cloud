## 前言

此项目为个人兴趣编写，实现了spring cloud alibaba的基本搭建及使用，并融合其他所学实现了一些系统的基本功能（系统数据字典，系统菜单设置，系统管理员角色分配，系统角色分配权限，系统权限管理等）和两个增删改查模块样例（番剧和用户），后续还会继续增加常用业务功能及C端服务

**此项目对应B端Vue项目地址： https://github.com/elianacc/yurayura-business-vue/tree/yurayura-cloud-business-vue** 

## 项目目录介绍

```apl
yurayura-cloud
├── yurayura-cloud-client-business-base ----- 服务消费者基础包整合（b端）
├── yurayura-cloud-client-business-comic ----- 番剧服务消费者（b端）
├── yurayura-cloud-client-business-sys ----- 系统服务消费者（b端）
├── yurayura-cloud-client-business-user ----- 用户服务消费者（b端）
├── yurayura-cloud-commons ----- 通用entity、vo、dto、aspect、enumerate、exception等整合
├── yurayura-cloud-service-base ----- 服务生产者基础包整合
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

-  sentinel可视化配置同步到nacos持久化（改官方的sentinel源码--步骤在个人空间中另一个项目总结https://github.com/elianacc/sentinel-dashboard-nacos）
-  全局捕获异常处理(包括系统异常500、自定义业务异常403、请求接口参数异常400、重复提交异常等)
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

## 项目服务提供者和消费者通用的一些Nacos配置

### 服务提供者在nacos中yml配置

```yaml
# SpringBoot内嵌web容器配置
server:
  # SpringBoot内嵌tomcat配置
  tomcat:
    # uri编码
    uri-encoding: UTF-8
    # 连接建立超时时间（单位：ms）
    connection-timeout: 12000
    # 最大等待队列长度：每个请求使用一个线程，线程数超过最大链接数后请求会进入等待队列，直到有线程处理
    accept-count: 1000
    # 线程数设置
    threads:
      # 最小线程数：SpringBoot启动时初始化的线程数量
      min-spare: 100
      # 最大线程数：可以设为CPU线程数的200~250倍
      max: 500
    # 最大连接数：Tomcat同一时间能接受的最大线程数量，需要大等于 max-threads + accept-count
    max-connections: 1500
  # 编码配置（编码过滤器）
  servlet:
    encoding:
      charset: UTF-8
      force-request: true
      force-response: true
      enabled: true

spring:
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/yurayura_sys?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
    username: root
    password: 123456
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      # 此属性控制从池返回的连接的默认自动提交行为，默认值：true
      auto-commit: true
      # 空闲连接存活最大时间，默认600000（10分钟）
      idle-timeout: 300000
      # 最小空闲连接数量，默认是10
      minimum-idle: 30
      # 连接池最大连接数，默认是10
      maximum-pool-size: 50
      # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
      max-lifetime: 1800000
      # 数据库连接超时时间，默认30秒，即30000
      connection-timeout: 30000
      connection-test-query: SELECT 1
      pool-name: ELiaNaCcHikariCP
  # thymeleaf配置
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false
  # 发送邮箱配置
  mail:
    host: smtp.126.com
    port: 465
    username: (隐藏)@126.com
    password: (隐藏)
    default-encoding: UTF-8
    properties:
      mail:
        smtp:
          auth: true
          socketFactory:
            class: javax.net.ssl.SSLSocketFactory
          starttls:
            enable: true
  # 文件上传配置
  servlet:
    multipart:
      # 上传文件位置
      location: D://yurayura-upload-resource/upload
      # 单个文件的最大值
      max-file-size: 10MB
      # 总上传文件的最大值
      max-request-size: 10MB
  # Redis配置
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    database: 10
    jedis:
      pool:
        max-active: 200
        max-wait: 100000
        max-idle: 10
        min-idle: 0
    timeout: 50000

# seata配置
seata:
  enabled: true
  application-id: ${spring.application.name}
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      cluster: yurayura-cloud
      username: nacos
      password: nacos
  config:
    type: nacos
    nacos:
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      username: nacos
      password: nacos

logging:
  # 日志级别设置
  level:
    io:
      seata: info

# mybatis plus配置
mybatis-plus:
  mapper-locations: classpath:pers/elianacc/yurayura/dao/**/*.xml

# SpringBoot指标监控配置
management:
  endpoints:
    # 是否开启所有监控端点
    enabled-by-default: false
    # 以web方式暴露所有监控端点
    web:
      exposure:
        include: '*'
  endpoint:
    # 健康状况端点
    health:
      enabled: true
      show-details: always
    # SpringBean端点
    beans:
      enabled: true
    # 环境端点
    env:
      enabled: true
    # 应用信息端点
    info:
      enabled: true
    # 日志信息端点
    loggers:
      enabled: true
    # 指标信息端点
    metrics:
      enabled: true

# 自定义配置
yurayura:
  # 接收邮箱
  receive-email: (隐藏)@126.com

```

### 服务消费者在nacos中yml配置

```yaml
# SpringBoot内嵌web容器配置
server:
  # SpringBoot内嵌tomcat配置
  tomcat:
    # uri编码
    uri-encoding: UTF-8
    # 连接建立超时时间（单位：ms）
    connection-timeout: 12000
    # 最大等待队列长度：每个请求使用一个线程，线程数超过最大链接数后请求会进入等待队列，直到有线程处理
    accept-count: 1000
    # 线程数设置
    threads:
      # 最小线程数：SpringBoot启动时初始化的线程数量
      min-spare: 100
      # 最大线程数：可以设为CPU线程数的200~250倍
      max: 500
    # 最大连接数：Tomcat同一时间能接受的最大线程数量，需要大等于 max-threads + accept-count
    max-connections: 1500
  # 编码配置（编码过滤器）
  servlet:
    encoding:
      charset: UTF-8
      force-request: true
      force-response: true
      enabled: true

spring:
  cloud:
    # sentinel设置
    sentinel:
      transport:
        # 配置Sentinel dashboard地址
        dashboard: localhost:8087
        port: 8719
      # 添加Nacos数据源配置,sentinel持久化
      datasource:
        # 自定义的流控规则数据源名称
        flow:
          nacos:
            server-addr: localhost:8888
            namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
            dataId: sentinel.rule.flow
            groupId: ${spring.application.name}
            data-type: json
            rule-type: flow
        # 自定义的降级规则数据源名称
        degrade:
          nacos:
            server-addr: localhost:8888
            namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
            dataId: sentinel.rule.degrade
            groupId: ${spring.application.name}
            data-type: json
            rule-type: degrade
  # thymeleaf配置
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false
  # 发送邮箱配置
  mail:  # 发送邮箱配置
  mail:
    host: smtp.126.com
    port: 465
    username: (隐藏)@126.com
    password: (隐藏)
    default-encoding: UTF-8
    properties:
      mail:
        smtp:
          auth: true
          socketFactory:
            class: javax.net.ssl.SSLSocketFactory
          starttls:
            enable: true
  # 文件上传配置
  servlet:
    multipart:
      # 上传文件位置
      location: D://yurayura-upload-resource/upload
      # 单个文件的最大值
      max-file-size: 10MB
      # 总上传文件的最大值
      max-request-size: 10MB
  # Redis配置
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    database: 10
    jedis:
      pool:
        max-active: 200
        max-wait: 100000
        max-idle: 10
        min-idle: 0
    timeout: 50000
  # session存储方式
  session:
    store-type: redis

# openfeign设置
feign:
  client:
    config:
      default:
        # 指的是建立连接后从服务器读取到可用资源所用的时间
        connect-timeout: 5000
        # 指的是建立连接所用的时间，适用于网络状况正常的情况下,两端连接所用的时间
        read-timeout: 12000
  sentinel:
    # 激活Sentinel对Feign的支持
    enabled: true

# seata配置
seata:
  enabled: true
  application-id: ${spring.application.name}
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      cluster: yurayura-cloud
      username: nacos
      password: nacos
  config:
    type: nacos
    nacos:
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      username: nacos
      password: nacos

logging:
  # 日志级别设置
  level:
    # feign日志以什么级别监控哪个接口
    pers.elianacc.yurayura.feign.**: debug
    io:
      seata: info

# lock4j配置
lock4j:
  acquire-timeout: 3000
  expire: 10000
  primary-executor: com.baomidou.lock.executor.RedisTemplateLockExecutor
  lock-key-prefix: lock4j

# knife4j配置
knife4j:
  # 开启增强配置
  enable: true
  # 开启Swagger的Basic认证功能,默认是false
  basic:
    enable: true
    # Basic认证用户名
    username: admin
    # Basic认证密码
    password: admin123

# SpringBoot指标监控配置
management:
  endpoints:
    # 是否开启所有监控端点
    enabled-by-default: false
    # 以web方式暴露所有监控端点
    web:
      exposure:
        include: '*'
  endpoint:
    # 健康状况端点
    health:
      enabled: true
      show-details: always
    # SpringBean端点
    beans:
      enabled: true
    # 环境端点
    env:
      enabled: true
    # 应用信息端点
    info:
      enabled: true
    # 日志信息端点
    loggers:
      enabled: true
    # 指标信息端点
    metrics:
      enabled: true

# 自定义配置
yurayura:
  # swagger是否启用
  swagger:
    enable: true
  # 接收邮箱
  receive-email: (隐藏)@126.com
  # 上传文件对外暴露的访问路径(虚拟路径)
  upload-file:
    virtual-path: /upload

```

## 开发工具

-  开发环境： JDK 1.8
-  编码： IntelliJ IDEA（idea）
-  数据库可视化： Navicat Premium
-  Redis可视化： RedisDesktopManager
-  maven私服工具： Nexus
-  页面访问及调试： Google Chrome
-  markdown： Typora
-  截图： Snipaste































