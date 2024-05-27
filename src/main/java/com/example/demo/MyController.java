/*package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyController {

    private MyService myService;

    @Autowired
    public void setMyService (MyService myService) {
        this.myService = myService;
    }

    public MyController (MyService myService) {
        this.myService = myService;
    }

    @Autowired
    private MyService myService2;

    public void doSomething0 () {
        System.out.println(myService2.serve());
    }

    public void doSomething () {
        System.out.println(myService.serve());
    }

}*/
