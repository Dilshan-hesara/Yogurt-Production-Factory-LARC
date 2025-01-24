package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;

import lombok.*;

@Getter
@Setter
@ToString

public class Inventroy {

    private String id;
    private String itemType;
    private String itemDescription;
    private String qty;
    private String prodId;


    public Inventroy(String string, String string1, String string2, String string3, String string4) {
        id = string;
        itemType = string1;
        itemDescription = string2;
        qty = string3;
        prodId = string4;


    }
}
