package com.berritus.mis.design.pattern.builder;

/**
 * 复杂对象Product建造者
 */
public class ProductBuilder implements Builder {
    private PartOne partOne;
    private PartTwo partTwo;

    public void builderOne(){
        partOne = new PartOne("partOne", "pay");
    }

    public void builderTwo(){
        partTwo = new PartTwo("partTwo", "sava");
    }

    @Override
    public Product buildProduct() {
        Product product = new Product();
        product.setPartOne(partOne);
        product.setPartTwo(partTwo);
        return product;
    }

}
