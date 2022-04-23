# Redis和Spring Cache


## Redis简介

Redis是开源免费的 key-value 内存数据库，主要解决在高并发、大数据场景下，热点数据访问的性能问题，提供高性能的数据快速访问。项目中部分数据访问比较频繁，造成了数据库服务器的高压环境，这时候可以使用缓存提高访问效率。

## Redis的数据类型

- String类型 （StringRedisTemplate）
- List类型 （ValueOperations）
- Set类型 （ListOperations）
- Hash类型 （HashOperations）

下文会详细介绍这几种类型的使用方式。

## 整合Spring Data Redis

### 环境搭建

> 1. 加依赖

```xml
<!--redis启动器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!--连接池-->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

> 2.  写配置(application.yml)

```yml
spring:
  redis:
    database: 0        #索引数据库
    host: localhost    #服务器地址
    port: 6379         #服务器连接端口
    password:          #redis密码，默认为空
    timeout: 5000      #连接超时，单位毫秒
    lettuce:
      pool:
        max-active: 8  #连接池最大连接数（使用负数表示没有限制） 默认为 8
        max-wait: -1   #连接池最大等待时间（使用负数表示没有限制） 默认-1
        max-idle: 8    #连接池最大空闲链接 默认为 8
        min-idle: 0     #连接池中最小空闲链接 默认0
```

### StringRedisTemplate 使用

```java
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("name","张三",20, TimeUnit.SECONDS);
        String name = stringStringValueOperations.get("name");
        System.out.println(name);
    }
}
```

![image-20220417175253797](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204171752868.png)

成功存入，且20秒之后就失效。





### RedisTemplate 使用

#### `ValueOperations 存储对象`

```java
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTest {

    @Resource
    private RedisTemplate<String, Person> redisTemplate;

    @Test
    void testRedis(){
        ValueOperations<String, Person> stringPersonValueOperations = redisTemplate.opsForValue();
        Person person = Person.builder()
                .name("qzk")
                .address(new Address("China", "nanjing"))
                .build();
        stringPersonValueOperations.set("person",person,20,TimeUnit.SECONDS);
    }
}
```

> 这时会发现，redis中存储数据都是二进制的非自然语言，这是因为redis在存储对象的时候没有进行一些序列化的配置。
>
> ![image-20220417192335587](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204171923658.png)

#### 序列化配置解决乱码

> 1. 新建`RedisConfig`配置类

```java
package com.qzk.boot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description TODO
 * @Date 2022-04-17-19-28
 * @Author Courage
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //重点在这四行代码
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

