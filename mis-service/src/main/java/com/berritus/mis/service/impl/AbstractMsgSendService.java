package com.berritus.mis.service.impl;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.IMsgSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:49
 */
@Primary
@Component
@Transactional
public class AbstractMsgSendService implements IMsgSendService {

	@Autowired
	private TbStudentMapper studentMapper;

	@Override
	public void sendSms(String msg) {
		System.out.println("这是sendSms通用方法实现，" + msg);

		TbStudent student = new TbStudent();
		student.setAge(23);
		student.setStuName("aaaaaa");
		studentMapper.insert(student);
		int i = 1 /0;
	}

	@Override
	public void sendEmail(String msg) {
		System.out.println("这是sendEmail通用方法实现，" + msg);
		TbStudent student = new TbStudent();
		student.setAge(23);
		student.setStuName("bbbbb");
		studentMapper.insert(student);
		int i = 1 /0;
	}
}
