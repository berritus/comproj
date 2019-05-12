package com.berritus.mis.design.pattern.factory;

public class Black implements Color {
    @Override
    public void createColor() {
        System.out.println("this is Black");
    }
}
