package com.berritus.mis.design.pattern.singleton;

/**
 * 单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，
 * 这个类的加载过程是线程互斥的。这样当我们第一次调用getInstance的时候，
 * JVM能够帮我们保证instance只被创建一次
 * 优点：延迟加载，线程安全（java中class加载时互斥的），也减少了内存消耗，推荐使用内部类方式。
 */
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
