package com.berritus.mis.design.pattern.observer;

/**
 * 对象接口
 */
public interface Subject {
    /*增加观察者*/
    void addObserver(Observer observer);
    /*删除观察者*/
    void delObserver(Observer observer);
    /*通知所有的观察者*/
    void notifyObservers();
    /*自身的操作*/
    void operation();
}
