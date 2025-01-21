package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PckingDto {

    private String Pac_ID;
    private String Prod_ID;
    private String Pac_Type;
    private String Pac_Desc;
    private String Pac_Date;
    private String Expire_Date;
    private double Qty;
    private  String Emp_ID;
    private double RedusQty;




    private ArrayList<InventroyDto> inventroyDTOS;
    private ArrayList<StockDto> stockDTOS;

}
