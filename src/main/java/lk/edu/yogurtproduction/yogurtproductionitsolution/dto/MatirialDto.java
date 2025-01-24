package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Matirial;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MatirialDto {

    private String matId;
    private String matName;
    private int matQty;
    private int matPrice;

    public MatirialDto(Matirial matirial) {
        this.matId = matirial.getMatId();
        this.matName = matirial.getMatName();
        this.matQty = matirial.getMatQty();
        this.matPrice = matirial.getMatPrice();


    }
}
