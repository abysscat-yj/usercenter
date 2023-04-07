## 一、项目简介
本项目是基于`SpringBoot`的成熟企业级微服务项目（骨架），用来实现简单的用户中心模块。

真实的复杂企业Java项目可以参考该模板实现自己的业务功能。

---

## 二、项目结构

maven多模块划分如下：
```xml
<!-- 父模块 -->
<module>user-center-parent</module>

<!-- 子模块 -->
<modules>
    <module>user-center-app</module>
    <module>user-center-base</module>
    <module>user-center-biz</module>
    <module>user-center-dal</module>
    <module>user-center-web</module>
    <module>user-center-sal</module>
</modules>
```
各模块功能如下：
- `parent`: 顶层父模块，用来管理子模块依赖以及定义项目插件、打包方式等。
- `base`: 基础模块，用来引入基础依赖以及各种通用组件，如公共实体、全局配置、工具类等。
- `biz`: 业务功能模块，用来实现具体业务功能逻辑，主要包含service类。
- `dal`: 数据模块，用来配置各种数据源、定义数据库操作方法。
- `web`: API接口模块，用来定义API接口controller类，以及接口切面（如日志、鉴权）。
- `sal`: 三方依赖模块，用来对接各种第三方远程依赖，通过http、RPC等方式调用。

---

## 三、功能介绍

### 1、业务功能
用户中心微服务：
> 用户注册
> 
> 指定用户信息查询（多个）
> 
> 用户封禁（多个）
> 
> 用户信息更新
> 
> 所有用户信息列表（分页/筛选）

邮件服务（后续可独立拆分，提供RPC服务）：
> 简洁的邮件发送API
> 
> 支持自定义发件人昵称
> 
> 支持扩展邮件Message
> 
> 支持抄送／HTML
> 
> 支持异步发送
> 
> 支持邮件模板


### 2、非业务功能
数据库相关：
> 采用仓储设计模式，用仓储接口`DaoService`隔离底层具体`DB-mapper`实现，便于替换不同数据源
> 
> 使用`tk-mybatis`增强原生`mybatis`，自带基础增删改查方法，无需手动编写
> 
> 通过`mybatis-generator`根据库表接口自动生成实体类、mapper，无需手动编写
> 
> 自定义多数据源管理器，支持不同库表同时操作


日志追踪相关：
> 自定义接口日志AOP切面，实现接口详细访问日志输出
> 
> 通过`p6spy`增强数据库操作日志打印，输出内容更加细致
> 
> 配置全局自定义异常处理器，方便各种异常捕捉追溯

安全稳定性相关：
> 通过`Sentinel`实现接口限流策略，保证服务稳定性
> 
> 自定义线程池配置以及异步处理方法，使多线程任务更加简单安全
> 
> 自定义鉴权AOP切面，实现不同方式接口权限控制

---

## 四、快速开始

1.通过运行内置脚本`db/user_info.sql`，创建用户信息表

2.修改`application.yml`的数据库配置

3.修改`EmailServiceImpl`中的邮箱服务端配置

4.部署运行
- 普通本地执行模式：`sh build.sh` 自动编译打jar包并启动应用
- docker模式：`sh build-docker.sh`自动编译及启动docker环境部署

---

## 五、测试
1.命令行下通过`mvn test`本地运行所有单元测试

2.在IDEA中右键各模块点击`Run All Tests With Coverage`，可运行单测并查看覆盖率

目前本项目核心业务功能单测覆盖率如下：

| 类名    | Class 覆盖率 | Method 覆盖率 | Line 覆盖率 |
|-------|----------|-------|------|
| UserServiceImpl | 100%     | 100%  | 97%  |
| UserInfoDaoServiceImpl | 100%     | 100%  | 100% |
| EmailServiceImpl | 100%     | 100%  | 100% |


## 六、其他

