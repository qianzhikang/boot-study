package com.qzk.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description TODO
 * @Date 2022-03-19-10-44
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_book")
public class Book {

    @Id
    private Integer id;
    private String name;
    private String cover;
}
