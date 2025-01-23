package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Stock {

    private String Stock_ID;
    private String Pac_ID;
    private String Product_Name;
    private double Qty;
    private String Manfac_date;
    private String Expire_date;
    private String Pack_Type;
    private double Unit_Price;
}
