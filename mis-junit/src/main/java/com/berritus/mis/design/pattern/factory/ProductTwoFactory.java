package com.berritus.mis.design.pattern.factory;

public class ProductTwoFactory extends AbstractFactory {
    @Override
    public Food createFood() {
        return new Orange();
    }

    @Override
    public Color createColor() {
        return new Black();
    }

    @Override
    public Provider createProvider() {
        return new MailFactory();
    }
}
