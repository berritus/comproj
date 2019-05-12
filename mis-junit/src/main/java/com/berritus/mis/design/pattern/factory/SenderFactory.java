package com.berritus.mis.design.pattern.factory;

public class SenderFactory {
    public Sender produce(String type){
        if("mail".equals(type)){
            return new MailSender();
        }else if ("sms".equals(type)){
            return new SmsSender();
        }else{
            return null;
        }
    }
}
