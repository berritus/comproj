package com.berritus.mis.design.pattern.proxy;

public class ProxySource implements Sourceable {
    private Source source;

    public ProxySource(){
        super();
        this.source = new Source();
    }

    @Override
    public void method1() {
        before();
        source.method1();
        after();
    }

    private void before(){
        System.out.println("ProxySource before");
    }

    private void after(){
        System.out.println("ProxySource after");
    }
}
