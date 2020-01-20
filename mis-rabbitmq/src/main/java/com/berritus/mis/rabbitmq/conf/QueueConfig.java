//package com.berritus.mis.rabbitmq.conf;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class QueueConfig {
//    public static final String ORDER_QUEUE_ONE = "order_queue1";
//    public static final String ORDER_QUEUE_TWO = "order_queue2";
//    public static final String STR_MSG_QUEUE_THIRD = "str_msg_queue3";
//
//    /**
//     durable="true" 持久化消息队列 ， rabbitmq重启的时候不需要创建新的队列
//     auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
//     exclusive  表示该消息队列是否只在当前connection生效,默认是false
//     */
//    //@Bean
//    public Queue orderQueue1(){
//        return new Queue(ORDER_QUEUE_ONE, true, false, false);
//    }
//
//    //@Bean
//    public Queue orderQueue2(){
//        return new Queue(ORDER_QUEUE_TWO, true, false, false);
//    }
//
//    //@Bean
//    public Queue strMsgQueue(){
//        Map<String, Object> param = new HashMap<>();
//        param.put("x-message-ttl", 60000);//60秒自动删除
//        return new Queue(STR_MSG_QUEUE_THIRD, true, false, true, param);
//    }
//}
