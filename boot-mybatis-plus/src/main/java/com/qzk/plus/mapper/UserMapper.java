package com.qzk.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qzk.plus.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Courage
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2022-04-02 21:07:36
 * @Entity com.qzk.plus.entity.User
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> findUser(@Param("name") String name, @Param("email") String email);
}




