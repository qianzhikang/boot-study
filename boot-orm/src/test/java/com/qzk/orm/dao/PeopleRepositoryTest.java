package com.qzk.orm.dao;

import com.qzk.orm.entity.People;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-19-10-05
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PeopleRepositoryTest {


    @Resource
    PeopleRepository peopleRepository;

    @Test
    void findAll() {
        List<People> peopleList = peopleRepository.findAll();
        assertEquals(3, peopleList.size());
    }

    @Test
    void findByFirstName() {
        List<People> peopleList = peopleRepository.findByFirstName("WANG");
        assertEquals(1, peopleList.size());
    }

    @Test
    void findByFirstNameAndLastName() {
        People people = peopleRepository.findByFirstNameAndLastName("qian", "zhikang");
        assertEquals(people.getLastName(), "zhikang");
    }

    @Test
    void findByFirstNameOrLastName() {
        People people = peopleRepository.findByFirstNameOrLastName("qian", "zhi");
        assertEquals(people.getLastName(), "zhikang");
    }

    @Test
    void findByFirstNameIs() {
        People people = peopleRepository.findByFirstNameIs("qia");
        assertNull(people);
    }

    @Test
    void findByFirstNameEquals() {
        People people = peopleRepository.findByFirstNameEquals("qia");
        assertNull(people);
    }


    @Test
    public void findByAgeBetween() {
        List<People> peopleList = peopleRepository.findByAgeBetween(20, 22);
        assertEquals(3, peopleList.size());
    }


    @Test
    public void findByAgeLessThan() {
        List<People> peopleList = peopleRepository.findByAgeLessThan(21);
        assertEquals(1, peopleList.size());
    }

    @Test
    public void findByAgeLessThanEqual() {
        List<People> peopleList = peopleRepository.findByAgeLessThanEqual(21);
        assertEquals(2, peopleList.size());
    }

    @Test
    public void findByAgeGreaterThan() {
        List<People> peopleList = peopleRepository.findByAgeGreaterThan(20);
        assertEquals(2, peopleList.size());
    }

    @Test
    public void findByAgeGreaterThanEqual() {
        List<People> peopleList = peopleRepository.findByAgeGreaterThanEqual(20);
        assertEquals(3, peopleList.size());
    }

    @Test
    public void findByAgeAfter() {
        List<People> peopleList = peopleRepository.findByAgeAfter(20);
        assertEquals(2, peopleList.size());
    }

    @Test
    public void findByAgeBefore() {
        List<People> peopleList = peopleRepository.findByAgeBefore(21);
        assertEquals(1, peopleList.size());
    }

    @Test
    public void findByAgeIsNull() {
        List<People> peopleList = peopleRepository.findByAgeIsNull();
        assertEquals(0, peopleList.size());
    }

    @Test
    public void findByAgeNotNull() {
        List<People> peopleList = peopleRepository.findByAgeNotNull();
        assertEquals(3, peopleList.size());
    }
}