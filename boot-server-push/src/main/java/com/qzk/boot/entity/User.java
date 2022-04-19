package com.qzk.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-04-19-08-58
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String avatar;
    private String name;
}
