package com.berritus.mis.service.impl;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.dao.school.TbStudentMapper;
import com.berritus.mis.dubbo.api.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:49
 */
@Component
@Transactional
public class AbstractDemoService implements IDemoService {

	@Autowired
	private TbStudentMapper studentMapper;

	@Override
	public void method1() {
		System.out.println("这是method1通用方法实现");

		TbStudent student = new TbStudent();
		student.setAge(23);
		student.setStuName("aaaaaa");
		studentMapper.insert(student);
		int i = 1 /0;
	}

	@Override
	public void method2() {
		System.out.println("这是method2通用方法实现");
		TbStudent student = new TbStudent();
		student.setAge(23);
		student.setStuName("bbbbb");
		studentMapper.insert(student);
		int i = 1 /0;
	}
}
