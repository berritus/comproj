package com.berritus.mis.design.pattern.factory;

public class ProductOneFactory extends AbstractFactory {
    @Override
    public Food createFood() {
        return new Apple();
    }

    @Override
    public Color createColor() {
        return new Yellow();
    }

    @Override
    public Provider createProvider() {
        return new SmsFactory();
    }
}
