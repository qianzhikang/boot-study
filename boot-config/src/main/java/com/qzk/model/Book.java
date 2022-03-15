package com.qzk.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-03-14-14-17
 * @Author Courage
 */
@Data
public class Book {
    private String bookName;
    @Min(value = 100)
    private Integer price;
    @Past
    private Date date;
}
