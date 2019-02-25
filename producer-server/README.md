## 搭建服务提供者
### 1. 在pom.xml文件中导入依赖
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.zkane</groupId>
	<artifactId>producer-server</artifactId>
	<version>1.0.0</version>
	<packaging>jar</packaging>

	<name>producer-server</name>
	<description>Spring Cloud Producer Server</description>

    <parent>
        <groupId>com.zkane</groupId>
        <artifactId>spring-cloud-demo</artifactId>
        <version>1.0.0</version>
        <relativePath/>
    </parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

</project>
```
### 2. 在application.yml配置文件中配置
```
server:
  port: 8100
spring:
  application:
    name: producer-server
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8000/eureka/
```
### 3. 在启动类上添加注解：@EnableDiscoveryClient
```
@EnableDiscoveryClient
@SpringBootApplication
public class ProducerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerServerApplication.class, args);
	}
}
```
### 4. 编写controller代码，提供restful风格的API访问
```
@RestController
public class ProducerController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/")
    public String get() {
        return "Producer Server port: " + port;
    }

}
```
### 5.可以修改yml配置文件的端口设置，启动多个服务来测试负载均衡
例如，先启动8100端口的服务，然后修改端口为8101再进行启动。
idea启动多个服务需要设置run/debug configurations，去掉勾选Single instance only