package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;
import lombok.*;
@Getter
@Setter
@ToString

public class Matirial {

    private String matId;
    private String matName;
    private int matQty;
    private int matPrice;

    public Matirial(String matId, String matName, int qty, int price) {
        this.matId = matId;
        this.matName = matName;
        this.matQty = qty;
        this.matPrice = price;

    }
}
