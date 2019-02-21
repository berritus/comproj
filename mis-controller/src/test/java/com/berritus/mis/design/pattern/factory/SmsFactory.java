package com.berritus.mis.design.pattern.factory;

public class SmsFactory implements Provider {
    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
