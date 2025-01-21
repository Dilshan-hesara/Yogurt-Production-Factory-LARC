package lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PRODStockTM {

    private String productId;
    private String packageType;
    private String description;
    private double quantity;
}
