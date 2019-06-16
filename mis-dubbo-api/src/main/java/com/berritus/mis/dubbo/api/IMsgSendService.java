package com.berritus.mis.dubbo.api;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2019-06-13 11:48
 */
public interface IMsgSendService{

	void sendSms(String msg);

	void sendEmail(String msg);
}
