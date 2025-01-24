package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Stock;
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

    public StockDto(Stock stock) {
        this.Stock_ID = stock.getStock_ID();
        this.Pac_ID = stock.getPac_ID();
        this.Product_Name = stock.getProduct_Name();
        this.Qty = stock.getQty();
        this.Manfac_date = stock.getManfac_date();
        this.Expire_date = stock.getExpire_date();
        this.Pack_Type = stock.getPack_Type();
        this.Unit_Price = stock.getUnit_Price();

    }
}
