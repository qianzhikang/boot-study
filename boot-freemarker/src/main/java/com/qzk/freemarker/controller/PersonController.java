package com.qzk.freemarker.controller;

import com.qzk.freemarker.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-04-03-22-58
 * @Author Courage
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    @GetMapping("/freemarker")
    public String goFreemarker(Model model) {
        Person person1 = new Person("qzk一号", 22, "江苏苏州");
        Person person2 = new Person("qzk二号", 21, "江苏南京");
        Person person3 = new Person("qzk三号", 20, null);
        List<Person> personList = Arrays.asList(person1, person2, person3);
        //添加人员列表
        model.addAttribute("personList", personList);
        //添加标题
        model.addAttribute("title", "qzk的freemarker测试");
        //添加判断测试
        model.addAttribute("flag",0);
        return "freemarker-demo";
    }
}
