//package com.berritus.mis.rabbitmq.component;
//
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
//    /**
//     * 当消息发送到交换机（exchange）时，该方法被调用.
//     * 1.如果消息没有到exchange,则 ack=false
//     * 2.如果消息到达exchange,则 ack=true
//     */
//    @Override
//    public void confirm(CorrelationData correlationData, boolean b, String s) {
//        System.out.println("correlationData : " + correlationData);
//        if(b){
//            System.out.println("correlationData success : " + s);
//        }else{
//            System.out.println("correlationData failed : " + s);
//        }
//    }
//}
