package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Supplier;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class SupplierDto {
    private String supId;
    private String supName;
    private String supNic;
    private String supEmail;
    private int supPhone;


    public SupplierDto(Supplier supplier) {
        this.supId = supplier.getSupId();
        this.supName = supplier.getSupName();
        this.supNic = supplier.getSupNic();
        this.supEmail = supplier.getSupEmail();
        this.supPhone = supplier.getSupPhone();

    }
}
