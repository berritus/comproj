package com.berritus.mis.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.berritus.mis.controller.annotation.MisLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Component
public class SysLogService {
    private Logger logger = LoggerFactory.getLogger(SysLogService.class);

    //切点为SysLogger注解
    @Pointcut("@annotation(com.berritus.mis.controller.annotation.MisLogger)")
    public void logPointCut(){

    }

    @Before("logPointCut()")
    public void savaLogInfo(JoinPoint joinPoint){
        String baseInfo = getLogBaseInfo(joinPoint);
        logger.warn(baseInfo);
    }

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        return null;
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void AfterThrowing(JoinPoint joinPoint, Exception e){
        try{
            String baseInfo = getLogBaseInfo(joinPoint);
//            String action = "";
//            MisLogger misLogger = method.getAnnotation(MisLogger.class);
//            if(misLogger != null){
//                action = misLogger.value();
//            }

            String errMsg = e.getMessage();
            logger.error("exception occur in " + baseInfo + ",errMsg:" + errMsg);
            e.printStackTrace();
        }catch (Exception ee){
            ee.printStackTrace();
            String errMsg = e.getMessage();
            logger.error("exception " + errMsg);
        }
    }

    private String getLogBaseInfo(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        Object args[] = joinPoint.getArgs();
        String param = "";
        for(Object obj : args){
            if(obj instanceof HttpServletRequest || obj instanceof HttpServletResponse){
                continue;
            }
            param += JSON.toJSONString(obj);
        }

        return className + "." + methodName + ",args:" + param;
    }
}
