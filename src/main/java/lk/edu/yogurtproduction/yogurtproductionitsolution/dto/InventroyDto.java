package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Inventroy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InventroyDto {

    private String id;
    private String itemType;
    private String itemDescription;
    private String qty;
    private String prodId;


    public InventroyDto(Inventroy inventroy) {
        this.id = inventroy.getId();
        this.itemType = inventroy.getItemType();
        this.itemDescription = inventroy.getItemDescription();
        this.qty = inventroy.getQty();
        this.prodId = inventroy.getProdId();

    }
}
