package com.example.demo.web;

import com.example.demo.MyBean;
import com.example.demo.MyComponent;
import com.example.demo.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping
//@Component
public class Controller {

    private MyService myService;
    private MyComponent myComponent;
    private MyBean myBean;

    @Autowired
    public Controller (MyService myService, MyComponent myComponent, MyBean myBean) {
        this.myService = myService;
        this.myComponent = myComponent;
        this.myBean = myBean;
    }

    /*public Controller (MyComponent myComponent) {

    }

    public Controller (MyBean myBean) {
        this.myBean = myBean;
    }*/



    @GetMapping("/")
    public String welcome () {

        System.out.println(myService.serve());

        System.out.println(myComponent.greet());

        System.out.println(myBean.greet());

        return "Welcome to my site!";


    }

    @GetMapping("/serving")
    public String mapping () {

        List<String> list = new ArrayList<>();

        list.add(myService.serve());
        list.add(myBean.greet());
        list.add(myComponent.greet());

        return list.toString();

    }

    @GetMapping("/admin")
    public String adminPage () {

        return "Admin sida";
    }



}
