package com.berritus.mis.service.impl;

import com.berritus.mis.dubbo.api.IFlowService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-16 14:45
 */
@Primary
@Component
@Transactional
public class AbstractFlowService implements IFlowService {

	@Override
	public void method3() {
		System.out.println("AbstractFlowService method3");
	}
}
