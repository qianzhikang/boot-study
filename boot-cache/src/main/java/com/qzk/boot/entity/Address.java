package com.qzk.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2022-04-17-17-46
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable {

    private String country;
    private String city;
}
