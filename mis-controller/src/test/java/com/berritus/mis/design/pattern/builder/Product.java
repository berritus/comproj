package com.berritus.mis.design.pattern.builder;

/**
 * 表示被构造的复杂对象
 */
public class Product {
    private PartOne partOne;
    private PartTwo partTwo;

    public PartOne getPartOne() {
        return partOne;
    }

    public void setPartOne(PartOne partOne) {
        this.partOne = partOne;
    }

    public PartTwo getPartTwo() {
        return partTwo;
    }

    public void setPartTwo(PartTwo partTwo) {
        this.partTwo = partTwo;
    }
}
