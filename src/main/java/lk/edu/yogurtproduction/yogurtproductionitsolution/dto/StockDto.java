package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class StockDto {

    private String Stock_ID;
    private String Pac_ID;
    private String Product_Name;
    private double Qty;
    private String Manfac_date;
    private String Expire_date;
    private String Pack_Type;
    private double Unit_Price;
}
