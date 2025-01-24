package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Prodtion;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProdtionDto {
    private String Prod_ID;
    private String Pro_Name;
    private double Prod_Qty;
    private String Prod_Name;


    private ArrayList<InventroyDto> inventroyDTOS;
    private ArrayList<ProdMixDto> prodMixDTOS;
    private ArrayList<MatirialUsageDto> matirialUsageDTOS;




    public ProdtionDto(String prodID, String proName, double qty, String prodName) {
        this.Prod_ID = prodID;
        this.Pro_Name = proName;
        this.Prod_Qty = qty;
        this.Prod_Name = prodName;
    }


    public ProdtionDto(Prodtion prodtion) {
        this.Prod_ID = prodtion.getProd_ID();
        this.Pro_Name = prodtion.getPro_Name();
        this.Prod_Qty = prodtion.getProd_Qty();
        this.Prod_Name = prodtion.getProd_Name();
        this.inventroyDTOS = new ArrayList<>();
        this.prodMixDTOS = new ArrayList<>();
        this.matirialUsageDTOS = new ArrayList<>();

    }
}
