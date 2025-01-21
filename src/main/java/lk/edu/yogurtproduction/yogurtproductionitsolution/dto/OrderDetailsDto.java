package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsDto {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}