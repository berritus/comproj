package com.berritus.mis.service.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/20
 */
public class DispatchBaseService implements InvocationHandler {

    private static int num = 0;

    private Object target;

    public DispatchBaseService(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        num++;
        System.out.println("begin ======================================= num = " + num);
        Object object = method.invoke(target, args);
        return object;
    }
}
