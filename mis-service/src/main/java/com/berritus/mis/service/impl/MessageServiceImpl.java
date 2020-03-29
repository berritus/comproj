package com.berritus.mis.service.impl;

import com.alibaba.fastjson.JSON;
import com.berritus.mis.bean.demo.MeetingRoomApplyDTO;
import com.berritus.mis.bean.demo.MeetingRoomApplyExt;
import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.core.rabbitmq.utils.RabbitMQUtil;
import com.berritus.mis.dao.demo.MeetingRoomApplyDao;
import com.berritus.mis.service.DemoService;
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
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
	//@Qualifier("msgRabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	//@Autowired
	//private MeetingRoomApplyDao meetingRoomApplyDao;
	@Autowired
	private DemoServiceImpl demoService;


	@Override
	public int sendConfirmMsg(String requestMsg) {
//		Message message = MessageBuilder.withBody(requestMsg.getBytes())
//				.setContentType(MessageProperties.CONTENT_TYPE_JSON)
//				.setCorrelationId("aaaaaa1232").build();
//
//		CorrelationData correlationData = new CorrelationData("aaaaaa1232");
//		rabbitTemplate.convertAndSend(ExChangeConfig.ORDER_EX_CHANGE,
//				RabbitMQConfig.ORDER_ROUTING_KEY1, message, correlationData);

		return 0;
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED)
	public int sendMsgTest() {
		demoService.sendMsgTest("MIS_TEST_DB");
		return 0;
	}

	@Override
	//@Transactional(propagation = Propagation.REQUIRED)
	//@Transactional(transactionManager = "xatx")
	//@Transactional
	public int dynamicTest(String sysCode) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		MeetingRoomApplyExt meetingRoomApplyDTO = new MeetingRoomApplyExt();
		meetingRoomApplyDTO.setApplyId(uuid);
		meetingRoomApplyDTO.setApplicationCode("MD_TEST_MEET");

		uuid = UUID.randomUUID().toString().replace("-", "");
		meetingRoomApplyDTO.setRoomId(uuid);
		meetingRoomApplyDTO.setCreator("oa");
		meetingRoomApplyDTO.setState((byte)1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = sdf.format(new Date());
		meetingRoomApplyDTO.setCrtDateStr(strDate);
		meetingRoomApplyDTO.setStartDateStr(strDate);
		meetingRoomApplyDTO.setEndDateStr(strDate);
		meetingRoomApplyDTO.setModifyDateStr(strDate);
		//meetingRoomApplyDao.insert(meetingRoomApplyDTO);
		demoService.dynamicTest(meetingRoomApplyDTO, "MIS_TEST_DB");
		demoService.dynamicTest(meetingRoomApplyDTO, "MIS_TEST_DB2");
		//int i = 10 /0;
		return 0;
	}

	// direct
	//@RabbitListener(queues = {"test_queue"})
	//@RabbitHandler
	public void handleMsg(Message message, Channel channel) throws IOException {
		try{
            //String cust = JSON.parseObject(message.getBody(), String.class);
			MeetingRoomApplyExt order = JSON.parseObject(message.getBody(), MeetingRoomApplyExt.class);
            //String str = message.getBody().toString();
			logger.info("handleCustMsg，{}", order.toString());
		}catch (Exception e){
			logger.error("处理失败，" + e.getMessage());
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
		}
	}

	// fanout
//	@RabbitListener(queues = "test_queue3")
//	@RabbitHandler
//	public void handleFanoutMsg3(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg3，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}
//
//	// fanout
//	@RabbitListener(queues = "test_queue4")
//	@RabbitHandler
//	public void handleFanoutMsg4(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg4，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}
//
//	// fanout
//	@RabbitListener(queues = "test_queue5")
//	@RabbitHandler
//	public void handleFanoutMsg5(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg5，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}
//
//
//	// topic
//	@RabbitListener(queues = "test_queue6")
//	@RabbitHandler
//	public void handleFanoutMsg6(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg6，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}
//
//	// topic
//	@RabbitListener(queues = "test_queue7")
//	@RabbitHandler
//	public void handleFanoutMsg7(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg7，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}
//
//
//	// topic
//	@RabbitListener(queues = "test_queue8")
//	@RabbitHandler
//	public void handleFanoutMsg8(Message message, Channel channel) throws IOException {
//		try{
//			//String cust = JSON.parseObject(message.getBody(), String.class);
//			TbStudent order = JSON.parseObject(message.getBody(), TbStudent.class);
//			//String str = message.getBody().toString();
//			logger.info("handleFanoutMsg8，{}", order.toString());
//		}catch (Exception e){
//			logger.error("处理失败，" + e.getMessage());
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
//		}
//	}

}
