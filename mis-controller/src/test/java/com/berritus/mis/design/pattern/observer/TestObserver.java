package com.berritus.mis.design.pattern.observer;

import org.junit.Test;

public class TestObserver {
    @Test
    public void test1(){
        Subject subject = new SubjectOne();
        subject.addObserver(new ObserverOne());
        subject.addObserver(new ObserverTwo());

        subject.operation();
    }
}
