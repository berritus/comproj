package com.berritus.mis.service.base;

/**
 * @Description: 服务基本引擎，用于协调服务调用
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/16
 */
public class BaseServiceEngine {
    private BaseServiceEngine() {
    }

    private static class BaseServiceEngineFactory {
        private static BaseServiceEngine baseServiceEngine = new BaseServiceEngine();
    }

    public static BaseServiceEngine getInstance() {
        return BaseServiceEngineFactory.baseServiceEngine;
    }

    private Object readResolve(){
        return getInstance();
    }


}
