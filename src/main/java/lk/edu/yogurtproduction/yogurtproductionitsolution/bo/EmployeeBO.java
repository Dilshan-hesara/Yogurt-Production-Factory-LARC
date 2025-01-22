package lk.edu.yogurtproduction.yogurtproductionitsolution.bo;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO{

    public String getNextId() throws SQLException;
    public boolean save(EmployeeDto employeeDto) throws SQLException ;


    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;
    public ArrayList<EmployeeDto> getAll() throws SQLException, ClassNotFoundException ;

    public boolean delete(String empId) throws SQLException ;
}
