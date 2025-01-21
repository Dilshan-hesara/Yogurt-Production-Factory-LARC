package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import javafx.fxml.FXML;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {

        private String customerId;
        private String name;
        private String nic;
        private String email;
        private String phone;
    }



