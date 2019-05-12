package com.berritus.mis.design.pattern.singleton;

public class TestSingleton {
    public static void main(String[] args){
        DemoSingleton demoSingleton = DemoSingleton.getInstance();
        DemoSingleton demoSingleton2 = DemoSingleton.getInstance();
        if(demoSingleton == demoSingleton2){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
    }
}
