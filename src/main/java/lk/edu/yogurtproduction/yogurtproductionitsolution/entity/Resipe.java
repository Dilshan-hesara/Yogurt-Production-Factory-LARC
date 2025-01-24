package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;

import lombok.*;

@Getter
@Setter
@ToString

public class Resipe {
    private String prodName;
    private int milk;
    private int suguer;
    private int jeliy;

    public Resipe(String string, int anInt, int anInt1, int anInt2) {
        prodName = string;
        milk = anInt;
        suguer = anInt1;
        jeliy = anInt2;

    }
}
