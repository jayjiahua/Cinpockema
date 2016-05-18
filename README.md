# Cinpockema
Course Project for SE305


## 1. 简介
Cinpockema (Backend) 是使用Spring-Boot微框架构建的Restful Service

#### 特性

1. 使用tomcat作为web服务器
2. 使用maven进行包管理

## 2. 运行
**有两种运行方式**
```
mvn spring-boot:run
```
或者
```
mvn package && java -jar target/Cinpockema-0.0.1-SNAPSHOT.jar
```

> 建议使用Eclipse (STS) 进行开发，在此环境下操作步骤如下
> 1. 右击```Pom.xml```，然后选择```Run As``` -> ```Maven Install```
> 2. 右击```Applicaiton.java```，然后选择```Run As``` -> ```Spring Boot```

默认监听 http://127.0.0.1:8080 ，如需修改请参考配置文件。

**注意：所有api的起始端点均为```/api```，如user模块的url为 ```http://127.0.0.1:8080/api/user```**

## 3. 项目结构
1. 项目包含多个模块，而每个模块按照层级结构划分，以注解(Annotation)作为标志

  ```@Entity``` - 数据库实体(POJO)

  ```@Repository``` - 仓库(DAO)，负责数据库对实体的CRUD操作

  ```@Service``` - 服务层(业务逻辑层)，与DAO交互，负责业务逻辑的实现，是最核心的一层

  ```@Controller``` - 负责路由，处理请求，并将数据递交给对应的Service

  **在开发每个业务模块时，至少包含以上四层，一般而言，开发的顺序是：Entity -> Repository -> Service -> Controller**

  > 除此之外，还有```@Configuration```注解，应用程序的配置，如Secure。

2. 目录结构（非核心路径已略去）
```
  ├─config
  |  └─application.yml    // 项目配置文件
  ├─logs     // tomcat access log
  ├─src      // 源码
  │  └─main
  │      └─java
  │          └─com
  │              └─c09
  │                  └─cinpockema
  │                      ├─config          // 应用配置文件
  │                      ├─controller      // 控制器
  │                      ├─entities        // 实体
  │                      │  └─repositories // 仓库
  │                      └─service         // 服务
  ├─pom.xml  // 包依赖
  |
  |...
```

## 4. 配置文件
配置文件位置为```config/application.yml```，使用yaml语法编写

**说明**
```
# Server settings (ServerProperties)
server:
  port: 8080              # 监听端口
  address: 127.0.0.1      # 监听地址
  sessionTimeout: 30      # 待用
  contextPath: /api/      # url端点（公共前缀）

  # Tomcat specifics
  tomcat:
    accessLogEnabled: true            # 打开日志记录
    protocolHeader: x-forwarded-proto
    remoteIpHeader: x-forwarded-for
    basedir:
    backgroundProcessorDelay: 30      # secs

# Spring settings
spring:
  # Use mysql, name of database is "cinpockema"
  datasource:     # 需要使用mysql时删除此处注释，开发阶段使用h2数据库（内存型）
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cinpockema?useUnicode=true&characterEncoding=utf8
    username: root
    password:

# Security settings
security:
  basic:
    enabled: true  # 打开安全切口
  user:            # 基本认证的用户名和密码，由于是固定的，不符合业务需求，注释之
    name: secured  
    password: foo
```

> 更多配置可以参考网站
> https://segmentfault.com/a/1190000004316731

## 5. 测试
每个模块开发完成之后，需要编写单元测试，包括controller, service的测试，测试代码放在src/test/java文件夹中

> 关于测试的参考网址

> 1. http://somefuture.iteye.com/blog/2247207
> 2. http://jinnianshilongnian.iteye.com/blog/2004660
> 3. http://www.blogjava.net/usherlight/archive/2015/06/16/425740.html

## 6. 学习开发参考网站
1. http://blog.csdn.net/zgmzyr/article/details/49837077 （强烈推荐）
2. http://www.ibm.com/developerworks/cn/java/j-lo-spring-boot/
3. http://docs.spring.io/spring/docs/4.0.0.RELEASE/spring-framework-reference/html/
4. http://spring.cndocs.tk/