```

> 2. 再次运行上文中的测试，拿到正确序列化的结果

![image-20220417193120587](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204171931677.png)

#### `ListOperations 存储 list`

```java
@Test
void testList() {
    ListOperations<String, Person> stringPersonListOperations = redisTemplate.opsForList();
    //添加数据
    stringPersonListOperations.leftPush("personList", Person.builder().name("张三").build());
    stringPersonListOperations.leftPush("personList", Person.builder().name("李四").build());
    stringPersonListOperations.leftPush("personList", Person.builder().name("王五").build());
    //取出一个数据
    Person person = stringPersonListOperations.leftPop("personList");
    System.out.println(person);
}
```

取出数据代表从lsit中拿出，所以上文使用`leftPop`取出的`王五`在redis中就被取出了。

![image-20220417194606728](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204171946813.png)



#### `SetOperations` 存储 Set（带去重）

```java
@Test
void testSet() {
    SetOperations<String, Person> stringPersonSetOperations = redisTemplate.opsForSet();
    Person p1 = Person.builder()
            .id(1)
            .name("qzk")
            .build();
    Person p2 = Person.builder()
            .id(2)
            .name("lmx")
            .build();
    //注意这里添加了两次 p1 对象，但是由于set是去重的，所以只会放入一个 p1
    stringPersonSetOperations.add("personSet",p1,p1,p2);
    Set<Person> personSet = stringPersonSetOperations.members("personSet");
    System.out.println(personSet);
}
```

![image-20220417195510892](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204171955958.png)





#### `HashOperations`存储 map

```java
@Test
void testHash() {
    HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
    Person person = Person.builder()
            .id(1)
            .name("qzk")
            .address(new Address("China", "苏州"))
            .build();
    stringObjectObjectHashOperations.put("personHash","id",person.getId());
    stringObjectObjectHashOperations.put("personHash","name",person.getName());
    stringObjectObjectHashOperations.put("personHash","address",person.getAddress());
    Object id = stringObjectObjectHashOperations.get("personHash", "id");
    Object name = stringObjectObjectHashOperations.get("personHash", "name");
    Object address = stringObjectObjectHashOperations.get("personHash", "address");
    System.out.println(id +","+ name +","+ address);
}
```

![image-20220417202656994](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204172026084.png)



### `使用 Redis Repository 快速crud`

> Redis相当于一个”内存数据库“，和主流的orm框架一样也提供了简单的 crud 的接口，只需要几步操作就可以实现快速生成增删改查的方法。

1. 在需要操作的实体类上加上注解`@RedisHash`,并且使用`@Id`注解指定实体类的主键

![image-20220417203752947](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204172037998.png)

2. 编写一个`PersonRepository`接口，继承`CrudRepository`

   ```java
   package com.qzk.boot.repository;
   
   import com.qzk.boot.entity.Person;
   import org.springframework.data.repository.CrudRepository;
   
   import java.util.Optional;
   
   /**
    * @Description TODO
    * @Date 2022-04-17-20-39
    * @Author Courage
    */
   public interface PersonRepository extends CrudRepository<Person,Integer> {
   
   }
   ```

3. 编写测试类

   ```java
   package com.qzk.boot.redis;
   
   import com.qzk.boot.entity.Address;
   import com.qzk.boot.entity.Person;
   import com.qzk.boot.repository.PersonRepository;
   import org.junit.jupiter.api.Test;
   import org.junit.jupiter.api.extension.ExtendWith;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit.jupiter.SpringExtension;
   
   import javax.annotation.Resource;
   import java.util.Optional;
   
   /**
    * @Description TODO
    * @Date 2022-04-17-20-42
    * @Author Courage
    */
   @SpringBootTest
   @ExtendWith(SpringExtension.class)
   public class RedisRepositoryTest {
   
       @Resource
       PersonRepository personRepository;
   
       @Test
       void repositoryTest() {
           Person person = Person.builder()
                   .id(1)
                   .name("qzk")
                   .address(new Address("China", "nanjing"))
                   .build();
           //存一个对象
           Person save = personRepository.save(person);
           System.out.println(save);
           //按id拿值
           Optional<Person> byId = personRepository.findById(1);
           //判空，非空就打印值
           byId.ifPresent(value -> System.out.println(value));
           //获取总记录数
           System.out.println(personRepository.count());
       }
   }
   
   ```

   ![image-20220417204908146](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204172049229.png)
   





## Spring Cache 缓存

> 缓存是一个提升程序性能的手段，当数据没有发生变化，且需要频繁进行数据库查询操作的时候可以使用缓存缓解频繁访问数据库的压力。

### 缓存操作流程

使用缓存最关键的一点就是要保证缓存与数据库的数据保持一致性：

- 更新数据：先将数据写入数据库，然后再让缓存失效或更新。缓存操作失败，数据库事务回滚。
- 删除数据：先从数据库中删除数据，再从缓存中删除。缓存操作失败，数据库事务回滚。

- 查询数据：
  - 缓存命中：先去缓存cache中取数据，取到后返回结果
  - 缓存失效：程序先从缓存中取到数据，若缓存中没有，则从数据库中取数据，成功后，再将数据存入缓存，下次就可以访问缓存获取。

### 整合Spring Cache

> 1. 添加依赖

```xml
<!--缓存-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

> 2. 环境准备
>
>    使用Spring JPA 实现一个简单 根据id查询文章

文章实体类：

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity  //注明实体类使用JPA
public class Article implements Serializable {
    @Id //注明主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;

    private String author;

    private String title;

    private String thumbnail;

    private String content;

    @Column(name = "create_time",updatable = false) //忽略这个字段，不更新
    private Date createTime;

