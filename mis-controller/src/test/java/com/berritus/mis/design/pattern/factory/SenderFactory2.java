package com.berritus.mis.design.pattern.factory;

public class SenderFactory2 {
    public static Sender produceMail(){
        return new MailSender();
    }

    public static Sender produceSms(){
        return new SmsSender();
    }
}
