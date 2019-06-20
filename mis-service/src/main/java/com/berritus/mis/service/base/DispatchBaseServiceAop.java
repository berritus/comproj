package com.berritus.mis.service.base;

import com.berritus.mis.core.component.SpringContextUtil;
import com.berritus.mis.dubbo.api.IMsgSendService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/20
 */
@Aspect
@Component
public class DispatchBaseServiceAop {

    @Autowired
    private SpringContextUtil springContextUtil;

    //@Pointcut(" execution(public * com.berritus.mis.service.base.*Service.*(..)) and args(..) ")
    private void aspectService() {

    }

    //@Before(value="aspectService()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        System.out.println("999999999999999999999999999999999");

        //MsgSendBaseService msgSendBaseService = new MsgSendBaseService();
        Type types = joinPoint.getTarget().getClass().getGenericSuperclass();
        Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
        String interfaceName = "";
        for (Type t : genericType) {
            interfaceName = t.getTypeName();
            System.out.println(t.getTypeName());
        }

        Class clazz = BaseServiceEngine.class.getClassLoader().loadClass(interfaceName);
        IMsgSendService serviceComponent = (IMsgSendService)springContextUtil.getBean(clazz);

        Method[] methods = serviceComponent.getClass().getMethods();

        System.out.println("666666666666");
    }


}
