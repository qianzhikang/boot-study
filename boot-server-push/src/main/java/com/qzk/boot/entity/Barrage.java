package com.qzk.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-04-19-00-08
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Barrage {
    private String message;
    private String height;
    private String color;
}
