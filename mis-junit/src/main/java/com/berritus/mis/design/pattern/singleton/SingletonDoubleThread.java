package com.berritus.mis.design.pattern.singleton;

/**
 * 优点：延迟加载，线程安全
 * 缺点：写法复杂，不简洁
 */
public class SingletonDoubleThread {
    private static SingletonDoubleThread instance = null;

    /* 私有构造方法，防止被实例化 */
    private SingletonDoubleThread(){}

    public static SingletonDoubleThread getInstance(){
        if(instance == null){
            synchronized (instance){
                if(instance == null){
                    instance = new SingletonDoubleThread();
                }
            }
        }

        return instance;
    }

}
