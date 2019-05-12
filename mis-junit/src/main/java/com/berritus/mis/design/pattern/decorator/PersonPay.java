package com.berritus.mis.design.pattern.decorator;

public class PersonPay implements PayService {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void pay() {
        System.out.println(name + " pay");
    }
}
