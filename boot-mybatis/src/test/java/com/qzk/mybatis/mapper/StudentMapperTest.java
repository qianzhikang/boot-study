package com.qzk.mybatis.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qzk.mybatis.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-28-19-05
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class StudentMapperTest {

    @Resource
    private StudentMapper studentMapper;


    @Test
    void findManyByOne() {
        Student student = studentMapper.findManyByOne(1001);
        System.out.println(student.getClazz());
    }

    @Test
    void getStudent() {
        Student student = studentMapper.getStudent(1001);
        System.out.println(student);
    }

    @Test
    void batchInsert() {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(Student.builder()
                    .clazzId(1)
                    .studentName("测试学生" + i)
                    .hometown("江苏苏州")
                    .birthday(new Date())
                    .build());
        }
        int count = studentMapper.batchInsert(students);
        System.out.println(count);

    }

    @Test
    void batchDelete() {

        List<Integer> ids = Arrays.asList(3010, 3011);
        int i = studentMapper.batchDelete(ids);
        System.out.println(i + "行数据被删除");
    }


    @Test
    void batchUpdate() {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            students.add(Student.builder().studentId(3010 + i).studentName("新名字" + i).build());
        }
        int i = studentMapper.batchUpdate(students);
        System.out.println(i + "行记录受影响");
    }


    @Test
    void findBy() {
        List<Student> students = studentMapper.findStudentBy(Student.builder()
                .hometown("州")
                .build());
        students.forEach(student -> System.out.println(student));
    }



    @Test
    void testPage(){
        PageHelper.startPage(1,2);
        List<Student> students = studentMapper.findStudentBy(new Student());
        PageInfo<Student> page = PageInfo.of(students);
        System.out.println(page.getPageNum());
    }


}