package com.berritus.mis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:50
 */
@Service("msgSendServiceSg")
@Transactional
public class MsgSendServiceSgImpl extends AbstractMsgSendService {

	@Override
	public void sendSms(String msg) {
		System.out.println("这是现场 SG sendSms方法实现，" + msg);
	}
}
