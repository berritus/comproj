package com.berritus.mis.design.pattern.adapter;

public class AdapterOne extends Source implements Targetable {
    @Override
    public void method2() {
        System.out.println("method2");
    }
}
