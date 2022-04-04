package com.qzk.thymeleaf.controller;

import com.qzk.thymeleaf.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-04-04-01-11
 * @Author Courage
 */
@Controller
public class PersonController {

    @GetMapping("/go")
    public String goThymeleaf(Model model){
        Person person1 = new Person("qzk一号", 22, "江苏苏州");
        Person person2 = new Person("qzk二号", 21, "江苏南京");
        Person person3 = new Person("qzk三号", 20, null);
        List<Person> personList = Arrays.asList(person1, person2, person3);
        //添加人员列表
        model.addAttribute("personList", personList);
        return "thymeleaf-demo";
    }
}
