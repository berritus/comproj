package com.berritus.mis.dubbo.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate
public class AuthFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String ip = RpcContext.getContext().getRemoteHost();
        logger.warn("access ip " + ip);
        if("192.168.30.119".equals(ip)){
            //return new RpcResult();
        }
        return invoker.invoke(invocation);
    }
}
