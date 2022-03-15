# SpringBoot配置管理

## 1. 启动流程

![image-20220315093208543](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203150932696.png)

原图链接：[Spring启动流程 | ProcessOn免费在线作图,在线流程图,在线思维导图 |](https://www.processon.com/view/link/5bf96f8be4b08522107182b5)

## 2. bean自动装配

**@EnableAutoConfiguration**：用于开启全局自动装配，在启动主类的@SpringBootApplication中已经开启。

**SpringFactoriesLoader**会以@EnableAutoConfiguration的包名和类名**org.springframework.boot.autoconfigure.EnableAutoConfiguration**为Key查找**spring.factories**文件，并将value中的类名实例化记载到SpringBoot应用中。

源码中的自动装载类举例：

![image-20220315093618910](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203150936027.png)

## 3. YAML配置变量

### YAML概述

*YAML*是"YAML Ain't a Markup Language"（YAML不是一种标记语言）,  用一定的格式代表数据解构，多用于配置文件。





### YAML语法及占位符语法

#### 基础语法

- `-` 表示数组中的一个元素

- 字符串的单引号与双引号：

  - 双引号: 会转义字符串⾥⾯的特殊字符，如下⾯\n被转义为换⾏： name: “zhangsan \n lisi”：输出：zhangsan 换⾏ lisi 
  - 单引号: 不会转义特殊字符，特殊字符最终只是作为⼀个普通的字符串数据，如：name: ‘zhangsan \n lisi’：输出：zhangsan \n lisi

- ⽀持松散的语法：以下三种写法相同建议使用==中划线==

  - ```yml
    family-name = familyName  = family_name
    ```

#### 配置文件占位符

##### 随机数占位符：

${random.value} - 类似uuid的随机数，没有"-"连接 

${random.int} - 随机取整型范围内的⼀个值 

${random.long} - 随机取⻓整型范围内的⼀个值 

${random.long(100,200)} - 随机⽣成⻓整型100-200范围内的⼀个值 

${random.uuid} - ⽣成⼀个uuid，有短杠连接 

${random.int(10)} - 随机⽣成⼀个10以内的数 

${random.int(100,200)} - 随机⽣成⼀个100-200 范围以内的数

##### 默认值:

⽐如为配置father.best属性，如果family.father.name存在则 father.best=${family.fath er.name} ,family.father.name这个配置不存在，则取值 father.best=tom  :

```yml
father:
   best: ${family.father.name:tom}
```



### 使用@Value，从yml配置文件中读取变量值：

1. 在resource下创建 application.yml （配置文件的名称只能为application）

2. 在yml文件下配置变量值

   - ```yml
     family:
       family-name: "happy family"
       father:
         name: "tom"
         age: 22
         email: "123123@qq.com"
       mother:
         alias:
           - "rose"
           - "alice"
       child:
         name: "jack"
         age: 6
         friends:
           - hobby: "football"
             gender: "male"
           - hobby: "football"
             gender: "female"
     ```

   - 创建对应配置的实体类：

     ```java
     @Data
     //标注为组件，交由spring托管，使用时可以使用@Autowired自动注入
     @Component
     public class Family {
         //获取配置文件中的内容
         @Value("${family.family-name}")
         private String familyName;
         private Father father;
         private Mother mother;
         private Child child;
     }
     ```

   - 使用注解@Value("${family.family-name}")取到值，这里的路径是按照yml中的缩进使用 . 进行划分。

### 使用@ConfigurationProperties自动获取所有属性的值

在需要读取变量的类上使用@ConfigurationProperties注解，**prefix的值为yml的顶格变量**。

![image-20220315095440296](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203150954333.png)

### 两者的区别

![image-20220315103734287](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151037327.png)

## 4. 配置属性值数据绑定校验

进行数据校验的步骤：

1. 引入依赖：

   ```xml
   <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-validator</artifactId>
      <version>6.2.0.Final</version>
   </dependency>
   ```

   

2. 在需要要校验的属性装配类上加@Validated
   - ![image-20220315100428115](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151004148.png)

3. 在校验的属性上设置规则

   - ```java
     //表示校验长度，最小5，最大20，错误信息自定义
     @Length(min = 5,max = 20,message = "家庭名称长度必须在5-20之间")
     private String familyName;
     ```

   - 其他的一些校验注解：

     ![image-20220315104144179](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151041243.png)

   - 附加：

     ![](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151042703.png)

## 5. 加载额外配置文件的方式

### properties配置⽂件加载

步骤：

1. esouces⽬录新建family.properties

   ```properties
   family.family-name=happy family
   family.father.name=汤米
   family.father.age=22
   family.father.email=123123@qq.com
   family.mother.alias[0]=rose
   family.mother.alias[1]=alice
   family.child.name=jack
   family.child.age=6
   family.child.friends[0].hobby=football
   family.child.friends[0].gender=male
   family.child.friends[1].hobby=football
   family.child.friends[1].gender=female
   ```

2. 在family类上加上@PropertySource(value = {"classpath:family.properties"})

   ```java
   Data
   @Component
   @ConfigurationProperties(prefix = "family")
   @Validated
   @PropertySource(value = {"classpath:family.properties"})
   public class Family {
   }
   ```

### 使用yml配置文件自定义（不用application.yml命名）

尽管其默认不⽀持读取YAML格式外部配置⽂件，但是我们可以通过继承 DefaultPropertySourceFactory ，然后对它的createPropertySource进⾏⼀下改造。就可以 实现YAML的“额外”配置⽂件加载。

步骤：

1. 新建工具类：

   ```java
   public class MixPropertySourceFactory extends
   DefaultPropertySourceFactory {
     @Override
     public PropertySource<?> createPropertySource(@Nullable String name,
                                                   EncodedResource resource)
   throws IOException {
       String sourceName = name != null ? name :
   resource.getResource().getFilename();
       if (sourceName != null
             &&(sourceName.endsWith(".yml") ||
   sourceName.endsWith(".yaml"))) {
         Properties propertiesFromYaml = loadYml(resource);
         //将YML配置转成Properties之后，再⽤PropertiesPropertySource绑定
         return new PropertiesPropertySource(sourceName,
   propertiesFromYaml);
      } else {
         return super.createPropertySource(name, resource);
      }
    }
     //将YML格式的配置转成Properties配置
     private Properties loadYml(EncodedResource resource) throws
   IOException{
       YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
       factory.setResources(resource.getResource());
       factory.afterPropertiesSet();
       return factory.getObject();
    }
   }
   ```

2. 准备yml配置文件（自定义命名）如：

   ```yml
   family:
     family-name: "happy family"
     father:
       name: "tom"
       age: 22
       email: "123123@qq.com"
     mother:
       alias:
         - "rose"
         - "alice"
     child:
       name: "jack"
       age: 6
       friends:
         - hobby: "football"
           gender: "male"
         - hobby: "football"
           gender: "female"
   ```

3. 在需要读取的类上加上注解，指明factory的值为新建的工具类。

   ```java
   PropertySource(value = {"classpath:family.yml"}, factory =MixPropertySourceFactory.class)
   public class Family {
   }
   ```



## 6. Spring记载xml配置的bean实现自动装配

### @ImportResource注解的使用

1. 新建服务类：

   ```java
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class TestBean {
       private String name;
   }
   ```

2. 在resource下新建bean.xml进行配置

   - 这里的bean标签 id 相当于 “对象名”，class要写被配置类的全类名
   - 这里使用构造器注入时，被配置的类需要有参无参构造器都有才可以成功配置

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="testBean" class="com.qzk.service.TestBean">
           <!--属性注入-->
           <property name="name" value="SpringBoot"/>
       </bean>
   
   
       <bean id="testBean1" class="com.qzk.service.TestBean">
           <!--构造器注入-->
           <constructor-arg name="name" value="spring"></constructor-arg>
       </bean>
   </beans>
   ```

3. 编写测试类：

   **注意：测试需要Spring容器环境测试：**

   在测试类上加上注解：

   - @SpringBootTest
     @ExtendWith(SpringExtension.class) //junit5 写法
     //@RunWith(SpringRunner.class) junit4 写法
   
   ```java
   @SpringBootTest
   @ExtendWith(SpringExtension.class)
   class UserTest {
   
       @Autowired
       User user;
   
       @Test
       public void print(){
           System.out.println(user);
       }
   
   }
   ```

测试结果：

![image-20220315145420344](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151454403.png)



## 7. SpEL表达式绑定配置

Spring Expression Language (SpEL)是-种功能非常强大的表达式语言，可用于在运行时查询和操作对象。SpEL书写 在XML配置文件或者Annotation注解上，在Spring Bean的创建过程中生效。

### 使⽤SpEL表达式绑定字符串集合

1. 创建⼀个配置⽂件employee.properties

   ```properties
   employee.names=aaa,bbb,张三
   employee.age={noe:'27',tow:'28',three:'29'}
   ```

2. 创建⼀个配置类 Employee

   ![image-20220315151212589](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151512653.png)

3. 测试

   ![image-20220315151550232](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151515325.png)





### SpEL获取系统环境变量

![image-20220315151748801](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151517865.png)

输出结果：

![image-20220315151838448](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151518502.png)

## 8. profile不同环境使用不同配置

**为了减少配置修改的次数，实现开发环境和测试环境的配置切换而需要切换项目的环境配置**

常用的环境：

- application.yml  全局配置文件
- application-dev.yml 开发环境
- application-test.yml 测试环境
- application-prod.yml 生产环境

切换方式：

- 在主配置文件中切换

  ```yml
  #主配置文件
  spring:
    profiles:
      active: dev
  ```

![image-20220314163336558](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203141633619.png)

- 配置VM options 、 Program argments 、 active profile来切换

  - VM option来切换

    ![image-20220314164123564](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203141641639.png)

  - Program argments来切换

    ![image-20220314164300076](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203141643145.png)

  - active profile 来切换

    ![image-20220314164410067](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203141644132.png)

- 通过启动时的参数配置来切换

  ![image-20220314164846600](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203141648652.png)





## 9. 配置文件加载的优先级

### 加载的优先级

第一优先级：file/config 项目目录下的config下的application.yml 

第二优先级：file  项目目录下的application.yml 

第三优先级：resource/config  项目下的resource的config下的application.yml 

第四优先级：resource  项目resouce下的application.yml

### 自定义路径的配置加载方式

```bash
java -jar boot-launch-1.0.jar --spring.config.location=application.yml的路径
```

### Maven的跳过单元测试打包

```bash
mvn package -Dmaven.test.skip=true
```





## 10. 配置文件加密

### 1.关于**jasypt**

它是由⼀个国外⼤神写的⼀个SpringBoot的⼯具包，⽤来加密配置⽂件中的信息。

### 2.使用jasypt进行加密

查看最新版本可以到:https://github.com/ulisesbocchio/jasypt-spring-boot

步骤：

1. 引入jar包

   ```xml
   <dependency>
     <groupId>com.github.ulisesbocchio</groupId>
     <artifactId>jasypt-spring-boot-starter</artifactId>
     <version>3.0.4</version>
   </dependency>
   ```

2. 在application.yml中配置密钥

   ```yml
   #jasypt加密的密匙
   jasypt:
     encryptor:
       password: Y6M9fAJQdU7jNp5MW
   ```

   

3. 编写测试类，测试加密：

   ![image-20220315153015290](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151530386.png)

4. 在yml配置使用时，使用`ENC(密文)`的方式来进行配置：

![image-20220315153703815](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151537883.png)

5. 再来测试family读取的内容：

![image-20220315153825170](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203151538278.png)
