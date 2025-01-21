package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import javafx.fxml.FXML;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {

    private String empId;
    private String empName;
    private String empNic;
    private String empEmail;
    private String empPhone;
}
