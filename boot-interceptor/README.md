# 生命周期内的拦截过滤与监听

## Servlet域对象与属性变化监听

### Servlet监听器

- ServletContext
- HttpSession
- ServletRequest

监听器使用了设计模式种的**观察者**模式，它关注特定事务的创建、销毁以及变化并做出回调函数。----> **异步机制**

![image-20220404191229566](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041912643.png)

### 监听器的主要接口

- ServletContextListener
- HttpSessionListener
- ServletRequestListener

### 使用场景

系统启动的时候，初始化信息。

### 实现

1. 在启动类加注解@ServletComponentScan

   ![image-20220404161649707](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041616061.png)

2. 编写自定义监听类

   ```java
   package com.qzk.interceptor.listener;
   
   import javax.servlet.*;
   import javax.servlet.annotation.WebListener;
   import javax.servlet.http.HttpSessionAttributeListener;
   import javax.servlet.http.HttpSessionBindingEvent;
   import javax.servlet.http.HttpSessionEvent;
   import javax.servlet.http.HttpSessionListener;
   
   /**
    * @Description TODO
    * @Date 2022-04-04-10-40
    * @Author Courage
    */
   @WebListener
   public class CustomListener implements ServletContextListener,
           HttpSessionListener, ServletRequestListener,
           ServletRequestAttributeListener, HttpSessionAttributeListener {
       @Override
       public void contextInitialized(ServletContextEvent sce) {
           System.out.println("context容器初始化");
       }
   
       @Override
       public void contextDestroyed(ServletContextEvent sce) {
           System.out.println("context容器销毁");
       }
   
       @Override
       public void attributeAdded(ServletRequestAttributeEvent srae) {
           if ("a".equals(srae.getName())) {
               System.out.println("a添加进了requestAttribute");
           }
       }
   
       @Override
       public void attributeRemoved(ServletRequestAttributeEvent srae) {
           if ("a".equals(srae.getName())) {
               System.out.println("a移除了requestAttribute");
           }
       }
   
       @Override
       public void requestDestroyed(ServletRequestEvent sre) {
           System.out.println("request销毁了");
       }
   
       @Override
       public void requestInitialized(ServletRequestEvent sre) {
           System.out.println("request初始化");
       }
   
       @Override
       public void sessionCreated(HttpSessionEvent se) {
           System.out.println("session创建");
       }
   
       @Override
       public void sessionDestroyed(HttpSessionEvent se) {
           System.out.println("session销毁了");
       }
   
       @Override
       public void attributeAdded(HttpSessionBindingEvent se) {
           if ("a".equals(se.getName())) {
               System.out.println("a添加了sessionAttribute");
           }
       }
   
       @Override
       public void attributeRemoved(HttpSessionBindingEvent se) {
           if ("a".equals(se.getName())) {
               System.out.println("a移除了sessionAttribute");
           }
       }
   }
   
   ```

   运行结果

   ![image-20220404162013677](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041620744.png)

   

## 过滤器

ServletFilter 过滤器是可以用于 Servlet 编程的Java类。

### 目的

- 在客户端请求访问后端资源之前，拦截这些请求
- 在服务器的响应发送回客户端之前，处理这些响应



### 使用场景

- 授权逻辑
- IP黑名单
- 对经过加密的http请求数据，进行统一加密
- 敏感词过滤

### 特点

- 可以过滤叼所有请求
- 能够改变请求的数据内容

### 过滤器的实现

1. 定义过滤器

2. 添加注解@WebFilter(filterName = "过滤器名字",urlPatterns = {"拦截的url"})

   ```java
   package com.qzk.interceptor.filter;
   
   import javax.servlet.*;
   import javax.servlet.annotation.WebFilter;
   import java.io.IOException;
   
   /**
    * @Description TODO
    * @Date 2022-04-04-13-12
    * @Author Courage
    */
   @WebFilter(filterName = "myFilter",urlPatterns = {"/*"})
   public class MyFilter implements Filter {
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           System.out.println("myFilter初始化");
       }
   
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           System.out.println("请求处理之前----myFilter之前过滤的请求");
           filterChain.doFilter(servletRequest,servletResponse);
           System.out.println("请求处理之后----myFilter之后处理响应");
       }
   
       @Override
       public void destroy() {
           System.out.println("myFilter销毁");
       }
   }
   
   ```

   ```java
   package com.qzk.interceptor.filter;
   
   import javax.servlet.*;
   import javax.servlet.annotation.WebFilter;
   import java.io.IOException;
   
   /**
    * @Description TODO
    * @Date 2022-04-04-13-05
    * @Author Courage
    */
   @WebFilter(filterName = "customFilter", urlPatterns = {"/*"})
   public class CustomFilter implements Filter {
       @Override
       public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           System.out.println("请求处理之前----CustomFilter之前过滤的请求");
           filterChain.doFilter(servletRequest,servletResponse);
           System.out.println("请求处理之后----CustomFilter之后处理响应");
       }
   
       @Override
       public void init(FilterConfig filterConfig) throws ServletException {
           System.out.println("customFilter初始化");
       }
   
       @Override
       public void destroy() {
           System.out.println("customFilter销毁");
       }
   }
   
   ```

   > 这里有一个执行顺序的问题：默认按照filterName的配置进行。

   运行效果：

   ![image-20220404162506388](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041625437.png)



