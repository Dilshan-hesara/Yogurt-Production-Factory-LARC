package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Resipe;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProdMixDto {
    private String prodName;
    private int milk;
    private int suguer;
    private int jeliy;

    public ProdMixDto(Resipe resipe) {
        this.prodName = resipe.getProdName();
        this.milk = resipe.getMilk();
        this.suguer = resipe.getSuguer();
        this.jeliy = resipe.getJeliy();

    }
}
