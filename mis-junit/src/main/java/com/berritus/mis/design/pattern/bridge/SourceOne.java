package com.berritus.mis.design.pattern.bridge;

public class SourceOne implements Sourceable {
    @Override
    public void method() {
        System.out.println("SourceOne method");
    }
}