项目具体目录层次如下：
```
├── Dockerfile
├── LICENSE
├── README.md
├── api_doc.md
├── build-docker.sh
├── build.sh
├── db
│   └── user_info.sql
├── nohup.log
├── pom.xml
├── user-center-app
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── abysscat
│           │           └── usercenter
│           │               └── UserCenterApplication.java
│           └── resources
│               ├── bootstrap.properties
│               ├── log4j.properties
│               └── spring.properties
├── user-center-base
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── abysscat
│       │   │           └── usercenter
│       │   │               └── base
│       │   │                   ├── asynctask
│       │   │                   │   ├── FutureService.java
│       │   │                   │   └── impl
│       │   │                   │       └── CommonFutureServiceImpl.java
│       │   │                   ├── enums
│       │   │                   │   ├── ErrorEnum.java
│       │   │                   │   └── IsDelEnum.java
│       │   │                   ├── exception
│       │   │                   │   ├── ApiException.java
│       │   │                   │   ├── BasicErrorCode.java
│       │   │                   │   ├── BusinessException.java
│       │   │                   │   └── GlobalExceptionHandler.java
│       │   │                   ├── logger
│       │   │                   │   └── LogInfo.java
│       │   │                   ├── utils
│       │   │                   │   └── IPUtil.java
│       │   │                   └── web
│       │   │                       ├── BaseResponse.java
│       │   │                       └── RequestContainer.java
│       │   └── resources
│       │       ├── application.yml
│       │       └── spy.properties
│       └── test
│           └── java
├── user-center-biz
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── com
│       │           └── abysscat
│       │               └── usercenter
│       │                   └── biz
│       │                       ├── service
│       │                       │   ├── UserService.java
│       │                       │   └── impl
│       │                       │       └── UserServiceImpl.java
│       │                       └── vo
│       │                           ├── BanUserReq.java
│       │                           ├── BaseUserInfoVO.java
│       │                           ├── GetAllUserInfoReq.java
│       │                           ├── GetUserInfoReq.java
│       │                           ├── GetUserInfoResp.java
│       │                           ├── RegisterUserReq.java
│       │                           └── UpdateUserInfoReq.java
│       └── test
│           └── java
│               └── com
│                   └── abysscat
│                       └── usercenter
│                           └── biz
│                               └── service
│                                   └── impl
│                                       └── UserServiceImplTest.java
├── user-center-dal
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── com
│       │           └── abysscat
│       │               └── usercenter
│       │                   └── dal
│       │                       ├── config
│       │                       │   ├── MyBatisUserCenterDao.java
│       │                       │   ├── MyBatisUserCenterDataSourceConfig.java
│       │                       │   └── mapper
│       │                       │       ├── BaseMapper.java
│       │                       │       ├── BaseQueryMapper.java
│       │                       │       ├── BatchMapper.java
│       │                       │       └── provider
│       │                       │           └── BatchProvider.java
│       │                       └── usercenter
│       │                           ├── entity
│       │                           │   └── UserInfo.java
│       │                           ├── impl
│       │                           ├── mapper
│       │                           │   └── UserInfoMapper.java
│       │                           └── service
│       │                               ├── UserInfoDaoService.java
│       │                               └── impl
│       │                                   └── UserInfoDaoServiceImpl.java
│       └── test
│           ├── java
│           │   └── com
│           │       └── abysscat
│           │           └── usercenter
│           │               └── dal
│           │                   ├── CustomMapperPlugin.java
│           │                   ├── TkMybatisGen.java
│           │                   └── usercenter
│           │                       └── service
│           │                           └── impl
│           │                               └── UserInfoDaoServiceImplTest.java
│           └── resources
│               ├── generator
│               │   └── mapper-usercenter.ftl
│               ├── generatorConfig.xml
│               └── jdbc.properties
├── user-center-sal
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── abysscat
│       │   │           └── usercenter
│       │   │               └── sal
│       │   │                   └── msgcenter
│       │   │                       ├── mail
│       │   │                       │   ├── core
│       │   │                       │   │   └── SendMailException.java
│       │   │                       │   ├── sender
│       │   │                       │   │   └── EmailSender.java
│       │   │                       │   ├── service
│       │   │                       │   │   ├── EmailService.java
│       │   │                       │   │   └── impl
│       │   │                       │   │       └── EmailServiceImpl.java
│       │   │                       │   └── vo
│       │   │                       │       └── RegisterMailReq.java
│       │   │                       └── sms
│       │   └── resources
│       │       └── mail
│       │           └── template
│       │               └── register.html
│       └── test
│           ├── java
│           │   └── com
│           │       └── abysscat
│           │           └── usercenter
│           │               └── sal
│           │                   └── msgcenter
│           │                       └── mail
│           │                           ├── sender
│           │                           │   └── EmailSenderTest.java
│           │                           └── service
│           │                               └── impl
│           │                                   └── EmailServiceImplTest.java
│           └── resources
│               └── mail
│                   └── template
│                       └── register.html
├── user-center-web
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── abysscat
│                       └── usercenter
│                           └── web
│                               ├── aop
│                               │   ├── auth
│                               │   │   ├── AccessAuth.java
│                               │   │   ├── AccessAuthAspect.java
│                               │   │   └── AuthType.java
│                               │   └── log
│                               │       └── LogAspect.java
│                               ├── base
│                               │   └── BaseController.java
│                               ├── config
│                               │   └── SentinelConfig.java
│                               └── controller
│                                   └── UserController.java
└── user-center.iml
```
