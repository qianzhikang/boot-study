package com.qzk.mybatis.mapper;

import com.qzk.mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Courage
* @description 针对表【t_student】的数据库操作Mapper
* @createDate 2022-03-28 12:53:14
* @Entity com.qzk.mybatis.entity.Student
*/
public interface StudentMapper {

    /**
     * 根据学生id查询学生
     * @param studentId 学生id
     * @return 学生信息(关联查询出其所在班级信息)
     */
    Student findManyByOne(int studentId);

    /**
     * 根据学生id查询学生
     * @param studentId 学生id
     * @return 学生信息（关联查询出所在班级信息，其所选择的课程信息）
     */
    Student getStudent(int studentId);


    /**
     * 批量新增学生
     * @param students 学生集合
     * @return 插入条数
     */
    int batchInsert(@Param("students") List<Student> students);


    /**
     * 批量删除
     * @param ids id集合
     * @return 删除条数
     */
    int batchDelete(@Param("ids") List<Integer> ids);


    /**
     * 批量修改
     * @param students  学生集合
     * @return 修改条数
     */
    int batchUpdate(@Param("students") List<Student> students);


    /**
     * 动态查询
     * @param student 查询对象
     * @return  查询到的学生对象
     */
    List<Student> findStudentBy(@Param("student") Student student);





}




