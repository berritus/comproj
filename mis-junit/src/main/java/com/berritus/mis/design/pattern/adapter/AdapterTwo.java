package com.berritus.mis.design.pattern.adapter;

public class AdapterTwo implements Targetable {
    private Source source;

    public AdapterTwo(Source source){
        super();
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
    }

    @Override
    public void method2() {
        System.out.println("method2");
    }
}
