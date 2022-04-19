# 整合数据库

## 1. JavaBean的赋值转换

在复杂业务中，通常不能做到一个model实体类贯穿持久、服务、控制，通常需要实体对象进行JavaBean的赋值转换。

### 1）persistent object（po）

持久对象，对应数据库中的entity。通常在进行数据库数据存取操作时使用。可以简单的认为一个PO对应数据库中一张表中的一个记录。PO对象里面只有基本数据类型和String类型的属性（如：int、String），与数据库字段是一一对应的。

### 2）view object（vo）

主要与web⻚面的展示结构相对应，所以VO也是前端与后端的数据交换定义。下图中是一个VO，用于返回给前端Web界面，用于渲染的数据内容

### 3）business object（bo）

业务对象，业务对象主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象。通常一个BO是多个PO的组合体，比如：PO在查询出来之后，需要经过业务处理，处理过程中对象的属性逐渐变复杂，有嵌套的数组，对象数组等等。

## 2. BeanUtils和Dozer

比较常用的JavaBean赋值转换工具是BeanUtils和Dozer，如果没有BeanUtils和Dozer帮我们进行对象之间的转换赋值。

### po、vo、dto准备

```java
//po
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String user;
    private String password;
    private String avatar;
}


//vo
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {
    private String user;
    private String touxiang;
}



//dto
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto{
    private String user;
    private String password;
}
```

### BeanUtils实现赋值转换

```java
@Test
public void test1(){
    User user = new User();
    UserDto userDto = UserDto.builder()
            .user("qzk")
            .password("123")
            .build();
    BeanUtils.copyProperties(userDto,user);
    System.out.println(user);
}
```

![image-20220402214008091](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204022140136.png)

### Dozer

1）引入依赖

```xml
<dependency>
    <groupId>com.github.dozermapper</groupId>
    <artifactId>dozer-spring-boot-starter</artifactId>
    <version>6.5.2</version>
</dependency>
```

2）使用

```java
@Test
public void test2(){
    UserDto userDto = UserDto.builder()
            .user("qzk")
            .password("123")
            .build();
    Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    User user = mapper.map(userDto, User.class);
    System.out.println(user);
}
```

![image-20220402214216174](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204022142224.png)

3）不同属性字段匹配赋值：两个头像字段的赋值

![image-20220402214414543](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204022144583.png)

![image-20220402214432998](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204022144036.png)

配置xml文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mappings xmlns="http://dozermapper.github.io/schema/bean-mapping"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://dozermapper.github.io/schema/bean-mapping http://dozermapper.github.io/schema/bean-mapping.xsd">

    <configuration>

    </configuration>

    <mapping>
        <class-a>com.qzk.orm.dozer.User</class-a>
        <class-b>com.qzk.orm.dozer.UserVo</class-b>
        <field>
            <a>avatar</a>
            <b>touxiang</b>
        </field>
    </mapping>
</mappings>
```

使用：

```java
 @Test
 public void test3(){
     UserVo userVo = UserVo.builder()
             .user("qzk")
             .touxiang("123.jpg")
             .build();
     Mapper mapper = DozerBeanMapperBuilder.create()
             .withMappingFiles("dozer/UserMapper.xml")
             .build();
     User user = mapper.map(userVo, User.class);
     System.out.println(user);
 }
```

效果：

![image-20220402214521281](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204022145327.png)





## 3.Mybatis

### select(查询)

> select标签是mybatis中最常用的标签

> 1、在`UserMapper`中添加对应方法

```java
public interface UserMapper {
    //根据id查询用户
    User selectUserById(int id);
}
```

> 在`UserMapper.xml`中添加Select语句

```xml
<select id="selectUserById" resultType="com.wd.entity.User" parameterType="int">
    SELECT id,username,password FROM user WHERE id = #{id}
</select>
```

> 字段

- `id`：制定对应的方法，就是你的告诉我你这sql对应的是哪个方法
- `resultType`：制定返回类型，查询是有结果的，结果啥类型，你得告诉我
- `parameterType`：参数类型，查询是有参数的，参数啥类型，你得告诉我
- `#{id}`：sql中的变量，要保证大括号的变量必须在User对象里有，
- 当然你可以使用map，这货啥也能干，这要你将来传进的map有id这个key就行
- `#{}`：占位符，其实就是咱们的【PreparedStatement】处理这个变量

> 除了#{}还有${}，有啥区别?

`#{}`的作用主要是替换预编译语句(PrepareStatement)中的占位符? 【推荐使用】

```sql
INSERT INTO user (username) VALUES (#{username});
INSERT INTO user (username) VALUES (?);
```

`${}` 的作用是直接进行字符串替换

```sql
INSERT INTO user (username) VALUES ('${username}');
INSERT INTO user (username) VALUES ('qzk');
```







### insert(插入)

> insert标签被用作插入操作

