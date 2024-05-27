package com.example.demo;

public class Car {

    private String make;
    private String model;

    public Car setMake (String make) {
        this.make = make;
        return this;
    }

    public Car setModel (String model) {
        this.model = model;
        return this;
    }

    public static void main (String[] args) {
        Car car = new Car().setMake("tesla").setModel("model s");
        // method chaining
        System.out.println(car.make);
        System.out.println(car.model);

    }
}
