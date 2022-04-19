# RESTful的实现与测试

## 1. 概述

REST（Representational State Transfer）表述性状态传递，决定了接口的形式与规则，RESTful是基于HTTP方法的API设计风格，而不是一种新的技术。

1. 看URL就知道要什么资源
2. 看HTTP method就知道对资源干啥
3. 看HTTP status code就知道结果如何

![image-20220310154005925](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203101540042.png)





## 2. Spring常用注解

### 1. 接口注解

@RestController 

表示这是一个不返回页面渲染的后端接口控制器

### @RequestMapping

@RequestMapping注解是⽤于标注HTTP服务端点。

属性：

- value： 应⽤请求端点，最核⼼的属性，⽤于标志请求处理⽅法的唯⼀性
- method： HTTP协议的method类型， 如：GET、POST、PUT、DELETE等； 
- consumes： HTTP协议请求内容的数据类型（Content-Type），例如application/json, text/html; produces: HTTP协议响应内容的数据类型。 
- params： HTTP请求中必须包含某些参数值的时候，才允许被注解标注的⽅法处理请求。 
- headers： HTTP请求中必须包含某些指定的header值，才允许被注解标注的⽅法处理请求。

`@RequestMapping(value = "/article", method = POST)` 

`@PostMapping(value = "/article")`

两种写法起到的是⼀样的效果，也就是PostMapping等同于@RequestMapping的 method等于POST。同理：@GetMapping、@PutMapping、@DeleteMapping也都是简写的⽅式。

### @RestController与@Controller

#### @Controller

- 告诉Spring，被该注解标注的类是⼀个Spring的Bean，需要被注⼊到Spring的上下⽂环境中。 

- 该类⾥⾯所有被RequestMapping标注的注解都是HTTP服务端点。

#### @RestController

**相当于 @Controller和@ResponseBody结合**

作为Controller的作⽤，将控制器类注⼊到Spring上下⽂环境，该类RequestMapping标注 ⽅法为HTTP服务端点。

作为ResponseBody的作⽤，请求响应默认使⽤的序列化⽅式是JSON，⽽不是跳转到jsp或模板⻚⾯。

### @PathVariable 与@RequestParam

PathVariable⽤于URI上的{参数}，如下⽅法⽤于删除⼀篇⽂章，其中id为⽂章id。如：我们的请求 URL为“/article/1”,那么将匹配DeleteMapping并且PathVariable接收参数id=1。⽽ RequestParam⽤于接收普通表单⽅式或者ajax模拟表单提交的参数数据。

```java
@DeleteMapping("/article/{id}")
public @ResponseBody AjaxResponse deleteArticle(@PathVariable Long id) {
}
@PostMapping("/article")
public @ResponseBody AjaxResponse deleteArticle(@RequestParam Long id) {
}
```

### **2. 复杂参数传递&接收注解**

![image-20220310160612121](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203101606190.png)



## 3. jackson注解

- @JsonIgnore 用于实体类的属性上，这样在返回对象的时候会不返回该属性
- @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 用于实体类的属性上，表示对该属性格式化。
- @JsonProperty("writer") 用于实体类属性上，表示在返回时为该属性起别名
- @JsonPropertyOrder(value = {"tittle", "content"}) 用于实体类上，表示在返回前端的时候属性的顺序（先tittle再content）

### 序列化&反序列化

- 序列化
  - 按规定的格式、顺序的将实体类对象转换为JSON字符串
- 反序列化
  - 在客户端将请求数据上川岛服务端的时候，自动的处理JSON数据对象中的字符串、数字，将其转换为包含Date类型、Integer等类型的对象

### Jackson全局配置

在application.yml中可以进行Jackson的全局配置：

```yml
spring:
 jackson:
    #⽇期类型格式化
   date-format: yyyy-MM-dd HH:mm:ss
   serialization:
      #格式化输出，通常为了节省⽹络流量设置为false。因为格式化之后会带有缩进，⽅便阅
读。
     indent_output: false
      #某些类对象⽆法序列化的时候，是否报错
     fail_on_empty_beans: false
    #设置空如何序列化，⻅下⽂代码⽅式详解
   defaultPropertyInclusion: NON_EMPTY
   deserialization:
      #json对象中有不存在的属性时候，是否报错
     fail_on_unknown_properties: false
   parser:
      #允许出现特殊字符和转义符
     allow_unquoted_control_chars: true
      #允许出现单引号
     allow_single_quotes: true
```





## 4.使⽤Mockito编码完成接⼝测试

测试使用代码：

**文章类：article.java**

