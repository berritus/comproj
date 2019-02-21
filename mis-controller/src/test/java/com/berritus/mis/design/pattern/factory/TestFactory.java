package com.berritus.mis.design.pattern.factory;

public class TestFactory {
    public static void main(String[] args){
//        SenderFactory senderFactory = new SenderFactory();
//        Sender sender = senderFactory.produce("sms");
//        sender.send();

        //SenderFactory2 senderFactory2 = new SenderFactory2();
//        Sender sender1 = SenderFactory2.produceMail();
//        sender1.send();

        Provider provider = new MailFactory();
        Sender mailSender = provider.produce();
        mailSender.send();
    }
}
