package com.berritus.mis.service.impl;

import com.alibaba.fastjson.JSON;
import com.berritus.mis.rabbitmq.conf.ExChangeConfig;
import com.berritus.mis.rabbitmq.conf.QueueConfig;
import com.berritus.mis.rabbitmq.conf.RabbitMQConfig;
import com.berritus.mis.service.MessageService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-01-17
 */
@Service
public class MessageServiceImpl implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	@Qualifier("msgRabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	@Override
	public int sendConfirmMsg(String requestMsg) {
		Message message = MessageBuilder.withBody(requestMsg.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON)
				.setCorrelationId("aaaaaa1232").build();

		CorrelationData correlationData = new CorrelationData("aaaaaa1232");
		rabbitTemplate.convertAndSend(ExChangeConfig.ORDER_EX_CHANGE,
				RabbitMQConfig.ORDER_ROUTING_KEY1, message, correlationData);

		return 0;
	}

	@RabbitListener(queues = QueueConfig.ORDER_QUEUE_ONE)
	@RabbitHandler
	public void handleCustMsg(Message message, Channel channel) throws IOException {
		try{
            //String cust = JSON.parseObject(message.getBody(), String.class);
            String str = message.getBody().toString();
			logger.info("handleCustMsg，{}", str);
		}catch (Exception e){
			logger.error("处理失败，" + e.getMessage());
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
		}
	}
}
