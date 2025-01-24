package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.PckingDto;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
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

    public Packing(PckingDto pckingDtos) {
        Pac_ID = pckingDtos.getPac_ID();
        Prod_ID = pckingDtos.getProd_ID();
        Pac_Type = pckingDtos.getPac_Type();
        Pac_Desc = pckingDtos.getPac_Desc();
        Pac_Date = pckingDtos.getPac_Date();
        Expire_Date = pckingDtos.getExpire_Date();
        Qty = pckingDtos.getQty();
        Emp_ID = pckingDtos.getEmp_ID();
        RedusQty = pckingDtos.getRedusQty();
        inventroyDTOS = new ArrayList<>();
        stockDTOS = new ArrayList<>();

    }
}
