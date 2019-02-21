package com.berritus.mis.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
//import com.google.common.base.Throwables;
import com.berritus.mis.core.bean.MisBean;
import com.berritus.mis.dao.school.TbStudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Activate
public class BlockDubboLogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(BlockDubboLogFilter.class);

    //@Autowired
    private TbStudentMapper studentMapper;

    public void setStudentMapper(TbStudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String logId = UUID.randomUUID().toString();
        long beginTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Properties prop = new Properties();
        Result result = null;

        try{
            InputStream in = BlockDubboLogFilter.class.getResourceAsStream("/dubbo.properties");
            Object[] objects = invocation.getArguments();
            String args = "";
            for(Object obj : objects){
                String objStr = "";
                if(obj instanceof MisBean){
                    objStr = JSON.toJSONString(obj);
                }else{
                    objStr = obj.toString();
                }

                if(StringUtils.isEmpty(args)){
                    args += objStr;
                }else{
                    args += objStr + "，";
                }
            }

            String ip = RpcContext.getContext().getRemoteHost();

            logger.warn(logId + "，ip：" + ip + "，" + simpleDateFormat.format(new Date())
                    + " 开始调用接口" + invocation.getMethodName() +
                    "，args：" + args);
            prop.load(in);
            String ipwhiteList = prop.getProperty("ipwhitelist");
            if(!ipwhiteList.contains(ip)){
                logger.warn("地址：" + ip + "禁止访问");
                return new RpcResult();
            }

            result = invoker.invoke(invocation);
            if(result.hasException()){
                //logger.error(Throwables.getStackTraceAsString(result.getException()));
            }else{
                if(result.getValue() != null){
                    String strMsg = JSON.toJSONString(result.getValue());
                    logger.warn(logId + "，返回数据：" + strMsg);
                }
            }
        }catch (RuntimeException e){
            logger.error(logId + "，调用接口：" + invocation.getMethodName() + "出现异常，" + e.toString());
        }catch (Exception e){
            logger.error(logId + "，调用接口：" + invocation.getMethodName() + "出现异常，" + e.toString());
        } finally{
            logger.warn(logId + "，" + simpleDateFormat.format(new Date()) + " 结束调用接口" + invocation.getMethodName());
            long endTime = System.currentTimeMillis();
            logger.warn(logId + "，调用时间花费：" + (endTime - beginTime) + "ms，接口：" + invocation.getMethodName());
        }
        return result;
    }
}
