package com.berritus.mis.design.pattern.factory;

public class SmsSender implements Sender {
    @Override
    public void send() {
        System.out.println("sms sender");
    }
}
