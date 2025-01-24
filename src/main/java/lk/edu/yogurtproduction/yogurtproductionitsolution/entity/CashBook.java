package lk.edu.yogurtproduction.yogurtproductionitsolution.entity;


import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString

public class CashBook {


    private String CBNo;
    private String SupId;
    private String matID;
    private String inID;
    private String desc;
    private int qty;
    private double amount;
    private String date;


    private ArrayList<Inventroy> inventroyDTOS;


    public CashBook(String CBNo, String SupId, String matID, String inID, String desc, int qty, double amount, String date) {
        this.CBNo = CBNo;
        this.SupId = SupId;
        this.matID = matID;
        this.inID = inID;
        this.desc = desc;
        this.qty = qty;
        this.amount = amount;
        this.date = date;


    }
}
