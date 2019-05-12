package com.berritus.mis.design.pattern.factory;

public class Apple implements Food {
    @Override
    public void createFood() {
        System.out.println("this is Apple");
    }
}
