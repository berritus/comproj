package com.berritus.mis.rabbitmq.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
     * 消息将会转发给queue参数指定的消息队列
     */
    public static final String ORDER_ROUTING_KEY1 = "order_routing_key1";
    public static final String ORDER_ROUTING_KEY2 = "order_routing_key2";
    public static final String MSG_ROUTING_KEY = "msg_routing_key";

    @Autowired
    private ExChangeConfig exChangeConfig;
    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private RabbitTemplate.ConfirmCallback confirmCallback;
    @Autowired
    private RabbitTemplate.ReturnCallback returnCallback;

    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(queueConfig.orderQueue1()).to(exChangeConfig.orderExChange()).with(ORDER_ROUTING_KEY1);
    }

    @Bean
    public Binding orderBingding2(){
        return BindingBuilder.bind(queueConfig.orderQueue2()).to(exChangeConfig.orderExChange()).with(ORDER_ROUTING_KEY2);
    }

    @Bean
    public Binding msgBingding2(){
        return BindingBuilder.bind(queueConfig.strMsgQueue()).to(exChangeConfig.msgExChange()).with(MSG_ROUTING_KEY);
    }

    @Bean("msgRabbitTemplate")
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}
