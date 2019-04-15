package com.berritus.mis.design.pattern.factory;

public abstract class AbstractFactory {
    public abstract Food createFood();
    public abstract Color createColor();
    public abstract  Provider createProvider();
}
