package com.berritus.mis.design.pattern.singleton;

/**
 * @Description: 利用枚举的特性，让JVM来帮我们保证线程安全和单一实例的问题
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2020/3/31
 */
public class EnumSingleton {
    public static EnumSingleton getInstance(){
        return Enum.INSTANCE.getInstance();
    }

    private enum Enum {
        INSTANCE;
        private EnumSingleton singleton;
        //关键，使用枚举类的构造方法创建对象实例
        Enum(){
            singleton = new EnumSingleton();
        }

        public EnumSingleton getInstance(){
            return this.singleton;
        }
    }
}
