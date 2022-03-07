# SpringBoot 起步

官网：https://spring.io/



## 概述

Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。



## 快速开始

1. 新建一个springboot项目

   ![image-20220307160718187](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071607277.png)

2. 写一个简单的控制器

   ```java
   @RestController
   public class HelloController {
       @GetMapping("/hello")
       public String hello(){
           return "hello world";
       }
   }
   ```

   ![image-20220307160846481](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071608593.png)

3. 运行测试

   - 运行默认的springboot的启动器

   ![image-20220307160918005](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071609109.png)

4. 访问端口发起请求

   ![image-20220307161030960](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071610016.png)

## 关于lombok

lombok（常用）：

- 自动生成属性的get & set       @Data
- 自动生成有参、无参的构造器   @AllArgsConstructor &  @NoArgsConstructor
- 建造者模式  @Builder

## 关于日志

- 有lombok的情况下(`@Slf4j`)

  - 直接使用`log.info("123");`
- 没有lombok的情况下
  - `Logger log = LoggerFactory.getLogger(Book.class);`

## 热更新

1. 添加依赖jar包：

```xml
		<!--热更新-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
```

2. 设置idea支持热更新

![image-20220307165434267](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071654337.png)

![image-20220307165411328](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071654408.png)

默认情况下，支持热加载的目录为：

![image-20220307170113060](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203071701103.png)
