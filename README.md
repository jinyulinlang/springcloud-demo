# spring-cloud-demo
## 最新升级到springboot2.0.3和springcloud Finchley.RELEASE
   - 各组件的README.md放在各组件的项目中
   - 博客地址：https://blog.csdn.net/qq_37170583
   - 如果引入的springboot版本低于2.0.3会造成导入jdbc依赖时，启动会报错（错误大概意思是DataSource循环依赖）
   ```
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jdbc</artifactId>
           </dependency>
   ```
## 项目介绍
- 项目基于springboot2.0和springcloud Finchley
- 实现功能：
    - eureka注册中心
    - feign调用
    - hystrix断路器
    - 网关层统一查看swagger2接口文档，包含Authorization头传递token
    - zuul网关，在网关层对所有rpc接口调用进行权限管理
    - oauth2.0授权服务
    - hystrix-dashboard监控
    - 整合turbine
    - 整合zipkin，springboot2开始建议使用官方提供的zipkin.jar启动zipkin服务
    - config配置中心，还未实现spring bus和kafka 进行动态刷新配置信息
    - springcloud的gateway网关

### 在父工程的pom.xml文件中导入依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zkane</groupId>
    <artifactId>spring-cloud-demo</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.3.RELEASE</version>
    </parent>
    <properties>
        <project.build.sourceencoding>UTF-8</project.build.sourceencoding>
        <project.reporting.outputencoding>UTF-8</project.reporting.outputencoding>
        <java.version>1.8</java.version>
        <swagger.version>2.6.0</swagger.version>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger2</artifactId>
                <version>${swagger.version}</version>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger-ui</artifactId>
                <version>${swagger.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <modules>
        <module>eureka-server</module>
        <module>producer-server</module>
        <module>consumer-server</module>
        <module>config-server</module>
        <module>config-client</module>
        <module>zuul-server</module>
        <module>auth-server</module>
        <module>hystrix-dashboard</module>
        <module>turbine</module>
    </modules>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### idea 同个项目启动多个服务
1.打开页面

![输入图片说明](https://images.gitee.com/uploads/images/2018/0725/155535_94ba8623_1305332.png "屏幕截图.png")

2.选择需要启动多次的项目，去掉勾选Single instance only

![输入图片说明](https://images.gitee.com/uploads/images/2018/0725/155648_fa322605_1305332.png "屏幕截图.png")
