package com.qzk.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2022-04-17-17-26
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("person")
public class Person implements Serializable {
    @Id
    private Integer id;
    private String name;
    private Address address;
}
