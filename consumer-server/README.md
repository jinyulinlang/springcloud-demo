## 搭建服务消费者
### 1. 编写pom.xml文件
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zkane</groupId>
    <artifactId>consumer-server</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>consumer-server</name>
    <description>Spring Cloud Consumer Server</description>

    <parent>
        <groupId>com.zkane</groupId>
        <artifactId>spring-cloud-demo</artifactId>
        <version>1.0.0</version>
        <relativePath/>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```
### 2. 编写application.yml配置文件
```
server:
  port: 8200
spring:
  application:
    name: consumer-server
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8000/eureka/
```
### 3. 在启动类上添加注解
- @EnableFeignClients注解是使用feign来调用服务提供者的API接口
```
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ConsumerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerServerApplication.class, args);
	}
}
```
### 4. 使用feign调用服务提供者的API接口
```
@RestController
public class ConsumerController {

    @Autowired
    private ProducerRemote producerRemote;

    @GetMapping("/")
    public String get() {
        return "Consumer: " + producerRemote.get();
    }

}
```
### 5. 使用hystrix实现服务熔断机制
- 引入依赖
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```
- 需要在application.yml中配置feign启用hystrix
```
feign:
  hystrix:
    enabled: true
```
- 在启动类添加启动hystrix的注解
```
@EnableHystrix
public class ConsumerServerApplication{

	public static void main(String[] args) {
		SpringApplication.run(ConsumerServerApplication.class, args);
	}
}
```
- 在@FeignClient注解上添加fallback属性，指定熔断后调用的本地方法
```
@Component
@FeignClient(value = "producer-server", fallback = ProducerRemoteHystrix.class)
public interface ProducerRemote {
    @GetMapping("/")
    String get();

}
```
- 熔断后调用的本地方法
```
@Component
public class ProducerRemoteHystrix implements ProducerRemote {
    @Override
    public String get() {
        return "Producer Server 的服务调用失败";
    }

}
```