### 自定义过滤器执行顺序

Spring提供了一个可以自定义过滤器执行顺序的过滤器配置方式，具体配置方式为：

```java
package com.qzk.interceptor.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-04-04-13-15
 * @Author Courage
 */
@Configuration
public class FilterRegistration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.setName("customFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(10);
        return  registrationBean;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean2(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setName("myFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(5);
        return  registrationBean;
    }
}

```

> 通过 registrationBean.setOrder(int arg) 的方式可以指定过滤器执行的顺序

## 拦截器（Spring独有）

### 拦截器Interceptor

- preHandle 处理执行controller之前的操作
- postHandle 处理返回后；Jsp（或模板引擎）渲染之前的操作
- after Completion 处理Jsp（或模板引擎）渲染之后的操作

![image-20220404191543969](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041915034.png)

> 执行顺序是先执行过滤器再执行拦截器

### 拦截器与过滤器的区别

- 规范不同，拦截器只在spring中可用，独有
- **拦截器**可以使用Spring IOC容器中的bean
- **拦截器**可以访问Spring上下文中的对象
- 拦截力度：过滤器比拦截器更大，拦截器更细

![](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041913733.png)

## 拦截器实现统一访问日志

需求：针对当前系统的每次一次接口访问，要记录是什么问访问（用户名）、什么时间访问、访问耗时多长时间、使用什么HTTP method方法访问、访问结果如何等等，可以称为审计日志。

日志输出：

- 控制台
- 输出一个单独的日志文件
- 持久化

实现步骤

1. 定义一个日志访问内容的实体类
2. 自定义一个日志拦截器
3. 注册

> 日志实体类AccessLog：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLog {
    private String ip;
    private String url;
    private String username;
    private Integer duration;
    private Date createTime;
    private Integer httpStatus;
    private String httpMethod;

    @Override
    public String toString() {
        return "ip = " + ip + " , " + "url = " + url + " , " + "username = " + username+ " , " + "duration = " + duration+ " , " + "createTime = " + createTime+ " , " + "httpStatus = "
                + httpStatus + " , "+ "method = " + httpMethod;
    }
}
```

> 拦截器类AccessLogInterceptor需要实现HandlerInterceptor接口：

```java
package com.qzk.interceptor.springinterceptor;

import com.qzk.interceptor.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-04-04-14-27
 * @Author Courage
 */
@Component
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(request.getRemoteAddr());
        accessLog.setHttpMethod(request.getMethod());
        accessLog.setUrl(request.getRequestURI());
        request.setAttribute("sendTime",System.currentTimeMillis());

        request.setAttribute("accessLog",accessLog);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AccessLog accessLog = (AccessLog) request.getAttribute("accessLog");

        int status = response.getStatus();
        accessLog.setHttpStatus(status);

        accessLog.setUsername(request.getParameter("username"));

        Long currentTime = System.currentTimeMillis();

        Long sendTime = Long.parseLong(request.getAttribute("sendTime").toString());

        accessLog.setDuration(Integer.valueOf((currentTime-sendTime)+""));
        accessLog.setCreateTime(new Date());

        log.info(accessLog.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求之后的处理。。。。");
    }
}

```

> 在带有@Configuration并且实现了WebMvcConfigurer的类注册拦截器

```java
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {


    @Resource
    AccessLogInterceptor accessLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLogInterceptor).addPathPatterns("/*");
    }
}
```

运行结果：

![image-20220404163455692](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041634737.png)

## 事件的发布与监听

### 事件监听介绍

事件的几个角色

- 事件发布者（事件源）
- 事件监听者
- 事件本身

事件监听的特点

- 异步
- 解耦
- 不规律性

### 实现方式

- 消息队列    中间件->发布订阅模式

- JDK自带     java.util.EventListener
- Spring环境下实现的事件发布监听

### 具体实现

1. 自定义事件

2. 定义事件的监听器

   1）写代码向ApplicationContext中添加监听器

   2）使用@Component注解将监听器装载如spring容器

   3）在application.yml中配置监听器

   4）通过@EventListener注解实现事件监听定义事件

> 定义事件

```java
package com.qzk.interceptor.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description TODO
 * @Date 2022-04-04-15-07
 * @Author Courage
 */
