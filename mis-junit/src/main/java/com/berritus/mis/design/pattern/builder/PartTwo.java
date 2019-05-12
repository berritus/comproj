package com.berritus.mis.design.pattern.builder;

public class PartTwo {
    private String partName;
    private String function;

    public PartTwo(String partName, String function){
        this.partName = partName;
        this.function = function;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
