package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

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


}
