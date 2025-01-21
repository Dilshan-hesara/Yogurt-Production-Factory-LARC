package lk.edu.yogurtproduction.yogurtproductionitsolution.dto.TM;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventryTM {
    private String id;
    private String itemType;
    private String itemDescription;
    private String qty;
    private String prodId;
}