public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source)
    {
        super(source);
    }
}

```



> 方式1：写代码向ApplicationContext中添加监听器

```java
package com.qzk.interceptor.listener;

import com.qzk.interceptor.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @Description TODO
 * @Date 2022-04-04-16-41
 * @Author Courage
 */
@Slf4j
public class MyListener1 implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info(String.format("%s 监听到事件源：%s.", MyListener1.class.getName(), event.getSource()));
    }
}

----------------------------------------------------------


//需要在启动类进行添加和发布，并且使用启动类运行测试
@SpringBootApplication
//@ServletComponentScan
public class BootInterceptorApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context  = SpringApplication.run(BootInterceptorApplication.class, args);
        //添加事件监听
        context.addApplicationListener(new MyListener1());
        //发布事件
        context.publishEvent(new MyEvent("测试事件"));
    }

}
```

测试结果：

![image-20220404164952809](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041649890.png)



> 方式2：使用@Component注解将监听器装载如spring容器

```java
package com.qzk.interceptor.listener;

import com.qzk.interceptor.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2022-04-04-16-41
 * @Author Courage
 */
@Component
@Slf4j
public class MyListener2 implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info(String.format("%s监听到事件源：%s.", MyListener2.class.getName(), event.getSource()));
    }

}


```

运行效果

![image-20220404165148920](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041651995.png)





> 方式3：在application.yml中配置监听器

```java
@Slf4j
public class MyListener3 implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info(String.format("%s监听到事件源：%s.", MyListener3.class.getName(), event.getSource()));
    }
}
```

配置文件application.yml

```yml
context:
  listener:
    classes: com.qzk.interceptor.listener.MyListener3
```

运行效果

![image-20220404165633229](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041656308.png)





> 方式4：通过@EventListener注解实现事件监听定义事件

```java
@Slf4j
public class MyListener4 {
    @EventListener
    public void listener(MyEvent event) {
        log.info(String.format("%s监听到事件源：%s.", MyListener4.class.getName(), event.getSource()));
    }
}
```

运行效果：

![image-20220404170052650](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041700727.png)



## 应用启动时的监听

### 简介

Spring Boot提供了两个接口：CommandLineRunner、ApplicationRunner，用于启动应用时做特殊处理，这些代码会在SpringApplication的run()方法运行完成之前被执行。

相对于Spring的ApplicationListener接口自定义监听器、Servlet的ServletContextListener监听器。使用二者的好处在于，可以方便的使用应用启动参数，根据参数不同做不同的初始化操作。

### 常用场景

- 将系统常用的数据加载到内存
-  应用上一次运行的垃圾数据清理
-  系统启动成功后的通知的发送



### 代码练习

#### 通过@Component定义方式实现

> CommandLineRunner：参数是字符串数组

```java
@Component
@Slf4j
public class CommandLineStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("CommandLineRunner传入参数：{}", Arrays.toString(args));
    }
}
```

在启动项配置如下：

![image-20220404190605999](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041906074.png)

运行效果：

![image-20220404190527977](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041905066.png)



> ApplicationRunner：参数被放入ApplicationArguments，通过getOptionNames()、getOptionValues()、getSourceArgs()获取参数

```java
@Slf4j
@Component
public class AppStartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner参数名称: {}", args.getOptionNames());
        log.info("ApplicationRunner参数值: {}", args.getOptionValues("age"));
        log.info("ApplicationRunner参数: {}", Arrays.toString(args.getSourceArgs()));
    }
}
```

运行效果：

![image-20220404190756047](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041907127.png)

#### 通过@Bean定义方式实现

> 这种方式可以指定执行顺序，注意前两个Bean是CommandLineRunner，最后一个Bean是ApplicationRunner 

```java
package com.qzk.interceptor.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * @Description TODO
 * @Date 2022-04-04-19-09
 * @Author Courage
 */
@Configuration
public class BeanRunner {
    @Bean
    @Order(1)
    public CommandLineRunner runner1(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args){
                System.out.println("BeanCommandLineRunner run1()" + Arrays.toString(args));
            }
        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner runner2(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args){
                System.out.println("BeanCommandLineRunner run2()" + Arrays.toString(args));
            }
        };
    }

    @Bean
    @Order(3)
    public ApplicationRunner runner3(){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args){
                System.out.println("BeanApplicationRunner run3()" + Arrays.toString(args.getSourceArgs()));
            }
        };
    }
}

```

运行效果：

![image-20220404191023269](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204041910338.png)





