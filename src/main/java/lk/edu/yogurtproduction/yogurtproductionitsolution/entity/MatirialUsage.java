package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.MatirialUsageDto;
import lombok.*;
@Getter
@Setter
@ToString

public class MatirialUsage {

    private  String MatUs_ID;
    private  String Prod_ID;
    private  String Mat_Milk;
    private  String Mat_Suguer;
    private  String Mat_Gelatin;

    public MatirialUsage(String string, String string1, String string2, String string3, String string4) {
        MatUs_ID = string;
        Prod_ID = string1;
        Mat_Milk = string2;
        Mat_Suguer = string3;
        Mat_Gelatin = string4;

    }



}
