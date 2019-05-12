package com.berritus.mis.design.pattern.observer;

/**
 * 对象具体实现类
 */
public class SubjectOne extends AbstractSubject {
    @Override
    public void operation() {
        notifyObservers();
    }
}
