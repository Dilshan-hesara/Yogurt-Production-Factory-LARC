package lk.edu.yogurtproduction.yogurtproductionitsolution.dto;

import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;
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


    public EmployeeDto(Employee employee) {
        this.empId = employee.getEmpId();
        this.empName = employee.getEmpName();
        this.empNic = employee.getEmpNic();
        this.empEmail = employee.getEmpEmail();
        this.empPhone = employee.getEmpPhone();


    }
}
