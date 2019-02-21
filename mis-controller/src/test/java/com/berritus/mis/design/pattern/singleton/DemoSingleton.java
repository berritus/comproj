package com.berritus.mis.design.pattern.singleton;

public class DemoSingleton {
    /* 私有构造方法，防止被实例化 */
    private DemoSingleton(){
    }

    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory{
        private static DemoSingleton instance = new DemoSingleton();
    }

    /* 获取实例 */
    public static DemoSingleton getInstance(){
        return SingletonFactory.instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    private Object readResolve(){
        return getInstance();
    }
}
