package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Prodtion {
    private String Prod_ID;
    private String Pro_Name;
    private double Prod_Qty;
    private String Prod_Name;


    private ArrayList<Inventroy> inventroyDTOS;
    private ArrayList<ProdMix> prodMixDTOS;
    private ArrayList<MatirialUsage> matirialUsageDTOS;




    public Prodtion(String prodID, String proName, double qty, String prodName) {
        this.Prod_ID = prodID;
        this.Pro_Name = proName;
        this.Prod_Qty = qty;
        this.Prod_Name = prodName;
    }

}
