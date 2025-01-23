package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Packing {

    private String Pac_ID;
    private String Prod_ID;
    private String Pac_Type;
    private String Pac_Desc;
    private String Pac_Date;
    private String Expire_Date;
    private double Qty;
    private  String Emp_ID;
    private double RedusQty;




    private ArrayList<Inventroy> inventroyDTOS;
    private ArrayList<Stock> stockDTOS;

}
