package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.MatirialUsage;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MatirialUsageDto {

    private  String MatUs_ID;
    private  String Prod_ID;
    private  String Mat_Milk;
    private  String Mat_Suguer;
    private  String Mat_Gelatin;

    public MatirialUsageDto(MatirialUsage matirialUsage) {
        MatUs_ID = matirialUsage.getMatUs_ID();
        Prod_ID = matirialUsage.getProd_ID();
        Mat_Milk = matirialUsage.getMat_Milk();
        Mat_Suguer = matirialUsage.getMat_Suguer();
        Mat_Gelatin = matirialUsage.getMat_Gelatin();

    }
}
