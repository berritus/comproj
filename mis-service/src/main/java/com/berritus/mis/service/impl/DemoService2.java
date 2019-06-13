package com.berritus.mis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:52
 */
@Service("demoService2")
@Transactional
public class DemoService2 extends AbstractDemoService {
	@Override
	public void method1() {
		System.out.println("这是现场2 method1方法实现");
	}
}
