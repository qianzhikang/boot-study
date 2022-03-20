package com.qzk.orm.service;

import com.qzk.orm.entity.Book;
import org.springframework.data.domain.Page;

/**
 * @Description TODO
 * @Date 2022-03-19-10-48
 * @Author Courage
 */
public interface BookService {


    /**
     * 不带条件的分页
     * @param page
     * @param size
     * @return
     */
    Page<Book> findBookNoCriteria(Integer page,Integer size);


    /**
     * 带条件的查询
     * @param page
     * @param size
     * @param book
     * @return
     */
    Page<Book> findBookCriteria(Integer page,Integer size,Book book);
}