> 1、接口中添加方法
>
> *多个参数时，使用@Param注解，能制定对应的参数，当然如果能封装对象最好封装对象*

```java
/**
* 新增用户
* @param userId
* @param name
* @param pws
* @return
*/
int addUser(@Param("id") int id,@Param("username") String
name,@Param("password") String pws);
```

> 2、xml中加入insert语句

```xml
<insert id="addUser" parameterType="com.wd.entity.User">
    insert into user (id,username,password) values (#{id},#{username},#{password})
</insert>
```

> 测试

```java
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {
    @Resource
    private UserMapper userMapper;
    @Test
    void testAddUser() {
        User user = new User(5,"王二狗","12345678");
        int affectedRows = mapper.addUser(user);
        System.out.println(affectedRows);
    }
}
```

### update(修改)

> update标签用于更新操作

1.Mapper接口添加方法

```java
/**
* 修改用户
* @param user
* @return
*/
int updateUser(User user);
```

2、xml文件

```xml
<update id="updateUser" parameterType="com.xinzhi.entity.User">
    update user set username=#{username},password=#{password} where id = #{id}
</update>
```

3、测试

```java
@Test
public void testUpdateUser() {
    UserMapper mapper = session.getMapper(UserMapper.class);
    User user = new User(5,"王东","12345678");
    int affectedRows = mapper.updateUser(user);
    System.out.println(affectedRows);
}
```





### delete（删除）

> delete标签用于做删除操作

1、Mapper添加方法

```java
/**
* 删除一个用户
* @param id
* @return
*/
int deleteUser(int id);
```

2、xml文件配置

```xml
<delete id="deleteUser" parameterType="int">
    delete from user where id = #{id}
</delete>
```

3、测试

```java
@Test
public void testDeleteUser(){
    UserMapper mapper = session.getMapper(UserMapper.class);
    int affectedRows = mapper.deleteUser(5);
    System.out.println(affectedRows);
}
```

### 模糊查询

> 方案一：在Java代码中拼串

```java
string name = "%Jo%";
list<name> names = mapper.getUserByName(name);
```

```xml
<select id="getUsersByName">
    select * from user where name like #{name}
</select>
```

> 方案二：在配置文件中拼接

```java
string name = "Jo";
list<User> users = mapper.getUserByName(name);
```

```xml
<select id="getUsersByName">
    select * from user where name like "%"#{name}"%"
</select>
```



### map的使用

> map可以代替任何的实体类，所以当我们数据比较复杂时，可以适当考虑使用map来完成相关工
> 作

1、写xml配置

```xml
<select id="getUsersByParams" parameterType="java.util.HashMapmap">
    select id,username,password from user where username = #{name}
</select>
```

2、Mapper添加方法

```java
/**
* 根据一些参数查询
* @param map
* @return
*/
List<User> getUsersByParams(Map<String,String> map);
```

3、测试

```java
@Test
public void findByParams() {
    UserMapper mapper = session.getMapper(UserMapper.class);
    Map<String,String> map = new HashMap<String, String>();
    map.put("name","磊");
    List<User> users = mapper.getUsersByParams(map);
    for (User user: users){
        System.out.println(user.getUsername());
    }
}
```



### 自定义返回映射resultMap

数据库不可能永远是你所想或所需的那个样子

> 属性名和字段名不一致,我们一般都会按照约定去设计数据的,但确实阻止不了一些孩子,瞎比起名字。

1、Java中的实体类设计

```java
@Data
public class User {
    private int id; //id
    private String name; //姓名，数据库为username
    private String password; //密码，一致
}
```

mybatis会根据这些查询的列名(会将列名转化为小写,数据库不区分大小写) , 利用反射去对应的实体类中查找相应列名的set方法设值，当然找不到username

> 方案一：为列名指定别名 , 别名和java实体类的属性名一致 .

```xml
<select id="selectUserById" resultType="User">
    select id , username as name ,password from user where id = #{id}
</select>
```

> 方案二：使用结果集映射->ResultMap 【推荐】

```xml
<resultMap id="UserMap" type="User">
    <!-- id为主键 -->
    <id column="id" property="id"/>
    <!-- column是数据库表的列名 , property是对应实体类的属性名 -->
    <result column="username" property="name"/>
    <result column="password" property="password"/>
</resultMap>
<select id="selectUserById" resultMap="UserMap">
    select id , username , password from user where id = #{id}
</select>
```

> 当然约定的最基本的操作就是全都都一样，还有就是下划线和驼峰命名的自动转化

```xml
<settings>
    <!--开启驼峰命名规则--> 
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```

## 4.动态sql

MyBatis提供了对SQL语句动态的组装能力，大量的判断都可以在 MyBatis的映射XML文件里面配置，以达到许多我们需要大量代码才能实现的功能，大大减少了我们编写代码的工作量。

> 动态SQL的元素

