package com.qzk.orm.dao;

import com.qzk.orm.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description TODO
 * @Date 2022-03-19-10-46
 * @Author Courage
 */
public interface BookRepository extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {

}
