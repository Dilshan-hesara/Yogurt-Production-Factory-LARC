package lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CashBookTM {

    private String CBNo;
    private String SupId;
    private String date;
    private String desc;
    private int qty;
    private double amount;

}
