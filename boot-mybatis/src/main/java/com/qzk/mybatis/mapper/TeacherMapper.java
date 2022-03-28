package com.qzk.mybatis.mapper;

import com.qzk.mybatis.entity.Teacher;

/**
* @author Courage
* @description 针对表【t_teacher】的数据库操作Mapper
* @createDate 2022-03-28 12:53:14
* @Entity com.qzk.mybatis.entity.Teacher
*/
public interface TeacherMapper {

    /**
     * 根据教师id查询教师信息
     * @param id 教师id
     * @return 教师对象信息
     */
    Teacher findOneByOne(Integer id);

}




