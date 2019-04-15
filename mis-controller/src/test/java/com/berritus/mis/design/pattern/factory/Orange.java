package com.berritus.mis.design.pattern.factory;

public class Orange implements Food {
    @Override
    public void createFood() {
        System.out.println("this is Orange");
    }
}
