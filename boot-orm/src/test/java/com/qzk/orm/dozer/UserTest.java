package com.qzk.orm.dozer;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.spring.DozerBeanMapperBuilderCustomizer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-28-11-01
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserTest {

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
}