package com.berritus.mis.design.pattern.observer;

/**
 * 观察者1
 */
public class ObserverOne implements Observer {
    @Override
    public void update() {
        System.out.println("ObserverOne");
    }
}
