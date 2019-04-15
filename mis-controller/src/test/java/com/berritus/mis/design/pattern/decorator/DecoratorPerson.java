package com.berritus.mis.design.pattern.decorator;

/**
 * 装饰类 DecoratorPerson 实现 Component 接口
 */
public class DecoratorPerson implements PayService {
    private PayService payService;

    public void decoratorObj(PayService payService){
        this.payService = payService;
    }

    @Override
    public void pay() {
        if(payService != null){
            payService.pay();
        }
    }
}
