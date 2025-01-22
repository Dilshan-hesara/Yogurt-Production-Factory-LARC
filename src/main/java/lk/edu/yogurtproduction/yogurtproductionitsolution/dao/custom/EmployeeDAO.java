package lk.edu.yogurtproduction.yogurtproductionitsolution.dao.custom;

import lk.edu.yogurtproduction.yogurtproductionitsolution.dao.CrudDAO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.EmployeeDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {

  //  public String getNextId() throws SQLException ;

    //public boolean saveEmpoyee(EmployeeDto employeeDto) throws SQLException ;

   // public boolean updateCustomer(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

   // public ArrayList<EmployeeDto> getAllEmployees() throws SQLException, ClassNotFoundException ;


   // public boolean deleteCustomer(String empId) throws SQLException ;

    public ArrayList<String> getAllEmpIds() throws SQLException ; // not unic


}
