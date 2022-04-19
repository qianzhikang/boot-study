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
