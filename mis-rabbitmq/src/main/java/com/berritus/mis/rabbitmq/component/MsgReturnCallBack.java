package com.berritus.mis.rabbitmq.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MsgReturnCallBack implements RabbitTemplate.ReturnCallback {
    /**
     * 当消息从交换机到队列失败时，该方法被调用。（若成功，则不调用）
     * 需要注意的是：该方法调用后，{@link MsgSendConfirmCallBack}中的confirm方法也会被调用，且ack = true
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("message fail : " + message);
    }
}