    private Date updateTime;
}
```

ArticleRepository继承JpaRepository快速实现crud

```java
public interface ArticleRepository extends JpaRepository<Article,Integer> {
}
```

service接口

```java
public interface ArticleService {
    /**
     * 根据id查询文章
     * @param id 文章id
     * @return 文档对象
     */
    Article findById(int id);
}
```

> 3. 启动类添加 `@EnableCaching` 注解开启缓存

![image-20220417225359097](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204172253182.png)

> 4. 在service实现类的方法上使用`@Cacheable(value = CACHE_OBJECT,key = "#id")`来开启注解 `value`为存入 redis 缓存的key，一般使用常量维护。key 为实体的主键。

```java
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String CACHE_OBJECT = "article";

    @Resource
    ArticleRepository articleRepository;

    @Override
    @Cacheable(value = CACHE_OBJECT,key = "#id")
    public Article findById(int id) {
        System.out.println(id+" 执行一次");
        Optional<Article> op = articleRepository.findById(id);
        //优雅判空，若为空则返回括号中的值
        return op.orElse(null);
    }
}
```

> 5. 编写接口测试

```java
@RestController
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PostMapping("/articles")
    public AjaxResponse findById(@RequestParam int id){
        return AjaxResponse.success(articleService.findById(id));
    }

}
```

> 6. 测试，调用多次接口，数据没有发生变化的时候，只会执行一次查询，其他都走的缓存，可以从控制台输出看出。

![image-20220417230707986](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204172307099.png)

## 远程服务器上的Redis

### 使用Docker安装Redis

1. 拉取redis

   ```bash
   docker pull redis
   ```

2. 创建文件

   ```bash
   mkdir /home/docker/redis/{conf,data} -p
   ```

3. 本地下载对应版本的redis，拷贝redis.conf，并修改部分内容 

   ![redis.conf文件位置](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204240109751.png)

   > 更改redis.conf配置文件（更改前建议先备份原始文件，更改备份后的文件）：
   >
   > 1. 注释 `bind 127.0.0.1`。这里限制redis只能本地访问，注释掉之后使redis可以外部访问；
   > 2. `protected-mode yes` 修改为 protected-mode no。默认yes，开启保护模式，限制仅本地访问，改为no之后使redis可以外部访问；
   > 3. `daemonize no` 。默认no，当前界面将进入redis的命令行界面，exit强制退出或者关闭连接工具(putty,xshell等)都会导致redis进程退出。 改为yes意为以守护进程方式启动，该模式下，redis会在后台运行，并将进程pid号写入至redis.conf选项pidfile设置的文件中，此时redis将一直运行，除非手动kill该进程，如果改为yes会使以配置文件方式启动redis的方式失败。
   > 4. `requirepass pwd`。这里的pwd是自己设置的密码，可以不开启，不开启表示无需密码即可连接。
   > 5. `databases 16`。默认数据库个数16，可以不修改。
   > 6. `appendonly no`。默认 no，表示不开启aof方式持久化，改为appendonly yes表示开启aof，可以不修改该配置。
   >
   > **主要是前两个配置，用于实现可以远程访问，如果只需要本地访问，则无需修改。**

4. 拷贝修改后的文件到远程服务器的 `/home/docker/redis/conf`下

4. 启动容器：

   ```bash
   docker run -p 6379:6379 --name redis -v /home/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /home/docker/redis/data:/data -d redis redis-server /etc/redis/redis.conf
   ```
   
   > 1. `-p 6379:6379`：把容器内的6379端口映射到宿主机6379端口。
   > 2. `--name redis`：指定容器名称。
   > 3. `-v /home/docker/redis/conf/redis.conf:/etc/redis/redis.conf`：把宿主机配置好的redis.conf放到容器内的这个位置中。
   > 4. `-v /home/docker/redis/data:/data`：把redis持久化的数据映射在宿主机内。
   > 5. `-d`：以守护进程的方式启动容器。
   >    redis-server /etc/redis/redis.conf：让redis按照redis.conf的配置启动。
   
6. 使用`docker ps`检查是否启动成功：

   ![启动成功](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204240118120.png)

### 本地连接远程服务器的Redis

> Another Redis Desktop Manager 介绍

Another Redis Desktop Manager是一款将Redis操作可视化的软件。

下载：https://github.com/qishibo/AnotherRedisDesktopManager

下载完成后新建连接，在地址处输入服务器的ip地址和端口号，如有密码也进行输入，点击连接即可。

![新建远程链接](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204240122561.png)

![连接成功](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204240124057.png)
