package com.berritus.mis.design.pattern.observer;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 对象抽象类
 */
public abstract class AbstractSubject implements Subject{
    private Vector<Observer> vertor = new Vector<>();

    @Override
    public void addObserver(Observer observer){
        vertor.add(observer);
    }

    @Override
    public void delObserver(Observer observer){
        vertor.remove(observer);
    }

    @Override
    public void notifyObservers(){
        Enumeration<Observer> enumeration = vertor.elements();
        while(enumeration.hasMoreElements()){
            enumeration.nextElement().update();
        }
    }
}
