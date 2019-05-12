package com.berritus.mis.design.pattern.builder;

/**
 * 为创建一个Product对象的各个部件指定抽象接口
 */
public interface Builder {
    Product buildProduct();
}
