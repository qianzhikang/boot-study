package com.qzk.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Date 2022-04-11-12-18
 * @Author Courage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Min(2)
    private Integer id;
    @NotNull(message = "标题不能为空")
    private String title;

}
