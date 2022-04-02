package com.qzk.plus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qzk.plus.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-02-12-46
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserMapperTest {

    @Resource
    UserMapper userMapper;


    @Test
    void select() {
        //按id查询
        System.out.println(userMapper.selectById(4));
    }


    @Test
    void selectBatch() {
        //批量查询
        List<Long> ids = Arrays.asList(4L,5L);
        System.out.println(userMapper.selectBatchIds(ids));
    }

    @Test
    void selectByMap() {
        Map<String, Object> map = new HashMap<>();
        //根据指定参数查询
        //map的key指代的是mysql表中的列名，并⾮java实体的属性名
        map.put("age",18);
        System.out.println(userMapper.selectByMap(map));
    }


    @Test
    void selectQuery(){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.select("name", "age") //指定查询结果字段:仅获取名字和年龄
                .in("age", Arrays.asList(21, 24))
                .last("limit 2");
        List<User> list = userMapper.selectList(query);
        list.forEach(System.out::println);


        QueryWrapper<User> query2 = new QueryWrapper<>();
        query2.like("name", "S%") //like是MP的条件构造器，表示"模糊查询"
                .lt("age", 30) //lt是MP的条件构造器，表示"⼩于"关系
                .select("name", "age");
        List<Map<String, Object>> maps =
                userMapper.selectMaps(query2);
        maps.forEach(System.out::println);
    }



    @Test
    void insert() {
        User user = User.builder().name("qzk").age(22).email("qzk@gmail.com").build();
        int row = userMapper.insert(user);
        System.out.println("受影响的行数: " + row);
        //雪花算法生成的id
        System.out.println(user.getId());
    }



    @Test
    void delete() {
        int i = userMapper.deleteById(1510118199263047681L);
        System.out.println("受影响的行数: " + i);
    }

    @Test
    void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        //map.put("name","jack");
        map.put("age",18);
        //根据条件删除，多条满足时，会删除多条
        int i = userMapper.deleteByMap(map);
        System.out.println("受影响的行数: " + i);
    }

    @Test
    void update() {
        User user = new User();
        user.setId(2L);
        user.setAge(18);
        user.setEmail("mybatis-plus@163.com");
        //更新传入对象字段的null值不会覆盖原来数据库中的字段，如 数据库中name为qzk，传入对象的name为null，则不会对qzk修改为null
        int rows = userMapper.updateById(user);
        System.out.println("影响记录数：" + rows);
    }


    @Test
    void updateWrapper() {
        UpdateWrapper<User> update = new UpdateWrapper<>();
        update.eq("name","Jack").eq("age",18);
        User user = new User();
        user.setAge(100);
        user.setEmail("mybatis-plus@163.com");
        int row = userMapper.update(user, update);
        System.out.println("受影响的行数："+row);
    }


}