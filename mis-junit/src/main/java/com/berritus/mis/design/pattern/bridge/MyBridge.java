package com.berritus.mis.design.pattern.bridge;

/**
 * 通过对Bridge类的调用，实现了对接口Sourceable的实现类SourceOne和SourceTwo的调用
 */
public class MyBridge extends Bridge {
    @Override
    public void method(){
        getSourceable().method();
    }
}
