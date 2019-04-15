package com.berritus.mis.design.pattern.decorator;

/**
 * 具体的装饰类
 */
public class DecoratorOne extends DecoratorPerson {
    @Override
    public void pay() {
        System.out.println("DecoratorOne pay");
        super.pay();
    }
}
