package com.berritus.mis.design.pattern.bridge;

public class SourceTwo implements Sourceable {
    @Override
    public void method() {
        System.out.println("SourceTwo method");
    }
}
