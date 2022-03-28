package com.qzk.orm.dozer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Date 2022-03-28-10-57
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String user;
    private String password;
    private String avatar;
}
