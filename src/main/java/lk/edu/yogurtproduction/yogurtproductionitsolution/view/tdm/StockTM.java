package lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockTM {
    private String Stock_ID;
    private String Pac_ID;
    private String Product_Name;
    private double Qty;
    private String Pack_Type;
    private String Manfac_date;
    private String Expire_date;

}
