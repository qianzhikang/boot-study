package com.qzk.orm.dao;

import com.qzk.orm.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-19-09-56
 * @Author Courage
 */
public interface PeopleRepository extends JpaRepository<People, Integer> {

    /**
     * 查询所有
     * @return
     */
    @Override
    List<People> findAll();

    /**
     * 姓氏查询
     * @param firstName
     * @return
     */
    List<People> findByFirstName(String firstName);

    /**
     * 姓氏+名字查询
     * @param firstName
     * @param lastName
     * @return
     */
    People findByFirstNameAndLastName(String firstName,String lastName);

    /**
     * 姓氏或者名字查询
     * @param firstName
     * @param lastName
     * @return
     */
    People findByFirstNameOrLastName(String firstName,String lastName);


    /**
     * 查询FirstName字段的值是否存在数据库
     * @param firstName
     * @return
     */
    People findByFirstNameIs(String firstName);

    /**
     * 和is的用法相同
     * @param firstName
     * @return
     */
    People findByFirstNameEquals(String firstName);


    /**
     * 查询年龄在age1和age2之间
     * @param age1
     * @param age2
     * @return
     */
    List<People>findByAgeBetween(Integer age1, Integer age2);


    /**
     * 查询年龄小于age
     * @param age
     * @return
     */
    List<People>findByAgeLessThan(Integer age);

    /**
     * 查找年龄小于等于age
     * @param age
     * @return
     */
    List<People>findByAgeLessThanEqual(Integer age);


    /**
     * 查找年龄大于age
     * @param age
     * @return
     */
    List<People>findByAgeGreaterThan(Integer age);


    /**
     * 查找年龄大于等于age
     * @param age
     * @return
     */
    List<People>findByAgeGreaterThanEqual(Integer age);




    /**
     * 在指定age数值之前的数据类似关键字<LessThan>
     * @param age
     * @return
     */
    List<People>findByAgeAfter(Integer age);



    /**
     * 在指定age数值之后的数据类似关键字<GreaterThan>
     * @param age
     * @return
     */
    List<People>findByAgeBefore(Integer age);

    /**
     * 返回指定字段为空的数据行
     * @return
     */
    List<People>findByAgeIsNull();

    /**
     * 返回指定字段不为空的数据行
     * @return
     */
    List<People>findByAgeNotNull();

}
