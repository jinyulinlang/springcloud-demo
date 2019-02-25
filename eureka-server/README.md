## 搭建注册中心
### 1.在注册中心服务的pom.xml导入依赖
```
    <parent>
        <groupId>com.zkane</groupId>
        <artifactId>spring-cloud-demo</artifactId>
        <version>1.0.0</version>
        <relativePath/>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
### 2.在application.yml文件中配置
```
server:
  port: 8000
spring:
  application:
    name: eureka-server
eureka:
  client:
    # 表示是否将自己注册到Eureka Server，默认为true。
    register-with-eureka: false
    # 表示是否从Eureka Server获取注册信息，默认为true。
    fetch-registry: false
    # 设置与Eureka Server交互的地址，查询服务和注册服务都需要依赖这个地址。默认是http://localhost:8761/eureka ；多个地址可使用,分隔
    service-url:
      defaultZone: http://localhost:${server.port}/eureka/
```
### 3.在启动类上配置注解：@EnableEurekaServer
```
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
```
### 4.开启密码认证
- 导入spring security的依赖jar包
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
- 在application.yml中配置用户名和密码
```yaml
spring:
  security:
    user:
      name: admin
      password: 123456
```
- 编写一个安全配置类，不然其他服务在启动时会报错`Cannot execute request on any known server`
```java
package com.zkane.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: 594781919@qq.com
 * @review:
 * @date: 2018/8/23
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
```
- 在其他服务配置注册中心
> admin为用户名，123456为密码
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://admin:123456@localhost:8000/eureka/
```