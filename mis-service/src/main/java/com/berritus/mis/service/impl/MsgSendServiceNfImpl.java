package com.berritus.mis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:52
 */
@Service("msgSendServiceNf")
@Transactional
public class MsgSendServiceNfImpl extends AbstractMsgSendService {
	@Override
	public void sendSms(String msg) {
		System.out.println("这是现场 Nf sendSms方法实现" + msg);
	}
}