| 元素                    | 作用                         | 备注                    |
| ----------------------- | ---------------------------- | ----------------------- |
| if                      | 判断语句                     | 单条件分支判断          |
| choose、when、otherwise | 相当于Java中的 case when语句 | 多条件分支判断          |
| trim、where、set        | 辅助元素                     | 用于处理一些SQL拼装问题 |
| foreach                 | 循环语句                     | 在in语句等列举条件常用  |

### 1、if 拼接条件

if元素相当于Java中的if语句，它常常与test属性联合使用。现在我们要根据name去查找学生，但是name是可选的，如下所示：

```xml
<select id="findUserById" resultType="com.wd.entity.User">
    select id,username,password from user
    where 1 = 1
    <if test="id != null and id != ''">
        AND id = #{id}
    </if>
    <if test="username != null and username != ''">
        AND username = #{username}
    </if>
    <if test="password != null and password != ''">
        AND password = #{password}
    </if> 
</select>
```

### 2、choose、when、otherwise分支判断

有些时候我们还需要多种条件的选择，在Java中我们可以使用switch、case、default语句，而在映射器的动态语句中可以使用choose、when、otherwise元素。

```xml
<!-- 有name的时候使用name搜索，没有的时候使用id搜索 -->
<select id="select" resultType="com.wd.entity.User">
    SELECT * FROM user
    WHERE 1=1
    <choose>
        <when test="name != null and name != ''">
            AND username LIKE concat('%', #{username}, '%')
        </when>
        <when test="id != null">
            AND id = #{id}
        </when> 
    </choose> 
</select>
```

### 3、where元素防止where中因if发生空

上面的select语句我们加了一个 1=1 的绝对true的语句，目的是为了防止语句错误，变成 `SELECT *FROM student WHERE` 这样where后没有内容的错误语句。这样会有点奇怪，此时可以使用 `<where>` 元素。

```xml
<select id="findUserById" resultType="com.xinzhi.entity.User">
    select id,username,password from user
    <where>
        <if test="id != null and id != ''">
            AND id = #{id}
        </if>
        <if test="username != null and username != ''">
            AND username = #{username}
        </if>
        <if test="password != null and password != ''">
            AND password = #{password}
        </if> 
    </where>
</select>
```

### 4、trim元素去除特殊字符串

有时候我们要去掉一些特殊的SQL语法，比如常见的and、or，此时可以使用trim元素。trim元素意味着我们需要去掉一些特殊的字符串，prefix代表的是语句的前缀，而prefixOverrides代表的是你需要去掉的那种字符串，suffix表示语句的后缀，suffixOverrides代表去掉的后缀字符串。


```xml
<select id="select" resultType="com.xinzhi.entity.User">
    SELECT * FROM user
    <trim prefix="WHERE" prefixOverrides="AND">
        <if test="username != null and username != ''">
            AND username LIKE concat('%', #{username}, '%')
        </if>
        <if test="id != null">
            AND id = #{id}
        </if> 
    </trim>
</select>
```

### 5、set配合if去逗号

在update语句中，如果我们只想更新某几个字段的值，这个时候可以使用set元素配合if元素来完成。注意：set元素遇到,会自动把,去掉。

```xml
<update id="update">
    UPDATE user
    <set>
    <if test="username != null and username != ''">
    username = #{username},
    </if>
    <if test="password != null and password != ''">
    password = #{password}
    </if>
    </set>
    WHERE id = #{id}
</update>
```

### 6.foreach循环

foreach元素是一个循环语句，它的作用是遍历集合，可以支持数组、List、Set接口。

```xml
<select id="select" resultType="com.xinzhi.entity.User">
    SELECT * FROM user
    WHERE id IN
    <foreach collection="ids" open="(" close=")" separator="," item="id">
    #{id}
    </foreach>
</select>
```

- `collection`配置的是传递进来的参数名称
- `item`配置的是循环中当前的元素。
- `index`配置的是当前元素在集合的位置下标。
- `open和 close`配置的是以什么符号将这些集合元素包装起来。
- `separator`是各个元素的间隔符。

### 7、SQL片段复用

有时候可能某个 sql 语句我们用的特别多，为了增加代码的重用性，简化代码，我们需要将这些代码抽取出来，然后使用时直接调用。

> 提取sql片段

```xml
<sql id="if-title-author">
    <if test="title != null">
    title = #{title}
    </if>
    <if test="author != null">
    and author = #{author}
    </if>
</sql>
```

> 引用sql片段：

```xml
<select id="queryBlogIf" parameterType="map" resultType="blog">
    select * from blog
    <where>
    <!-- 引用 sql 片段，如果refid 指定的不在本文件中，那么需要在前面加上 namespace
    -->
    <include refid="if-title-author"></include>
    <!-- 在这里还可以引用其他的 sql 片段 -->
    </where> 
</select>
```
