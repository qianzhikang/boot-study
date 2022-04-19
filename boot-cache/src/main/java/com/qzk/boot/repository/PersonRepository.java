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