```java
package com.qzk.boot.restful.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-03-10-16-27
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder(value = {"tittle", "content"})
public class Article {
    @JsonIgnore
    private Long id;
    @JsonProperty("writer")
    private String author;
    private String tittle;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Date updateTime;
    private Reader[] readerList;
}
```

 



**读者类：Reader.java**

```java
package com.qzk.boot.restful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-03-10-19-03
 * @Author Courage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Reader {
    private String name;
    private Integer age;
}

```





**控制器：ArticleController.java**

```java
package com.qzk.boot.restful.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qzk.boot.restful.common.AjaxResponse;
import com.qzk.boot.restful.model.Article;
import com.qzk.boot.restful.model.Reader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-03-10-16-29
 * @Author Courage
 */

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @PostMapping("/articles/body")
    public AjaxResponse saveArticle(@RequestBody Article article) {
        System.out.println("saveArticle:" + article);
        return AjaxResponse.success(article);
    }
}

```





**测试类：ArticleControllerTest.java**

```java
package com.qzk.boot.restful.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Description TODO
 * @Date 2022-03-12-19-12
 * @Author Courage
 */
@Slf4j
class ArticleControllerTest {

    private static MockMvc mockMvc;


    @BeforeAll
    static void setUp() {
        //在所有测试⽅法执⾏之前进⾏mock对象初始化
        mockMvc = MockMvcBuilders.standaloneSetup(new
                ArticleController()).build();
    }

    @Test
    void saveArticle() throws Exception {
        //""" 是jdk15新特性，代表一个文本块。
        String article = """
               {
                    "id": 1,
                    "writer": "qzk",
                    "title": "SpringBoot",
                    "content": "SpringBoot",
                    "createTime": "2022-03-12 11:11:11",
                    "readerList":[{"name":"qzk","age":18},
                    {"name":"cw","age":20}]
               }""";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/v1/articles/body")
                        .contentType("application/json").content(article))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.writer").value("qzk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.readerList[0].age").value(18))
                .andDo(print())
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }

}
```





### 用法总结

```java
//模拟GET请求：
mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", userId));
//模拟Post请求：
mockMvc.perform(MockMvcRequestBuilders.post("uri", parameters));
//模拟⽂件上传：
mockMvc.perform(MockMvcRequestBuilders.multipart("uri").file("fileName",
"file".getBytes("UTF-8")));
//模拟session和cookie：
mockMvc.perform(MockMvcRequestBuilders.get("uri").sessionAttr("name",
"value"));
mockMvc.perform(MockMvcRequestBuilders.get("uri").cookie(new
Cookie("name", "value")));
//设置HTTP Header：
mockMvc.perform(MockMvcRequestBuilders
                       .get("uri", parameters)
                       .contentType("application/x-www-form-urlencoded")
                       .accept("application/json")
                       .header("", ""));

```





## 5. Swagger3即OpenAPI使⽤

Swagger 给我们提供了⼀个全新的维护 API ⽂档的⽅式，下⾯我们来了解⼀下它的优点： 

- 代码变，⽂档变。只需要少量的注解，Swagger 就可以根据代码⾃动⽣成 API ⽂档，很好的保 证了⽂档的时效性。 
- 跨语⾔性，⽀持 40 多种语⾔。 
- Swagger UI 呈现出来的是⼀份可交互式的 API ⽂档，我们可以直接在⽂档⻚⾯尝试 API 的调 ⽤，省去了准备复杂的调⽤参数的过程。 
- 可以将⽂档规范导⼊相关的⼯具（例如 SoapUI）, 这些⼯具将会为我们⾃动地创建⾃动化测

### 1. 整合springdoc-openapi

添加依赖：

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.6</version>
</dependency>
```

访问：http://localhost:8080/swagger-ui/index.html

![image-20220312202222530](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203122022866.png)



### 2. 将api分组显示

1. 新建OpenApiConfig.java类

   ```java
   package com.qzk.boot.restful.config;
   
   import org.springdoc.core.GroupedOpenApi;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   
   /**
    * @Description TODO
    * @Date 2022-03-12-20-26
    * @Author Courage
    */
   @Configuration
   public class OpenAPIConfig {
       @Bean
       public GroupedOpenApi articleAPI(){
           return GroupedOpenApi.builder().group("api-v1-article").pathsToMatch("/api/v1/articles/**").build();
       }
   
       @Bean
       public GroupedOpenApi helloAPI(){
           return GroupedOpenApi.builder().group("hello").pathsToMatch("/hello/**").build();
       }
   }
   ```

2. 测试：

![image-20220312203213354](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203122032486.png)

