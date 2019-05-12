package com.berritus.mis.design.pattern.factory;

public class Yellow implements Color {
    @Override
    public void createColor() {
        System.out.println("this is Yellow");
    }
}
