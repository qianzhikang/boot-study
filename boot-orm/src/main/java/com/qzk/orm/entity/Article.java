package com.qzk.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 文章实体类
 * @Date 2022-03-18-08-43
 * @Author Courage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String author;

    private String title;

    private String thumbnail;

    private String content;

    @Column(name = "create_time",updatable = false)
    private Date createTime;

    private Date updateTime;
}
