package com.berritus.mis.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class ProcessInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(ProcessInterceptor.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        //生成日志ID
        String logId = UUID.randomUUID().toString();
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("logId", logId);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //Controller 的包名
        String className = handlerMethod.getBean().getClass().getName();
        //方法名称
        String methodName = method.getName();
        //完整请求地址
        String url = request.getRequestURL().toString();
        //请求的ip地址
        String ip = request.getRemoteHost();

        String uri = request.getRequestURI();
        String startTime = sdf.format(new Date());
        //StringBuilder sb = new StringBuilder();

        //InputStream is = request.getInputStream();
        //BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        //String instr;
//        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
//        for(MethodParameter methodParameter : methodParameters){
//            sb.append(methodParameter.getParameterName());
//        }

        //while(reader.readLine() != null){
            //instr = reader.readLine();
//            sb.append(instr);
        //}
//        Map<String, String[]> paramMap = request.getParameterMap();
//        for(Map.Entry<String, String[]> entry : paramMap.entrySet()){
//            sb.append("paramName : " + entry.getKey() + "- paramValue : " + request.getParameter(entry.getKey()));
//        }

        logger.warn(logId + "," + startTime + "," + className + "."
                + methodName + ",url:" + url + ",ip:" + ip);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        long beginTime = (long)request.getAttribute("beginTime");
        String logId = (String)request.getAttribute("logId");
        request.removeAttribute("beginTime");
        request.removeAttribute("logId");

        long endTime = System.currentTimeMillis();
        long handlingTime = endTime - beginTime;
        //System.out.println("handlingTime : " + handlingTime + " ms");
        logger.warn(logId + ",usingTime : " + handlingTime + " ms");
        request.setAttribute("usingTime", handlingTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws Exception {


    }

}
