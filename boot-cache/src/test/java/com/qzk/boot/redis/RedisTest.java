package com.qzk.boot.redis;

import com.qzk.boot.entity.Address;
import com.qzk.boot.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2022-04-17-17-33
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RedisTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, Person> redisTemplate;

    @Test
    void testString(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("name","张三",20, TimeUnit.SECONDS);
        String name = stringStringValueOperations.get("name");
        System.out.println(name);
    }


    @Test
    void testRedis(){
        ValueOperations<String, Person> stringPersonValueOperations = redisTemplate.opsForValue();
        Person person = Person.builder()
                .name("qzk")
                .address(new Address("China", "nanjing"))
                .build();
        stringPersonValueOperations.set("person",person,20,TimeUnit.SECONDS);
    }

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

}
