package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;

import lombok.*;


@Getter
@Setter
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

    public Stock(String string, String string1, String string2, double aDouble, String string3, String string4, String string5, double aDouble1) {
        this.Stock_ID = string;
        this.Pac_ID = string1;
        this.Product_Name = string2;
        this.Qty = aDouble;
        this.Manfac_date = string3;
        this.Expire_date = string4;
        this.Pack_Type = string5;
        this.Unit_Price = aDouble1;


    }
}
